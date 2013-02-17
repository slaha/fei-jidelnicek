package cz.upce.fei.jidelak.controller;

import java.util.List;

import android.app.Dialog;
import cz.upce.fei.jidelak.model.IDenniJidelnicek;
import cz.upce.fei.jidelak.view.fragments.IJidelnicekFragment;

public interface IJidelnicekActivityController {

	/**
	 * Spustí aktivitu nastavení
	 */
	void startSettingsActivity();

	/**
	 * Zkontroluje, je app spuštěna poprve (není vyplněno ST ani heslo)
	 * @return true jestli je spuštěno poprve, false jinak
	 */
	boolean isFirstRun();
	
	/**
	 * Zaktualizuje jídelníček pro aktuální obrazovku
	 * @return null, když je vše ok, jinak dialog zobrazující info, co je blbě
	 */
	Dialog doRefresh();
	
	/**
	 * Zaktualizuje jídelníček pro obě menzy
	 * @return null, když je vše ok, jinak dialog zobrazující info, co je blbě
	 */
	Dialog doFullRefresh();
	
	/**
	 * Vytvoří dialog zobrazený pokud je aplikace spuštěna poprve
	 * @return dialog
	 */
	Dialog getFirstRunDialog();
	

	/**
	 * Slouží k aktualizaci stažených dnů
	 * @param days nově stažené dny
	 */
	void updateDays(List<IDenniJidelnicek> days);

	List<IJidelnicekFragment> getFragments();

	/**
	 * Restores state from DB after start
	 */
	void doFullRestore();
	
	boolean updateOnNewWeek();
	
	Dialog getNewWeekRefreshDialog();
	
	
}
