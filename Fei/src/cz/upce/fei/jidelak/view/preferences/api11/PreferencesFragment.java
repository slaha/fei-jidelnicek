package cz.upce.fei.jidelak.view.preferences.api11;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import cz.upce.fei.jidelak.R;
import cz.upce.fei.jidelak.view.preferences.PreferencesConsts;

/** Created with IntelliJ IDEA. User: slaha Date: 21.9.13 Time: 12:12 */
public class PreferencesFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

	ISetResult result;

	private Preference defaultFragmentPreference;
	private Preference downloadFeiPreference;
	private ListPreference stylePreference;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.settings);

		this.result = (ISetResult) getActivity();

		defaultFragmentPreference = findPreference(PreferencesConsts.Keys.PREFERENCE_DEFAULT_SCREEN);
		defaultFragmentPreference.setOnPreferenceChangeListener(this);

		downloadFeiPreference = findPreference(PreferencesConsts.Keys.PREFERENCE_DOWNLOAD_FEI);
		downloadFeiPreference.setOnPreferenceChangeListener(this);

		stylePreference = (ListPreference) findPreference(PreferencesConsts.Keys.PREFERENCE_CSS);
		stylePreference.setOnPreferenceChangeListener(this);
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object arg1) {

		if (preference == stylePreference) {
			if (!stylePreference.getValue().equals(arg1)) {
				result.addToResult(PreferencesConsts.Results.CHANGED_STYL);
			}
		} else {
			result.addToResult(PreferencesConsts.Results.CHANGED_FRAGMENT);
		}

		return true;
	}
}