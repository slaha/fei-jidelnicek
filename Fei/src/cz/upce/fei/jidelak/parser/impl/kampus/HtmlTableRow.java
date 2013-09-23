package cz.upce.fei.jidelak.parser.impl.kampus;

import cz.upce.fei.jidelak.parser.impl.KampusHtmlParserImpl;
import cz.upce.fei.jidelak.utils.ElementUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/** Created with IntelliJ IDEA. User: slaha Date: 21.9.13 Time: 22:19 */
public class HtmlTableRow {

	private final Element row;
	private boolean lastRowWasEmpty;

	public HtmlTableRow(Element row) {
		this.row = row;
	}

	public void tryParse(Day currentDay) {
		final Elements tds = row.getElementsByTag(KampusHtmlParserImpl.TD);

		Food food = new Food();

		for (int i = 0; i < tds.size(); i++) {
			Element td = tds.get(i);
			StringBuilder element = new StringBuilder();
			if (ElementUtils.shouldProceed(td, element)) {
				String value = element.toString();
				if (i == 0) {
					currentDay.setDayOrDate(value);
				} else {
					food.setValue(value);
				}
			}
		}

		lastRowWasEmpty = food.isEmpty();
		if (!lastRowWasEmpty) {
			currentDay.addFood(food);
		}
	}

	public boolean startNewDay() {
		return lastRowWasEmpty;
	}
}
