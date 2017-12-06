package com.github.stagirs.crawler.downloader;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import com.github.stagirs.crawler.model.business.Record;

public class DiscreteMathTest extends DiscreteMath {
  @Test
  public void recordTest() throws IOException {
	  Document document = Jsoup.parse(FileUtils.readFileToString(new File("src/test/resources/DiscreteMath/DiscreteMath_test_article.html"), "cp1251"));
      Record record = getRecord(document);
      assertEquals(record.getDoi(), "https://doi.org/10.4213/dm1268");
      assertEquals(record.getAuthors().size(), 2);  
      assertEquals(record.getAuthors().get(0).getName(), "В. А.");
      assertEquals(record.getAuthors().get(0).getSurname(), "Копытцев");
      assertEquals(record.getAuthors().get(1).getName(), "В. Г.");
      assertEquals(record.getAuthors().get(1).getSurname(), "Михайлов");
      assertEquals(record.getKeyWords().size(), 3); 
      assertEquals(record.getPages(), "том 26, выпуск 1, страницы 75–84");  
      assertEquals(record.getSource(), "Дискретная математика");  
      assertEquals(record.getTitle(), "Оценка точности аппроксимациив предельной теореме Б. А. Севастьянова и ее применение в задаче о случайных включениях");  
      assertEquals(record.getUrl(), "http://www.mathnet.ru/php/getFT.phtml?jrnid=dm&paperid=1268&what=fullt&option_lang=rus");  
      assertEquals(record.getYear(), "2014");   
      assertEquals(record.getBibliography().size(), 21);
      assertEquals(record.getBibliography().get(0), "Севастьянов Б. А., “Предельный закон Пуассона в схеме сумм зависимых случайных величин”, Теория вероятн. и ее применен., 17:4 (1972), 733–738");
      assertEquals(record.getBibliography().get(20), "Barbour A. D., Holst L., Janson S., Poisson Approximation, Oxford University Press, Oxford, 1992");
      
  }
}
