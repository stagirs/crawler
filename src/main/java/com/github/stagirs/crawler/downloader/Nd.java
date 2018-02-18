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
public class Nd extends Downloader {

    @Override
    public String getName() {
        return "Нелинейная динамика";
    }

    @Override
    public String getUrl() {
        return "http://nd.ics.org.ru";
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

        Document doc = Jsoup.connect("http://nd.ics.org.ru/archive_nd").get();
        for (Element el : doc.select(".arch.items ").select("a[href*=/archive_nd]")) {
            String href = el.attr("href");
            releases.add(el.attr("href"));
        }

        return releases;
    }

    private List<Record> releaseDownload(String releaseName) throws IOException, InterruptedException {
        List<Record> releases = new ArrayList<>();
        Document doc1 = Jsoup.connect(getUrl()+releaseName).get();
        for (Element el1 : doc1.select(".list-journal-item-name a")) {
            String href = el1.attr("href");
            releases.add(getRecord(getUrl() + href));
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
        for (Element el7: doc2.select(".item-description")){
            if (el7.text().isEmpty()){
                record.setAnnotation(" ");
            } else {
                record.setAnnotation(el7.text().split("Ключевые слова")[0]);
            }

            break;
        }
        for (Element el3 : doc2.select(".list-journal-item a[href*=authors_nd]")){

            String temp = el3.text().replace(" ","").replace("\u00a0"," ");
            String[] temp1 = temp.trim().split(" ");
            Author au = new Author(temp1[0],temp1[1]);
            authorlist.add(au);
        }
        record.setAuthors(authorlist);
        record.setUrl(getUrl() + doc2.select("a[href*=/upload/]").attr("href"));
        return record;
    }


}