package cz.upce.fei.jidelak.parser;

import cz.upce.fei.jidelak.model.JidelnicekTyp;

/**
 * Created with IntelliJ IDEA.
 * User: slaha
 * Date: 2013-04-14
 * Time: 10:17
 * To change this template use File | Settings | File Templates.
 */
public class HtmlParserFactory {

	public static IHtmlParser getParser(JidelnicekTyp typ) {
		switch (typ) {

			case KAMPUS:
				return new KampusHtmlParserImpl();
			case FEI:
				return new FeiHtmlParserImpl();
		}

		return null;
	}
}
