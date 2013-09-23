package cz.upce.fei.jidelak.view.fragments;

import cz.upce.fei.jidelak.model.Menu;
import cz.upce.fei.jidelak.model.MenuType;

public interface IJidelnicekFragment {

	void setMenu(Menu menu);

	String getName();

	void updateJidelnicek();

	MenuType getTyp();
}
