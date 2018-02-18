package com.github.stagirs.crawler.downloader;



import com.github.stagirs.crawler.model.business.Record;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
public class VudmTest extends Vudm {
    @Test
    public void recordTest() throws IOException {
        Record record = getRecord("http://vst.ics.org.ru/journal/article/2642/");

        assertEquals(record.getAuthors().size(), 1);
        assertEquals(record.getAuthors().get(0).getName(), "С.Ю.");
        assertEquals(record.getAuthors().get(0).getSurname(), "Лукащук");
        assertEquals(record.getTitle(), "Приближение обыкновенных дробно-дифференциальных уравнений дифференциальными уравнениями с малым параметром");
        assertEquals(record.getUrl(), "http://vst.ics.org.ru/uploads/vestnik/4_2017/17-04-03.pdf");


        record = getRecord("http://vst.ics.org.ru/journal/article/2485/");

        assertEquals(record.getAuthors().size(), 3);
        assertEquals(record.getAuthors().get(0).getName(), "О.В.");
        assertEquals(record.getAuthors().get(0).getSurname(), "Казак");
        assertEquals(record.getAuthors().get(1).getName(), "П.К.");
        assertEquals(record.getAuthors().get(1).getSurname(), "Галенко");
        assertEquals(record.getAuthors().get(2).getName(), "Д.В.");
        assertEquals(record.getAuthors().get(2).getSurname(), "Александров");
        assertEquals(record.getTitle(), "Влияние конвективного потока на рост чистого и сплавного дендрита");
        assertEquals(record.getUrl(), "http://vst.ics.org.ru/uploads/vestnik/3_2016/16-03-01.pdf");

    }
}