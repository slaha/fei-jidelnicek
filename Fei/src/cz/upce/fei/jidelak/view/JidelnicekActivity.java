package cz.upce.fei.jidelak.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import cz.upce.fei.jidelak.R;
import cz.upce.fei.jidelak.controller.IJidelnicekActivityController;
import cz.upce.fei.jidelak.controller.JidelnicekActivityContollerImpl;
import cz.upce.fei.jidelak.utils.RefreshViewHelper;

public class JidelnicekActivity extends FragmentActivity implements IJidelnicekActivity  {

	private static final int SETTINGS_ACTIVITY_RESULT = 0x0001;

	private IJidelnicekActivityController controller;
	private Dialog dialog;
	
	private FragmentStatePagerAdapter mSectionsPagerAdapter;

	private ViewPager mViewPager;

	private RefreshViewHelper refreshView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jidelnicek);

		controller = new JidelnicekActivityContollerImpl(this);
		mViewPager = (ViewPager) findViewById(R.id.pager);

		initSectionsPagerAdapter();

		if (controller.isFirstRun()) {
			dialog = controller.getFirstRunDialog();
		}
		
		controller.doFullRestore();
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
		this.refreshView =  new RefreshViewHelper(item, this);

		return true;
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
			case SETTINGS_ACTIVITY_RESULT :
				if (resultCode == SettingsActivity.CHANGED) {
					controller.recreateFragments();
					initSectionsPagerAdapter();
				}
				break;
		}
	}


	private void initSectionsPagerAdapter() {
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager(),
				controller
		);

		mViewPager.setAdapter(mSectionsPagerAdapter);

		for(int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			mSectionsPagerAdapter.getItem(i);
		}
	}
}
