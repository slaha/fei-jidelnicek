package cz.upce.fei.jidelak.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import cz.upce.fei.jidelak.model.FragmentModel;
import cz.upce.fei.jidelak.view.fragments.IJidelnicekFragment;

public class SectionsPagerAdapter<T extends IJidelnicekFragment> extends FragmentStatePagerAdapter {

	private FragmentModel<T> model;

	public SectionsPagerAdapter(FragmentManager fm, FragmentModel<T> model) {
		super(fm);
		this.model = model;
	}

	@Override
	public Fragment getItem(int i) {
		return (Fragment) model.get(i);
	}

	@Override
	public int getCount() {
		return model.count();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return model.get(position).getName();
	}

	@Override
	public int getItemPosition(Object object) {
		int position = model.indexOf(object);
		if (position < 0) {
			return POSITION_NONE;
		}
		return position;
	}

}
