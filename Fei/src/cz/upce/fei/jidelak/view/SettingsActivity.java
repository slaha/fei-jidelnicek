package cz.upce.fei.jidelak.view;

import android.R.string;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import cz.upce.fei.jidelak.R;

public class SettingsActivity extends PreferenceActivity implements OnPreferenceChangeListener {
	
	public static String PREFERENCE_IS_FIRST_RUN = "pref_first_run";
	public static String PREFERENCE_DOWNLOAD_FEI = "pref_download_fei";
	public static String PREFERENCE_DEFAULT_SCREEN = "pref_default_fragment";
	public static String PREFERENCE_LAST_UPDATE = "pref_last_update";
	
	
    @SuppressWarnings("deprecation")
		@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
       addPreferencesFromResource(R.xml.settings);
       
       findPreference(PREFERENCE_DEFAULT_SCREEN).setOnPreferenceChangeListener(this);
       findPreference(PREFERENCE_DOWNLOAD_FEI).setOnPreferenceChangeListener(this);
       
       
    }
    
    Dialog getRestartDialog() {
    	AlertDialog.Builder adb = new AlertDialog.Builder(this);
    	
    	adb.setTitle(getText(string.dialog_alert_title))
    	.setMessage(getText(R.string.dlg_restart_needed))
    	.setPositiveButton(string.ok, null);
    	
    	return adb.create();
    }

	@Override
	public boolean onPreferenceChange(Preference arg0, Object arg1) {
		getRestartDialog().show();
		return true;
	}

}