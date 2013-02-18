package cz.upce.fei.jidelak.model;

import java.io.Serializable;
import java.util.List;



public interface IDenniJidelnicek extends Serializable {

	String getDen();
	List<String> getJidla();
	
	void setDen(String string);
	void setJidla(List<String> jidla);
	
	JidelnicekTyp getTyp();	
}
