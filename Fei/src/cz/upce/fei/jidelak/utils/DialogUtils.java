package cz.upce.fei.jidelak.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import cz.upce.fei.jidelak.R;
import cz.upce.fei.jidelak.controller.IJidelnicekActivityController;
import cz.upce.fei.jidelak.view.IJidelnicekActivity;
import cz.upce.fei.jidelak.view.refresh.RefreshViewHelper;

/**
 * Created with IntelliJ IDEA.
 * User: slaha
 * Date: 2013-04-15
 * Time: 22:10
 */
public class DialogUtils {

	public static Dialog getErrorDialog(Context ctx) {

		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle(ctx.getText(R.string.err_document_null)).setCancelable(false)
				.setMessage(R.string.err_document_null_msg).setPositiveButton(android.R.string.ok, null);

		return builder.create();
	}

	public static Dialog getFirstRunDialog(Context ctx, final IJidelnicekActivity jidelnicekActivity) {

		DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				switch (which) {
				case Dialog.BUTTON_POSITIVE:
					jidelnicekActivity.startSettingsActivity();
					break;
				}
				dialog.dismiss();
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle(ctx.getText(R.string.dlg_first_run_title)).setCancelable(false)
				.setMessage(R.string.dlg_first_run_message).setNegativeButton(android.R.string.no, listener)
				.setPositiveButton(android.R.string.yes, listener);

		return builder.create();

	}

	public static Dialog getNewWeekRefreshDialog(Context ctx, final RefreshViewHelper imageView,
			final IJidelnicekActivityController ctrl) {
		DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					ctrl.doFullRefresh(imageView);
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
				dialog.dismiss();
			}
		};

		AlertDialog.Builder adb = new AlertDialog.Builder(ctx);

		adb.setTitle(android.R.string.dialog_alert_title).setMessage(R.string.dlg_update)
				.setPositiveButton(android.R.string.yes, listener).setNegativeButton(android.R.string.no, listener);

		return adb.create();
	}

	public static Dialog getRefreshDialog(Context ctx) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle(ctx.getText(R.string.dlg_reload_needed_title)).setCancelable(false)
				.setMessage(R.string.dlg_reload_needed).setPositiveButton(android.R.string.ok, null);

		return builder.create();
	}
}
