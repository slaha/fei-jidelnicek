package cz.upce.fei.jidelak.utils;

import android.os.Build;

/** Created with IntelliJ IDEA. User: slaha Date: 21.9.13 Time: 19:23 */
public class SettingsUtils {

	public static Class<?> getSettingsActivity() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			return cz.upce.fei.jidelak.view.preferences.api11.SettingsActivity.class;
		}
		return cz.upce.fei.jidelak.view.preferences.api0.SettingsActivity.class;
	}
}
