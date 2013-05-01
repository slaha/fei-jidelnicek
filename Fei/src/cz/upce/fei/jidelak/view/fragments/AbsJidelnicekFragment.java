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
import cz.upce.fei.jidelak.controller.PrefferenceManager;
import cz.upce.fei.jidelak.model.CssTyp;
import cz.upce.fei.jidelak.model.JidelnicekTyp;
import cz.upce.fei.jidelak.utils.HtmlHelper;

public abstract class AbsJidelnicekFragment extends Fragment implements IJidelnicekFragment {


	private static final String base = "file:///android_asset/";
	private static final String mime = "text/html";
	private static final String encoding = "utf-8";
	private static final String TAG = AbsJidelnicekFragment.class.getSimpleName();

	protected WebView webView;
	protected String jidelnicek;

	public final static String KEY_JIDELNICEK = "key_jidelnicek";

	protected PrefferenceManager prefferenceManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		if (savedInstanceState != null) {
			this.jidelnicek = savedInstanceState.getString(KEY_JIDELNICEK, null);
		}

		this.prefferenceManager = new PrefferenceManager(getActivity());

		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_jidelnicek, null);

		webView = (WebView) view.findViewById(R.id.webView);

		if (webView != null && jidelnicek != null) {
			updateJidelnicek();
		}

		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (jidelnicek != null) {
			outState.putString(KEY_JIDELNICEK, jidelnicek);
		}
	}

	@Override
	public void setJidelnicek(String jidelnicek) {

		this.jidelnicek = jidelnicek;
	}

	@Override
	public void updateJidelnicek() {

		if (jidelnicek != null && !jidelnicek.isEmpty()) {
			if (webView != null) {
				webView.clearView();

				CssTyp css = prefferenceManager.getCssTyp();
				String styled = HtmlHelper.surroundHtml(jidelnicek, css);
				webView.loadDataWithBaseURL(base, styled, mime, encoding, null);
			} else {
				//..webView je null
				if (getActivity() == null) {
					Log.e(TAG, "WebView i getActivity() je NULL");
				} else {

					AlertDialog.Builder bld = new AlertDialog.Builder(getActivity());
					bld
						.setMessage("WebView je null")
						.setPositiveButton("OK", null);

					bld.create().show();
				}
			}
		}
	}

	public String getName() {
		return getTyp().toString();
	}

	public abstract JidelnicekTyp getTyp();
}
