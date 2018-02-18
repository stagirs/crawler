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
public class Pdi extends Downloader {

    @Override
    public String getName() {
        return "Прикладная Нелинейная Динамика";
    }

    @Override
    public String getUrl() {
        return "http://andjournal.sgu.ru";
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

        Document doc = Jsoup.connect("http://andjournal.sgu.ru/ru").get();
        for (Element el : doc.select(".views-field.views-field-field-image").select(".field-content a")) {
            releases.add(el.attr("href"));
        }

        return releases;
    }

     private List<Record> releaseDownload(String releaseName) throws IOException, InterruptedException {
        List<Record> releases = new ArrayList<>();
        Document doc1 = Jsoup.connect(getUrl()+releaseName).get();
        for (Element el1 : doc1.select(".views-field.views-field-title-field a")) {
            String href = el1.attr("href");
            releases.add(getRecord(getUrl() + href));
        }
        return releases;
    }
       protected Record getRecord(String url) throws IOException{
        Record record = new Record();
        record.setType(Record.Type.PAPER);
        Document doc2=Jsoup.connect(url).userAgent("Chrome/64.0.3282.167 ").get();
        ArrayList<String> authors = new ArrayList<String>();
        List<Author> authorlist = new ArrayList<>();
        record.setTitle(doc2.select(".title").text());
        record.setAnnotation(" ");
        for (Element el7: doc2.select(".field.field-name-body.field-type-text-with-summary.field-label-hidden p")){
            if (el7.text().isEmpty()){
                record.setAnnotation(" ");
            } else {
                record.setAnnotation(el7.text());
            }

            break;
        }
        String aut = (doc2.select(".field.field-name-field-lastname.field-type-text.field-label-hidden").text());//автор
        if (!aut.isEmpty()) {
            String[] temp = aut.trim().split(" ");
            for (String y : temp) {
                Author au = new Author(y, " ");
                authorlist.add(au);
            }
        } else {
            Author au = new Author(" ", " ");
            authorlist.add(au);

        }
        record.setAuthors(authorlist);
        String a = doc2.select(".field.field-name-field-text-pdf.field-type-file.field-label-inline.clearfix a").select("a[href]").attr("href");
        if (a.isEmpty()) {
            record.setUrl(doc2.select(".field.field-name-field-short-text-pdf.field-type-file.field-label-inline.clearfix a").select("a[href]").attr("href"));
        } else {
            record.setUrl(a);

        }

        return record;
    }

}

