/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.to.geoclimalp.dbalps.entity.processo;

import it.cnr.to.geoclimalp.dbalps.entity.ubicazione.Ubicazione;
import java.sql.Timestamp;

/**
 *
 * @author Mauro
 */
public class Segnalazione{
  private int idProcesso;
	private String nome;
	private Timestamp data;
	private int formatoData;
	private AttributiProcesso attributiProcesso;
    private Ubicazione ubicazione;

  /**
   * @return the idProcesso
   */
 
  public int getIdProcesso() {
    return idProcesso;
  }

  /**
   * @param idProcesso the idProcesso to set
   */
 
  public void setIdProcesso(int idProcesso) {
    this.idProcesso = idProcesso;
  }

  /**
   * @return the nome
   */
 
  public String getNome() {
    return nome;
  }

  /**
   * @param nome the nome to set
   */
 
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   * @return the data
   */
 
  public Timestamp getData() {
    return data;
  }

  /**
   * @param data the data to set
   */
 
  public void setData(Timestamp data) {
    this.data = data;
  }

  /**
   * @return the formatoData
   */
 
  public int getFormatoData() {
    return formatoData;
  }

  /**
   * @param formatoData the formatoData to set
   */
 
  public void setFormatoData(int formatoData) {
    this.formatoData = formatoData;
  }

  /**
   * @return the attributiProcesso
   */
 
  public AttributiProcesso getAttributiProcesso() {
    return attributiProcesso;
  }

  /**
   * @param attributiProcesso the attributiProcesso to set
   */
 
  public void setAttributiProcesso(AttributiProcesso attributiProcesso) {
    this.attributiProcesso = attributiProcesso;
  }

  /**
   * @return the ubicazione
   */
 
  public Ubicazione getUbicazione() {
    return ubicazione;
  }

  /**
   * @param ubicazione the ubicazione to set
   */
 
  public void setUbicazione(Ubicazione ubicazione) {
    this.ubicazione = ubicazione;
  }

 
  public boolean isWarning() {
    return true;
  }
    
}
