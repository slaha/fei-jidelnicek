package cz.upce.fei.jidelak.view.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import cz.upce.fei.jidelak.R;
import cz.upce.fei.jidelak.controller.impl.PrefferenceManagerImpl;
import cz.upce.fei.jidelak.model.CssTyp;
import cz.upce.fei.jidelak.model.Menu;
import cz.upce.fei.jidelak.model.MenuType;

public abstract class AbsJidelnicekFragment extends Fragment implements IJidelnicekFragment {

	private static final String TAG = AbsJidelnicekFragment.class.getSimpleName();

	private static final String BASE = "file:///android_asset/";
	private static final String MIME = "text/html";
	private static final String ENCODING = "utf-8";

	protected WebView webView;
	protected Menu menu;

	public final static String KEY_JIDELNICEK = "key_jidelnicek";

	protected PrefferenceManagerImpl prefferenceManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		if (savedInstanceState != null) {
			this.menu = savedInstanceState.getParcelable(KEY_JIDELNICEK);
		}

		this.prefferenceManager = new PrefferenceManagerImpl(getActivity());

		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_jidelnicek, null);

		webView = (WebView) view.findViewById(R.id.webView);

		if (webView != null && menu != null) {
			updateJidelnicek();
		}

		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (menu != null) {
			outState.putParcelable(KEY_JIDELNICEK, menu);
		}
	}

	@Override
	public void setMenu(Menu menu) {

		this.menu = menu;
	}

	@Override
	public void updateJidelnicek() {

		if (menu != null && !menu.isEmpty()) {
			if (webView != null) {
				webView.clearView();

				CssTyp css = prefferenceManager.getCssTyp();
				String styled = menu.getStylledHtml(css);
				webView.loadDataWithBaseURL(BASE, styled, MIME, ENCODING, null);
			} else {
				//..webView je null
				if (getActivity() == null) {
					Log.e(TAG, "WebView i getActivity() je NULL");
				} else {

					AlertDialog.Builder bld = new AlertDialog.Builder(getActivity());
					bld.setMessage("WebView je null").setPositiveButton("OK", null);

					bld.create().show();
				}
			}
		}
	}

	public String getName() {
		return getTyp().toString();
	}

	public abstract MenuType getTyp();
}
