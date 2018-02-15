package com.github.stagirs.crawler.downloader;
import static org.junit.Assert.assertEquals;
import java.io.IOException;
import com.github.stagirs.crawler.model.business.Record;
import org.junit.Test;

public class MathMelPubTest extends MathMelPub {
    @Test
    public void recordTest() throws IOException {
        Record record = getRecord("http://www.mathmelpub.ru/jour/article/view/96");
        assertEquals(record.getDoi(), "http://dx.doi.org/10.24108/mathm.0617.0000096");
        assertEquals(record.getAuthors().size(), 3);
        assertEquals(record.getAuthors().get(0).getName(), "М. С.");
        assertEquals(record.getAuthors().get(0).getSurname(), "Виноградова");
        assertEquals(record.getAuthors().get(1).getName(), "А. Н.");
        assertEquals(record.getAuthors().get(1).getSurname(), "Канатников");
        assertEquals(record.getAuthors().get(2).getName(), "О. С.");
        assertEquals(record.getAuthors().get(2).getSurname(), "Ткачева");
        assertEquals(record.getTitle(), "Поведение двухкомпонентной популяционной системы в окрестности нулевого положения равновесия");
        assertEquals(record.getUrl(), "http://www.mathmelpub.ru/jour/article/download/96/97");
        assertEquals(record.getYear(), "2017");


        record = getRecord("http://www.mathmelpub.ru/jour/article/view/78");
        assertEquals(record.getDoi(), "http://dx.doi.org/10.24108/mathm.0517.0000078");
        assertEquals(record.getAuthors().size(), 1);
        assertEquals(record.getAuthors().get(0).getName(), "И. В.");
        assertEquals(record.getAuthors().get(0).getSurname(), "Станкевич");
        assertEquals(record.getTitle(), "Численное решение смешанных задач теории упругости с односторонними связями");
        assertEquals(record.getUrl(), "http://www.mathmelpub.ru/jour/article/download/78/91");
        assertEquals(record.getYear(), "2017");
    }
}
