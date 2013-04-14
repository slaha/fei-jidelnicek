package cz.upce.fei.jidelak.view;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import cz.upce.fei.jidelak.R;
import cz.upce.fei.jidelak.controller.IJidelnicekActivityController;
import cz.upce.fei.jidelak.controller.JidelnicekActivityContollerImpl;
import cz.upce.fei.jidelak.model.JidelnicekTyp;
import cz.upce.fei.jidelak.utils.RefreshViewHelper;

public class JidelnicekActivity extends FragmentActivity implements IJidelnicekActivity  {

	private IJidelnicekActivityController controller;
	private Dialog dialog;
	
	private FragmentPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;

	private RefreshViewHelper refreshView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jidelnicek);

		controller = new JidelnicekActivityContollerImpl(this);

		mSectionsPagerAdapter = new SectionsPagerAdapter(
					getSupportFragmentManager(),
					controller
				);
		
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

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
				controller.startSettingsActivity();
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
	public FragmentPagerAdapter getFragmentPagerAdapter() {
		return mSectionsPagerAdapter;
	}

	@Override
	public ViewPager getViewPager() {
		return mViewPager;
	}

}
