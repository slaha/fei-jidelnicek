package cz.upce.fei.jidelak.view.fragments;

import cz.upce.fei.jidelak.model.JidelnicekTyp;

public interface IJidelnicekFragment {

	void setJidelnicek(String jidelnicek);

	String getName();
	
	void updateJidelnicek();
	
	JidelnicekTyp getTyp();
}
