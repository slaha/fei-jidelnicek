package cz.upce.fei.jidelak.model;


public class DenniJidelnicekFeiImpl implements IDenniJidelnicek {

	private static final long serialVersionUID = 1L;
	
	private long id;
	String den;
	String polivka;
	String jidlo1;
	String jidlo2;
	String jidlo3;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public DenniJidelnicekFeiImpl() {
	}

	public DenniJidelnicekFeiImpl(String den, String polivka, String j1, String j2,
			String j3) {
		this.den = den;
		this.polivka = polivka;
		this.jidlo1 = j1;
		this.jidlo2 = j2;
		this.jidlo3 = j3;
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
		return null;
	}

	@Override
	public String getVecere1() {
		return null;
	}

	@Override
	public String getVecere2() {
		return null;
	}

	@Override
	public void setBezmasse(String string) {
		// nothing
	}

	@Override
	public void setVecere1(String string) {
		// nothing
	}

	@Override
	public void setVecere2(String string) {
		// nothing
	}

	@Override
	public JidelnicekTyp getTyp() {
		return JidelnicekTyp.FEI;
	}
}
