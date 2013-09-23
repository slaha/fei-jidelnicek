package cz.upce.fei.jidelak.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;

public interface IJidelnicekActivity {

	void setAndShowDialog(Dialog dialog);

	Fragment getCurrentFragment();

	void startSettingsActivity();

	Context getContext();
}
