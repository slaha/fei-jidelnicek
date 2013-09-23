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
import cz.upce.fei.jidelak.controller.impl.JidelnicekActivityContollerImpl;
import cz.upce.fei.jidelak.model.FragmentModel;
import cz.upce.fei.jidelak.model.impl.FragmentsModelImpl;
import cz.upce.fei.jidelak.utils.SettingsUtils;
import cz.upce.fei.jidelak.view.fragments.IJidelnicekFragment;
import cz.upce.fei.jidelak.view.preferences.PreferencesConsts;
import cz.upce.fei.jidelak.view.refresh.ActionRefreshViewImpl;
import cz.upce.fei.jidelak.view.refresh.RefreshViewHelper;

public class JidelnicekActivity extends FragmentActivity implements IJidelnicekActivity {

	private static final int SETTINGS_ACTIVITY_RESULT = 0x0001;
	private static final String TABS_COUNT = "tabs_count";
	private static final String TITLES = "titles";

	private FragmentModel<IJidelnicekFragment> fragmentsModel;
	private IJidelnicekActivityController controller;
	private Dialog dialog;

	private FragmentStatePagerAdapter fragmentsAdapter;

	private ViewPager viewPager;

	private RefreshViewHelper progressView;

	private Bundle savedInstanceState;

	private boolean firstRun;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jidelnicek);

		this.savedInstanceState = savedInstanceState;

		fragmentsModel = new FragmentsModelImpl(this);
		controller = new JidelnicekActivityContollerImpl(this, fragmentsModel);
		fragmentsAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), fragmentsModel);

		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(fragmentsAdapter);

		if (savedInstanceState != null) {
			Integer count = savedInstanceState.getInt(TABS_COUNT);
			String[] titles = savedInstanceState.getStringArray(TITLES);
			for (int i = 0; i < count; i++) {
				fragmentsModel.addFragment(getFragment(i), titles[i]);
			}
		} else {
			fragmentsModel.recreate();
		}
		fragmentsAdapter.notifyDataSetChanged();

		firstRun = controller.isFirstRun();
		if (firstRun) {
			controller.setFirstRunAlreadyDone();
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

		if (!firstRun) {
			if (controller.updateOnNewWeek()) {
				if (progressView == null) {
					//..it is not ok but better than nothing
					progressView = ActionRefreshViewImpl.create(this, null);
				}

				this.dialog = controller.getNewWeekRefreshDialog(progressView);
			}
		} else {
			//..first run just one all the time
			this.firstRun = false;
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
		this.progressView = RefreshViewHelper.create(this, item);

		return true;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		final int tabsCount = fragmentsAdapter.getCount();

		CharSequence[] titles = new String[tabsCount];
		for (int i = 0; i < tabsCount; i++) {
			titles[i] = fragmentsAdapter.getPageTitle(i);
		}

		outState.putInt(TABS_COUNT, tabsCount);
		outState.putCharSequenceArray(TITLES, titles);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			startSettingsActivity();
			return true;
		case R.id.menu_refresh:
			controller.doRefresh(progressView);
			return true;
		case R.id.menu_refreshAll:
			controller.doFullRefresh(progressView);
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
	public Fragment getCurrentFragment() {
		int current = viewPager.getCurrentItem();
		return fragmentsAdapter.getItem(current);
	}

	@Override
	public void startSettingsActivity() {
		Class<?> settings = SettingsUtils.getSettingsActivity();
		Intent intent = new Intent(this, settings);
		startActivityForResult(intent, SETTINGS_ACTIVITY_RESULT);
	}

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case SETTINGS_ACTIVITY_RESULT:
			if ((resultCode & PreferencesConsts.Results.CHANGED_FRAGMENT) != 0) {
				fragmentsModel.recreate();
				initSectionsPagerAdapter();
			}
			if ((resultCode & PreferencesConsts.Results.CHANGED_STYL) != 0) {
				controller.doFullRestore();
			}
			break;
		}
	}

	private void initSectionsPagerAdapter() {
		fragmentsAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), fragmentsModel);

		viewPager.setAdapter(fragmentsAdapter);
	}

	private IJidelnicekFragment getFragment(int position) {
		return (IJidelnicekFragment) getStandardFragment(position);
	}

	private Fragment getStandardFragment(int position) {
		if (savedInstanceState == null) {
			return fragmentsAdapter.getItem(position);
		}

		String tag = getFragmentTag(position);
		return getSupportFragmentManager().findFragmentByTag(tag);
	}

	private String getFragmentTag(int position) {
		return "android:switcher:" + R.id.pager + ":" + position;
	}
}
