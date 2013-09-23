package cz.upce.fei.jidelak.view.preferences;

/** Created with IntelliJ IDEA. User: slaha Date: 21.9.13 Time: 19:30 */
public class PreferencesConsts {

	public static class Keys {
		public static final String PREFERENCE_IS_FIRST_RUN = "pref_first_run";
		public static final String PREFERENCE_DOWNLOAD_FEI = "pref_download_fei";
		public static final String PREFERENCE_DEFAULT_SCREEN = "pref_default_fragment";
		public static final String PREFERENCE_LAST_UPDATE = "pref_last_update";
		public static final String PREFERENCE_CSS = "pref_look_css";
	}

	public static class Results {
		public static final int NO_CHANGE = 0;
		public static final int CHANGED_FRAGMENT = 1;
		public static final int CHANGED_STYL = 2;
	}

}
