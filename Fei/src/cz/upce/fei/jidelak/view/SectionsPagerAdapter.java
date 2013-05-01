package cz.upce.fei.jidelak.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import cz.upce.fei.jidelak.controller.IJidelnicekActivityController;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

	IJidelnicekActivityController controller;

	public SectionsPagerAdapter(FragmentManager fm, IJidelnicekActivityController controller) {
		super(fm);
		Log.i("SectionsPagerAdapter", "constructor");
		this.controller = controller;
	}


	@Override
	public Fragment getItem(int i) {
		Log.i("SectionsPagerAdapter", "getItem " + i);
		Log.i("SectionsPagerAdapter", controller.getFragments().toString());
		return (Fragment) controller.getFragments().get(i);
	}

	@Override
	public int getCount() {
		return controller.getFragments().size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Log.i("SectionsPagerAdapter", "getPageTitle " + position);
		return controller.getFragments().get(position).getName();
	}

	@Override
	public int getItemPosition(Object object) {
		return controller.getFragments().indexOf(object);
	}

}
