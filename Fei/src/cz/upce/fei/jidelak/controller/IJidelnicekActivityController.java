package cz.upce.fei.jidelak.controller;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import cz.upce.fei.jidelak.model.JidelnicekTyp;
import cz.upce.fei.jidelak.utils.RefreshViewHelper;
import cz.upce.fei.jidelak.view.fragments.IJidelnicekFragment;

import java.util.List;

public interface IJidelnicekActivityController {

	/**
	 * Zkontroluje, je app spuštěna poprve (není vyplněno ST ani heslo)
	 *
	 * @return true jestli je spuštěno poprve, false jinak
	 */
	boolean isFirstRun();

	/**
	 * Zaktualizuje jídelníček pro aktuální obrazovku
	 */
	void doRefresh(RefreshViewHelper imageView);

	/**
	 * Zaktualizuje jídelníček pro obě menzy
	 *
	 * @param imageView iv
	 */
	void doFullRefresh(RefreshViewHelper imageView);

	/**
	 * Vytvoří dialog zobrazený pokud je aplikace spuštěna poprve
	 *
	 * @return dialog
	 */
	Dialog getFirstRunDialog();

	List<IJidelnicekFragment> getFragments();

	void initFragments();

	/**
	 * Restores state from DB after start
	 */
	void doFullRestore();

	boolean updateOnNewWeek();

	Dialog getNewWeekRefreshDialog(RefreshViewHelper imageView);

	Context getContext();

	void setResult(String result, JidelnicekTyp typ);

	void recreateFragments();

	void addFragment(Fragment fragment, String title);
}
