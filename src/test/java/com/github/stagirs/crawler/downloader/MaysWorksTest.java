package com.github.stagirs.crawler.downloader;
import com.github.stagirs.crawler.model.business.Record;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class MaysWorksTest extends MaysWorks{
    @Test
    public void recordTest() throws IOException {
        Record record = getRecord("http://mais-journal.ru/jour/article/view/612");
        assertEquals(record.getDoi(), "http://dx.doi.org/10.18255/1818-1015-2017-6-755-759");
        assertEquals(record.getAuthors().size(), 2);
        assertEquals(record.getAuthors().get(0).getName(), "Екатерина Юрьевна");
        assertEquals(record.getAuthors().get(0).getSurname(), "Антошина");
        assertEquals(record.getAuthors().get(1).getName(), "Дмитрий Юрьевич");
        assertEquals(record.getAuthors().get(1).getSurname(), "Чалый");
        assertEquals(record.getTitle(), "Семантические средства обеспечения безопасности в программно-конфигурируемых сетях");
        assertEquals(record.getUrl(), "http://mais-journal.ru/jour/article/download/612/476");
        assertEquals(record.getYear(), "2017");


        record = getRecord("http://mais-journal.ru/jour/article/view/617");
        assertEquals(record.getDoi(), "http://dx.doi.org/10.18255/1818-1015-2017-6-811-815");

        assertEquals(record.getAuthors().size(), 2);
        assertEquals(record.getAuthors().get(0).getName(), "Владимир Николаевич");
        assertEquals(record.getAuthors().get(0).getSurname(), "Бойков");
        assertEquals(record.getAuthors().get(1).getName(), "Мария Сергеевна");
        assertEquals(record.getAuthors().get(1).getSurname(), "Каряева");
        assertEquals(record.getTitle(), "Поэтология: задачи построения тезауруса и спецификации стихового текста");
        assertEquals(record.getUrl(), "http://mais-journal.ru/jour/article/download/617/481");
        assertEquals(record.getYear(), "2017");
    }
}
