package cz.upce.fei.jidelak.view.preferences.api0;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import cz.upce.fei.jidelak.R;
import cz.upce.fei.jidelak.view.preferences.PreferencesConsts;

public class SettingsActivity extends PreferenceActivity implements OnPreferenceChangeListener {

	private int result = PreferencesConsts.Results.NO_CHANGE;

	private Preference defaultFragmentPreference;
	private Preference downloadFeiPreference;
	private ListPreference stylePreference;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.settings);

		defaultFragmentPreference = findPreference(PreferencesConsts.Keys.PREFERENCE_DEFAULT_SCREEN);
		defaultFragmentPreference.setOnPreferenceChangeListener(this);

		downloadFeiPreference = findPreference(PreferencesConsts.Keys.PREFERENCE_DOWNLOAD_FEI);
		downloadFeiPreference.setOnPreferenceChangeListener(this);

		stylePreference = (ListPreference) findPreference(PreferencesConsts.Keys.PREFERENCE_CSS);
		stylePreference.setOnPreferenceChangeListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onPreferenceChange(Preference preference, Object arg1) {

		if (preference == stylePreference) {
			if (!stylePreference.getValue().equals(arg1)) {
				result = result | PreferencesConsts.Results.CHANGED_STYL;
			}
		} else {
			result = result | PreferencesConsts.Results.CHANGED_FRAGMENT;
		}

		return true;
	}

	@Override
	public void finish() {
		setResult(result);
		super.finish();
	}
}