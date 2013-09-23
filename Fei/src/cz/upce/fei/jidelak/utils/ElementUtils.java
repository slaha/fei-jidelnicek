package cz.upce.fei.jidelak.utils;

import org.jsoup.nodes.Element;

/** Created with IntelliJ IDEA. User: slaha Date: 21.9.13 Time: 22:21 */
public class ElementUtils {

	public static String trimTextInElement(Element td) {
		return td.text().replace((char) 160, ' ').trim();
	}

	public static boolean shouldProceed(Element td, StringBuilder builder) {
		if (td == null)
			return false;
		String value = trimTextInElement(td);
		if (value.isEmpty())
			return false;

		builder.append(value);
		return true;
	}
}
