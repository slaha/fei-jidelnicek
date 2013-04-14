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
public class FeiHtmlParserImpl implements IHtmlParser {

	private static final String DIV_CLASS = "content-text";

	@Override
	public String parse(String html) {
		Document document = Jsoup.parse(html);

		Elements divClasses = document.getElementsByClass(DIV_CLASS);
		if (divClasses.isEmpty() || divClasses.size() > 1) {
			return html;
		}

		Element divClass = divClasses.get(0);

		return divClass.toString();
	}

}
