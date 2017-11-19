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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Dmitriy Malakhov
 */
@Component
public class MsuCmcWorks extends Downloader{

    @Override
    protected void process(Session session) throws Exception {
        List<Element> releases = getAvailableReleases();
        for (int i = releases.size() - 1; i >= 0; i--) {
            Element release = releases.get(i);
            String curRelease = release.select("a.show").text();
            if(!isNewRelease(curRelease)){
                continue;
            }
            for(Record record : processList(release.select(".list").first().children(), curRelease)){
                save(session, record);
            }
            save(curRelease);
        }
    }
    
    protected List<Record> processList(Elements children, String release){
        String[] parts = release.split(",");
        String releaseName = parts[0]; 
        String year = parts[1].trim().substring(0, 4);
        List<Record> list = new ArrayList<>();
        String section = null;
        for (int i = 0; i < children.size(); i++) {
            if(children.get(i).is("strong") && children.get(i + 1).is("br") && children.get(i + 2).is("hr")){
                section = children.get(i).text();
                continue;
            }
            if(children.get(i).is("strong") && children.get(i + 1).is("a") && children.get(i + 2).is("br")){
                Record record = new Record();
                record.setType(Record.Type.PAPER);
                record.setTitle(children.get(i + 1).text());
                record.setUrl(getUrl() + children.get(i + 1).attr("href"));
                record.setLocation("Москва");
                record.setSource(getName());
                record.setPages(releaseName + ", " + section);
                record.setYear(year);
                fillAuthors(record, children.get(i));
                list.add(record);
            }
        }
        return list;
    }
    
    private void fillAuthors(Record record, Element el){
        for(String author : el.text().split(",")){
            author = author.trim();
            if(author.isEmpty()){
                continue;
            }
            String[] name = author.trim().split(" ");
            record.getAuthors().add(new Author(name[0], name[1]));
        } 
    }
    
    private List<Element> getAvailableReleases() throws IOException{
        return Jsoup.connect(getUrl() + "/node/2272").validateTLSCertificates(false).get().select(".row");
    }

    @Override
    public String getName() {
        return "Труды ВМК МГУ «Прикладная математика и информатика»";
    }

    @Override
    public String getUrl() {
        return "https://cs.msu.ru";
    }
    
}
