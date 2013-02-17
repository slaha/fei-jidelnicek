package cz.upce.fei.jidelak.model;

import java.io.Serializable;



public interface IDenniJidelnicek extends Serializable {

	String getDen();
	String getPolivka();
	String getJidlo1();
	String getJidlo2();
	String getJidlo3();
	String getBezmase();
	String getVecere1();
	String getVecere2();
	
	void setDen(String string);
	void setPolivka(String string);
	void setJidlo1(String string);
	void setJidlo2(String string);
	void setJidlo3(String string);
	void setBezmasse(String string);
	void setVecere1(String string);
	void setVecere2(String string);
	
	JidelnicekTyp getTyp();	
}
