package cz.upce.fei.jidelak;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public  class Den {
	private long id;
	String den, polivka, j1, j2, j3;
	
	public long getId() {
		    return id;
		  }

	  public void setId(long id) {
		  this.id = id;
	  }
	  
	  public Den() { }
	  
	public Den(String den, String polivka, String j1, String j2,String  j3) {
		this.den = den;
		this.polivka = polivka;
		this.j1 = j1;
		this.j2 = j2;
		this.j3 = j3;
	}
	
	public View getView(Context c) {
		View view; 
		LayoutInflater inflater = (LayoutInflater)   c.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		view = inflater.inflate(R.layout.den, null);
		
		TextView denTV = (TextView) view.findViewById(R.id.den);
		TextView polivkaTV = (TextView) view.findViewById(R.id.polivka);
		TextView j1TV = (TextView) view.findViewById(R.id.jidlo1);
		TextView j2TV = (TextView) view.findViewById(R.id.jidlo2);
		TextView j3TV = (TextView) view.findViewById(R.id.jidlo3);
		
		denTV.setText(den);
		polivkaTV.setText(polivka);
		j1TV.setText(j1);
		j2TV.setText(j2);
		j3TV.setText(j3);
		
		return view;
		
	}

	public String getDen() {
		return den;
	}

	public void setDen(String den) {
		this.den = den;
	}

	public String getPolivka() {
		return polivka;
	}

	public void setPolivka(String polivka) {
		this.polivka = polivka;
	}

	public String getJ1() {
		return j1;
	}

	public void setJ1(String j1) {
		this.j1 = j1;
	}

	public String getJ2() {
		return j2;
	}

	public void setJ2(String j2) {
		this.j2 = j2;
	}

	public String getJ3() {
		return j3;
	}

	public void setJ3(String j3) {
		this.j3 = j3;
	}
	
	
	
}
