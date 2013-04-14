package cz.upce.fei.jidelak.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import cz.upce.fei.jidelak.model.JidelnicekTyp;

public interface IJidelnicekActivity {

	void setAndShowDialog(Dialog dialog);
	
	Context getContext();
	
	FragmentPagerAdapter getFragmentPagerAdapter();
	
	ViewPager getViewPager();
	
}
