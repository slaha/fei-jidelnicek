package cz.upce.fei.jidelak.utils;

import cz.upce.fei.jidelak.model.CssTyp;

/**
 * Created with IntelliJ IDEA.
 * Date: 2013-04-15
 * Time: 20:06
 */
public class HtmlHelper {

	private static final String GLOBAL_HEADER =
			"<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"%s\"></head><body>";
	private static final String HTML_END = "</body></html>";


	public static String surroundHtml(String html, CssTyp css) {
		String header =  String.format(GLOBAL_HEADER, css.getCss());

		return header + html + HTML_END;
	}
}
