package cz.upce.fei.jidelak.parser;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.R.string;

import cz.upce.fei.jidelak.R;
import cz.upce.fei.jidelak.model.DenniJidelnicekKampusImpl;
import cz.upce.fei.jidelak.model.IDenniJidelnicek;
import cz.upce.fei.jidelak.view.IJidelnicekActivity;

public class KampusJidelnicekParserImpl extends AbsParser implements IParser {

	private static final String CONTENT = "contentspace";
	private static final String TABLE = "table";
	private static final String TABLE_ROW = "tr";
	private static final String TABLE_CELL = "td";

	public KampusJidelnicekParserImpl(IJidelnicekActivity act) {
		super(act);
	}

	@Override
	public List<IDenniJidelnicek> parseDocument(Document document) {
		List<IDenniJidelnicek> dny = new ArrayList<IDenniJidelnicek>();
		if (document == null) {
			showDialog(
					getErrorDialog(
							jidelnicekActivity.getContext().getText(string.dialog_alert_title), 
							jidelnicekActivity.getContext().getText(R.string.err_document_null)
					)
			);
		}
		Elements contentspaces = document.getElementsByClass(CONTENT);
		
		if (contentspaces == null || contentspaces.size() < 1) {
			showDialog(
					getErrorDialog(
							jidelnicekActivity.getContext().getText(string.dialog_alert_title), 
							jidelnicekActivity.getContext().getText(R.string.err_document_null)
					)
			);
		}
		
		Elements tables = contentspaces.get(0).getElementsByTag(TABLE);
		
		if (tables == null || tables.size() < 3) {
			showDialog(
					getErrorDialog(
							jidelnicekActivity.getContext().getText(string.dialog_alert_title), 
							jidelnicekActivity.getContext().getText(R.string.err_document_null)
					)
			);
		}
		Element table = tables.get(2);
		
		Elements rows = table.getElementsByTag(TABLE_ROW);
		
		List<List<Elements>> jidelnicekMapa = getJidelnicekNaDen(rows);
		
		for (List<Elements> jidlaNaDen : jidelnicekMapa) {
			dny.add(getKampusDen(jidlaNaDen));
		}
		return dny;
	}

	/**
	 * 
	 * @param rows řádky tabulky s jídelníčkem
	 * @return mapa, kte vnější list jsou dny (tzn. size by měla být pět), a vnitřní listy jsou řádky týkající se daného dne
	 */
	private List<List<Elements>> getJidelnicekNaDen(Elements rows) {
		List<List<Elements>> jidelnicekMapa = new ArrayList<List<Elements>>(5);
		
		List<Elements> denList = new ArrayList<Elements>();
		for (int i = 0; i < rows.size(); i++) {
			
			Elements cellsOnRow = rows.get(i).getElementsByTag(TABLE_CELL);
			if (!isEmptyRow(cellsOnRow)) {
				if (isFoodRow(cellsOnRow)) {
					denList.add(cellsOnRow);
				}
			} else {
				if (!denList.isEmpty()) {
					jidelnicekMapa.add(denList);
				}
				denList = new ArrayList<Elements>();
			}
		}
		
		return jidelnicekMapa;
	}
	
	private boolean isEmptyRow(Elements cellsOnRow) {
		
		for (Element element : cellsOnRow) {	
			String s = removeWhiteSpaces(
						element.text()
					);
			
			if (!s.isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isFoodRow(Elements cellsOnRow) {
		
		return !cellsOnRow.get(1).text().isEmpty();
	}

	private IDenniJidelnicek getKampusDen(List<Elements> jidlaNaDen) {

		String den, polivka, jidlo1, jidlo2, jidlo3, bezmase, veca1, veca2;
		den = polivka = jidlo1 = jidlo2 =  jidlo3 =  bezmase =  veca1 =  veca2 = null;
		
		den = getDen(jidlaNaDen.get(0), jidlaNaDen.get(1));
		polivka = getPokrm(jidlaNaDen.get(0));
		jidlo1 = getPokrm(jidlaNaDen.get(1));
		jidlo2 = getPokrm(jidlaNaDen.get(2));
		jidlo3 = getPokrm(jidlaNaDen.get(3));
		bezmase = getPokrm(jidlaNaDen.get(4));
		
		if (jidlaNaDen.size() > 5) {
			veca1 = getPokrm(jidlaNaDen.get(5));
			veca2 = getPokrm(jidlaNaDen.get(6));
		}
		
		
		return new DenniJidelnicekKampusImpl(den, polivka, jidlo1, jidlo2, jidlo3, bezmase, veca1, veca2);
		
	}
	
	private String getDen(Elements cellsInRowJmenoDne, Elements cellsInRowDatum) {
		String den = removeWhiteSpaces(
					cellsInRowJmenoDne.get(0).text()
				);

		String datum = removeWhiteSpaces(
					cellsInRowDatum.get(0).text()
				);
		
		char velkePrvni = Character.toUpperCase(den.charAt(0));
		den = velkePrvni + den.substring(1);
		return den + " " + datum;
	}

	private String getPokrm(Elements  cellsInRowPokrm) {
		String typ = removeWhiteSpaces(
				cellsInRowPokrm.get(1).text()
			) + ": ";
		
		String gramaz = removeWhiteSpaces(
					cellsInRowPokrm.get(2).text()
				);
		
		String pokrm = removeWhiteSpaces(
					cellsInRowPokrm.get(3).text()
				);
		
		if (gramaz.length() > 0) {
			return typ + gramaz + " " + pokrm;
		}
		//..kvuli polivce
		return typ + pokrm;
	}
	
	private String removeWhiteSpaces(String s) {
		return s.replace(((char)160), ' ').trim();
	}
}
 