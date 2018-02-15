package com.github.stagirs.crawler.downloader;

import com.github.stagirs.crawler.model.business.Record;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
public class SpiiRasWorksTest extends SpiiRasWorks {
    @Test
    public void recordTest() throws IOException {
        Record record = getRecord("http://www.proceedings.spiiras.nw.ru/ojs/index.php/sp/article/view/3687");
        assertEquals(record.getDoi(), "http://dx.doi.org/10.15622/sp.56.1");
        assertEquals(record.getAuthors().size(), 2);
        assertEquals(record.getAuthors().get(0).getName(), "К.");
        assertEquals(record.getAuthors().get(0).getSurname(), "Зандкуль");
        assertEquals(record.getAuthors().get(1).getName(), "А.В.");
        assertEquals(record.getAuthors().get(1).getSurname(), "Смирнов");
        assertEquals(record.getTitle(), "УПРАВЛЕНИЕ ЗНАНИЯМИ В ПРОИЗВОДСТВЕННЫХ СЕТЯХ: КЛАССИФИКАЦИЯ И ТЕХНОЛОГИИ ДЛЯ ПОВТОРНОГО ИСПОЛЬЗОВАНИЯ ЗНАНИЙ");
        assertEquals(record.getUrl(), "http://www.proceedings.spiiras.nw.ru/ojs/index.php/sp/article/download/3687/2150");
        assertEquals(record.getYear(), "2018");


        record = getRecord("http://www.proceedings.spiiras.nw.ru/ojs/index.php/sp/article/view/14");
        assertEquals(record.getDoi(), "http://dx.doi.org/10.15622/sp.32.4");

        assertEquals(record.getAuthors().size(), 1);
        assertEquals(record.getAuthors().get(0).getName(), "В.В.");
        assertEquals(record.getAuthors().get(0).getSurname(), "Евдокимова");
        assertEquals(record.getTitle(), "АНАЛИЗ СПЕКТРА ГЛАСНЫХ НА ОСНОВЕ НЕРАВНОМЕРНОЙ ПСИХОАКУСТИЧЕСКОЙ ШКАЛЫ ЭРБОВ ДЛЯ ОПРЕДЕЛЕНИЯ СЛОВЕСНОГО УДАРЕНИЯ");
        assertEquals(record.getUrl(), "http://www.proceedings.spiiras.nw.ru/ojs/index.php/sp/article/download/14/1656");
        assertEquals(record.getYear(), "2014");
    }
}
