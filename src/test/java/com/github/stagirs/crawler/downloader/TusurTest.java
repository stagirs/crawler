package com.github.stagirs.crawler.downloader;



import com.github.stagirs.crawler.model.business.Record;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
public class TusurTest extends Tusur {
    @Test
    public void recordTest() throws IOException {
        Record record = getRecord("https://journal.tusur.ru/ru/arhiv/4-2017/koeffitsient-shuma-aktivnoy-antennoy-reshetki");

        assertEquals(record.getAuthors().size(), 2);
        assertEquals(record.getAuthors().get(0).getName(), "В.");
        assertEquals(record.getAuthors().get(0).getSurname(), "Хлусов");
        assertEquals(record.getAuthors().get(1).getName(), "В.");
        assertEquals(record.getAuthors().get(1).getSurname(), "Доценко");
        assertEquals(record.getTitle(), "Коэффициент шума активной антенной решетки");
        assertEquals(record.getUrl(), "https://journal.tusur.ru/storage/97886/1-%D0%A5%D0%BB%D1%83%D1%81%D0%BE%D0%B2-%D0%94%D0%BE%D1%86%D0%B5%D0%BD%D0%BA%D0%BE.pdf?1516251128");


        record = getRecord("https://journal.tusur.ru/ru/arhiv/4-2017/issledovanie-vliyaniya-parametrov-koltsevogo-rezonatora-na-harakteristiki-tryohosevogo-optoelektronnogo-preobrazovatelya-uglovoy-skorosti");

        assertEquals(record.getAuthors().size(), 3);
        assertEquals(record.getAuthors().get(0).getName(), "В.");
        assertEquals(record.getAuthors().get(0).getSurname(), "Бусурин");
        assertEquals(record.getAuthors().get(1).getName(), "В.");
        assertEquals(record.getAuthors().get(1).getSurname(), "Коробков");
        assertEquals(record.getAuthors().get(2).getName(), "Н.");
        assertEquals(record.getAuthors().get(2).getSurname(), "Йин");
        assertEquals(record.getTitle(), "Исследование влияния параметров кольцевого резонатора на характеристики трёхосевого оптоэлектронного преобразователя угловой скорости");
        assertEquals(record.getUrl(), "https://journal.tusur.ru/storage/97912/10-%D0%91%D1%83%D1%81%D1%83%D1%80%D0%B8%D0%BD-%D0%9A%D0%BE%D1%80%D0%BE%D0%B1%D0%BA%D0%BE%D0%B2-%D0%99%D0%B8%D0%BD_%D0%9D%D0%B0%D0%B8%D0%BD%D0%B3_%D0%92%D0%B8%D0%BD.pdf?1516267090");

    }
}