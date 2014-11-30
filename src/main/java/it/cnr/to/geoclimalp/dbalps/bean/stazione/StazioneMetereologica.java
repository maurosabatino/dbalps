
package it.cnr.to.geoclimalp.dbalps.bean.stazione;

import it.cnr.to.geoclimalp.dbalps.bean.Allegato;
import java.util.ArrayList;

import it.cnr.to.geoclimalp.dbalps.bean.ubicazione.Ubicazione;

public class StazioneMetereologica {
	public int idStazioneMetereologica;
	public SitoStazioneMetereologica sito;
	public Ente ente;
	public ArrayList<Sensori> sensori;
	public Ubicazione ubicazione;
	public String nome;
	public String aggregazioneGiornaliera;
	public String note;
	public String dataInizio;
	public String dataFine;
	public boolean oraria;
	public int idUtente;
        public String tipoAggregazione;
        public ArrayList<Allegato> allegati;
	public boolean pubblico;
	public double distanzaProcesso;
	
	
	public StazioneMetereologica(){
		idStazioneMetereologica=0;
		sito=new SitoStazioneMetereologica();
		ubicazione=new Ubicazione();
		nome="";
		ente=new Ente();
		sensori=new ArrayList<Sensori>() ;
		aggregazioneGiornaliera="";
                 note="";
		dataInizio=null;
		dataFine=null;
		oraria=false;
		distanzaProcesso=-1;
                pubblico=false;
	}
	
	public void setIdStazioneMetereologica(int stazione){
		idStazioneMetereologica=stazione;
		}
	public int getIdStazioneMetereologica(){
		return idStazioneMetereologica;
	}
	
	public void setSito(SitoStazioneMetereologica sito){
		this.sito=sito;
	}
	public SitoStazioneMetereologica getSito(){
		return sito;
	}
	
	
	public void setUbicazione(Ubicazione ubicazione){
		this.ubicazione=ubicazione;
	}
	public Ubicazione getUbicazione(){
		return ubicazione;
	}
	
	
	public void setNome(String nome){
		this.nome=nome;
	}
	public String getNome(){
		return nome;
	}
	
	public void setAggregazioneGiornaliera(String aggregazione){
		aggregazioneGiornaliera=aggregazione;
	}
	public String getAggregazioneGiornaliera(){
		return aggregazioneGiornaliera;
	}
	
	
	public void setOraria(boolean oraria){
		this.oraria=oraria;
		}
	public boolean getOraria(){
		return oraria;
	}	
	
	public void setNote(String note){
		this.note=note;
	}
	public String getNote(){
		return note;
	}
	
	public String getVisualizza(){
		
		String out="";
		out+="<p>quota: "+ubicazione.getQuota()+"</p> <p> nome "+getNome()+"";
	
		return out;
	}
	
	public String getDataInizio(){
		return dataInizio;
	}
	public void setDataInizio(String data){
		this.dataInizio=data;
	}
	
	public String getDataFine(){
		return dataFine;
	}
	public void setDataFine(String data){
		this.dataFine=data;
	}
	
	public void setEnte(Ente ente){
		this.ente=ente;
	}
	public Ente getEnte(){
		return ente;
	}
	
	public void setSensori(ArrayList<Sensori> sensori){
		this.sensori=sensori;
	}
	public ArrayList<Sensori> getSensori(){
		return sensori;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public double getDistanzaProcesso() {
		return distanzaProcesso;
	}

	public void setDistanzaProcesso(double distanzaProcesso) {
		this.distanzaProcesso = distanzaProcesso;
	}

    public String getTipoAggregazione() {
        return tipoAggregazione;
    }

    public void setTipoAggregazione(String tipoAggregazione) {
        this.tipoAggregazione = tipoAggregazione;
    }
        

    public ArrayList<Allegato> getAllegati() {
        return allegati;
    }

    public void setAllegati(ArrayList<Allegato> allegati) {
        this.allegati = allegati;
    }

    public boolean getPubblico() {
        return pubblico;
    }

    public void setPubblico(boolean pubblico) {
        this.pubblico = pubblico;
    }
        
    
}