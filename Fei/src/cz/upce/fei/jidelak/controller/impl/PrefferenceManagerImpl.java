package cz.upce.fei.jidelak.controller.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import cz.upce.fei.jidelak.R;
import cz.upce.fei.jidelak.controller.IPrefferenceManager;
import cz.upce.fei.jidelak.model.CssTyp;
import cz.upce.fei.jidelak.model.MenuType;
import cz.upce.fei.jidelak.view.preferences.PreferencesConsts;

/**
 * Created with IntelliJ IDEA.
 * User: slaha
 * Date: 2013-04-15
 * Time: 20:41
 */
public class PrefferenceManagerImpl implements IPrefferenceManager {

	private SharedPreferences preferences;
	private Resources resources;

	public PrefferenceManagerImpl(Context ctx) {
		this.resources = ctx.getResources();
		this.preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
	}

	@Override
	public CssTyp getCssTyp() {
		String defaultCss = preferences.getString(PreferencesConsts.Keys.PREFERENCE_CSS,
				CssTyp.BLACK_ON_WHITE.toString());

		String array[] = resources.getStringArray(R.array.css);
		for (int i = 0; i < array.length; i++) {
			String typ = array[i].toString();
			if (defaultCss.equals(typ)) {
				return CssTyp.values()[i];
			}
		}
		return CssTyp.BLACK_ON_WHITE;
	}

	@Override
	public boolean isFirstRun() {
		return preferences.getBoolean(PreferencesConsts.Keys.PREFERENCE_IS_FIRST_RUN, true);
	}

	@Override
	public void setNotFirstRun() {
		SharedPreferences.Editor e = preferences.edit();
		e.putBoolean(PreferencesConsts.Keys.PREFERENCE_IS_FIRST_RUN, false);
		e.commit();
	}

	@Override
	public boolean isDownloadFeiEnabled() {

		return preferences.getBoolean(PreferencesConsts.Keys.PREFERENCE_DOWNLOAD_FEI, false);
	}

	@Override
	public boolean isDefaultScreen(MenuType screen) {

		String defaultScreen = preferences.getString(PreferencesConsts.Keys.PREFERENCE_DEFAULT_SCREEN,
				MenuType.KAMPUS.toString());

		return screen == MenuType.fromTitle(defaultScreen);
	}

	@Override
	public int getLastUpdateWeek() {
		return preferences.getInt(PreferencesConsts.Keys.PREFERENCE_LAST_UPDATE, -1);
	}

	@Override
	public void updateWeekNumber(int uptadeTo) {
		if (uptadeTo < 0 || uptadeTo > 54) {
			throw new IllegalArgumentException("Week number should be from interval <0; 54>");
		}

		SharedPreferences.Editor e = preferences.edit();

		e.putInt(PreferencesConsts.Keys.PREFERENCE_LAST_UPDATE, uptadeTo);

		e.commit();
	}
}
