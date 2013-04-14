package cz.upce.fei.jidelak.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cz.upce.fei.jidelak.R;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: slaha
 * Date: 2013-04-14
 * Time: 12:40
 * To change this template use File | Settings | File Templates.
 */
public class RefreshViewHelper {


	private MenuItem item;

	private Animation rotation;
	private ImageView view;

	private AtomicInteger animationCount;

	public RefreshViewHelper(MenuItem item, Context context) {
		this.item = item;
		this.animationCount = new AtomicInteger(0);

		this.rotation = AnimationUtils.loadAnimation(context, R.anim.refresh);
		this.rotation.setRepeatCount(Animation.INFINITE);

		Drawable drawable = context.getResources().getDrawable(android.R.drawable.ic_popup_sync);

		this.view = new ImageView(context);

		view.setImageDrawable(drawable);
	}


	public void startAnimation() {

		if (animationCount.incrementAndGet() == 1) {

			view.startAnimation(rotation);

			item.setActionView(view);
		}
	}

	public void stopAnimation() {

		if (animationCount.decrementAndGet() == 0) {
			item.getActionView().clearAnimation();
			item.collapseActionView();
			item.setActionView(null);
		}
	}
}
