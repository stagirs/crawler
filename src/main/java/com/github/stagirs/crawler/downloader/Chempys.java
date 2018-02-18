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
public class Chempys extends Downloader  {

    @Override
    public String getName() {
        return "«Физико-химическая кинетика в газовой динамике»";
    }

    @Override
    public String getUrl() {
        return "http://chemphys.edu.ru";
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

        Document doc = Jsoup.connect("http://chemphys.edu.ru/issues").get();
        for (Element el : doc.select(".btn-block.btn.panel-heading")) {
            releases.add(el.attr("href"));
        }

        return releases;
    }

    private List<Record> releaseDownload(String releaseName) throws IOException, InterruptedException {
        List<Record> releases = new ArrayList<>();
            String href ;
            Document doc1 = Jsoup.connect(getUrl()+releaseName).get();
            for (Element el1 : doc1.select(".table a[href*=/articles/]")) {
                String a = el1.text();
                if (el1.text().length() == 0){
                    continue;
                }
                href = el1.attr("href");
                releases.add(getRecord(getUrl() + href));
            }

        return releases;

    }


    protected Record getRecord(String url) throws IOException {
        Record record = new Record();
        record.setType(Record.Type.PAPER);
        ArrayList<String> authors= new ArrayList<String>();
        List<Author>authorlist = new ArrayList<>();
        String href;
        Document doc2 = Jsoup.connect(url).get();
        record.setUrl(getUrl() + doc2.select(".col-lg-2 a").attr("href"));
        record.setTitle(doc2.select(".h4").text());
        for (Element el2 : doc2.select(".tab-pane.active").select(".col-lg-3 a[href*=/authors/]")){
            href = el2.attr("title");
            if (href.length() == 0){
                continue;
            }
            authors.add(el2.text());

        }
        for (int i = 0; i < authors.size(); i++) {
            String[] temp = authors.get(i).split(" ");
            String surname = temp[temp.length - 1];
            String name = authors.get(i).split(" ")[0];
            Author au = new Author(surname,name);
            authorlist.add(au);
        }
        record.setAuthors(authorlist);
        return record;
    }
}