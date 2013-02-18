package cz.upce.fei.jidelak.model;

import java.util.List;


public class DenniJidelnicekKampusImpl implements IDenniJidelnicek {
	
	private static final long serialVersionUID = 1L;
	
	String den;
	
	List<String> jidla;
	
	public DenniJidelnicekKampusImpl(String den, List<String> jidla) {
		this.den = den;
		this.jidla = jidla;
	}

	@Override
	public String getDen() {
		return den;
	}

	@Override
	public void setDen(String den) {
		this.den = den;
	}

	@Override
	public JidelnicekTyp getTyp() {
		return JidelnicekTyp.KAMPUS;
	}

	@Override
	public List<String> getJidla() {
		return jidla;
	}

	@Override
	public void setJidla(List<String> jidla) {
		this.jidla = jidla;
	}
}
