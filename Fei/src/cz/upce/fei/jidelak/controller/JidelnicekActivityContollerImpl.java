package cz.upce.fei.jidelak.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import cz.upce.fei.jidelak.R;
import cz.upce.fei.jidelak.dao.JidelnicekDaoImpl;
import cz.upce.fei.jidelak.dao.IDao;
import cz.upce.fei.jidelak.downloader.JidelnicekDownloader;
import cz.upce.fei.jidelak.model.JidelnicekTyp;
import cz.upce.fei.jidelak.utils.RefreshViewHelper;
import cz.upce.fei.jidelak.view.IJidelnicekActivity;
import cz.upce.fei.jidelak.view.SettingsActivity;
import cz.upce.fei.jidelak.view.fragments.FeiJidelnicekFragmentImpl;
import cz.upce.fei.jidelak.view.fragments.IJidelnicekFragment;
import cz.upce.fei.jidelak.view.fragments.KampusJidelnicekFragmentImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JidelnicekActivityContollerImpl implements IJidelnicekActivityController {

	IJidelnicekActivity jidelnicekActivity;
	Context ctx; 
	
	IJidelnicekFragment kampusTydenniJidelnicekFragment;
	IJidelnicekFragment feiTydenniJidelnicekFragment;
	List<IJidelnicekFragment> jidelnicky;

	public JidelnicekActivityContollerImpl(IJidelnicekActivity jidelnicekActivity) {
		
		this.jidelnicekActivity = jidelnicekActivity;
		this.ctx = jidelnicekActivity.getContext();

		createFragments();
	}
	
	private void createFragments() {

		this.kampusTydenniJidelnicekFragment = new KampusJidelnicekFragmentImpl();
		
		if (isDownloadFeiEnabled()) {
			this.feiTydenniJidelnicekFragment = new FeiJidelnicekFragmentImpl();
		}
	}

	private void startDownload(JidelnicekTyp typ, RefreshViewHelper refreshView) {
		JidelnicekDownloader jd = new JidelnicekDownloader(typ, refreshView, this);
		jd.execute();
	}

	@Override
	public void setResult(String result, JidelnicekTyp typ) {
		if (result == null || result.isEmpty()) {
			Dialog dialog = getErrorDialog();

			jidelnicekActivity.setAndShowDialog(dialog);
		} else {
			IJidelnicekFragment jidelnicekFragment = getFragment(typ);
			jidelnicekFragment.setJidelnicek(result);
			jidelnicekFragment.updateJidelnicek();

			storeToDB(result, typ);
		}
	}

	@Override
	public void startSettingsActivity() {
		ctx.startActivity(new Intent(ctx, SettingsActivity.class));
	}

	@Override
	public void doFullRestore() {
		IDao dao = new JidelnicekDaoImpl(ctx);
		dao.open();
		for (IJidelnicekFragment jidelnicekFragment : getFragments()) {
			
			String jidelnicek = dao.getJidelnicek(jidelnicekFragment.getTyp());
			jidelnicekFragment.setJidelnicek(jidelnicek);
			jidelnicekFragment.updateJidelnicek();
		}
		dao.close();
	}

	@Override
	public boolean isFirstRun() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
		
		boolean firstRun = preferences.getBoolean(SettingsActivity.PREFERENCE_IS_FIRST_RUN, true);
		
		if (firstRun) {
			Editor e = preferences.edit();
			e.putBoolean(SettingsActivity.PREFERENCE_IS_FIRST_RUN, !firstRun);	
			e.commit();
		}
		
		return firstRun;
	}
	
	@Override
	public Dialog getFirstRunDialog() {
		
		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
	
				switch (which) {
				case Dialog.BUTTON_POSITIVE:
					startSettingsActivity();
					break;
				}
				dialog.dismiss();
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle(ctx.getText(R.string.dlg_first_run_title))
				.setCancelable(false)
				.setMessage(R.string.dlg_first_run_message)
				.setNegativeButton(android.R.string.no, listener)
				.setPositiveButton(android.R.string.yes, listener);

		return builder.create();

	}

	public Dialog getErrorDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle(ctx.getText(R.string.err_document_null))
				.setCancelable(false)
				.setMessage(R.string.err_document_null_msg)
				.setPositiveButton(android.R.string.ok, null);

		return builder.create();

	}

	private boolean isDownloadFeiEnabled() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
		
		return preferences.getBoolean(SettingsActivity.PREFERENCE_DOWNLOAD_FEI, false);
	}
	
	@Override
	public void doRefresh(RefreshViewHelper imageView) {
		IJidelnicekFragment currentFragment = getCurrentJidelnicekFragment();
		
		if (currentFragment == feiTydenniJidelnicekFragment) {
			if (isDownloadFeiEnabled()) {
				startDownload(JidelnicekTyp.FEI, imageView);
				return;
			} else {
				//..jsme na fei fragmentu, ale je zakázaný ho stahovat
				return;
			}
		} else if (currentFragment == kampusTydenniJidelnicekFragment) {
			startDownload(JidelnicekTyp.KAMPUS, imageView);
			return;
		}
	}

	private void storeToDB(String jidelnicekHtml, JidelnicekTyp typ) {
		IDao dao = new JidelnicekDaoImpl(ctx);
		dao.open();
		dao.save(jidelnicekHtml, typ);
		dao.close();
	}

	@Override
	public void initFragments() {
		jidelnicky = new ArrayList<IJidelnicekFragment>();

		String kampus = JidelnicekTyp.KAMPUS.toString();
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
		String defaultScreen = preferences.getString(SettingsActivity.PREFERENCE_DEFAULT_SCREEN, kampus);
		
		jidelnicky.add(kampusTydenniJidelnicekFragment);
		
		if (isDownloadFeiEnabled()) {
			if (defaultScreen.equals(kampus)) {
				jidelnicky.add(feiTydenniJidelnicekFragment);
			} else {
				jidelnicky.add(0, feiTydenniJidelnicekFragment);
			}
		}
	}
	@Override
	public List<IJidelnicekFragment> getFragments() {
		
		return jidelnicky;
	}

	private IJidelnicekFragment getCurrentJidelnicekFragment() {
		return (IJidelnicekFragment) getCurrentFragment();
	}

	@Override
	public void doFullRefresh(RefreshViewHelper progress) {
		startDownload(JidelnicekTyp.KAMPUS, progress);
		
		if (isDownloadFeiEnabled()) {
			startDownload(JidelnicekTyp.FEI, progress);
		}
	}

	@Override
	public boolean updateOnNewWeek() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
		int lastUpdate = preferences.getInt(SettingsActivity.PREFERENCE_LAST_UPDATE, -1);
		//..no update anytime
		if (lastUpdate == -1) {
			return false;
		}
		int week = getNumberOfCurrentWeek();
		return (week == lastUpdate) || (week == 1 && lastUpdate > 1); //new year
	}

	@Override
	public Dialog getNewWeekRefreshDialog(final RefreshViewHelper imageView) {
		OnClickListener listener = new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					doFullRefresh(imageView);
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
				dialog.dismiss();
			}
		};
		
		AlertDialog.Builder adb = new AlertDialog.Builder(ctx);
		
		adb.setTitle(android.R.string.dialog_alert_title)
		.setMessage(R.string.dlg_update)
		.setPositiveButton(android.R.string.yes, listener)
		.setNegativeButton(android.R.string.no, listener);
		
		return adb.create();
	}

	@Override
	public Context getContext() {
		return ctx;
	}


	private int getNumberOfCurrentWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.WEEK_OF_YEAR);
	}
	
	private Fragment getCurrentFragment() {
		int currentIndex = jidelnicekActivity.getViewPager().getCurrentItem();
		return  jidelnicekActivity.getFragmentPagerAdapter().getItem(currentIndex);
	}

	private IJidelnicekFragment getFragment(JidelnicekTyp typ) {

		for (IJidelnicekFragment jidelnicekFragment : jidelnicky) {
			if (jidelnicekFragment.getTyp() == typ) {
				return jidelnicekFragment;
			}
		}

		return  null;
	}
}
