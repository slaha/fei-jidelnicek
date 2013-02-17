package cz.upce.fei.jidelak.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import cz.upce.fei.jidelak.R;
import cz.upce.fei.jidelak.model.IDenniJidelnicek;

public class DenUtils {

	private static int DEN_LAYOUT = R.layout.den;
	
	LayoutInflater inflater;
	Context ctx;
	
	public DenUtils(Context c) {
		this.inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public View getView(IDenniJidelnicek den) {
		View view; 
		view = inflater.inflate(DEN_LAYOUT, null);
		
		TextView denTextView = (TextView) view.findViewById(R.id.den);
		TextView polivka = (TextView) view.findViewById(R.id.polivka);
		TextView jidlo1 = (TextView) view.findViewById(R.id.jidlo1);
		TextView jidlo2 = (TextView) view.findViewById(R.id.jidlo2);
		TextView jidlo3 = (TextView) view.findViewById(R.id.jidlo3);
		TextView vegetarianske = (TextView) view.findViewById(R.id.jidloBezmase);
		TextView vecere1 = (TextView) view.findViewById(R.id.vecere1);
		TextView vecere2 = (TextView) view.findViewById(R.id.vecere2);
		
		denTextView.setText(den.getDen());
		polivka.setText(den.getPolivka());
		jidlo1.setText(den.getJidlo1());
		jidlo2.setText(den.getJidlo2());
		jidlo3.setText(den.getJidlo3());
		
		String bezmase = den.getBezmase();
		if (bezmase != null) {
			vegetarianske.setText(bezmase);
			vegetarianske.setVisibility(View.VISIBLE);
		}
		String vecere1Str = den.getVecere1();
		if (vecere1Str != null) {
			vecere1.setText(vecere1Str);
			vecere1.setVisibility(View.VISIBLE);
		}
		String vecere2Str = den.getVecere2();
		if (vecere2Str != null) {
			vecere2.setText(vecere2Str);
			vecere2.setVisibility(View.VISIBLE);
		}

		return view;
	}

}
