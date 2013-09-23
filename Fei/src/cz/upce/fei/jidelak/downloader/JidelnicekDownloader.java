package cz.upce.fei.jidelak.downloader;

import android.os.AsyncTask;
import android.util.Log;
import cz.upce.fei.jidelak.controller.IJidelnicekActivityController;
import cz.upce.fei.jidelak.model.MenuType;
import cz.upce.fei.jidelak.parser.HtmlParserFactory;
import cz.upce.fei.jidelak.parser.IHtmlParser;
import cz.upce.fei.jidelak.view.refresh.RefreshViewHelper;
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
 */
public class JidelnicekDownloader extends AsyncTask<Void, Void, Void> {

	private static final String TAG = JidelnicekDownloader.class.getSimpleName();

	private static final String FEI_URL = "http://www.upce.cz/fei/fakulta/restaurace.html";
	private static final String KAMPUS_URL = "https://dokumenty.upce.cz/skm/menza/menu-menza.html";

	private String url;
	private RefreshViewHelper progressBar;
	private String result;
	private IJidelnicekActivityController controller;
	private final MenuType typ;

	public JidelnicekDownloader(MenuType typ, RefreshViewHelper progressBar, IJidelnicekActivityController ctrl) {
		switch (typ) {
		case FEI:
			this.url = FEI_URL;
			break;
		case KAMPUS:
			this.url = KAMPUS_URL;
			break;
		}

		this.typ = typ;
		this.progressBar = progressBar;
		this.controller = ctrl;
	}

	@Override
	protected void onPreExecute() {
		progressBar.startAnimation();

		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... voids) {

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);

		HttpResponse response;
		try {
			response = httpClient.execute(get);
			String downloadedHtml = EntityUtils.toString(response.getEntity());

			IHtmlParser htmlParser = HtmlParserFactory.createParser(typ);
			this.result = htmlParser.parse(downloadedHtml);
		} catch (IOException ioe) {
			Log.e(TAG, "Něco se nepovedlo při stahování jídelníčku", ioe);
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void _void) {
		progressBar.stopAnimation();

		controller.setResult(result, typ);
	}
}
