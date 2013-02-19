package cz.upce.fei.jidelak.view.fragments;

import android.view.View;
import cz.upce.fei.jidelak.model.ITydenniJidelnicek;
import cz.upce.fei.jidelak.model.JidelnicekTyp;

public interface IJidelnicekFragment {

	void setJidelnicek(ITydenniJidelnicek jidelnicek);
	ITydenniJidelnicek getJidelnicek();
	
	String getName();
	
	void updateJidelnicek();
	
	JidelnicekTyp getTyp();
}
