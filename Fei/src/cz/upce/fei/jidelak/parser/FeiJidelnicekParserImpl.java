package cz.upce.fei.jidelak.parser;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator.IsEmpty;

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
		boolean dontSkip = false; //přeskočit publikováno
		
		Elements allRows = content.getElementsByTag(RADEK);
		
		List<String> jidla;
		
		for (int i = 0; i < allRows.size(); i++) {
			if (hasStrong(allRows.get(i)) && dontSkip) {
				//..je to nový den v jídelníčku
				
				jidla = new ArrayList<String>();
				String den = allRows.get(i).text();
				
				do {
					jidla.add(allRows.get(++i).text());
				} while(isNotEmptyParagraph(allRows.get(i)));
				
				dny.add(new DenniJidelnicekFeiImpl(den, jidla));
			} else if (hasStrong(allRows.get(i))) {
				dontSkip = true; //další nepřeskakovat
			}
		}	
		
		return dny;
	}
	
	private boolean isNotEmptyParagraph(Element element) {
		String text = removeWhiteSpaces(element.text());
		
		return (text.length() != 0);
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
