package cz.upce.fei.jidelak.view.refresh;

import android.app.ProgressDialog;
import android.content.Context;
import cz.upce.fei.jidelak.R;

/** Created with IntelliJ IDEA. User: slaha Date: 31.7.13 Time: 17:41 */
public class DialogRefreshViewImpl extends RefreshViewHelper {

	private ProgressDialog progressDialog;

	public DialogRefreshViewImpl(Context context) {
		progressDialog = new ProgressDialog(context);
		CharSequence text = context.getResources().getString(R.string.dlg_updating);
		progressDialog.setMessage(text);
	}

	@Override
	public void startProgress() {
		progressDialog.show();
	}

	@Override
	public void stopProgress() {
		progressDialog.dismiss();
	}
}
