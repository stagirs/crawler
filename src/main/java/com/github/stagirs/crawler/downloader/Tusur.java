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
public class Tusur extends Downloader {

    @Override
    public String getName() {
        return "Доклады Томского государственного университета систем управления и радиоэлектроники";
    }

    @Override
    public String getUrl() {
        return "https://journal.tusur.ru";
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

        Document doc = Jsoup.connect("https://journal.tusur.ru/ru/arhiv").get();
        for (Element el : doc.select(".col-lg-9.col-md-9.col-sm-8.col-xs-12.left-side a[href*=arhiv]")) {
            String href = el.attr("href");
            releases.add(el.attr("href"));
        }

        return releases;
    }

     private List<Record> releaseDownload(String releaseName) throws IOException, InterruptedException {
        List<Record> releases = new ArrayList<>();
        Document doc1 = Jsoup.connect(getUrl() + releaseName).get();
        for (Element el1 : doc1.select(".col-lg-9.col-md-9.col-sm-8.col-xs-12.left-side").select(".content a[href*=arhiv]")) {
            String href = el1.attr("href");
            releases.add(getRecord(getUrl() + href));
        }


        return releases;
    }

     protected Record getRecord(String url) throws IOException {
          Record record = new Record();
          record.setType(Record.Type.PAPER);
          Document doc2 = Jsoup.connect(url).userAgent("Chrome/64.0.3282.167 ").get();
          ArrayList<String> authors = new ArrayList<String>();
          List<Author> authorlist = new ArrayList<>();
          record.setTitle(doc2.select("h1").text());

          for (Element el3 : doc2.select(".content a[href*=authors]")) {
              String[] temp1 = el3.text().trim().split(" ");
              Author au = new Author(temp1[0], temp1[1]);
              authorlist.add(au);
          }
          record.setAuthors(authorlist);
          record.setUrl(doc2.select(".content a[href*=/storage/]").attr("href"));
          return record;

    }
}