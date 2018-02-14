package com.github.stagirs.crawler.downloader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.github.stagirs.crawler.Downloader;
import com.github.stagirs.crawler.model.business.Author;
import com.github.stagirs.crawler.model.business.Record;
import com.github.stagirs.crawler.model.business.Record.Type;
import com.github.stagirs.crawler.model.service.Session;

/**
*
* @author Anton Khandzhyan
*/
@Component
public class MathNet extends Downloader{
	
	 @Override
	    public String getName() {
	        return "Портал MathNet";
	    }
	
	private String[] jIDs = { "im", "tmf", "dm", "mzm", "sm",
							 "tvp", "rm", "faa", "zvmmf", "mm",
							 "jetpl", "ufn", "de", "ia", "ppi",
							 "ssi", "at", "tvt", "aa", "qe",
							 "vmp", "fpm", "vspui", "ubs", "irj"};
	private String jID;

	
	@Override
    public String getUrl() {
        return "http://www.mathnet.ru";
    }
	
	@Override
    public void process(Session session) throws IOException {
		try{
			for(String st : jIDs){
				jID = st;
				int releaseCount = 0;
		        for (List<String> release : getAvailableReleases(false)) {
		            if(!isNewRelease(release.get(0))){
		                continue;
		            }
		            //File fl = new File(System.getProperty("catalina.home") + "/work/crawler/realese.log");
		        	//FileUtils.write(fl, release.get(0), "utf-8", true);
		        	for(Record record : releaseDownload(release.get(0), release.get(1))){
	                    save(session, record);
	                }
		        	//File fl1 = new File(System.getProperty("catalina.home") + "/work/crawler/record.log");
		        	//FileUtils.write(fl1, "SAVED\n", "utf-8", true);
		           // FileUtils.write(fl, " DONE\n", "utf-8", true);
		            save(release.get(0));
		            releaseCount++;
		        	if (releaseCount % 20 == 0){
		        		//FileUtils.write(fl, "SLEEP\n", "utf-8", true);
		            	Thread.sleep(25 * 1000);
		            }
		        	Thread.sleep(1000);
		        }
			}
		} catch (Exception e){
			File fl = new File(System.getProperty("catalina.home") + "/work/crawler/errors.log");
			FileUtils.write(fl, e.getMessage() + "\n", "utf-8", true);
			//fl = new File(System.getProperty("catalina.home") + "/work/crawler/record.log");
	        //FileUtils.write(fl, " ERROR\n", "utf-8", true);
	        //fl = new File(System.getProperty("catalina.home") + "/work/crawler/release.log");
	        //FileUtils.write(fl, " ERROR\n", "utf-8", true);
		}
    } 

    
	protected List<Record> releaseDownload(String release, String name) throws IOException, InterruptedException{
	    List<Record> records = new ArrayList<>();
        Document doc = Jsoup.connect(getUrl() + release).get();
        //File fl = new File(System.getProperty("catalina.home") + "/work/crawler/record.log");
        //FileUtils.write(fl, "DOWNLOAD:\n", "utf-8", true);
        for(Element el : doc.select(String.format("a[href^=/rus/%s]", jID))){
            String href = el.attr("href");
            
        	//FileUtils.write(fl, href, "utf-8", true);
            Record r = getRecord(Jsoup.connect(getUrl() + href).get());
            if(r != null){
            	//FileUtils.write(fl, " DONE", "utf-8", true);
            	r.setSource(name);
            	records.add(r);
            } else {
            	//FileUtils.write(fl, " is NULL", "utf-8", true);
            }
            //FileUtils.write(fl, "\n", "utf-8", true);
            Thread.sleep(500);
        }
        return records;
    }
    
    protected Record getRecord(Document doc) throws IOException{
        Record record = new Record();
        record.setType(Type.PAPER);
        
        Elements td = doc.select("td[valign=top]");
        
        Elements ft = td.select("a[href^=/php/getFT.phtml]");
        if (ft.isEmpty()){
        	return null;
        }
        record.setUrl(getUrl() + ft.attr("href"));
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
        if(text.contains("Тип публикации:") && !text.contains("Тип публикации: Статья")){
        	return null;
        }
        Pattern p = Pattern.compile(pattern, Pattern.UNICODE_CASE);
        Matcher m = p.matcher(text);
        String found;
        Pattern endp = Pattern.compile(endpat, Pattern.UNICODE_CASE);
        if(m.find()){              
	        found = m.group();
	        
	        Matcher m1 = endp.matcher(found);
	        m1.find();
	        record.setAnnotation(found.substring(11, m1.start() - 1));
        }
        
        Elements doi = td.select("a[Title^=DOI:]:contains(https://doi.org/)");
        if (!doi.isEmpty()){
        	record.setDoi(doi.get(0).attr("href"));
        }
        
        if (text.contains("Ключевые слова:")){
        	String keypat = "Ключевые слова:.*?" + endpat;
            Pattern keyp = Pattern.compile(keypat, Pattern.UNICODE_CASE);
            Matcher mk = keyp.matcher(text);
            mk.find();
            found = mk.group().substring(16);
            
            m = endp.matcher(found);
            m.find();
            record.setKeyWords(Arrays.asList(found.substring(0, m.start() - 1).replaceAll("\\.$", "").split(", ")));
        }
        
        String source = td.select("td[width=70%]").text();
        pattern = "[12][089][0-9][0-9].*\\(Mi";
        p = Pattern.compile(pattern, Pattern.UNICODE_CASE);
        m = p.matcher(source);
        if (!m.find()){
        	return null;
        }
        String res = m.group();
        record.setPages(res.substring(6, res.length() - 4).replaceAll("[   ]", " "));
        record.setYear(res.substring(0, 4));
        
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
    

    protected List<List> getAvailableReleases(Boolean test) throws IOException{
        List<List> releases = new ArrayList<>();
        Document doc = Jsoup.connect(String.format("http://www.mathnet.ru/php/archive.phtml?jrnid=%s&wshow=contents&option_lang=rus", jID)).get();
        String sourcename = null;
        for(Element el : doc.select("td.issue a, td.issue_with_corner a, td.jnamebyyear")){
        	if(el.is("td.jnamebyyear")){
        		sourcename = el.text();
        		continue;
        	}
        	List<String> release = new ArrayList<>();
        	release.add(el.attr("href"));
        	release.add(sourcename);
            releases.add(release);
            if (test){
            	break;
            }
        }
        return releases;
    }

}
