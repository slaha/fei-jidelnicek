package cz.upce.fei.jidelak.model;

import java.util.ArrayList;
import java.util.List;

public class TydenniJidelnicekImpl implements ITydenniJidelnicek {

	private static final long serialVersionUID = 1L;
	
	List<IDenniJidelnicek> tydenniJidelnicek;
	
	public TydenniJidelnicekImpl() {
		tydenniJidelnicek = new ArrayList<IDenniJidelnicek>();
	}

	@Override
	public List<IDenniJidelnicek> getDays() {
		return tydenniJidelnicek;
	}

	@Override
	public void setDays(List<IDenniJidelnicek> days) {
		this.tydenniJidelnicek = days;
	}

	@Override
	public boolean isEmpty() {
		return tydenniJidelnicek.isEmpty();
	}
}
