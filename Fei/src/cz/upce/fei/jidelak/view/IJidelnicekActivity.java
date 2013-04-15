package cz.upce.fei.jidelak.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

public interface IJidelnicekActivity {

	void setAndShowDialog(Dialog dialog);
	
	Context getContext();
	
	FragmentStatePagerAdapter getFragmentPagerAdapter();
	
	ViewPager getViewPager();

	public void startSettingsActivity();
}
