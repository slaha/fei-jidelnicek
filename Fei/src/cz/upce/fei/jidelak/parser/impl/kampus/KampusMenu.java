package cz.upce.fei.jidelak.parser.impl.kampus;

import cz.upce.fei.jidelak.parser.impl.KampusHtmlParserImpl;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/** Created with IntelliJ IDEA. User: slaha Date: 21.9.13 Time: 22:17 */
public class KampusMenu {
	private final Element table;
	private final List<Day> days;

	public KampusMenu(Element table) {
		this.table = table;
		this.days = new ArrayList<Day>();
	}

	public void tryParse() {
		final Elements rows = table.getElementsByTag(KampusHtmlParserImpl.TR);

		Day currentDay = new Day();
		for (Element element : rows) {
			HtmlTableRow row = new HtmlTableRow(element);
			row.tryParse(currentDay);
			if (row.startNewDay()) {
				days.add(currentDay);
				currentDay = new Day();
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (Day day : days) {
			sb.append(day.toString());
		}

		return sb.toString();
	}
}
