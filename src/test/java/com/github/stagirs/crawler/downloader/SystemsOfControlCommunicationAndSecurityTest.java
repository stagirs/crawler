package com.github.stagirs.crawler.downloader;

import com.github.stagirs.crawler.model.business.Record;
import org.jsoup.Jsoup;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
public class SystemsOfControlCommunicationAndSecurityTest extends SystemsOfControlCommunicationAndSecurity {
    @Test
    public void recordTest() throws IOException {

        Record record = getRecord(Jsoup.connect("http://sccs.intelgr.com/20171.html").get().select("li:has(div.artname)").first(),"2017");
        assertEquals(record.getAuthors().size(), 1);
        assertEquals(record.getAuthors().get(0).getName(), "А. П.");
        assertEquals(record.getAuthors().get(0).getSurname(), "Шабанов");
        assertEquals(record.getTitle(), "Инновации в консолидируемых организационных системах: технологическая совместимость систем управления");
        assertEquals(record.getUrl(), "http://sccs.intelgr.com/archive/2017-01/09-Shabanov.pdf");
        assertEquals(record.getYear(), "2017");


        record = getRecord(Jsoup.connect("http://sccs.intelgr.com/20152.html").get().select("li:has(div.artname)").first(),"2015");
        assertEquals(record.getAuthors().size(), 4);
        assertEquals(record.getAuthors().get(0).getName(), "Ю. А.");
        assertEquals(record.getAuthors().get(0).getSurname(), "Кропотов");
        assertEquals(record.getAuthors().get(1).getName(), "А. Ю.");
        assertEquals(record.getAuthors().get(1).getSurname(), "Проскуряков");
        assertEquals(record.getAuthors().get(2).getName(), "А. А.");
        assertEquals(record.getAuthors().get(2).getSurname(), "Белов");
        assertEquals(record.getAuthors().get(3).getName(), "А. А.");
        assertEquals(record.getAuthors().get(3).getSurname(), "Колпаков");

        assertEquals(record.getTitle(), "Модели, алгоритмы системы автоматизированного мониторинга и управления экологической безопасности промышленных производств");
        assertEquals(record.getUrl(), "http://sccs.intelgr.com/archive/2015-02/08-Kropotov.pdf");
        assertEquals(record.getYear(), "2015");
    }
}
