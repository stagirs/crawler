package com.github.stagirs.crawler.downloader;


import com.github.stagirs.crawler.model.business.Record;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
public class ChebishevWorksTest extends ChebishevWorks {
    @Test
    public void recordTest() throws IOException {
        Record record = getRecord("http://www.chebsbornik.ru/jour/article/view/305");
        assertEquals(record.getDoi(), "http://dx.doi.org/10.22405/2226-8383-2017-18-1-73-91");
        assertEquals(record.getAuthors().size(), 2);
        assertEquals(record.getAuthors().get(0).getName(), "А. Р.");
        assertEquals(record.getAuthors().get(0).getSurname(), "Есаян");
        assertEquals(record.getAuthors().get(1).getName(), "Н. Н.");
        assertEquals(record.getAuthors().get(1).getSurname(), "Добровольский");
        assertEquals(record.getTitle(), "КОМПЬЮТЕРНОЕ ДОКАЗАТЕЛЬСТВО ГИПОТЕЗЫ О ЦЕНТРОИДАХ");
        assertEquals(record.getUrl(), "http://www.chebsbornik.ru/jour/article/download/305/278");
        assertEquals(record.getYear(), "2017");


        record = getRecord("http://www.chebsbornik.ru/jour/article/view/303");
        assertEquals(record.getDoi(), "http://dx.doi.org/10.22405/2226-8383-2017-18-1-44-64");
        assertEquals(record.getAuthors().size(), 1);
        assertEquals(record.getAuthors().get(0).getName(), "А. М.");
        assertEquals(record.getAuthors().get(0).getSurname(), "Ветошкин");
        assertEquals(record.getTitle(), "ВСЕГДА НЕВЫРОЖДЕННЫЕ МНОГОЧЛЕНЫ ОТ ДВУХ ПРОЕКТОРОВ");
        assertEquals(record.getUrl(), "http://www.chebsbornik.ru/jour/article/download/303/276");
        assertEquals(record.getYear(), "2017");

    }
}
