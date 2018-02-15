package com.github.stagirs.crawler.downloader;
import com.github.stagirs.crawler.model.business.Record;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class InternationalJournalOfOpenInformationTechnologiesTest extends InternationalJournalOfOpenInformationTechnologies {
    @Test
    public void recordTest() throws IOException {
        Record record = getRecord("http://injoit.org/index.php/j1/article/view/528");
        assertEquals(record.getAuthors().size(), 2);
        assertEquals(record.getAuthors().get(0).getName(), "Stanislav");
        assertEquals(record.getAuthors().get(0).getSurname(), "Dolganov");
        assertEquals(record.getAuthors().get(1).getName(), "Dmitriy");
        assertEquals(record.getAuthors().get(1).getSurname(), "Vatolin");
        assertEquals(record.getTitle(), "Object boundaries inconsistencies detection method for 2D-3D conversion results and depth maps");
        assertEquals(record.getUrl(), "http://injoit.org/index.php/j1/article/download/528/509");
        assertEquals(record.getYear(), "2018");


        record = getRecord("http://injoit.org/index.php/j1/article/view/271");
        assertEquals(record.getAuthors().size(), 1);
        assertEquals(record.getAuthors().get(0).getName(), "М.А.");
        assertEquals(record.getAuthors().get(0).getSurname(), "Шнепс-Шнеппе");
        assertEquals(record.getTitle(), "On DISN evolution under cybersecurity needs");
        assertEquals(record.getUrl(), "http://injoit.org/index.php/j1/article/download/271/220");
        assertEquals(record.getYear(), "2016");
    }
}
