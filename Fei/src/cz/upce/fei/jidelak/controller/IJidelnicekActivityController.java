package cz.upce.fei.jidelak.controller;

import android.app.Dialog;
import cz.upce.fei.jidelak.model.MenuType;
import cz.upce.fei.jidelak.view.refresh.RefreshViewHelper;

public interface IJidelnicekActivityController<T> {

	/**
	 * Check if app is launched for the first time
	 * 
	 * @return true if it is first launch ever; false otherwise
	 */
	boolean isFirstRun();

	/**
	 * Refresh current screen
	 * 
	 * @param imageView
	 *            progress
	 */
	void doRefresh(RefreshViewHelper imageView);

	/**
	 * Refresh all screens
	 * 
	 * @param imageView
	 *            progress
	 */
	void doFullRefresh(RefreshViewHelper imageView);

	/**
	 * Creates dialog to notify user to do some settings
	 * 
	 * @return dialog
	 */
	Dialog getFirstRunDialog();

	/**
	 * Restores state from DB after start
	 */
	void doFullRestore();

	/**
	 * Check if now is newer week than on last update
	 * 
	 * @return true if is now newer week; false otherwise
	 */
	boolean updateOnNewWeek();

	/**
	 * Creates refresh dialog
	 * 
	 * @return refresh dialog
	 */
	Dialog getNewWeekRefreshDialog(RefreshViewHelper imageView);

	/**
	 * sets result downloaded from web to fragment
	 * 
	 * @param result
	 *            downloaded string
	 * @param typ
	 *            type of downloaded menu
	 */
	void setResult(String result, MenuType typ);

	/**
	 * Saves that app launched for the first time already
	 */
	void setFirstRunAlreadyDone();
}
