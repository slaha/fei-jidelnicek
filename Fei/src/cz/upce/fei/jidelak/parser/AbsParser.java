package cz.upce.fei.jidelak.parser;

import java.util.List;

import org.jsoup.nodes.Document;

import android.R.string;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.view.View;
import cz.upce.fei.jidelak.controller.IJidelnicekActivityController;
import cz.upce.fei.jidelak.model.IDenniJidelnicek;
import cz.upce.fei.jidelak.view.IJidelnicekActivity;

public abstract class AbsParser extends AsyncTask<Void, Void, List<IDenniJidelnicek>> implements IParser {

	protected IJidelnicekActivity jidelnicekActivity;
	protected Document document;
	private IJidelnicekActivityController controller;
	private View progressBar;
	
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

	@Override
	public final void parseDocument(Document document,  
			IJidelnicekActivityController ctrl, View pB) {
		this.document = document;
		this.controller = ctrl;
		this.progressBar = pB;
		
		execute();
	}

	protected abstract List<IDenniJidelnicek> parseDocument();
	
	@Override
	protected List<IDenniJidelnicek> doInBackground(Void... params) {
		if (document == null || controller == null) {
			throw new IllegalStateException("");
		}
		return parseDocument();
	}
	
	@Override
	protected void onPostExecute(List<IDenniJidelnicek> list) {
		controller.updateDays(list);
		progressBar.setVisibility(View.GONE);
	}
}
