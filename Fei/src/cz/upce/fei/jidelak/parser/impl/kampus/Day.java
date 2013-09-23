package cz.upce.fei.jidelak.parser.impl.kampus;

import java.util.ArrayList;
import java.util.List;

/** Created with IntelliJ IDEA. User: slaha Date: 21.9.13 Time: 22:18 */
public class Day {

	private String day, date;
	private List<Food> foods;

	Day() {
		this.foods = new ArrayList<Food>();
	}

	void setDayOrDate(String value) {
		if (this.day == null) {
			this.day = value;
		} else if (date == null) {
			this.date = value;
		}
	}

	public void addFood(Food food) {
		this.foods.add(food);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<p>");
		if (day != null && date != null) {
			sb.append("<strong>").append(day).append(" ").append(date).append("</strong><br>");
		}

		for (Food food : foods) {
			sb.append(food.toString()).append("<br>");
		}
		return sb.toString();
	}
}
