package cz.upce.fei.jidelak.model;


public class DenniJidelnicekKampusImpl implements IDenniJidelnicek {
	
	private static final long serialVersionUID = 1L;
	
	String den;
	String polivka;
	String jidlo1;
	String jidlo2;
	String jidlo3;
	String bezmaseJidlo;

	String vecere1;
	String vecere2;
	
	public DenniJidelnicekKampusImpl(String den, String polivka, String j1, String j2, String  j3, String bezmase, String veca1,String veca2) {
		this.den = den;
		this.polivka = polivka;
		this.jidlo1 = j1;
		this.jidlo2 = j2;
		this.jidlo3 = j3;
		this.bezmaseJidlo = bezmase;
		this.vecere1 = veca1;
		this.vecere2 = veca2;
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
	public String getPolivka() {
		return polivka;
	}

	@Override
	public void setPolivka(String polivka) {
		this.polivka = polivka;
	}

	@Override
	public String getJidlo1() {
		return jidlo1;
	}

	@Override
	public String getJidlo2() {
		return jidlo2;
	}

	@Override
	public String getJidlo3() {
		return jidlo3;
	}

	@Override
	public void setJidlo1(String value) {
		this.jidlo1 = value;
	}

	@Override
	public void setJidlo2(String value) {
		this.jidlo2 = value;
	}

	@Override
	public void setJidlo3(String value) {
		this.jidlo3 = value;
	}

	
	@Override
	public String getBezmase() {
		return bezmaseJidlo;
	}

	@Override
	public String getVecere1() {
		return vecere1;
	}

	@Override
	public String getVecere2() {
		return vecere2;
	}

	@Override
	public void setBezmasse(String value) {
		bezmaseJidlo = value;
	}

	@Override
	public void setVecere1(String value) {
		vecere1 = value;
	}

	@Override
	public void setVecere2(String value) {
		vecere2 = value;
	}
	
	@Override
	public JidelnicekTyp getTyp() {
		return JidelnicekTyp.KAMPUS;
	}
}
