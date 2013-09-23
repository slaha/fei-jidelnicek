package cz.upce.fei.jidelak.parser.impl;

import cz.upce.fei.jidelak.parser.IHtmlParser;
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
public class FeiHtmlParserImpl implements IHtmlParser {

	private static final String DIV_CLASS = "content-text";

	@Override
	public String parse(String html) {
		Document document = Jsoup.parse(html);

		Elements divClasses = document.getElementsByClass(DIV_CLASS);
		if (divClasses.size() != 1) {
			//..no div with class "content-text" or more than one. Just return all
			return html;
		}

		Element divClass = divClasses.get(0);

		return divClass.toString();
	}

}
