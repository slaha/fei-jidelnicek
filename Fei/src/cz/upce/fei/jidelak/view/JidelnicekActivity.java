package cz.upce.fei.jidelak.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import cz.upce.fei.jidelak.R;
import cz.upce.fei.jidelak.controller.IJidelnicekActivityController;
import cz.upce.fei.jidelak.controller.JidelnicekActivityContollerImpl;
import cz.upce.fei.jidelak.model.ITydenniJidelnicek;
import cz.upce.fei.jidelak.model.TydenniJidelnicekImpl;

public class JidelnicekActivity extends FragmentActivity implements IJidelnicekActivity  {

	String login;
	String password;

	IJidelnicekActivityController controller;
	ITydenniJidelnicek feiTydenniJidelnicek;
	ITydenniJidelnicek kampusTydenniJidelnicek;
	Dialog dialog;
	
	FragmentPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jidelnicek);

		feiTydenniJidelnicek = new TydenniJidelnicekImpl();
		kampusTydenniJidelnicek = new TydenniJidelnicekImpl();

		controller = new JidelnicekActivityContollerImpl(this, feiTydenniJidelnicek, kampusTydenniJidelnicek);
		
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
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_settings:
				controller.startSettingsActivity();
				return true;
			case R.id.menu_refresh:
				
				setAndShowDialog(controller.doRefresh());
				return true;
			case R.id.menu_refreshAll:
				do {
					setAndShowDialog(controller.doFullRefresh());
				} while (dialog != null);
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

	@Override
	public View getProgressBar(Fragment fr) {
		View v = fr.getView();
		Log.i("*****Jidelnicek", "v je " + v);
		if (v == null) {
			return findViewById(R.id.progressBar1);
		} else {
			View pb =  v.findViewById(R.id.progressBar1);
			if (pb != null) {
				return pb;
			}
			return findViewById(R.id.progressBar1);
		}
	}
}
