package cz.upce.fei.jidelak.controller;

import cz.upce.fei.jidelak.model.CssTyp;
import cz.upce.fei.jidelak.model.MenuType;

/** Created with IntelliJ IDEA. User: slaha Date: 21.9.13 Time: 11:48 */
public interface IPrefferenceManager {
	/**
	 * Returns current CSS type. If it has not been set yet returns the default
	 * (black text, white background).
	 * 
	 * @return Current CSS type or the default one if it is not set
	 */
	CssTyp getCssTyp();

	/**
	 * Check if application is running for the first time.
	 * 
	 * @return true if it is running first time; false otherwise
	 */
	boolean isFirstRun();

	/**
	 * Save that application is not running for the first time.
	 * 
	 * <p>
	 * After calling this {@code isFirstRun} will return always false
	 */
	void setNotFirstRun();

	/**
	 * Check if FEI should be downloaded or not
	 * 
	 * @return true if FEI should be downloaded; false if not
	 */
	boolean isDownloadFeiEnabled();

	/**
	 * Check if {@code screen} is default (=displayed at the most left screen)
	 * 
	 * @return true if {@code screen} is default; false otherwise
	 */
	boolean isDefaultScreen(MenuType screen);

	/**
	 * @return Last number of week when menu was updated or -1 if no update was
	 *         made yet
	 */
	int getLastUpdateWeek();

	/**
	 * Sets number of week when menu was updated for the last time
	 * 
	 * @param uptadeTo
	 *            number of week {@code <0; 54>}
	 * 
	 * @throws IllegalArgumentException
	 *             if {@code updateTo} is lower than 0 or bigger than 54
	 */
	void updateWeekNumber(int uptadeTo);
}
