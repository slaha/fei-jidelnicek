package cz.upce.fei.jidelak.model;

import java.util.Collection;
import java.util.List;


public class DenniJidelnicekFeiImpl implements IDenniJidelnicek {

	private static final long serialVersionUID = 1L;
	
	private long id;
	String den;
	
	List<String> jidla;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public DenniJidelnicekFeiImpl() {
	}

	public DenniJidelnicekFeiImpl(String den,  List<String> jidla) {
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
		return JidelnicekTyp.FEI;
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
