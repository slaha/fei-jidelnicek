package cz.upce.fei.jidelak.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import cz.upce.fei.jidelak.model.MenuType;

public class FeiJidelnicekFragmentImpl extends AbsJidelnicekFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		return v;
	}

	@Override
	public MenuType getTyp() {
		return MenuType.FEI;
	}
}
