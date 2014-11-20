package it.cnr.to.geoclimalp.dbalps.bean.Utente;

import it.cnr.to.geoclimalp.dbalps.bean.OperazioneUtente;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Utente {
	int idUtente;
	String nome;
	String cognome;
	String username;
	String password;
	String email;
	Role ruolo;
	Timestamp dataCreazione;
	Timestamp dataultimoAccesso;
        ArrayList<OperazioneUtente> operazioni;
	
	public Utente(){
		idUtente=0;
		nome="";
		cognome="";
		username="";
		password="";
		email="";
		ruolo = Role.BASE;
		dataCreazione=new Timestamp(0);
		dataultimoAccesso=new Timestamp(0);
                operazioni=new ArrayList<OperazioneUtente>();
	}
	
	public void setIdUtente(int id){
		idUtente=id;
	}
	public int getIdUtente(){
		return idUtente;
	}
	
	public void setNome(String nome){
		this.nome=nome;
	}
	public String getNome(){
		return nome;
	}
	
	public void setCognome(String cognome){
		this.cognome=cognome;
	}
	public String getCognome(){
		return cognome;
	}
	
	public void setUsername(String user){
		username=user;
	}
	public String getUsername(){
		return username;
	}
	
	public void setPassword(String password){
		this.password=password;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setEmail(String email){
		this.email=email;
	}
	public String getEmail(){
		return email;
	}
	
	public void setRuolo(Role ruolo){
		this.ruolo=ruolo;
	}
	public Role getRuolo(){
		return ruolo;
	}

	public Timestamp getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Timestamp creazione) {
		this.dataCreazione = creazione;
	}

	public Timestamp getDataUltimoAccesso() {
		return dataultimoAccesso;
	}

	public void setDataUltimoAccesso(Timestamp ultimoAccesso) {
		this.dataultimoAccesso = ultimoAccesso;
	}
	
	public String getstampautente(){
		System.out.println("nome"+nome);
		String s="nome="+nome;
		return s;
	}

  public void setDataUltimoaccesso(Timestamp dataUltimoAccesso) {
    this.dataultimoAccesso = dataUltimoAccesso;
  }

    public Timestamp getDataultimoAccesso() {
        return dataultimoAccesso;
    }

    public void setDataultimoAccesso(Timestamp dataultimoAccesso) {
        this.dataultimoAccesso = dataultimoAccesso;
    }

    public ArrayList<OperazioneUtente> getOperazioni() {
        return operazioni;
    }

    public void setOperazioni(ArrayList<OperazioneUtente> operazioni) {
        this.operazioni = operazioni;
    }
	
}