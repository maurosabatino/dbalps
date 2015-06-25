package it.cnr.to.geoclimalp.dbalps.entity;

import java.util.ArrayList;

public class Dati {
	ArrayList<Double> dati;
	int anno;
	boolean ok;
	
	public Dati(int anno){
		this.anno=anno;
		this.dati=new ArrayList<Double>();
		this.ok=true;
	}
	public Dati(){
		this.anno=0;
		this.dati=new ArrayList<Double>();
		this.ok=true;
	}
	public ArrayList<Double> getDati() {
		return dati;
	}

	public void setDati(ArrayList<Double> dati) {
		this.dati = dati;
	}
	
	public boolean getOk() {
		return ok;
	}
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}
}
