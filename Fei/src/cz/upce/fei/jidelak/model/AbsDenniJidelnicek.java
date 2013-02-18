package cz.upce.fei.jidelak.model;

import java.util.List;

public abstract class AbsDenniJidelnicek implements IDenniJidelnicek {

	private static final long serialVersionUID = 1L;


	private String den;
	List<String> jidla;
	
	public AbsDenniJidelnicek(String den, List<String> jidla) {
		this.den = den;
		this.jidla = jidla;
	}
	@Override
	public String getDen() {
		return den;
	}

	@Override
	public List<String> getJidla() {
		return jidla;
	}

	@Override
	public void setDen(String string) {
		this.den = string;
	}

	@Override
	public void setJidla(List<String> jidla) {
		this.jidla = jidla;
	}

	@Override
	public abstract JidelnicekTyp getTyp();
}
