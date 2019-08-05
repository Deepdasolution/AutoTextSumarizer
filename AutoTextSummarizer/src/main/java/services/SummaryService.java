package services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import models.Summary;

@Service
public class SummaryService {
	
	public Summary getSourceContent(Summary summary)
	{
		String url=summary.getSourceUrl();
		System.out.println("Url: "+url);
		String a="";
		try {
	      Document document = Jsoup.connect(url).get();
	     // System.out.println(document.title());
	    
	      String html =String.valueOf(document.body());
	      Document doc = Jsoup.parse(html);
	      System.out.println(doc.title());
	      Elements paragraphs = doc.getElementsByTag("p");
	      a=paragraphs.text();
	      System.out.println(a);
	      summary.setSourceText(a);
	      return summary;
	     }
		catch(Exception e)
			{
				System.out.println("Error Aayo Url ma"+e);
				return null;
					
			}
		
	}
	

}
