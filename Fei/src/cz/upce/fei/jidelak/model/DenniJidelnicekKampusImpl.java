package cz.upce.fei.jidelak.model;

import java.util.List;


public class DenniJidelnicekKampusImpl extends AbsDenniJidelnicek {
	
	private static final long serialVersionUID = 1L;
	
	public DenniJidelnicekKampusImpl(String den, List<String> jidla) {
		super(den, jidla);
	}

	@Override
	public JidelnicekTyp getTyp() {
		return JidelnicekTyp.KAMPUS;
	}
}
