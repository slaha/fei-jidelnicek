package cz.upce.fei.jidelak.view;

import android.app.Dialog;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import cz.upce.fei.jidelak.R;
import cz.upce.fei.jidelak.utils.DialogUtils;

public class SettingsActivity extends PreferenceActivity implements OnPreferenceChangeListener {

	public static String PREFERENCE_IS_FIRST_RUN = "pref_first_run";
	public static String PREFERENCE_DOWNLOAD_FEI = "pref_download_fei";
	public static String PREFERENCE_DEFAULT_SCREEN = "pref_default_fragment";
	public static String PREFERENCE_LAST_UPDATE = "pref_last_update";
	public static String PREFERENCE_CSS = "pref_look_css";

	public static final int NO_CHANGE = 0;
	public static final int CHANGED_FRAGMENT = 1;
	public static final int CHANGED_STYL = 2;

  private int result = NO_CHANGE;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.settings);

		setResult(NO_CHANGE);

		findPreference(PREFERENCE_DEFAULT_SCREEN).setOnPreferenceChangeListener(this);
		findPreference(PREFERENCE_DOWNLOAD_FEI).setOnPreferenceChangeListener(this);
		findPreference(PREFERENCE_CSS).setOnPreferenceChangeListener(this);
	}

	@Override
	public boolean onPreferenceChange(Preference arg0, Object arg1) {

		if (arg0 == findPreference(PREFERENCE_CSS)) {
			if (!((ListPreference) arg0).getValue().equals(arg1)) {
			  result = result | CHANGED_STYL;
			  setResult(result);
			}
		} else {
      result = result | CHANGED_FRAGMENT;
			setResult(result);
		}

		return true;
	}

}