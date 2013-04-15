package cz.upce.fei.jidelak.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import cz.upce.fei.jidelak.R;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: slaha
 * Date: 2013-04-14
 * Time: 12:40
 */
public class RefreshViewHelper {


	private MenuItem item;

	private Animation rotation;
	private ImageView view;

	private ProgressDialog progressDialog;

	private AtomicInteger animationCount;

	public RefreshViewHelper(MenuItem item, Context context) {
		this.animationCount = new AtomicInteger(0);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			this.item = item;
			this.rotation = AnimationUtils.loadAnimation(context, R.anim.refresh);
			this.rotation.setRepeatCount(Animation.INFINITE);

			Drawable drawable = context.getResources().getDrawable(android.R.drawable.ic_popup_sync);

			this.view = new ImageView(context);

			view.setImageDrawable(drawable);
		} else {
			progressDialog = new ProgressDialog(context);
			CharSequence text = context.getResources().getString(R.string.dlg_updating);
			progressDialog.setMessage(text);
		}
	}


	public void startAnimation() {

		if (animationCount.incrementAndGet() == 1) {
 			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				view.startAnimation(rotation);

				item.setActionView(view);
			} else {
				progressDialog.show();
			 }
		}
	}

	public void stopAnimation() {

		if (animationCount.decrementAndGet() == 0) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				item.getActionView().clearAnimation();
				item.collapseActionView();
				item.setActionView(null);
			} else {
				progressDialog.dismiss();
			}
		}
	}
}
