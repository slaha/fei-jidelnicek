package cz.upce.fei.jidelak;

import cz.upce.fei.jidelak.utils.PreferencesUtils;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingsActivity extends PreferenceActivity {
	
	public static String LOGIN_PREFERENCE = "pref_login";
	public static String PASSWORD_PREFERENCE = "pref_password";
	
	EditTextPreference loginPreference;
	EditTextPreference passPreference;
	
	SharedPreferences preferences;
	
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
       addPreferencesFromResource(R.xml.settings);
       
       loginPreference = (EditTextPreference) findPreference(LOGIN_PREFERENCE);
       passPreference = (EditTextPreference) findPreference(PASSWORD_PREFERENCE);   
       
       preferences = PreferenceManager.getDefaultSharedPreferences(this);
       preferences.getString(LOGIN_PREFERENCE, "");
       preferences.getString(PASSWORD_PREFERENCE, "");
       
       
    }

}