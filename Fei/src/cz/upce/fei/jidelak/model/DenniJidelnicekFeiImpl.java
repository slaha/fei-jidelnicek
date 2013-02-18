package cz.upce.fei.jidelak.model;

import java.util.List;


public class DenniJidelnicekFeiImpl extends AbsDenniJidelnicek {

	private static final long serialVersionUID = 1L;

	public DenniJidelnicekFeiImpl(String den,  List<String> jidla) {
		super(den, jidla);
	}

	@Override
	public JidelnicekTyp getTyp() {
		return JidelnicekTyp.FEI;
	}
}
