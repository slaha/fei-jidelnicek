package cz.upce.fei.jidelak.parser;

import cz.upce.fei.jidelak.model.MenuType;
import cz.upce.fei.jidelak.parser.impl.FeiHtmlParserImpl;
import cz.upce.fei.jidelak.parser.impl.KampusHtmlParserImpl;

/**
 * Created with IntelliJ IDEA.
 * User: slaha
 * Date: 2013-04-14
 * Time: 10:17
 */
public class HtmlParserFactory {

	public static IHtmlParser createParser(MenuType typ) {
		switch (typ) {

		case KAMPUS:
			return new KampusHtmlParserImpl();
		case FEI:
			return new FeiHtmlParserImpl();
		}

		throw new IllegalArgumentException("Not valid typ. Was " + typ + ". Should be one of " + MenuType.values());
	}
}
