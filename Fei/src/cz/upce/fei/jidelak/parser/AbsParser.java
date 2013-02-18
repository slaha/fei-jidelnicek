package cz.upce.fei.jidelak.parser;

import android.R.string;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import cz.upce.fei.jidelak.view.IJidelnicekActivity;

public class AbsParser {

	protected IJidelnicekActivity jidelnicekActivity;
	
	public AbsParser(IJidelnicekActivity act) {
		this.jidelnicekActivity = act;
	}
	
	protected void showDialog(Dialog d) {
		jidelnicekActivity.setAndShowDialog(d);
	}
	
	protected Dialog getErrorDialog(CharSequence title, CharSequence message) {
		AlertDialog.Builder adb = new AlertDialog.Builder(jidelnicekActivity.getContext());
		
		adb.setTitle(title)
		.setMessage(message)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setPositiveButton(string.ok, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		return adb.create();
	}
	
	protected String removeWhiteSpaces(String s) {
		return s.replace(((char)160), ' ').trim();
	}
	
	protected String capitalizeDay(String den) {
		char velkePrvni = Character.toUpperCase(den.charAt(0));
		return velkePrvni + den.substring(1);
	}
}
