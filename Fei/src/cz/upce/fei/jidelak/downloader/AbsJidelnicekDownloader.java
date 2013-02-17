package cz.upce.fei.jidelak.downloader;

import java.util.List;

import org.jsoup.nodes.Document;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.View;
import cz.upce.fei.jidelak.R;
import cz.upce.fei.jidelak.controller.IJidelnicekActivityController;
import cz.upce.fei.jidelak.model.IDenniJidelnicek;
import cz.upce.fei.jidelak.parser.IParser;

public abstract class AbsJidelnicekDownloader extends AsyncTask<Void, Void, Document> {

	protected Context c;
	protected View progressBar;
	protected IParser parser;
	private IJidelnicekActivityController controller;
	
	public AbsJidelnicekDownloader(View progressBar, IParser parser, IJidelnicekActivityController controller) {
		this.c = progressBar.getContext();
		this.progressBar = progressBar;
		this.parser = parser;
		
		this.controller = controller;
	}
	
	@Override
	protected void onPreExecute() {
		progressBar.setVisibility(View.VISIBLE);
		super.onPreExecute();
	}
	

	@Override
	protected void onPostExecute(Document result) {
		
		if (result == null) {
			getErrorDialog(c.getText(android.R.string.dialog_alert_title), c.getText(R.string.err_document_null));
		} else {
			
			List<IDenniJidelnicek> dny = parser.parseDocument(result);
			
			controller.updateDays(dny);
		}
		progressBar.setVisibility(View.GONE);
	}
	
	private Dialog getErrorDialog(CharSequence title, CharSequence message) {
		AlertDialog.Builder alert = new AlertDialog.Builder(c);
		
		alert.setTitle(title)
		.setMessage(message)
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override		
			public void onClick(DialogInterface dialog, int whichButton) {
						
					dialog.dismiss();
				}
			});
		
		return alert.create();
	}
}
