/*
 * Copyright 2017 Dmitriy Malakhov.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.stagirs.crawler.downloader;

import com.github.stagirs.crawler.Downloader;
import com.github.stagirs.crawler.model.business.Author;
import com.github.stagirs.crawler.model.business.Record;
import com.github.stagirs.crawler.model.service.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import static org.apache.commons.lang.math.NumberUtils.toInt;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dmitriy Malakhov
 */
@Component
public class MiptWorks extends Downloader{

    @Override
    protected void process(Session session) throws Exception {
        List<Element> releases = getAvailableReleases();
        for (int i = releases.size() - 1; i >= 0; i--) {
            if(!releases.get(i).text().startsWith("Содержание журнала")){
                continue;
            }
            if(!isNewRelease(releases.get(i).attr("href"))){
                continue;
            }
            Element release = getRelease(releases.get(i).attr("href"));
            for(Record record : processList(release)){
                save(session, record);
            }
            save(releases.get(i).attr("href"));
        }
    }
    
    private List<Element> getAvailableReleases() throws IOException{
        return Jsoup.connect(getUrl() + "/science/trudy/").validateTLSCertificates(false).get().select("#mainblock").select("a");
    }
    
    private Element getRelease(String path) throws IOException{
        if(!path.startsWith("/")){
            path = "/science/trudy/" + path;
        }
        return Jsoup.connect(getUrl() + path).validateTLSCertificates(false).get().select("#mainblock").first();
    }
    
    protected Iterable<Record> processList(Element release) {
        List<Record> result = new ArrayList<>();
        String title = release.select(".title").text();
        String prefix = "Содержание журнала «Труды МФТИ» ";
        String suffix = title.substring(title.indexOf(prefix) + prefix.length());
        String year = suffix.substring(suffix.lastIndexOf("(") + 1, suffix.lastIndexOf(")"));
        String pages = suffix.substring(0, suffix.lastIndexOf("(")).trim();
        
        release.select("page-export-controls").remove();
        for(Element link : release.select("a")){
            if(link.text().equals("Печать") || link.text().equals("DOC") || link.text().equals("PDF") || 
                    link.text().startsWith("Научный журнал") || link.text().startsWith("Аннотации и ключевые слова всех статей") || 
                    link.text().startsWith("Summaries and ") || link.text().startsWith("Summaries_and")){
                continue;
            }
            if(!link.attr("href").endsWith(".pdf") || link.text().isEmpty() || !link.text().contains(" ")){
                continue;
            }
            if(isAuthors(link.text())){
                Element div = link.parent();
                Record record = new Record();
                record.setType(Record.Type.PAPER);
                record.setYear(year);
                record.setPages(pages);
                record.setUrl(getUrl() + link.attr("href"));
                while(div.parent().childNodeSize() <= div.elementSiblingIndex() + 1){
                    div = div.parent();
                }
                setTitle(record, div.parent().child(div.elementSiblingIndex() + 1).text());
                for(String author : link.text().split(",")){
                    author = author.trim();
                    record.getAuthors().add(new Author(author.substring(0, author.indexOf(" ")), author.substring(author.indexOf(" ") + 1)));
                }
                result.add(record);
            }else{
                if(startByAuthor(link.text())){
                    //авторы объединены с заголовком
                    Record record = new Record();
                    record.setType(Record.Type.PAPER);
                    record.setYear(year);
                    record.setPages(pages);
                    record.setUrl(getUrl() + link.attr("href"));
                    setTitle(record, link.text());
                    result.add(record);
                }else{
                    if(link.elementSiblingIndex() == 0){
                        //ссылка единственная в этом элементе, авторы в предыдущем от родительского
                        Record record = new Record();
                        record.setType(Record.Type.PAPER);
                        record.setYear(year);
                        record.setPages(pages);
                        record.setUrl(getUrl() + link.attr("href"));
                        setTitle(record, link.text());
                        if(link.parent().elementSiblingIndex() != 0){
                            String authors = StringUtils.join(link.parent().parent().child(link.parent().elementSiblingIndex() - 1).textNodes(), " ").replace("&nbsp;", " ").trim();
                            if(!authors.isEmpty()){
                                for(String author : authors.split(",")){
                                    if(!author.contains(" ")){
                                        continue;
                                    }
                                    record.getAuthors().add(new Author(author.substring(0, author.indexOf(" ")), author.substring(author.indexOf(" ") + 1)));
                                }
                            }
                        }
                        result.add(record);
                    }else{
                        //авторы указаны в том же элементе до ссылки
                        Record record = new Record();
                        record.setType(Record.Type.PAPER);
                        record.setYear(year);
                        record.setPages(pages);
                        record.setUrl(getUrl() + link.attr("href"));
                        setTitle(record, link.text());
                        Element el = link.parent();
                        if(el.equals(release)){
                            continue;
                        }
                        String authors = StringUtils.join(el.textNodes(), " ").replace("&nbsp;", " ").trim();
                        for(String author : authors.split(",")){
                            author = author.trim();
                            if(!author.contains(" ")){
                                continue;
                            }
                            record.getAuthors().add(new Author(author.substring(0, author.indexOf(" ")), author.substring(author.indexOf(" ") + 1)));
                        }
                        result.add(record);
                    }      
                }
            }
        }
        return result;
    }
    
    protected void setTitle(Record record, String title){
        if(title.endsWith(")")){
            record.setTitle(title.substring(0, title.lastIndexOf("(")));
            record.setPages(record.getPages() + " " + title.substring(title.lastIndexOf("(") + 1, title.lastIndexOf(")")));
        }else{
            record.setTitle(title);
        }
    }
    
    protected boolean isAuthors(String line){
        String[] parts = line.split(" ");
        if(parts.length < 2){
            return false;
        }
        if(!startByAuthor(parts[0] + " " + parts[1])){
            return false;
        }
        parts = line.split(",");
        return startByAuthor(parts[parts.length - 1]) && parts[parts.length - 1].split(" ").length < 4;
    }
    
    protected boolean startByAuthor(String line){
        String[] parts = line.trim().split(" ");
        if(!Character.isUpperCase(parts[0].charAt(0)) || parts.length < 2){
            return false;
        }
        return Character.isUpperCase(parts[1].charAt(0)) && parts[1].charAt(1) == '.';
    }

    @Override
    public String getName() {
        return "Труды МФТИ";
    }

    @Override
    public String getUrl() {
        return "https://mipt.ru";
    }
}
