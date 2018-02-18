package com.github.stagirs.crawler.downloader;



import com.github.stagirs.crawler.model.business.Record;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
public class MisisTest extends Misis {
    @Test
    public void recordTest() throws IOException {
        Record record = getRecord("http://met.misis.ru/jour/article/view/67");

        assertEquals(record.getAuthors().size(), 1);
        assertEquals(record.getAuthors().get(0).getName(), "Т.");
        assertEquals(record.getAuthors().get(0).getSurname(), "Белик");
        assertEquals(record.getTitle(), "ОСОБЕННОСТИ ПОРИСТОГО КРЕМНИЯ, ПОЛУЧЕННОГО ХИМИЧЕСКИМ ТРАВЛЕНИЕМ");
        assertEquals(record.getUrl(), "http://met.misis.ru/jour/article/view/67/60");
        assertEquals(record.getAnnotation(), "Проведено комплексное исследование пористых слоев, полученных химическим травлением, определена взаимосвязь между условиями получения и свойствами пористых слоев.Предложена модель проводимости пористых слоев, аналогичная механизмам проводимости аморфного кремния. Показано, что пористый кремний, полученный химическим травлением, по своим фотолюминесцентным и светоотражающим свойствам не уступает пористым слоям, полученным электрохимическим способом.");


        record = getRecord("http://met.misis.ru/jour/article/view/90");

        assertEquals(record.getAuthors().size(), 2);
        assertEquals(record.getAuthors().get(0).getName(), "В.");
        assertEquals(record.getAuthors().get(0).getSurname(), "Румянцев");
        assertEquals(record.getAuthors().get(1).getName(), "С.");
        assertEquals(record.getAuthors().get(1).getSurname(), "Федоров");
        assertEquals(record.getTitle(), "ОСОБЕННОСТИ РАСПРОСТРАНЕНИЯ ЭЛЕКТРОМАГНИТНЫХ ВОЗБУЖДЕНИЙ В НЕИДЕАЛЬНОМ 1D ФОТОННОМ КРИСТАЛЛЕ НА ОСНОВЕ КРЕМНИЯ И ЖИДКОГО КРИСТАЛЛА");
        assertEquals(record.getUrl(), "http://met.misis.ru/jour/article/view/90/82");
        assertEquals(record.getAnnotation(), "Показано, что оптические характеристики  несовершенного фотонного кристалла могут значительно меняться за счет трансформации спектра фотонных мод, вызванной присутствием примесных слоев. Изучен спектр фотонных мод в модели неидеальной сверхрешетки — «одномерного кри- сталла»  с двумя элементами (слоями) в элементарной ячейке: первый слой — кремний, а второй — жидкий кристалл. Исследованы особенности зависимости ширины нижайшей запрещенной зоны от концентрации хаотически внедренных примесных слоев (в том числе плазмы) в такой системе. Показано, что развитая на основе при- ближения виртуального кристалла теория позволяет выполнять численный расчет концентрационной зависимости соответствующих оптических характеристик. Последнее обстоятельство значительно расширяет возможности моделирования подобных композитных материалов с заданными свойствами.");

    }
}
