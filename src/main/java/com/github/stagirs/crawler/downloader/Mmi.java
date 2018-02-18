package com.github.stagirs.crawler.downloader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.stagirs.crawler.Downloader;
import com.github.stagirs.crawler.model.business.Author;
import com.github.stagirs.crawler.model.business.Record;
import com.github.stagirs.crawler.model.business.Record.Type;
import com.github.stagirs.crawler.model.service.Session;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
@Component
public class Mmi extends Downloader {

    @Override
    public String getName() {
        return "«Известия Саратовского университета. Новая серия. Серия «Математика. Механика. Информатика»";
    }

    @Override
    public String getUrl() {
        return "http://mmi.sgu.ru";
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
        for (int i = 0; i <= 2; i++) {
            Document doc = Jsoup.connect("http://mmi.sgu.ru/ru/journal/issues?page=" + i).get();
            for (Element el : doc.select(".views-field.views-field-field-number a")) {
                releases.add(el.attr("href"));

            }
        }

        return releases;
    }

    private List<Record> releaseDownload(String releaseName) throws IOException, InterruptedException {
        List<Record> releases = new ArrayList<>();
        String href;
        Document doc1 = Jsoup.connect(getUrl()+releaseName).get();
        for (Element el1 : doc1.select(".views-field.views-field-title-field a")) {
            href = el1.attr("href");
            releases.add(getRecord(getUrl() + href));
        }



        return releases;
    }

    protected Record getRecord(String url) throws IOException {

        Record record = new Record();
        record.setType(Record.Type.PAPER);
        ArrayList<String> authors = new ArrayList<String>();
        List<Author> authorlist = new ArrayList<>();
        Document doc2 = Jsoup.connect(url).get();
        record.setUrl(doc2.select(".field.field-name-field-text-pdf.field-type-file.field-label-inline.clearfix a").select("a[href]").attr("href"));
        record.setTitle(doc2.select("h1").text());
        for (Element el7: doc2.select(".field.field-name-body.field-type-text-with-summary.field-label-above p")){
            record.setAnnotation(el7.text());
            break;
        }
        String aut = doc2.select(".field.field-name-field-lastname.field-type-text.field-label-hidden").text();//автор
        String[] temp = aut.split(" ");
        for (String y : temp){
            Author au = new Author(y," ");
            authorlist.add(au);
        }
        record.setAuthors(authorlist);

        return record;
    }
}