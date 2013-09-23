package cz.upce.fei.jidelak.parser.impl;

import cz.upce.fei.jidelak.parser.IHtmlParser;
import cz.upce.fei.jidelak.parser.impl.kampus.KampusMenu;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created with IntelliJ IDEA.
 * User: slaha
 * Date: 2013-04-14
 * Time: 10:20
 */
public class KampusHtmlParserImpl implements IHtmlParser {

	private static final String CONTENT_CLASS = "contentspace";
	private static final String TABLE = "table";
	public static final String TR = "tr";
	public static final String TD = "td";

	@Override
	public String parse(String html) {
		Document document = Jsoup.parse(html);

		Elements contentClasses = document.getElementsByClass(CONTENT_CLASS);

		if (contentClasses.size() > 0) {
			Element tableContent = contentClasses.get(0);
			Elements tables = tableContent.getElementsByTag(TABLE);

			if (tables.size() < 3) {
				//..we need the 3rd one. In this case return the whole content
				return tableContent.toString();
			}
			Element table = tables.get(2);
			KampusMenu kampusMenu = new KampusMenu(table);
			try {
				kampusMenu.tryParse();
				return kampusMenu.toString();
			} catch (Exception ignored) {
				//nothing
			}
			return table.toString();
		}

		return html;
	}
}
