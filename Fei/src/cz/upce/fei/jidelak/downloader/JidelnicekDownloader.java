package cz.upce.fei.jidelak.downloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import cz.upce.fei.jidelak.R;
import cz.upce.fei.jidelak.controller.IJidelnicekActivityController;
import cz.upce.fei.jidelak.model.JidelnicekTyp;
import cz.upce.fei.jidelak.parser.HtmlParserFactory;
import cz.upce.fei.jidelak.parser.IHtmlParser;
import cz.upce.fei.jidelak.utils.RefreshViewHelper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: slaha
 * Date: 2013-04-14
 * Time: 7:43
 * To change this template use File | Settings | File Templates.
 */
public class JidelnicekDownloader extends AsyncTask<Void, Void, Void> {

	public static final String FEI_URL = "http://www.upce.cz/fei/fakulta/restaurace.html";
	public static final String KAMPUS_URL = "https://dokumenty.upce.cz/skm/menza/menu-menza.html";
	private static final String TAG = "JidelnicekDownloader";

	private String url;
	private RefreshViewHelper progressBar;
	private String result;
	private IJidelnicekActivityController controller;
	private final JidelnicekTyp typ;

	public JidelnicekDownloader(JidelnicekTyp typ, RefreshViewHelper progressBar, IJidelnicekActivityController ctrl) {
		this.typ = typ;
		switch (typ) {
			case FEI:
				this.url = FEI_URL;
				break;
			case KAMPUS:
				this.url = KAMPUS_URL;
				break;
		}
		this.progressBar = progressBar;
		this.controller = ctrl;
	}

	@Override
	protected void onPreExecute() {
		Context c = controller.getContext();

		progressBar.startAnimation();

		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... voids) {

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);

		HttpResponse response = null;
		try {
			response = httpClient.execute(get);
			String downloadedHtml = EntityUtils.toString(response.getEntity());

			IHtmlParser htmlParser = HtmlParserFactory.getParser(typ);
			this.result = htmlParser.parse(downloadedHtml);
		} catch (Exception e) {
			Log.e(TAG, "Něco se nepovedlo při stahování jídelníčku", e);
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		progressBar.stopAnimation();

		controller.setResult(this.result, typ);
	}
}
