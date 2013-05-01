package cz.upce.fei.jidelak.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import cz.upce.fei.jidelak.R;
import cz.upce.fei.jidelak.controller.IJidelnicekActivityController;
import cz.upce.fei.jidelak.controller.JidelnicekActivityContollerImpl;
import cz.upce.fei.jidelak.utils.RefreshViewHelper;

public class JidelnicekActivity extends FragmentActivity implements IJidelnicekActivity {

	private static final int SETTINGS_ACTIVITY_RESULT = 0x0001;
	private static final String TABS_COUNT = "tabs_count";
	private static final String TITLES = "titles";

	private IJidelnicekActivityController controller;
	private Dialog dialog;

	private FragmentStatePagerAdapter mSectionsPagerAdapter;

	private ViewPager mViewPager;

	private RefreshViewHelper refreshView;

	private Bundle savedInstanceState;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jidelnicek);

		this.savedInstanceState = savedInstanceState;
		controller = new JidelnicekActivityContollerImpl(this);
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), controller);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		if (savedInstanceState == null) {
			controller.initFragments();
		} else {
			Integer count = savedInstanceState.getInt(TABS_COUNT);
			String[] titles = savedInstanceState.getStringArray(TITLES);
			for (int i = 0; i < count; i++) {
				controller.addFragment(getFragment(i), titles[i]);
			}
			controller.initFragments();
		}

//		initSectionsPagerAdapter();

		if (controller.isFirstRun()) {
			dialog = controller.getFirstRunDialog();
		}

		controller.doFullRestore();
		if (Build.VERSION.SDK_INT >= 11) {
			invalidateOptionsMenu();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (controller.updateOnNewWeek()) {
			if (refreshView == null) {
				//TODO
			} else {
				this.dialog = controller.getNewWeekRefreshDialog(refreshView);
			}
		}
		if (dialog != null) {
			dialog.show();
		}
	}


	@Override
	protected void onPause() {
		super.onPause();

		//..close and null dialog (if there is any)
		if (dialog != null) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}

			dialog = null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_jidelnicek, menu);

		MenuItem item = menu.findItem(R.id.menu_refresh);
		this.refreshView = new RefreshViewHelper(item, this);

		return true;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		int pocetTabu = mSectionsPagerAdapter.getCount();

		CharSequence[] titly = new String[pocetTabu];
		for (int i = 0; i < pocetTabu; i++) {
			titly[i] = mSectionsPagerAdapter.getPageTitle(i);
		}

		outState.putInt(TABS_COUNT, pocetTabu);
		outState.putCharSequenceArray(TITLES, titly);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_settings:
				startSettingsActivity();
				return true;
			case R.id.menu_refresh:
				controller.doRefresh(refreshView);
				return true;
			case R.id.menu_refreshAll:
				controller.doFullRefresh(refreshView);
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void setAndShowDialog(Dialog dlg) {
		if (dialog != null) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}
		this.dialog = dlg;
		if (this.dialog != null) {
			dialog.show();
		}
	}

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	public FragmentStatePagerAdapter getFragmentPagerAdapter() {
		return mSectionsPagerAdapter;
	}

	@Override
	public ViewPager getViewPager() {
		return mViewPager;
	}

	@Override
	public void startSettingsActivity() {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivityForResult(intent, SETTINGS_ACTIVITY_RESULT);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case SETTINGS_ACTIVITY_RESULT:
				if ((resultCode & SettingsActivity.CHANGED_FRAGMENT) != 0) {
					controller.recreateFragments();
					initSectionsPagerAdapter();
				}
				if ((resultCode & SettingsActivity.CHANGED_STYL) != 0) {
					controller.doFullRestore();
				}
				break;
		}
	}


	private void initSectionsPagerAdapter() {
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), controller);

		mViewPager.setAdapter(mSectionsPagerAdapter);
	}

	private Fragment getFragment(int position) {
		if (savedInstanceState == null) {
			return mSectionsPagerAdapter.getItem(position);
		}

		String tag = getFragmentTag(position);
		return getSupportFragmentManager().findFragmentByTag(tag);
	}

	private String getFragmentTag(int position) {
		return "android:switcher:" + R.id.pager + ":" + position;
	}
}
