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

import com.github.stagirs.crawler.model.business.Author;
import com.github.stagirs.crawler.model.business.Record;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Dmitriy Malakhov
 */
public class IspRasWorksTest extends IspRasWorks{
    @Test
    public void authorTest0(){
        List<Author> authors = getAuthors("А.Н. Коварцев (СГАУ (НИУ), Самара), В.С. Смирнов (СГАУ (НИУ), Самара), С.В. Смирнов (ИПУСС РАН, Самара)");
        assertEquals(authors.get(0).getName(), "А.Н.");
        assertEquals(authors.get(0).getSurname(), "Коварцев");
        assertEquals(authors.get(0).getOrganization(), "СГАУ НИУ");
        assertEquals(authors.get(0).getLocation(), "Самара");
        
        assertEquals(authors.get(1).getName(), "В.С.");
        assertEquals(authors.get(1).getSurname(), "Смирнов");
        assertEquals(authors.get(1).getOrganization(), "СГАУ НИУ");
        assertEquals(authors.get(1).getLocation(), "Самара");
        
        assertEquals(authors.get(2).getName(), "С.В.");
        assertEquals(authors.get(2).getSurname(), "Смирнов");
        assertEquals(authors.get(2).getOrganization(), "ИПУСС РАН");
        assertEquals(authors.get(2).getLocation(), "Самара");
    }
    
    @Test
    public void authorTest1(){
        List<Author> authors = getAuthors("И.Б. Бурдонов, А.С. Косачев.");
        assertEquals(authors.get(0).getName(), "И.Б.");
        assertEquals(authors.get(0).getSurname(), "Бурдонов");
        assertEquals(authors.get(1).getName(), "А.С.");
        assertEquals(authors.get(1).getSurname(), "Косачев");
    }
    
    @Test
    public void authorTest2(){
        List<Author> authors = getAuthors("Н.Ф. Димитриева (НАНУ, Киев, Украина)");
        assertEquals(authors.get(0).getName(), "Н.Ф.");
        assertEquals(authors.get(0).getSurname(), "Димитриева");
        assertEquals(authors.get(0).getOrganization(), "НАНУ");
        assertEquals(authors.get(0).getLocation(), "Киев, Украина");
    }
    
    @Test
    public void authorTest3(){
        List<Author> authors = getAuthors("П.С. Лукашин (МГТУ, Москва, Россия)\n" +
                    "<br>С.В. Стрижак (ИСП РАН, Москва, Россия)\n" +
                    "<br>Г.А. Щеглов (МГТУ, Москва, Россия)");
        assertEquals(authors.get(0).getName(), "П.С.");
        assertEquals(authors.get(0).getSurname(), "Лукашин");
        assertEquals(authors.get(0).getOrganization(), "МГТУ");
        assertEquals(authors.get(0).getLocation(), "Москва, Россия");
        
        assertEquals(authors.get(1).getName(), "С.В.");
        assertEquals(authors.get(1).getSurname(), "Стрижак");
        assertEquals(authors.get(1).getOrganization(), "ИСП РАН");
        assertEquals(authors.get(1).getLocation(), "Москва, Россия");
        
        assertEquals(authors.get(2).getName(), "Г.А.");
        assertEquals(authors.get(2).getSurname(), "Щеглов");
        assertEquals(authors.get(2).getOrganization(), "МГТУ");
        assertEquals(authors.get(2).getLocation(), "Москва, Россия");
    }
    
    @Test
    public void authorTest4(){
        List<Author> authors = getAuthors("В.П. Иванников.");
        assertEquals(authors.get(0).getName(), "В.П.");
        assertEquals(authors.get(0).getSurname(), "Иванников");
    }
    
    @Test
    public void authorTest5(){
        getAuthors("");
    }
    
    @Test
    public void recordTest() throws IOException{
        Document document = Jsoup.parse(FileUtils.readFileToString(new File("src/test/resources/IspRasWorks/isp_29_2017_1_39"), "utf-8"));
        Record record = getRecord(document);
        assertEquals(record.getDoi(), "DOI: 10.15514/ISPRAS-2017-29(1)-3");
        assertEquals(record.getAuthors().get(0).getName(), "П.С.");
        assertEquals(record.getAuthors().get(0).getSurname(), "Лукашин");
        assertEquals(record.getAuthors().get(0).getOrganization(), "МГТУ");
        assertEquals(record.getAuthors().get(0).getLocation(), "Москва, Россия");
        
        assertEquals(record.getAuthors().get(1).getName(), "С.В.");
        assertEquals(record.getAuthors().get(1).getSurname(), "Стрижак");
        assertEquals(record.getAuthors().get(1).getOrganization(), "ИСП РАН");
        assertEquals(record.getAuthors().get(1).getLocation(), "Москва, Россия");
        
        assertEquals(record.getAuthors().get(2).getName(), "Г.А.");
        assertEquals(record.getAuthors().get(2).getSurname(), "Щеглов");
        assertEquals(record.getAuthors().get(2).getOrganization(), "МГТУ");
        assertEquals(record.getAuthors().get(2).getLocation(), "Москва, Россия");
        assertEquals(record.getKeyWords().size(), 8);  
        assertEquals(record.getLocation(), "Москва");  
        assertEquals(record.getPages(), "том 29, вып. 1, стр. 39-52.");  
        assertEquals(record.getSource(), "Труды Института системного программирования РАН");  
        assertEquals(record.getTitle(), "Тестирование возможностей открытого кода BEM++ по решению задач акустики");  
        assertEquals(record.getUrl(), "http://ispras.ru/proceedings/docs/2017/29/1/isp_29_2017_1_39.pdf");  
        assertEquals(record.getYear(), "2017");   
    }
}
