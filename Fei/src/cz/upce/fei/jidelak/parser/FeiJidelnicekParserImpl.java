package cz.upce.fei.jidelak.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import android.R.string;
import cz.upce.fei.jidelak.R;
import cz.upce.fei.jidelak.model.DenniJidelnicekFeiImpl;
import cz.upce.fei.jidelak.model.IDenniJidelnicek;
import cz.upce.fei.jidelak.view.IJidelnicekActivity;

public class FeiJidelnicekParserImpl extends AbsParser {

	public FeiJidelnicekParserImpl(IJidelnicekActivity act) {
		super(act);
	}

	private static final String DIV_CONTENT = "content-text";
	private static final String DIV = "div";
	
	@Override
	public List<IDenniJidelnicek> parseDocument() {
		
		if (document == null) {
			showDialog(
					getErrorDialog(
							jidelnicekActivity.getContext().getText(string.dialog_alert_title), 
							jidelnicekActivity.getContext().getText(R.string.err_document_null)
					)
			);
		}
		Element content = document.getElementsByClass(DIV_CONTENT).get(0);
		
		if (content == null) {
			showDialog(
					getErrorDialog(
							jidelnicekActivity.getContext().getText(string.dialog_alert_title), 
							jidelnicekActivity.getContext().getText(R.string.err_document_null)
					)
			);
		}
		
		ArrayList<IDenniJidelnicek> dny = new ArrayList<IDenniJidelnicek>();
		
		Elements allDivs = content.getElementsByTag(DIV);
		
		List<String> jidla;
		
		for (int i = 0; i < allDivs.size(); i++) {
			if (hasStrong(allDivs.get(i))) {
				//..je to nový den v jídelníčku
				
				jidla = new ArrayList<String>();
				String den = allDivs.get(i).text();
				den = capitalizeDay(den);
				
				do {
					++i;
					if (isNotEmptyParagraph(allDivs.get(i))) {
						jidla.addAll(getJidla(allDivs.get(i)));
					}
					//jidla.add(allDivs.get(++i).text());
				} while(((i+1) < allDivs.size()) && (!hasStrong(allDivs.get(i+1))));
				
				dny.add(new DenniJidelnicekFeiImpl(den, jidla));
			} 
		}	
		int varovaniIndex = dny.get(dny.size() -1).getJidla().size() - 1;
		dny.get(dny.size() -1).getJidla().remove(varovaniIndex);
		return dny;
	}
	
	private Collection<? extends String> getJidla(Element element) {
		List<String> jidla = new ArrayList<String>();
		
		List<TextNode> radkyJidel = element.textNodes();
		
		for (TextNode jidloRadek : radkyJidel) {
			String jidloRadekStr = removeWhiteSpaces(jidloRadek.text());
			if (jidloRadekStr.length() > 0) {
				jidla.add(jidloRadekStr);
			}
		}
		
		return jidla;
	}

	private boolean isNotEmptyParagraph(Element element) {
		String text = removeWhiteSpaces(element.text());
		
		return (text.length() != 0);
	}

	private boolean hasStrong(org.jsoup.nodes.Element e) {
		Elements children = e.children();
		
		if (children.size() == 0) {
			return false;
		} else if ("strong".equals(children.get(0).tagName().toLowerCase(Locale.US))) {
			return true;
		}
		
		return false;
	}

}
