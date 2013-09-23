package cz.upce.fei.jidelak.controller.impl;

import android.app.Dialog;
import android.content.Context;
import cz.upce.fei.jidelak.controller.IJidelnicekActivityController;
import cz.upce.fei.jidelak.dao.IDao;
import cz.upce.fei.jidelak.dao.JidelnicekDaoImpl;
import cz.upce.fei.jidelak.dao.SaveableBundle;
import cz.upce.fei.jidelak.downloader.JidelnicekDownloader;
import cz.upce.fei.jidelak.model.FragmentModel;
import cz.upce.fei.jidelak.model.Menu;
import cz.upce.fei.jidelak.model.MenuType;
import cz.upce.fei.jidelak.utils.DateUtils;
import cz.upce.fei.jidelak.utils.DialogUtils;
import cz.upce.fei.jidelak.view.IJidelnicekActivity;
import cz.upce.fei.jidelak.view.fragments.IJidelnicekFragment;
import cz.upce.fei.jidelak.view.refresh.RefreshViewHelper;

import java.io.Serializable;
import java.util.List;

public class JidelnicekActivityContollerImpl implements IJidelnicekActivityController<IJidelnicekFragment>,
		Serializable {

	private IJidelnicekActivity jidelnicekActivity;
	private Context ctx;

	private FragmentModel<IJidelnicekFragment> fragmentsModel;

	private PrefferenceManagerImpl prefferenceManager;

	public JidelnicekActivityContollerImpl(IJidelnicekActivity jidelnicekActivity, FragmentModel fragmentsModel) {

		this.jidelnicekActivity = jidelnicekActivity;
		this.ctx = jidelnicekActivity.getContext();

		this.prefferenceManager = new PrefferenceManagerImpl(ctx);

		this.fragmentsModel = fragmentsModel;
	}

	private void startDownload(MenuType typ, RefreshViewHelper refreshView) {
		JidelnicekDownloader jd = new JidelnicekDownloader(typ, refreshView, this);
		jd.execute();
	}

	@Override
	public void setResult(String result, MenuType typ) {
		if (result == null || result.isEmpty()) {
			Dialog dialog = DialogUtils.getErrorDialog(ctx);

			jidelnicekActivity.setAndShowDialog(dialog);
		} else {

			IJidelnicekFragment jidelnicekFragment = getFragment(typ);

			Menu menu = new Menu(result);
			jidelnicekFragment.setMenu(menu);
			jidelnicekFragment.updateJidelnicek();

			storeToDB(menu, typ);

			int currentWeek = DateUtils.getNumberOfCurrentWeek();
			prefferenceManager.updateWeekNumber(currentWeek);
		}
	}

	@Override
	public void setFirstRunAlreadyDone() {
		prefferenceManager.setNotFirstRun();
	}

	@Override
	public void doFullRestore() {
		IDao<Menu, MenuType> dao = new JidelnicekDaoImpl(ctx);
		List<MenuType> types = fragmentsModel.getTypes();
		SaveableBundle<Menu, MenuType> menus;
		menus = dao.getMultiple(types.toArray(new MenuType[types.size()]));

		for (SaveableBundle<Menu, MenuType>.Bundle menu : menus) {

			IJidelnicekFragment jidelnicekFragment = fragmentsModel.get(menu.getKey());
			jidelnicekFragment.setMenu(menu.getValue());
			jidelnicekFragment.updateJidelnicek();
		}
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
		return prefferenceManager.isDownloadFeiEnabled();
	}

	@Override
	public void doRefresh(RefreshViewHelper imageView) {
		IJidelnicekFragment currentFragment = getCurrentJidelnicekFragment();

		if (fragmentsModel.isReferenceEqual(currentFragment, MenuType.FEI)) {
			if (isDownloadFeiEnabled()) {
				startDownload(MenuType.FEI, imageView);
			}
		} else if (fragmentsModel.isReferenceEqual(currentFragment, MenuType.KAMPUS)) {
			startDownload(MenuType.KAMPUS, imageView);
		}
	}

	private void storeToDB(Menu menu, MenuType typ) {
		IDao dao = new JidelnicekDaoImpl(ctx);
		dao.save(menu, typ);
	}

	private IJidelnicekFragment getCurrentJidelnicekFragment() {
		return (IJidelnicekFragment) jidelnicekActivity.getCurrentFragment();
	}

	@Override
	public void doFullRefresh(RefreshViewHelper progress) {
		startDownload(MenuType.KAMPUS, progress);

		if (isDownloadFeiEnabled()) {
			startDownload(MenuType.FEI, progress);
		}
	}

	@Override
	public boolean updateOnNewWeek() {
		int lastUpdate = prefferenceManager.getLastUpdateWeek();
		if (lastUpdate < 0) {
			//..no update yet
			return true;
		}
		boolean isNowNewWeek = !DateUtils.isCurrentWeek(lastUpdate);
		return isNowNewWeek;

	}

	@Override
	public Dialog getNewWeekRefreshDialog(final RefreshViewHelper imageView) {

		return DialogUtils.getNewWeekRefreshDialog(ctx, imageView, this);
	}

	private IJidelnicekFragment getFragment(MenuType typ) {

		return fragmentsModel.get(typ);

	}
}
