package cz.upce.fei.jidelak.utils;

import java.util.Calendar;

/** Created with IntelliJ IDEA. User: slaha Date: 31.7.13 Time: 16:32 */
public class DateUtils {

	public static int getNumberOfCurrentWeek() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	public static boolean isCurrentWeek(int weekNumber) {
		int currentWeek = getNumberOfCurrentWeek();

		return currentWeek == weekNumber;
	}
}
