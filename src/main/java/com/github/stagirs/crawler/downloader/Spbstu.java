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
public class Spbstu extends Downloader {

    @Override
    public String getName() {
        return "Научно-технические ведомости СПбГПУ. Физико-математические науки";
    }

    @Override
    public String getUrl() {
        return "http://ntv.spbstu.ru";
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
        Document doc = Jsoup.connect("http://ntv.spbstu.ru/physics/archive/").userAgent("Chrome/64.0.3282.167").get();
        for (Element el : doc.select(".magazineArchive__listItemTitle a")) {
            if ((el.attr("href").split("/")[3].contains("2008")) || (el.attr("href").split("/")[3].contains("2009"))
                    || (el.attr("href").split("/")[3].contains("2010")) || (el.attr("href").split("/")[3].contains("2011"))
                    || (el.attr("href").split("/")[3].contains("2012"))) {
                continue;
            }
            String href = el.attr("href");
            Document doc1 = Jsoup.connect(getUrl() + href).userAgent("Chrome/64.0.3282.167 ").get();
            for (Element el1 : doc1.select(".magazineArchive__listItemTitle a")) {
                href = el1.attr("href");
                System.out.println(href);
                releases.add(el.attr("href"));
            }
        }
        return releases;
    }

    private List<Record> releaseDownload(String releaseName) throws IOException, InterruptedException {
        List<Record> releases = new ArrayList<>();
        String href;
        Document doc3=Jsoup.connect(getUrl() + releaseName).userAgent("Chrome/64.0.3282.167 ").get();
        for (Element el2 : doc3.select(".articlesList__articleTitle a")) {
            href = el2.attr("href");
            releases.add(getRecord(getUrl()+href));
        }

        return releases;
    }

    protected Record getRecord(String url) throws IOException{
        Record record = new Record();
        record.setType(Record.Type.PAPER);
        ArrayList<String> authors= new ArrayList<String>();
        List<Author>authorlist = new ArrayList<>();
        Document doc2=Jsoup.connect(url).userAgent("Chrome/64.0.3282.167 ").get();
        record.setUrl(getUrl() + doc2.select(".articlePage__download").select("a[href*=fulltext]").attr("href"));
        record.setTitle(doc2.select("h1").text());//title
        record.setAnnotation(doc2.select("div.articlePage__annotaton").text());
        for (Element el6 : doc2.select(".articlePage__authorLink")) {
            String[] temp = el6.text().split(" ");
            Author au = new Author(temp[0],temp[1]);
            authorlist.add(au);
        }
        record.setAuthors(authorlist);
        return record;
    }

}