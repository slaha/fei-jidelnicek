package cz.upce.fei.jidelak.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created with IntelliJ IDEA.
 * User: slaha
 * Date: 2013-04-14
 * Time: 10:20
 * To change this template use File | Settings | File Templates.
 */
public class KampusHtmlParserImpl implements IHtmlParser {

	private static final String CONTENT_CLASS = "contentspace";
	private static final String TABLE = "table";

	@Override
	public String parse(String html) {
		Document document = Jsoup.parse(html);

		Elements contentClasses = document.getElementsByClass(CONTENT_CLASS);

		if (contentClasses.size() > 0) {
			Element tableContent = contentClasses.get(0);
			Elements tabulky = tableContent.getElementsByTag(TABLE);

			if (tabulky.size() < 3) {
				return tableContent.toString();
			}
			Element tabulka = tabulky.get(2);
			tabulka.attr("width", "500");
			return  tabulka.toString();
		}

		return html;
	}
}
