package cz.upce.fei.jidelak;

import cz.upce.fei.jidelak.utils.DBUtilsDen;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.backup.RestoreObserver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;

public class Jidelnicek extends Activity implements OnClickListener {

	String login;
	String password;

	LinearLayout layout;
	View progressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jidelnicek);

		layout = (LinearLayout) findViewById(R.id.LinearLayout1);
		progressBar = findViewById(R.id.progressBar1);
		checkFirstRun();
		
		restore();
	}

	private void restore() {
		
		DBUtilsDen dbUtilsDen = new DBUtilsDen(this);
		dbUtilsDen.open();
		for (Den den : dbUtilsDen.getAllDays()) {
		
			layout.addView(den.getView(this));
		}
		dbUtilsDen.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_jidelnicek, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_settings:
			startSettingsActivity();
			return true;
		case R.id.menu_refresh:
			doRefresh();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void doRefresh() {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String log = preferences.getString(SettingsActivity.LOGIN_PREFERENCE,
				"");
		String pass = preferences.getString(
				SettingsActivity.PASSWORD_PREFERENCE, "");

		if (log.trim().length() > 0 && pass.trim().length() > 0) {
			login = log;
			password = pass;
			///..všecko vyplněný
			startDownload();
		} else {
			checkLoginAndPassword(log, pass);
		}
	}

	private boolean checkLoginAndPassword(String log, String pass) {
		if (log.trim().length() == 0 && pass.trim().length() == 0) {
			
			final EditText input = new EditText(this);
			DialogInterface.OnClickListener positiveButtonListener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					login = input.getText().toString();
					showPasswordDialog();
					dialog.dismiss();
				}
			}; 
			
			showLoginDialog(input, positiveButtonListener);
		}else if (log.trim().length() == 0 && pass.trim().length() > 0 ) {
			password = pass;
			final EditText input = new EditText(this);
			DialogInterface.OnClickListener positiveButtonListener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					login = input.getText().toString();
					
					startDownload();
					
					dialog.dismiss();
				}
			};
			
			showLoginDialog(input, positiveButtonListener);
		} else if (pass.trim().length() == 0 && log.trim().length() > 0) {
			login = log;
			showPasswordDialog();
		}
		
		
		return true;
	}
	
	private void showLoginDialog(final EditText input, DialogInterface.OnClickListener positiveButtonListener) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Zadejte jméno");
		alert.setView(input);

		alert.setPositiveButton("Ok",
				positiveButtonListener);

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				});
		alert.show();
	}
	private void showPasswordDialog() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Zadejte heslo");
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		alert.setView(input);

		alert.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						password = input.getText().toString();
						startDownload();
						dialog.dismiss();
					}
				});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				});
		alert.show();
	}

	private void startSettingsActivity() {
		startActivity(new Intent(this, SettingsActivity.class));

	}

	private void checkFirstRun() {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String login = preferences.getString(SettingsActivity.LOGIN_PREFERENCE,
				"");
		String pass = preferences.getString(
				SettingsActivity.PASSWORD_PREFERENCE, "");

		if (login.trim().length() == 0 && pass.trim().length() == 0) {

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getText(R.string.dlg_first_run_title))
					.setMessage(R.string.dlg_first_run_message)
					.setNegativeButton(android.R.string.no, this)
					.setPositiveButton(android.R.string.yes, this);

			builder.show();

		}

	}

	private void startDownload() {
		JidelnicekDownloader jd = new JidelnicekDownloader(login, password, layout, progressBar);
		jd.execute();
	}
	@Override
	public void onClick(DialogInterface dialog, int which) {

		switch (which) {
		case Dialog.BUTTON_POSITIVE:
			startSettingsActivity();
			break;
		}
		dialog.dismiss();

	}
}
