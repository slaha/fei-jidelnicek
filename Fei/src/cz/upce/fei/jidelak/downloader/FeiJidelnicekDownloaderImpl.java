package cz.upce.fei.jidelak.downloader;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.util.Log;
import android.view.View;
import cz.upce.fei.jidelak.controller.JidelnicekActivityContollerImpl;
import cz.upce.fei.jidelak.parser.IParser;


public class FeiJidelnicekDownloaderImpl extends AbsJidelnicekDownloader {
	private static final String TAG = "JidelnicekDownloader";

	private static final String FEI_JIDELNICEK = "http://www.upce.cz/fei/fakulta/restaurace.html";
	
	public FeiJidelnicekDownloaderImpl(View progressBar, IParser parser, JidelnicekActivityContollerImpl jidelnicekContollerImpl) {
		super(progressBar, parser, jidelnicekContollerImpl);
	}
	
	@Override
	protected Document doInBackground(Void... params) {
		
		Document r = null;
		
		HttpClient client = new HttpClient();
		client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);

		try {
			GetMethod get = new GetMethod(FEI_JIDELNICEK);
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
