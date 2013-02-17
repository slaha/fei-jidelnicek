package cz.upce.fei.jidelak.model;

import java.io.Serializable;
import java.util.List;

public interface ITydenniJidelnicek extends Serializable {

	List<IDenniJidelnicek> getDays();
	
	void setDays(List<IDenniJidelnicek> days);
	
	boolean isEmpty();
}
