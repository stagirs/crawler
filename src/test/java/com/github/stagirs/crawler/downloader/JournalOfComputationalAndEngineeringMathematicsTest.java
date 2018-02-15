package com.github.stagirs.crawler.downloader;
import com.github.stagirs.crawler.model.business.Record;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JournalOfComputationalAndEngineeringMathematicsTest extends JournalOfComputationalAndEngineeringMathematics{
    @Test
    public void recordTest() throws IOException {
        Record record = getRecord("http://jcem.susu.ru/jcem/article/view/129");
        assertEquals(record.getAuthors().size(), 3);
        assertEquals(record.getAuthors().get(0).getName(), "V. G.");
        assertEquals(record.getAuthors().get(0).getSurname(), "Mokhov");
        assertEquals(record.getAuthors().get(1).getName(), "T. S.");
        assertEquals(record.getAuthors().get(1).getSurname(), "Demyanenko");
        assertEquals(record.getAuthors().get(2).getName(), "K. V.");
        assertEquals(record.getAuthors().get(2).getSurname(), "Demyanenko");
        assertEquals(record.getTitle(), "ANALYSIS OF FORMALIZED METHODS FOR FORECASTING THE VOLUME OF ELECTRICITY CONSUMPTION");
        assertEquals(record.getUrl(), "http://jcem.susu.ru/jcem/article/download/129/108");
        assertEquals(record.getYear(), "2017");


        record = getRecord("http://jcem.susu.ru/jcem/article/view/10");
        assertEquals(record.getAuthors().size(), 1);
        assertEquals(record.getAuthors().get(0).getName(), "Evgeniy Viktorovich");
        assertEquals(record.getAuthors().get(0).getSurname(), "Bychkov");
        assertEquals(record.getTitle(), "THE NUMERICAL SOLUTION OF SOME CLASSES OF THE SEMILINEAR SOBOLEV-TYPE EQUATIONS");
        assertEquals(record.getUrl(), "http://jcem.susu.ru/jcem/article/download/10/15");
        assertEquals(record.getYear(), "2014");
    }
}
