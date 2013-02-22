package cz.upce.fei.jidelak.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import cz.upce.fei.jidelak.R;
import cz.upce.fei.jidelak.model.IDenniJidelnicek;
import cz.upce.fei.jidelak.model.ITydenniJidelnicek;
import cz.upce.fei.jidelak.model.JidelnicekTyp;
import cz.upce.fei.jidelak.utils.DenUtils;

public abstract class AbsJidelnicekFragment extends Fragment implements IJidelnicekFragment {

	protected ITydenniJidelnicek tydenniJidelnicek;
	protected LinearLayout layout;
	protected View progressBar;
	
	
	public final static String KEY_JIDELNICEK = "key_jidelnicek";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		if (savedInstanceState != null) {
			tydenniJidelnicek = (ITydenniJidelnicek) savedInstanceState.getSerializable(KEY_JIDELNICEK);
		}
		
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_jidelnicek, null);
		
		layout = (LinearLayout) view.findViewById(R.id.LinearLayout1);
		progressBar = view.findViewById(R.id.progressBar1);
		
		if (tydenniJidelnicek != null) {
			updateJidelnicek();
		}
		
		return view;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (tydenniJidelnicek != null) {
			outState.putSerializable(KEY_JIDELNICEK, tydenniJidelnicek);
		}
	}
	
	@Override
	public void setJidelnicek(ITydenniJidelnicek jidelnicek) {
		this.tydenniJidelnicek = jidelnicek;
	}
	
	@Override
	public void updateJidelnicek() {

		if (!tydenniJidelnicek.isEmpty()) {
			if (layout != null) {
				layout.removeAllViews();
				
				DenUtils denUtils = new DenUtils(getActivity());
				for (IDenniJidelnicek den : tydenniJidelnicek.getDays()) {
					layout.addView(denUtils.getView(den));
				}
			}
		}
	}
	
	@Override
	public ITydenniJidelnicek getJidelnicek() {
		return tydenniJidelnicek;
	}
	public String getName() {
		return getTyp().toString();
	}
	
	@Override
	public View getProgressBar() {
		return progressBar;
	}
	public abstract JidelnicekTyp getTyp();
}
