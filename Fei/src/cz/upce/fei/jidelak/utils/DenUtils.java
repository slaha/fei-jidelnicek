package cz.upce.fei.jidelak.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
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
		View view = inflater.inflate(DEN_LAYOUT, null);
		
		LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.LinearLayout1);
		
		TextView denTextView = (TextView) view.findViewById(R.id.den);
		denTextView.setText(den.getDen());

		TextView textView;
		for (String jidlo : den.getJidla()) {
			textView = new TextView(linearLayout.getContext());
			textView.setText(jidlo);
			linearLayout.addView(textView);
		}
		
		return view;
	}

}
