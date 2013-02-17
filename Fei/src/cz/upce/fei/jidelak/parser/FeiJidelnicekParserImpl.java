package cz.upce.fei.jidelak.parser;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.R.string;

import cz.upce.fei.jidelak.R;
import cz.upce.fei.jidelak.model.DenniJidelnicekFeiImpl;
import cz.upce.fei.jidelak.model.IDenniJidelnicek;
import cz.upce.fei.jidelak.view.IJidelnicekActivity;

public class FeiJidelnicekParserImpl extends AbsParser implements IParser {

	public FeiJidelnicekParserImpl(IJidelnicekActivity act) {
		super(act);
	}

	private static final String DIV_CONTENT = "contents";
	private static final String RADEK = "p";
	
	@Override
	public List<IDenniJidelnicek> parseDocument(Document document) {
		
		if (document == null) {
			showDialog(
					getErrorDialog(
							jidelnicekActivity.getContext().getText(string.dialog_alert_title), 
							jidelnicekActivity.getContext().getText(R.string.err_document_null)
					)
			);
		}
		Element content = document.getElementById(DIV_CONTENT);
		
		if (content == null) {
			showDialog(
					getErrorDialog(
							jidelnicekActivity.getContext().getText(string.dialog_alert_title), 
							jidelnicekActivity.getContext().getText(R.string.err_document_null)
					)
			);
		}
		
		ArrayList<IDenniJidelnicek> dny = new ArrayList<IDenniJidelnicek>();
		String den, polivka, j1, j2, j3;
		boolean dontSkip = false; //přeskočit publikováno
		
		Elements all = content.getElementsByTag(RADEK);
		
		for (int i = 0; i < all.size(); i++) {
			if (hasStrong(all.get(i)) && dontSkip) {
				den = all.get(i).text();
				polivka = all.get(++i).text();
				j1 = all.get(++i).text();
				j2 = all.get(++i).text();
				j3 = all.get(++i).text();
				dny.add(new DenniJidelnicekFeiImpl(den, polivka, j1, j2, j3));
			} else if (hasStrong(all.get(i))) {
				dontSkip = true; //další nepřeskakovat
			}
		}	
		
		return dny;
	}
	
	private boolean hasStrong(org.jsoup.nodes.Element e) {
		Elements children = e.children();
		
		if (children.size() == 0) {
			return false;
		} else if ("strong".equals(children.get(0).tagName().toLowerCase())) {
			return true;
		}
		
		return false;
	}

}
