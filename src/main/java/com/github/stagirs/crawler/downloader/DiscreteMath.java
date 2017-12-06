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
import java.util.regex.*;


/**
 *
 * @author Anton Khandzhyan
 */
@Component
public class DiscreteMath extends Downloader{

    @Override
    public String getName() {
        return "Дискретная математика";
    }

    @Override
    public String getUrl() {
        return "http://www.mathnet.ru";
    }
    
    
    
    @Override
    public void process(Session session) throws IOException, InterruptedException {
        for (String release : getAvailableReleases()) {
            if(!isNewRelease(release)){
                continue;
            }
            releaseDownload(release, session);
            save(release);
        }
    }
    
    private void releaseDownload(String release, Session session) throws IOException, InterruptedException{
        List<Record> releases = new ArrayList<>();
        Document doc = Jsoup.connect(getUrl() + release).get();
        for(Element el : doc.select("a[href^=/rus/dm]")){
            String href = el.attr("href");
            
            Record r = getRecord(Jsoup.connect(getUrl() + href).get());
            if(r != null){
            	save(session, r);
            }
        }
    }
    
    protected Record getRecord(Document doc) throws IOException{
        Record record = new Record();
        record.setType(Type.PAPER);
        Elements td = doc.select("td[valign=top]");
        record.setTitle(td.select("span.red font").text());
        List<Author> authors = new ArrayList<>();
        for (Element el : td.select("a[href^=/php/person.phtml?option_lang=rus&]")){
        	Author a = new Author();
        	String[] strs = el.text().split("[   ]");
        	a.setSurname(strs[strs.length - 1]);
        	String name = strs[0];
        	for (int i = 1; i < strs.length - 1; i++){
        		name += " " + strs[i];
        	}
        	a.setName(name);
        	authors.add(a);
        }
        if (authors.isEmpty()){
        	return null;
        }
        record.setAuthors(authors);
        String endpat = "(Полный текст: PDF файл|DOI:|Ключевые слова:|Финансовая поддержка)";
        String pattern = "Аннотация:.*?" + endpat;
        String text = td.text();
        Pattern p = Pattern.compile(pattern, Pattern.UNICODE_CASE);
        Matcher m = p.matcher(text);
        if(!m.find()){
        	return null;
        }
        
        String found = m.group();
        
        Pattern endp = Pattern.compile(endpat, Pattern.UNICODE_CASE);
        Matcher m1 = endp.matcher(found);
        m1.find();
        record.setAnnotation(found.substring(11, m1.start() - 1));
        
        Elements doi = td.select("a[Title^=DOI:]");
        if (!doi.isEmpty()){
        	record.setDoi(doi.get(0).attr("href"));
        }
        
        if (text.contains("Ключевые слова:")){
        	String keypat = "Ключевые слова:.*?" + endpat;
            Pattern keyp = Pattern.compile(keypat, Pattern.UNICODE_CASE);
            Matcher mk = keyp.matcher(text);
            mk.find();
            found = mk.group().substring(16);
            
            m1 = endp.matcher(found);
            m1.find();
            record.setKeyWords(Arrays.asList(found.substring(0, m1.start() - 1).replaceAll("\\.$", "").split(", ")));
        }
        
        record.setSource(getName());
        
        String source = td.select("td[width=70%]").text();
        pattern = "том [0-9]*, выпуск [0-9]*, страниц[аы] [0-9]*–?[0-9]*";
        p = Pattern.compile(pattern, Pattern.UNICODE_CASE);
        m = p.matcher(source);
        m.find();
        record.setPages(m.group());
        record.setYear(source.substring(17, 21));
        record.setUrl(getUrl() + td.select("a[href^=/php/getFT.phtml]").attr("href"));
        record.setBibliography(getBibliography(td));
        return record;
    }
    
    private List<String> getBibliography(Elements el) throws IOException{
    	Elements els = el.select("a[href~=/php/getRefFromDB.phtml.*output=htm.*]");
    	List<String> res = null;
    	if(!els.isEmpty()){
    		res = new ArrayList<>();
    		Document doc = Jsoup.connect(getUrl() + els.attr("href")).get();
    		for (Element e : doc.select("tr:contains([)")){
    			res.add(e.select("td").get(1).text().replaceAll("[   ]+", " ").trim());
    		}
    	}
    	
    	return res;
    }
    

    private List<String> getAvailableReleases() throws IOException{
        List<String> releases = new ArrayList<>();
        Document doc = Jsoup.connect("http://www.mathnet.ru/php/archive.phtml?jrnid=dm&wshow=contents&option_lang=rus").get();
        for(Element el : doc.select("td.issue a, td.issue_with_corner a")){
            releases.add(el.attr("href"));
        }
        Collections.sort(releases);
        return releases;
    }
    
}
