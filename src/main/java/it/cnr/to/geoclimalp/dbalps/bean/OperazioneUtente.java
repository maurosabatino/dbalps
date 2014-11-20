/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.to.geoclimalp.dbalps.bean;

import java.sql.Timestamp;

/**
 *
 * @author daler
 */
public class OperazioneUtente {
    int idUtente;
    int idTraccia;
    Timestamp data;
    Timestamp dataInizio;
    Timestamp dataFine;
    String operazione;
    String tabella;
    int idStazione;
    int idProcesso;

    public OperazioneUtente(int idUtente, int idTraccia, Timestamp data, Timestamp dataInizio, Timestamp dataFine, String operazione, int idStazione, int idProcesso,String tabella) {
        this.idUtente = idUtente;
        this.idTraccia = idTraccia;
        this.data = data;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.operazione = operazione;
       this.tabella = tabella;
        this.idStazione = idStazione;
        this.idProcesso = idProcesso;
    }
    
      public OperazioneUtente() {
        this.idUtente = 0;
        this.idTraccia = 0;
        this.data = new Timestamp(0);
        this.dataInizio = new Timestamp(0);
        this.dataFine = new Timestamp(0);
        this.operazione = "";
        this.idStazione = 0;
        this.idProcesso = 0;
        this.tabella="";
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int iduUtente) {
        this.idUtente = iduUtente;
    }

    public int getIdTraccia() {
        return idTraccia;
    }

    public void setIdTraccia(int idTraccia) {
        this.idTraccia = idTraccia;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public Timestamp getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Timestamp dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Timestamp getDataFine() {
        return dataFine;
    }

    public void setDataFine(Timestamp dataFine) {
        this.dataFine = dataFine;
    }

    public String getOperazione() {
        return operazione;
    }

    public void setOperazione(String operazione) {
        this.operazione = operazione;
    }

    public int getIdStazione() {
        return idStazione;
    }

    public void setIdStazione(int idStazione) {
        this.idStazione = idStazione;
    }

    public int getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(int idProcesso) {
        this.idProcesso = idProcesso;
    }

    public String getTabella() {
        return tabella;
    }

    public void setTabella(String tabella) {
        this.tabella = tabella;
    }

  
    
    
    
}
