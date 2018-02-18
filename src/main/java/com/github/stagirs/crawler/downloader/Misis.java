package com.github.stagirs.crawler.downloader;

import com.github.stagirs.crawler.Downloader;
import com.github.stagirs.crawler.model.business.Author;
import com.github.stagirs.crawler.model.business.Record;
import com.github.stagirs.crawler.model.service.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Misis extends Downloader {

    @Override
    public String getName() {
        return "Известия высших учебных заведений. Материалы электронной техники";
    }

    @Override
    public String getUrl() {
        return "http://met.misis.ru/";
    }

    @Override
    public void process(Session session) throws IOException, InterruptedException {
        for (String release : getAvailableReleases()) {
            if(!isNewRelease(release)){
                continue;
            }
            for(Record record : releaseDownload(release)){
                save(session, record);
            }
            save(release);
        }
    }


     private List<String> getAvailableReleases() throws IOException {
        List<String> releases = new ArrayList<>();

        Document doc = Jsoup.connect("http://met.misis.ru/jour/issue/archive").get();
        for (Element el : doc.select("h4 a[href*=/jour/issue]")) {
            String href = el.attr("href");
            releases.add(el.attr("href"));
        }

        return releases;
    }

     private List<Record> releaseDownload(String releaseName) throws IOException, InterruptedException {
        List<Record> releases = new ArrayList<>();
        Document doc1 = Jsoup.connect(releaseName).get();
        for (Element el1 : doc1.select(".tocTitle a")) {
            String href = el1.attr("href");
            releases.add(getRecord(href));
        }
        return releases;
    }
       protected Record getRecord(String url) throws IOException{
        Record record = new Record();
        record.setType(Record.Type.PAPER);
        Document doc2=Jsoup.connect(url).get();
        ArrayList<String> authors = new ArrayList<String>();
        List<Author> authorlist = new ArrayList<>();
        record.setTitle(doc2.select("h1").text());
        for (Element el7: doc2.select(".tab-wrap")){
            String a = el7.text().split("Аннотация")[1].split("Ключ. слова")[0];//anno
            if (a.isEmpty()){
                record.setAnnotation(" ");
            } else {
                record.setAnnotation(a.trim());
            }

            break;
        }
        for (Element el3 : doc2.select("#authorString a")){
               String[] temp1 = el3.text().trim().split(" ");
               Author au = new Author(temp1[temp1.length - 1],temp1[0]);
               authorlist.add(au);
        }
        record.setAuthors(authorlist);
        record.setUrl(doc2.select("#articleFullText a").attr("href"));
        return record;
    }
}