package cz.upce.fei.jidelak.view.preferences.api11;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import cz.upce.fei.jidelak.view.preferences.PreferencesConsts;

public class SettingsActivity extends PreferenceActivity implements ISetResult {

	private int result = PreferencesConsts.Results.NO_CHANGE;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferencesFragment()).commit();
	}

	@Override
	public void finish() {
		setResult(result);
		super.finish();
	}

	@Override
	public void addToResult(int result) {
		this.result |= result;
	}
}