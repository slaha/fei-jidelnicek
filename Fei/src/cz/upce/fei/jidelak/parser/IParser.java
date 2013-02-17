package cz.upce.fei.jidelak.parser;

import java.util.List;

import org.jsoup.nodes.Document;

import cz.upce.fei.jidelak.model.IDenniJidelnicek;

public interface IParser {
	
	public List<IDenniJidelnicek> parseDocument(Document document);

}
