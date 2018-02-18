package com.github.stagirs.crawler.downloader;



import com.github.stagirs.crawler.model.business.Record;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
public class PdiTest extends Pdi {
    @Test
    public void recordTest() throws IOException {
        Record record = getRecord("http://andjournal.sgu.ru/ru/articles/stabilizaciya-chastoty-girotrona-pod-vliyaniem-vneshnego-monohromaticheskogo-signala-ili");

        assertEquals(record.getAuthors().size(), 8);
        assertEquals(record.getAuthors().get(0).getSurname(), "Новожилова");
        assertEquals(record.getAuthors().get(1).getSurname(), "Денисов");
        assertEquals(record.getAuthors().get(2).getSurname(), "Глявин");
        assertEquals(record.getAuthors().get(3).getSurname(), "Рыскин");
        assertEquals(record.getAuthors().get(4).getSurname(), "Бакунин");
        assertEquals(record.getAuthors().get(5).getSurname(), "Богдашов");
        assertEquals(record.getAuthors().get(6).getSurname(), "Мельникова");
        assertEquals(record.getAuthors().get(7).getSurname(), "Фокин");
        assertEquals(record.getTitle(), "СТАБИЛИЗАЦИЯ ЧАСТОТЫ ГИРОТРОНА ПОД ВЛИЯНИЕМ ВНЕШНЕГО МОНОХРОМАТИЧЕСКОГО СИГНАЛА ИЛИ ОТРАЖЕННОЙ ОТ НАГРУЗКИ ВОЛНЫ: ОБЗОР");
        assertEquals(record.getUrl(), "http://andjournal.sgu.ru/sites/default/files/short_text/a2017no1r004.pdf");
        assertEquals(record.getAnnotation(),"В работе исследована проблема захвата частоты гиротрона внешней монохроматической волной и стабилизация частоты гиротрона волной, отраженной от нерезонансной или резонансной нагрузки. Хотя в последние десятилетия воздействие внешнего монохроматического сигнала или отраженной волны на режим работы гиротрона исследовалось в ряде публикаций, конкретные схемы стабилизации частоты гиротрона не обсуждались. Задача стабилизации частоты сигналом, попадающим в резонатор из внешнего электродинамического тракта, стала особенно актуальной после разработки в Институте прикладной физики РАН квазиоптического преобразователя, который позволяет трансформировать большую часть поступающей из выходного тракта волны в рабочую моду мощного гиротрона.");

        record = getRecord("http://andjournal.sgu.ru/ru/articles/ekologo-ekonomicheskie-analogii");

        assertEquals(record.getAuthors().size(), 1);
        assertEquals(record.getAuthors().get(0).getSurname(), "Алексеев");
        assertEquals(record.getAnnotation()," ");
        assertEquals(record.getTitle(), "ЭКОЛОГО-ЭКОНОМИЧЕСКИЕ АНАЛОГИИ");
        assertEquals(record.getUrl(), "http://andjournal.sgu.ru/sites/default/files/ilovepdf_com-4-13.pdf");

    }
}