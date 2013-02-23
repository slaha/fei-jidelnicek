package cz.upce.fei.jidelak.downloader;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.util.Log;
import android.view.View;
import cz.upce.fei.jidelak.controller.IJidelnicekActivityController;
import cz.upce.fei.jidelak.parser.IParser;

public class KampusJidelnicekDownloaderImpl extends AbsJidelnicekDownloader {

	private static final String JIDELNICEK_URL = "https://dokumenty.upce.cz/skm/menza/menu-menza.html";
	private static final String TAG = "KampusJidelnicekDownloaderImpl";
	
	
	public KampusJidelnicekDownloaderImpl(View progressBar, IParser parser, IJidelnicekActivityController controller) {
		super(progressBar, parser, controller);
	}

	@Override
	protected Document doInBackground(Void... params) {
		
		Document r = null;
		
		HttpClient client = new HttpClient();
		client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);

		try {
			GetMethod get = new GetMethod(JIDELNICEK_URL);
			client.executeMethod(get);
			
			r = Jsoup.parse(get.getResponseBodyAsString());
			get.releaseConnection();

		} catch (IOException e) {
			Log.e(TAG, "Vyskytla se chyba při stahování jídelníčku", e);
			e.printStackTrace();
			r = null;
		}
		return r;
	}

}
