package cz.upce.fei.jidelak.controller;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import cz.upce.fei.jidelak.dao.IDao;
import cz.upce.fei.jidelak.dao.JidelnicekDaoImpl;
import cz.upce.fei.jidelak.downloader.JidelnicekDownloader;
import cz.upce.fei.jidelak.model.CssTyp;
import cz.upce.fei.jidelak.model.JidelnicekTyp;
import cz.upce.fei.jidelak.utils.DialogUtils;
import cz.upce.fei.jidelak.utils.HtmlHelper;
import cz.upce.fei.jidelak.utils.RefreshViewHelper;
import cz.upce.fei.jidelak.view.IJidelnicekActivity;
import cz.upce.fei.jidelak.view.fragments.FeiJidelnicekFragmentImpl;
import cz.upce.fei.jidelak.view.fragments.IJidelnicekFragment;
import cz.upce.fei.jidelak.view.fragments.KampusJidelnicekFragmentImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JidelnicekActivityContollerImpl implements IJidelnicekActivityController, Serializable {

	private  IJidelnicekActivity jidelnicekActivity;
	private Context ctx;
	
	private IJidelnicekFragment kampusTydenniJidelnicekFragment;
	private IJidelnicekFragment feiTydenniJidelnicekFragment;
	private final List<IJidelnicekFragment> jidelnicky;

	private PrefferenceManager prefferenceManager;

	public JidelnicekActivityContollerImpl(IJidelnicekActivity jidelnicekActivity) {
		
		this.jidelnicekActivity = jidelnicekActivity;
		this.ctx = jidelnicekActivity.getContext();

		this.prefferenceManager = new PrefferenceManager(ctx);

		this.jidelnicky = new ArrayList<IJidelnicekFragment>();
		createFragments();
	}
	
	private void createFragments() {

		this.kampusTydenniJidelnicekFragment = new KampusJidelnicekFragmentImpl();
		this.feiTydenniJidelnicekFragment = new FeiJidelnicekFragmentImpl();

		if (isDownloadFeiEnabled()) {
		}
	}

	private void startDownload(JidelnicekTyp typ, RefreshViewHelper refreshView) {
		JidelnicekDownloader jd = new JidelnicekDownloader(typ, refreshView, this);
		jd.execute();
	}

	@Override
	public void setResult(String result, JidelnicekTyp typ) {
		if (result == null || result.isEmpty()) {
			Dialog dialog = DialogUtils.getErrorDialog(ctx);

			jidelnicekActivity.setAndShowDialog(dialog);
		} else {

			IJidelnicekFragment jidelnicekFragment = getFragment(typ);


			jidelnicekFragment.setJidelnicek(result);
			jidelnicekFragment.updateJidelnicek();

			storeToDB(result, typ);
			prefferenceManager.updateWeekNumber();
		}
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
		return prefferenceManager.isFirstRun();
	}
	
	@Override
	public Dialog getFirstRunDialog() {

		return DialogUtils.getFirstRunDialog(ctx, jidelnicekActivity);
	}

	private boolean isDownloadFeiEnabled() {
		return  prefferenceManager.isDownloadFeiEnabled();
	}
	
	@Override
	public void doRefresh(RefreshViewHelper imageView) {
		IJidelnicekFragment currentFragment = getCurrentJidelnicekFragment();
		
		if (currentFragment == feiTydenniJidelnicekFragment) {
			if (isDownloadFeiEnabled()) {
				startDownload(JidelnicekTyp.FEI, imageView);
			}
		}
		else if (currentFragment == kampusTydenniJidelnicekFragment) {
			startDownload(JidelnicekTyp.KAMPUS, imageView);
		}
	}

	private void storeToDB(String jidelnicekHtml, JidelnicekTyp typ) {
		IDao dao = new JidelnicekDaoImpl(ctx);
		dao.open();
		dao.save(jidelnicekHtml, typ);
		dao.close();
	}

	@Override
	public  void initFragments() {
		synchronized (jidelnicky) {
			jidelnicky.clear();

			jidelnicky.add(kampusTydenniJidelnicekFragment);

			if (isDownloadFeiEnabled()) {
				if (prefferenceManager.isDefaultScreen(JidelnicekTyp.KAMPUS)) {
					jidelnicky.add(feiTydenniJidelnicekFragment);
				} else {
					jidelnicky.add(0, feiTydenniJidelnicekFragment);
				}
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
		return prefferenceManager.isNewWeek();
	}

	@Override
	public Dialog getNewWeekRefreshDialog(final RefreshViewHelper imageView) {

		return DialogUtils.getNewWeekRefreshDialog(ctx, imageView, this);
	}

	@Override
	public Context getContext() {
		return ctx;
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

	@Override
	public void recreateFragments() {
		//createFragments();
		initFragments();
	}
}
