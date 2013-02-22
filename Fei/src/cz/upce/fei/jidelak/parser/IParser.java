package cz.upce.fei.jidelak.parser;

import org.jsoup.nodes.Document;

import android.view.View;
import cz.upce.fei.jidelak.controller.IJidelnicekActivityController;

public interface IParser {
	
	public void parseDocument(Document document, IJidelnicekActivityController ctrl, View pB);

}
