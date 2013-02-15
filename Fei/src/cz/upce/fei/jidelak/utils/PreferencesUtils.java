package cz.upce.fei.jidelak.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUtils {

	private static final String PREFS_FILE_NAME = "jidelnicek.prefs";

	private Context ctx;
	
	public enum Keys {
		PREFS_NAME,
		PREFS_PASS,
		PREFS_LAST_SYNC
	}
	
	public PreferencesUtils(Context c) {
		ctx = c;
	}
	
	public void save(Keys key, String value) {
		SharedPreferences settings = ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE);
	    SharedPreferences.Editor editor = settings.edit();
	    	    
	    editor.putString(key.toString(), value);
	    editor.commit();
	}
	
	public String get(Keys key, String defValue) {
		SharedPreferences settings = ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE);
	    return settings.getString(key.toString(), defValue);
	}
	
}
