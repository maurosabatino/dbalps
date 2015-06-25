package it.cnr.to.geoclimalp.dbalps.entity.ubicazione;


public class Ubicazione {
	int idUbicazione;
	LocazioneIdrologica locIdr;
	LocazioneAmministrativa locAmm;
	Double quota;
	String esposizione;
	String attendibilita;
	Coordinate coordinate;
	public Ubicazione(){
		idUbicazione = 0;
		locIdr = new LocazioneIdrologica();
		locAmm= new LocazioneAmministrativa();
		quota = 0.0;
		esposizione = "";
		coordinate = new Coordinate();
	}
	public int getIdUbicazione(){
		return idUbicazione;
	}
	public LocazioneIdrologica getLocIdro(){
		return locIdr;
	}
	public LocazioneAmministrativa getLocAmm(){
		return locAmm;
	}
	public Double getQuota(){
		return quota;
	}
	public String getEsposizione(){
		return esposizione;
	}
	public Coordinate getCoordinate(){
		return coordinate;
	}
	public void setIdUbicazione (int idUbicazione){
		this.idUbicazione = idUbicazione;
	}
	public void setLocIdro(LocazioneIdrologica locIdr){
		this.locIdr = locIdr;
	}
	public void setLocAmm(LocazioneAmministrativa locAmm){
		this.locAmm = locAmm;
	}
	public void setQuota(Double quota){
		this.quota = quota;
	}
	public void setEsposizione(String esposizione){
		this.esposizione = esposizione;
	}
	public void setCoordinate(Coordinate coord){
		this.coordinate = coord;
	}
	
	public String getAttendibilita() {
		return attendibilita;
	}
	public void setAttendibilita(String attendibilita) {
		this.attendibilita = attendibilita;
	}
	public String toString(){
		String out = "<p> amministrazione"+locAmm.toString()+"</p><p> idrologia"+locIdr.toString()+"</p><p> coordinate"+coordinate.toString()+"</p><p> esposizione"+esposizione+"</p><p>quota"+quota+"</p>";
		return out;
	}
	public boolean isEmpty(){
		if(idUbicazione == 0 &&	locIdr.isEmpty() && locAmm.isEmpty() &&quota ==0.0 && (esposizione == "" || esposizione=="sconosciuto")&& coordinate.isEmpty())
			return true;
		else return false;
	}
}
