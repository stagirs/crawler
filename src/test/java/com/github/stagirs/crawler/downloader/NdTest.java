package com.github.stagirs.crawler.downloader;



import com.github.stagirs.crawler.model.business.Record;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
public class NdTest extends Nd {
    @Test
    public void recordTest() throws IOException {
        Record record = getRecord("http://nd.ics.org.ru/article-6175/");

        assertEquals(record.getAuthors().size(), 3);
        assertEquals(record.getAuthors().get(0).getSurname(), "Борисов");
        assertEquals(record.getAuthors().get(0).getName(), "А.");
        assertEquals(record.getAuthors().get(1).getSurname(), "Казаков");
        assertEquals(record.getAuthors().get(1).getName(), "А.");
        assertEquals(record.getAuthors().get(2).getSurname(), "Пивоварова");
        assertEquals(record.getAuthors().get(2).getName(), "Е.");
        assertEquals(record.getTitle(), "Регулярная и хаотическая динамика в «резиновой» модели волчка Чаплыгина");
        assertEquals(record.getUrl(), "http://nd.ics.org.ru/upload/iblock/d84/nd1702009.pdf");
        assertEquals(record.getAnnotation(),"В работе исследуется качение динамически несимметричного неуравновешенного шара (волчка Чаплыгина) в поле тяжести по плоскости в предположении отсутствия проскальзывания и прокручивания в точке контакта. Приводится описание странных аттракторов, существующих в системе, а также подробно описывается сценарий рождения одного из них через последовательность бифуркаций удвоения периода. Кроме того, проанализирована динамика системы в абсолютном пространстве и показано, что поведение точки контакта при наличии в системе странных аттракторов существенно зависит от характеристик аттрактора и может иметь как хаотический, так и близкий к квазипериодическому характер. ");

        record = getRecord("http://nd.ics.org.ru/article-5614/");

        assertEquals(record.getAuthors().size(), 1);
        assertEquals(record.getAuthors().get(0).getSurname(), "Чернова");
        assertEquals(record.getAuthors().get(0).getName(), "А.");
        assertEquals(record.getTitle(), "Ограничение контактного угла в задаче о капле жидкости на вибрирующей подложке");
        assertEquals(record.getUrl(), "http://nd.ics.org.ru/upload/iblock/9f7/nd1702002.pdf");
        assertEquals(record.getAnnotation(),"В данной работе изучается процесс колебания капли жидкости малого объема, лежащей на вибрирующей гидрофобной недеформируемой подложке. Исследование проводится в постановке вязкой несжимаемой жидкости на математической модели объема жидкости — Volume of Fluid (VoF). Изучены вопросы учета динамического изменения контактного угла в тройной точке жидкость/подложка/воздух, показана целесообразность применения моделей гистерезиса контактного угла для данных задач. Выявлен и описан механизм возбуждения поверхностных волн. Особое внимание уделяется топологическим особенностям формирующихся в капле внутренних течений. Подробно рассматривается связь между взаимодействием различных поверхностных эффектов, трансформацией внутренних течений, величиной ограничения изменения контактного угла и фазой колебания подложки. Выявлена зависимость степени деформации свободной поверхности, интенсивности поверхностных эффектов и частоты образования топологических особенностей внутренних течений от допустимого диапазона изменения угла смачивания. Полученные численные результаты согласуются с известными экспериментальными данными. ");


    }
}