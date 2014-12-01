package docr.spring;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Odds {

	public static void main(String[] args) throws IOException {
		Odds odds = new Odds();
		odds.run();
	}

	public void run() throws IOException {

		Document doc = Jsoup.parse(new File("/Users/graham/Desktop/six-nations-2013.html"), "UTF-8");

		Element table = doc.getElementById("tournamentTable");

		Elements body = table.getElementsByTag("tbody");
		
		Elements rows = body.get(0).getElementsByTag("tr");
		
		

		for(int rowNumber = 2; rowNumber < rows.size(); rowNumber++){
		
			Element row = rows.get(rowNumber);
			if(row.hasClass("nob-border")){
				System.out.println(row.getElementsByTag("th").get(0).getElementsByClass("datet"));
			}
		}
	}
}
