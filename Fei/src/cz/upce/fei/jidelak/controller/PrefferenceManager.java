package cz.upce.fei.jidelak.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import cz.upce.fei.jidelak.R;
import cz.upce.fei.jidelak.model.CssTyp;
import cz.upce.fei.jidelak.model.JidelnicekTyp;
import cz.upce.fei.jidelak.view.SettingsActivity;

import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: slaha
 * Date: 2013-04-15
 * Time: 20:41
 */
public class PrefferenceManager {

	private Context ctx;

	public PrefferenceManager(Context ctx) {
		this.ctx = ctx;
	}

	public CssTyp getCssTyp() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
		String defaultCss = preferences.getString(SettingsActivity.PREFERENCE_CSS, CssTyp.BLACK_ON_WHITE.toString());

		CharSequence array[] = ctx.getResources().getTextArray(R.array.css);
		for (int i = 0; i < array.length; i++) {
			String typ = array[i].toString();
			if (defaultCss.equals(typ)) {
				return CssTyp.values()[i];
			}
		}
		return CssTyp.BLACK_ON_WHITE;
	}

	public boolean isFirstRun() {

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);

		boolean firstRun = preferences.getBoolean(SettingsActivity.PREFERENCE_IS_FIRST_RUN, true);

		if (firstRun) {
			SharedPreferences.Editor e = preferences.edit();
			e.putBoolean(SettingsActivity.PREFERENCE_IS_FIRST_RUN, !firstRun);
			e.commit();
		}

		return firstRun;
	}

	public boolean isDownloadFeiEnabled() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);

		return preferences.getBoolean(SettingsActivity.PREFERENCE_DOWNLOAD_FEI, false);
	}

	public boolean isDefaultScreen(JidelnicekTyp screen) {

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
		String defaultScreen = preferences.getString(SettingsActivity.PREFERENCE_DEFAULT_SCREEN, JidelnicekTyp.KAMPUS.toString());

		String screenString = screen.toString();
		return defaultScreen.equalsIgnoreCase(screenString);
	}

	public boolean isNewWeek() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
		int lastUpdate = preferences.getInt(SettingsActivity.PREFERENCE_LAST_UPDATE, -1);
		//..no update anytime
		if (lastUpdate == -1) {
			return false;
		}
		int currentWeek = getNumberOfCurrentWeek();
		return (currentWeek > lastUpdate) || (currentWeek == 1 && lastUpdate > 1); //new year
	}

	public void updateWeekNumber() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
		SharedPreferences.Editor e = preferences.edit();

		int currentWeek = getNumberOfCurrentWeek();

		e.putInt(SettingsActivity.PREFERENCE_LAST_UPDATE, currentWeek);

		e.commit();
	}

	private int getNumberOfCurrentWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.WEEK_OF_YEAR);
	}
}
