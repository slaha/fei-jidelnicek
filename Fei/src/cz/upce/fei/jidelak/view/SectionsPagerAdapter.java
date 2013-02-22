package cz.upce.fei.jidelak.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import cz.upce.fei.jidelak.controller.IJidelnicekActivityController;
import cz.upce.fei.jidelak.view.fragments.IJidelnicekFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

	IJidelnicekActivityController controller;
	
	public SectionsPagerAdapter(FragmentManager fm, IJidelnicekActivityController controller) {
		super(fm);
		Log.i("SectionsPagerAdapter", "constructor");
		this.controller = controller;
		controller.initFragments();
	}

	
	@Override
	public Fragment getItem(int i) {
		return (Fragment) controller.getFragments().get(i);
	}

	@Override
	public int getCount() {
		return controller.getFragments().size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Log.i("SectionsPagerAdapter", "getPsageTitle " + position);
		return ((IJidelnicekFragment)controller.getFragments().get(position)).getName();
	}
}
