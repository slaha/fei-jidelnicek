package cz.upce.fei.jidelak.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import cz.upce.fei.jidelak.R;
import cz.upce.fei.jidelak.model.JidelnicekTyp;

public abstract class AbsJidelnicekFragment extends Fragment implements IJidelnicekFragment {

	protected WebView webView;
	protected String jidelnicek;

	public final static String KEY_JIDELNICEK = "key_jidelnicek";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		if (savedInstanceState != null) {
			this.jidelnicek = savedInstanceState.getString(KEY_JIDELNICEK, null);
		}
		
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

				String base = "file:///android_asset/";
				String mime = "text/html";
				String encoding = "utf-8";

				webView.loadDataWithBaseURL(base, jidelnicek, mime, encoding, null);
			}
		}
	}
	
	public String getName() {
		return getTyp().toString();
	}

	public abstract JidelnicekTyp getTyp();
}
