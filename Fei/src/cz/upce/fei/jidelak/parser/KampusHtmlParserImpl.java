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
 */
public class KampusHtmlParserImpl implements IHtmlParser {

	private static final String CONTENT_CLASS = "contentspace";
	private static final String TABLE = "table";
	private static final String TR = "tr";
	private static final String TD = "td";

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
			String column = tabulka.toString();
			try {
				column = createOneColumnTable(tabulka);
			} catch (Exception ignored) {
				//nothing
			}
			return  column;
		}

		return html;
	}

	private String createOneColumnTable(Element tabulka) {
		StringBuilder sb = new StringBuilder();

		Elements trs = tabulka.getElementsByTag(TR);

		StringBuilder denSB = new StringBuilder();
		denSB.append("<p><strong>");
		StringBuilder jidlaSB = new StringBuilder();
		jidlaSB.append("<p>");
		StringBuilder jidlaDenSB = new StringBuilder();
		for (Element element : trs) {

			Elements tds = element.getElementsByTag(TD);
			for (int i = 0; i < tds.size(); i++) {
				String obsah = tds.get(i).text();
				obsah = obsah.replace((char)160, ' ');
				obsah = obsah.trim();
				if (i == 0) {
					denSB
							.append(obsah)
							.append(' ');
				} else {
					jidlaDenSB.append(obsah);
					if (obsah.length() > 0) {
						jidlaDenSB.append(' ');
					}
				}
			}
			if (jidlaDenSB.length() == 0) {
				denSB.append("</strong></p>");


				sb.append(denSB.toString());
				sb.append(jidlaSB.toString());

				denSB = new StringBuilder();
				denSB.append("<p><strong>");
				jidlaSB = new StringBuilder();
			} else {
				jidlaSB
						.append(jidlaDenSB.toString())
						.append("<br>");

				jidlaDenSB = new StringBuilder();
			}
		}

		return sb.toString();
	}
}
