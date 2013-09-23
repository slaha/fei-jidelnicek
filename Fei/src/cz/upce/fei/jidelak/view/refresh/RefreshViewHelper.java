package cz.upce.fei.jidelak.view.refresh;

import android.content.Context;
import android.os.Build;
import android.view.MenuItem;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: slaha
 * Date: 2013-04-14
 * Time: 12:40
 */
public abstract class RefreshViewHelper {

	private enum AnimationType {
		ACTION_VIEW,
		DIALOG
	}

	private AtomicInteger animationCount;

	public RefreshViewHelper() {
		this.animationCount = new AtomicInteger();
	}

	public static RefreshViewHelper create(Context context, MenuItem menuItem) {

		if (menuItem != null && getAnimationType() == AnimationType.ACTION_VIEW) {
			return new ActionRefreshViewImpl(context, menuItem);
		}

		return new DialogRefreshViewImpl(context);
	}

	private boolean isTimeToStartAnimation() {
		return animationCount.incrementAndGet() == 1;
	}

	public final void startAnimation() {
		if (isTimeToStartAnimation()) {
			startProgress();
		}
	}

	private boolean isTimeToStopAnimation() {
		return animationCount.decrementAndGet() == 0;
	}

	public final void stopAnimation() {
		if (isTimeToStopAnimation()) {
			stopProgress();
		}
	}

	/**
	 * Start with notifiing user download in progress
	 */
	protected abstract void startProgress();

	/**
	 * Download completed → stop notifiing user that download in progress
	 */
	protected abstract void stopProgress();

	private static AnimationType getAnimationType() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			return AnimationType.ACTION_VIEW;
		} else {
			return AnimationType.DIALOG;
		}
	}
}
