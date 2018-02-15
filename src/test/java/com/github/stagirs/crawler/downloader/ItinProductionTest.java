package com.github.stagirs.crawler.downloader;

import com.github.stagirs.crawler.model.business.Record;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
public class ItinProductionTest extends ItinProduction {
    @Test
    public void recordTest() throws IOException {
        Record record = getRecord("http://izdat.istu.ru/index.php/ISM/article/view/3995");
        assertEquals(record.getAuthors().size(), 2);
        assertEquals(record.getAuthors().get(0).getName(), "Y.V.");
        assertEquals(record.getAuthors().get(0).getSurname(), "Turygin");
        assertEquals(record.getAuthors().get(1).getName(), "A.A.");
        assertEquals(record.getAuthors().get(1).getSurname(), "Yakupov");
        assertEquals(record.getTitle(), "Occurrence of Fretting in Connection with Interference with Loading by Bending with the Rotation");
        assertEquals(record.getUrl(), "http://izdat.istu.ru/index.php/ISM/article/download/3995/2517");
        assertEquals(record.getYear(), "2017");


        record = getRecord("http://izdat.istu.ru/index.php/ISM/article/view/1423");
        assertEquals(record.getAuthors().size(), 3);
        assertEquals(record.getAuthors().get(0).getName(), "J.");
        assertEquals(record.getAuthors().get(0).getSurname(), "Zabka");
        assertEquals(record.getAuthors().get(1).getName(), "Y.");
        assertEquals(record.getAuthors().get(1).getSurname(), "Turygin");
        assertEquals(record.getAuthors().get(2).getName(), "J.");
        assertEquals(record.getAuthors().get(2).getSurname(), "Binka");
        assertEquals(record.getTitle(), "RELIABILITY OF MECHATRONIC SYSTEMS");
        assertEquals(record.getUrl(), "http://izdat.istu.ru/index.php/ISM/article/download/1423/236");
        assertEquals(record.getYear(), "2008");
    }
}
