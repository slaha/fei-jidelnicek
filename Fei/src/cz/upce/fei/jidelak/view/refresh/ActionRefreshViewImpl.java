package cz.upce.fei.jidelak.view.refresh;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import cz.upce.fei.jidelak.R;

/** Created with IntelliJ IDEA. User: slaha Date: 31.7.13 Time: 17:41 */
public class ActionRefreshViewImpl extends RefreshViewHelper {

	private MenuItem item;

	private Animation rotation;
	private ImageView view;

	public ActionRefreshViewImpl(Context context, MenuItem item) {
		this.item = item;
		this.rotation = AnimationUtils.loadAnimation(context, R.anim.refresh);
		this.rotation.setRepeatCount(Animation.INFINITE);

		Drawable drawable = context.getResources().getDrawable(android.R.drawable.ic_popup_sync);

		this.view = new ImageView(context);

		view.setImageDrawable(drawable);
	}

	@Override
	public void startProgress() {
		view.startAnimation(rotation);
		item.setActionView(view);
	}

	@Override
	public void stopProgress() {
		item.getActionView().clearAnimation();
		item.collapseActionView();
		item.setActionView(null);

	}
}
