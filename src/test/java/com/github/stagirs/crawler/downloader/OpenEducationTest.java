package com.github.stagirs.crawler.downloader;
import com.github.stagirs.crawler.model.business.Record;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class OpenEducationTest extends OpenEducation {
    @Test
    public void recordTest() throws IOException {
        Record record = getRecord("http://openedu.rea.ru/jour/article/view/361");
        assertEquals(record.getDoi(), "http://dx.doi.org/10.21686/1818-4243-2017-1-4-13");
        assertEquals(record.getAuthors().size(), 2);
        assertEquals(record.getAuthors().get(0).getName(), "А. А.");
        assertEquals(record.getAuthors().get(0).getSurname(), "Солодов");
        assertEquals(record.getAuthors().get(1).getName(), "Е. А.");
        assertEquals(record.getAuthors().get(1).getSurname(), "Солодова");
        assertEquals(record.getTitle(), "Анализ динамических характеристик случайных воздействий в когнитивных системах");
        assertEquals(record.getUrl(), "http://openedu.rea.ru/jour/article/download/361/306");
        assertEquals(record.getYear(), "2017");


        record = getRecord("http://openedu.rea.ru/jour/article/view/227");
        assertEquals(record.getDoi(), "http://dx.doi.org/10.21686/1818-4243-2013-6(101-24-30");

        assertEquals(record.getAuthors().size(), 1);
        assertEquals(record.getAuthors().get(0).getName(), "Н. В.");
        assertEquals(record.getAuthors().get(0).getSurname(), "Комлева");
        assertEquals(record.getTitle(), "Моделирование процесса создания открытых электронных образовательных ресурсов");
        assertEquals(record.getUrl(), "http://openedu.rea.ru/jour/article/download/227/231");
        assertEquals(record.getYear(), "2013");
    }
}
