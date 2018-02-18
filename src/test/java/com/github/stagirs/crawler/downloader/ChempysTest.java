package com.github.stagirs.crawler.downloader;



import com.github.stagirs.crawler.model.business.Record;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
public class ChempysTest extends Chempys {
    @Test
    public void recordTest() throws IOException {
        Record record = getRecord("http://chemphys.edu.ru/issues/2017-18-1/articles/673/");

        assertEquals(record.getAuthors().size(), 1);
        assertEquals(record.getAuthors().get(0).getName(), "Александра");
        assertEquals(record.getAuthors().get(0).getSurname(), "Железнякова");
        assertEquals(record.getTitle(), "Эффективные методы декомпозиции неструктурированных адаптивных сеток для высокопроизводительных расчетов при решении задач вычислительной аэродинамики");
        assertEquals(record.getUrl(), "http://chemphys.edu.ru/media/published/Zhelez_2017_4_corr.pdf");


        record = getRecord("http://chemphys.edu.ru/issues/2011-11/articles/177/");

        assertEquals(record.getAuthors().size(), 3);
        assertEquals(record.getAuthors().get(0).getName(), "Александр");
        assertEquals(record.getAuthors().get(0).getSurname(), "Ланкин");
        assertEquals(record.getAuthors().get(1).getName(), "Генри");
        assertEquals(record.getAuthors().get(1).getSurname(), "Норман");
        assertEquals(record.getAuthors().get(2).getName(), "И.");
        assertEquals(record.getAuthors().get(2).getSurname(), "Саитов");
        assertEquals(record.getTitle(), "Флуктуации давления в неидеальной плазме: предвестник плазменного фазового перехода");
        assertEquals(record.getUrl(), "http://chemphys.edu.ru/media/published/016_ZGCy7Uq.pdf");

    }
}