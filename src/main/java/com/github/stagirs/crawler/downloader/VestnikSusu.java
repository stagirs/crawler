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
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class VestnikSusu extends Downloader  {
    @Override
    public String getName() {
        return "Вестник Южно-Уральского Государственного Университета";
    }

    @Override
    public  String getUrl() {
        return "https://vestnik.susu.ru/cmi/issue";
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
            Document doc = Jsoup.connect(getUrl()+"/archive").get();
            for (Element el : doc.select("a[href*=/issue/view]")) {
                releases.add(el.childNode(0).toString());
            }
        return releases;
    }

    private List<Record> releaseDownload(String releaseName) throws IOException, InterruptedException{
        List<Record> releases = new ArrayList<>();
            Document doc = Jsoup.connect(getUrl()+"/archive").get();
            for(Element el : doc.select("a[href*=/issue/view]")){
                if(!(el.childNode(0).toString().contains(releaseName)))
                    continue;
                Document doc1 =Jsoup.connect(el.attr("href")).get();
                for(Element el1: doc1.select("a[href*=/article/view]")) {
                    if(el1.childNode(0).toString().contains("PDF"))
                        continue;
                    String recordurl=el1.attr("href");
                    releases.add(getRecord(recordurl));
                }
            }

        return releases;
    }

    protected Record getRecord(String url) throws IOException {

        Record record = new Record();
        record.setType(Type.PAPER);
        Document doc=Jsoup.connect(url).get();
        if(doc.select("#articleFullText").size()!=0)
            record.setUrl(doc.select("#articleFullText").first().childNode(3).attr("href").replace("view","download"));
        record.setTitle(doc.select("h3").text());
        if(doc.select("#articleAbstract").first().childNode(5).childNode(0).toString().contains("<p>"))
            record.setAnnotation(doc.select("#articleAbstract").first().childNode(5).childNode(0).childNode(0).toString());
        else
            record.setAnnotation(doc.select("#articleAbstract").first().childNode(5).childNode(0).toString());
        record.setSource("Вестник Южно-Уральского государственного университета. ");
        record.setLocation("Челябинск");
        List<Author>authorlist = new ArrayList<>();
        Elements auth=doc.select("#authorString");
        if(!(auth.get(0).childNode(0).childNodes().isEmpty())){
            String[] authors=auth.first().childNode(0).childNode(0).toString().split(",");
            for(int j=0;j<authors.length;j++)
            {
                if(j==0)
                {
                    String[] temp=authors[j].split(" ");
                    if(temp.length<2) {
                        Author a = new Author(temp[0], " ");
                        authorlist.add(a);
                    }
                    else{
                        Author a;
                        if(temp.length==2)
                            a= new Author(temp[1],temp[0]);
                        else
                            a= new Author(temp[2],temp[0]+" "+temp[1]);
                        authorlist.add(a);}
                }
                else {
                    if(authors[j].length()<2)
                        continue;
                    String[] temp=authors[j].substring(1).split(" ");
                    if(temp.length<2) {
                        Author a = new Author(temp[0], " ");
                        authorlist.add(a);
                    }
                    else{
                        Author a;
                        if(temp.length==2)
                            a= new Author(temp[1],temp[0]);
                        else
                            a= new Author(temp[2],temp[0]+" "+temp[1]);
                        authorlist.add(a);}
                }

            }
            record.setAuthors(authorlist);
        }
        String year=doc.select("#breadcrumb").first().childNode(3).childNode(0).toString();
        record.setYear(year.substring(year.length()-5,year.length()-1));
        Elements doi=doc.select("a[href*=dx.doi.org]");
        if (doi.size()!=0)
            record.setDoi(doc.select("a[href*=dx.doi.org]").get(0).childNode(0).toString());
        return record;
    }
}
