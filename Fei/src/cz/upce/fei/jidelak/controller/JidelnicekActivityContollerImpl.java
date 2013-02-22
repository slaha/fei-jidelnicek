package cz.upce.fei.jidelak.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import cz.upce.fei.jidelak.R;
import cz.upce.fei.jidelak.dao.DaoDenImpl;
import cz.upce.fei.jidelak.dao.IDao;
import cz.upce.fei.jidelak.downloader.AbsJidelnicekDownloader;
import cz.upce.fei.jidelak.downloader.FeiJidelnicekDownloaderImpl;
import cz.upce.fei.jidelak.downloader.KampusJidelnicekDownloaderImpl;
import cz.upce.fei.jidelak.model.IDenniJidelnicek;
import cz.upce.fei.jidelak.model.ITydenniJidelnicek;
import cz.upce.fei.jidelak.model.JidelnicekTyp;
import cz.upce.fei.jidelak.parser.FeiJidelnicekParserImpl;
import cz.upce.fei.jidelak.parser.IParser;
import cz.upce.fei.jidelak.parser.KampusJidelnicekParserImpl;
import cz.upce.fei.jidelak.view.IJidelnicekActivity;
import cz.upce.fei.jidelak.view.SettingsActivity;
import cz.upce.fei.jidelak.view.fragments.AbsJidelnicekFragment;
import cz.upce.fei.jidelak.view.fragments.FeiJidelnicekFragmentImpl;
import cz.upce.fei.jidelak.view.fragments.IJidelnicekFragment;
import cz.upce.fei.jidelak.view.fragments.KampusJidelnicekFragmentImpl;

public class JidelnicekActivityContollerImpl implements IJidelnicekActivityController {

	IJidelnicekActivity jidelnicekActivity;
	Context ctx; 
	
	IJidelnicekFragment kampusTydenniJidelnicekFragment;
	IJidelnicekFragment feiTydenniJidelnicekFragment;
	List<IJidelnicekFragment> jidelnicky;
	
	FragmentPagerAdapter pagerAdapter;

	public JidelnicekActivityContollerImpl(
				IJidelnicekActivity jidelnicekActivity, 
				ITydenniJidelnicek kampusTydenniJidelnicek, 
				ITydenniJidelnicek feiTydenniJidelnicek
				) 
		{
		
		this.jidelnicekActivity = jidelnicekActivity;
		this.ctx = jidelnicekActivity.getContext();
		
		createFragments(kampusTydenniJidelnicek, feiTydenniJidelnicek);
	}
	
	private void createFragments(ITydenniJidelnicek kampusTydenniJidelnicek, ITydenniJidelnicek feiTydenniJidelnicek) {
		Bundle bdl = new Bundle(1);
		
		this.kampusTydenniJidelnicekFragment = new KampusJidelnicekFragmentImpl();
		bdl.putSerializable(AbsJidelnicekFragment.KEY_JIDELNICEK, kampusTydenniJidelnicek);
		
		((Fragment)kampusTydenniJidelnicekFragment).setArguments(bdl);
		kampusTydenniJidelnicekFragment.setJidelnicek(kampusTydenniJidelnicek);
		
		if (isDownloadFeiEnabled()) {
			this.feiTydenniJidelnicekFragment = new FeiJidelnicekFragmentImpl();
		    bdl.putSerializable(AbsJidelnicekFragment.KEY_JIDELNICEK, feiTydenniJidelnicek);
		    
		    ((Fragment)feiTydenniJidelnicekFragment).setArguments(bdl);
		    feiTydenniJidelnicekFragment.setJidelnicek(feiTydenniJidelnicek);
		}
		
	}

	private void startDownloadFei(View progressBar) { 
		IParser parser = new FeiJidelnicekParserImpl(jidelnicekActivity);
		AbsJidelnicekDownloader jd = new FeiJidelnicekDownloaderImpl(progressBar, parser, this);
		jd.execute();
	}
	
	private void startDownloadKampus(View progressBar) { 
		IParser parser = new KampusJidelnicekParserImpl(jidelnicekActivity);
		AbsJidelnicekDownloader jd = new KampusJidelnicekDownloaderImpl(progressBar, parser, this);
		jd.execute();
	}

	@Override
	public void startSettingsActivity() {
		ctx.startActivity(new Intent(ctx, SettingsActivity.class));
	}

	@Override
	public void doFullRestore() {
		IDao<IDenniJidelnicek> dao = new DaoDenImpl(ctx);
		dao.open();
		for (IJidelnicekFragment jidelnicekFragment : getFragments()) {
			
			List<IDenniJidelnicek> days = dao.getAll(jidelnicekFragment.getTyp());
			ITydenniJidelnicek jidelnicek = jidelnicekFragment.getJidelnicek();
			jidelnicek.setDays(days);
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

	
	private boolean isDownloadFeiEnabled() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
		
		return preferences.getBoolean(SettingsActivity.PREFERENCE_DOWNLOAD_FEI, false);
	}
	
	@Override
	public Dialog doRefresh() {
		IJidelnicekFragment currentFragment = getCurrentJidelnicekFragment();
		
		if (currentFragment == feiTydenniJidelnicekFragment) {
			if (isDownloadFeiEnabled()) {
				startDownloadFei(jidelnicekActivity.getProgressBar(getCurrentFragment()));
				return null;
			} else {
				//..jsme na fei fragmentu, ale je zakázaný ho stahovat
				return null;
			}
		} else if (currentFragment == kampusTydenniJidelnicekFragment) {
			startDownloadKampus(jidelnicekActivity.getProgressBar(getCurrentFragment()));
			return null;
		}
		//..should never happen
		return null;
	}

	@Override
	public void updateDays(List<IDenniJidelnicek> days) {
		if (!days.isEmpty()) {
			JidelnicekTyp typ = days.get(0).getTyp();
			IJidelnicekFragment jidelnicekFragment = kampusTydenniJidelnicekFragment;
			if (typ == JidelnicekTyp.FEI) {
				jidelnicekFragment = feiTydenniJidelnicekFragment;
			}
			jidelnicekFragment.getJidelnicek().setDays(days);
			jidelnicekFragment.updateJidelnicek();
			storeToDB(days, typ);
			
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
			Editor e = preferences.edit();
			e.putInt(SettingsActivity.PREFERENCE_LAST_UPDATE, getNumberOfCurrentWeek());
			e.commit();
		}
	}

	private void storeToDB(List<IDenniJidelnicek> days, JidelnicekTyp typ) {
		IDao<IDenniJidelnicek> dao = new DaoDenImpl(ctx);
		dao.open();
		dao.saveAll(days, typ);
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
	public Dialog doFullRefresh() {
		startDownloadKampus(jidelnicekActivity.getProgressBar(getCurrentFragment()));
		
		if (isDownloadFeiEnabled()) {
			startDownloadFei(jidelnicekActivity.getProgressBar(getCurrentFragment()));
		}
		return null;
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
	public Dialog getNewWeekRefreshDialog() {
		OnClickListener listener = new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					doFullRefresh();
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
	
	
	private int getNumberOfCurrentWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.WEEK_OF_YEAR);
	}
	
	private Fragment getCurrentFragment() {
		int currentIndex = jidelnicekActivity.getViewPager().getCurrentItem();
		return  jidelnicekActivity.getFragmentPagerAdapter().getItem(currentIndex);
	}
}
