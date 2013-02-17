package cz.upce.fei.jidelak.view;

import java.util.List;

import cz.upce.fei.jidelak.view.fragments.IJidelnicekFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

	List<IJidelnicekFragment> fragmenty;
	
	public SectionsPagerAdapter(FragmentManager fm, List<IJidelnicekFragment> list) {
		super(fm);
		
		this.fragmenty =list;  
	}

	@Override
	public Fragment getItem(int i) {
		return (Fragment) fragmenty.get(i);
	}

	@Override
	public int getCount() {
		return fragmenty.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return fragmenty.get(position).getName();
	}
}
