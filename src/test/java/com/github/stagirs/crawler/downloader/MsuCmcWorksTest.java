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

import com.github.stagirs.crawler.model.business.Record;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Dmitriy Malakhov
 */
public class MsuCmcWorksTest {
    @Test
    public void test() throws IOException{
        MsuCmcWorks downloader = new MsuCmcWorks();
        Document doc = Jsoup.parse(FileUtils.readFileToString(new File("src/test/resources/CmcWorks/main_page"), "utf-8").replaceAll("[  ]", " "));
        Element release = doc.select(".row").first();
        List<Record> records = downloader.processList(release.select(".list").first().children(), release.select("a.show").text());
        assertEquals(records.get(0).getTitle(), "Численное решение задач определения начального условия в задачах Коши для гиперболического уравнения с малым параметром.");
        assertEquals(records.get(0).getLocation(), "Москва");
        assertEquals(records.get(0).getPages(), "Выпуск №54, Раздел I. Численные методы");
        assertEquals(records.get(0).getSource(), "Труды ВМК МГУ «Прикладная математика и информатика»");
        assertEquals(records.get(0).getYear(), "2017");
        assertEquals(records.get(0).getType(), Record.Type.PAPER);
        assertEquals(records.get(0).getUrl(), "https://cs.msu.ru/sites/cmc/files/docs/denisov_0.pdf");
        assertEquals(records.get(0).getAuthors().get(0).getSurname(), "Денисов");
        assertEquals(records.get(0).getAuthors().get(0).getName(), "А.М.");
        assertEquals(records.get(0).getAuthors().get(1).getSurname(), "Соловьева");
        assertEquals(records.get(0).getAuthors().get(1).getName(), "С.И.");
        
        assertEquals(records.get(1).getTitle(), "О свойствах согласованных сеточных операторов для сеточных функций, определенных в ячейках и на гранях сетки.");
        assertEquals(records.get(1).getLocation(), "Москва");
        assertEquals(records.get(1).getPages(), "Выпуск №54, Раздел I. Численные методы");
        assertEquals(records.get(1).getSource(), "Труды ВМК МГУ «Прикладная математика и информатика»");
        assertEquals(records.get(1).getYear(), "2017");
        assertEquals(records.get(1).getType(), Record.Type.PAPER);
        assertEquals(records.get(1).getUrl(), "https://cs.msu.ru/sites/cmc/files/docs/ardelyan_0.pdf");
        assertEquals(records.get(1).getAuthors().get(0).getSurname(), "Арделян");
        assertEquals(records.get(1).getAuthors().get(0).getName(), "Н.В.");
        assertEquals(records.get(1).getAuthors().get(1).getSurname(), "Космачевский");
        assertEquals(records.get(1).getAuthors().get(1).getName(), "К.В.");
        assertEquals(records.get(1).getAuthors().get(2).getSurname(), "Саблин");
        assertEquals(records.get(1).getAuthors().get(2).getName(), "М.Н.");
        
        assertEquals(records.get(8).getTitle(), "Об S-регулярных управляемых объектах.");
        assertEquals(records.get(8).getLocation(), "Москва");
        assertEquals(records.get(8).getPages(), "Выпуск №54, Раздел II. Математическое моделирование");
        assertEquals(records.get(8).getSource(), "Труды ВМК МГУ «Прикладная математика и информатика»");
        assertEquals(records.get(8).getYear(), "2017");
        assertEquals(records.get(8).getType(), Record.Type.PAPER);
        assertEquals(records.get(8).getUrl(), "https://cs.msu.ru/sites/cmc/files/docs/nikolskiy_1.pdf");
        assertEquals(records.get(8).getAuthors().get(0).getSurname(), "Никольский");
        assertEquals(records.get(8).getAuthors().get(0).getName(), "М.С.");
    }
}
