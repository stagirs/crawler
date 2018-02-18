package com.github.stagirs.crawler.downloader;

import com.github.stagirs.crawler.model.business.Record;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SpbstuTest extends Spbstu {

    @Test
    public void recordTest() throws IOException {
        Record record = getRecord("http://ntv.spbstu.ru/physics/article/P.2.242.2016_03/");

        assertEquals(record.getAuthors().size(), 2);
        assertEquals(record.getAuthors().get(0).getSurname(), "Панькин");
        assertEquals(record.getAuthors().get(0).getName(), "Д.В.");
        assertEquals(record.getAuthors().get(1).getSurname(), "Смирнов");
        assertEquals(record.getAuthors().get(1).getName(), "М.Б.");
        assertEquals(record.getTitle(), "Возможность применения спектроскопии комбинационного рассеяния света для оценки толщины интерфейсного слоя в сверхрешетках AlN/GaN");
        assertEquals(record.getUrl(), "http://ntv.spbstu.ru/fulltext/P.2.242.2016_03.PDF");
        assertEquals(record.getAnnotation(),"В рамках модели диэлектрического континуума рассматривается поведение полярных оптических фононов в четырехслойных периодических системах со слоями GaN, AlN и твердым раствором на их основе. Аналитически установлен вид векового уравнения, описывающего свойства данного типа фононов в системах с различным отношением значений толщины слоев в практически важном короткопериодном пределе. Модель бинарной слоистой структуры, традиционно применяемая ранее для описания свойств сверхрешеток (СР), дополнена двумя слоями, имеющими диэлектрические свойства, характерные для твердого раствора Al0,5Ga0,5N. Такую модель можно применять для описания тонкопериодных бинарных СР с размытыми интерфейсами. На основании полученных результатов сделано заключение, что введение дополнительного слоя приводит к появлению дополнительных слабоинтенсивных мод в спектрах комбинационного рассеяния, величина частотного расщепления которых зависит от толщины переходного слоя. В то же время значения частот основных интенсивных мод в указанных спектрах слабо зависят от толщины интерфейса, но весьма чувствительны к отношению значений толщины слоев GaN и AlN.");

        record = getRecord("http://ntv.spbstu.ru/physics/article/05/");

        assertEquals(record.getAuthors().size(), 1);
        assertEquals(record.getAuthors().get(0).getSurname(), "Степанов");
        assertEquals(record.getAuthors().get(0).getName(), "А.Б.");
        assertEquals(record.getTitle(), "Применение нейронных сетей при синтезе вейвлетов для непрерывного вейвлет-преобразования");
        assertEquals(record.getUrl(), "http://ntv.spbstu.ru/fulltext/05.PDF");
        assertEquals(record.getAnnotation(),"Рассматриваются основные принципы синтеза вейвлетов для непрерывного вейвлет-преобразования с применением искусственных нейронных сетей. Приводятся теоретические и практические обоснования выбора математического аппарата.");


    }
}

