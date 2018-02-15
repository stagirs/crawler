package com.github.stagirs.crawler.downloader;

import com.github.stagirs.crawler.model.business.Record;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
public class VestnikSusuTest extends VestnikSusu {
    @Test
    public void recordTest() throws IOException {
        Record record = getRecord("https://vestnik.susu.ru/cmi/article/view/6787");
        assertEquals(record.getDoi(), "http://dx.doi.org/10.14529/cmse170401");
        assertEquals(record.getAuthors().size(), 1);
        assertEquals(record.getAuthors().get(0).getName(), "Александр Дмитриевич");
        assertEquals(record.getAuthors().get(0).getSurname(), "Дрозин");
        assertEquals(record.getTitle(), "Метод обработки информации о неметаллических включениях, получаемой при обследовании микрошлифа готовой стали");
        assertEquals(record.getUrl(), "https://vestnik.susu.ru/cmi/article/download/6787/5851");
        assertEquals(record.getYear(), "2017");


        record = getRecord("https://vestnik.susu.ru/cmi/article/view/2851");
        assertEquals(record.getDoi(), "http://dx.doi.org/10.14529/cmse120201");
        assertEquals(record.getAuthors().size(), 2);
        assertEquals(record.getAuthors().get(0).getName(), "Анна Олеговна");
        assertEquals(record.getAuthors().get(0).getSurname(), "Гаязова");
        assertEquals(record.getAuthors().get(1).getName(), "Санжар Муталович");
        assertEquals(record.getAuthors().get(1).getSurname(), "Абдуллаев");
        assertEquals(record.getTitle(), "Прогнозирование численности Microcystis aeruginosa на основе правил нечеткой логики и нечетких нейронных сетей");
        assertEquals(record.getUrl(), "https://vestnik.susu.ru/cmi/article/download/2851/2701");
        assertEquals(record.getYear(), "2012");
    }
}
