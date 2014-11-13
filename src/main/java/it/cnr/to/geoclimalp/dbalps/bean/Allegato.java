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
public class Allegato {
   private int id;
   private  String autore;
   private String titolo;
   private String anno;
   private String fonte;
   private String tipoAllegato;
   private String urlWeb;
   private String nella;
   private String note;
   private int idUtente;
   private String linkFile;
   private Timestamp data;
   
   public Allegato(){
       autore="";
       titolo="";
       anno="";
       fonte="";
       tipoAllegato="";
       urlWeb="";
       nella="";
       note="";
       idUtente=0;
       linkFile="";
       data=null;
   }
   public Allegato(String autore,String titolo,String anno,String fonte,String tipoAllegato,String urlWeb,String nella,String note,int idUtente,String linkFile,Timestamp data){
       this.autore=autore;
       this.titolo=titolo;
       this.anno=anno;
       this.fonte=fonte;
       this.tipoAllegato=tipoAllegato;
       this.urlWeb=urlWeb;
       this.nella=nella;
       this.note=note;
       this.idUtente=idUtente;
       this.linkFile=linkFile;
       this.data=data;
   }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the autore
     */
    public String getAutore() {
        return autore;
    }

    /**
     * @param autore the autore to set
     */
    public void setAutore(String autore) {
        this.autore = autore;
    }

    /**
     * @return the titolo
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * @param titolo the titolo to set
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * @return the anno
     */
    public String getAnno() {
        return anno;
    }

    /**
     * @param anno the anno to set
     */
    public void setAnno(String anno) {
        this.anno = anno;
    }

    /**
     * @return the fonte
     */
    public String getFonte() {
        return fonte;
    }

    /**
     * @param fonte the fonte to set
     */
    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    /**
     * @return the tipoAllegato
     */
    public String getTipoAllegato() {
        return tipoAllegato;
    }

    /**
     * @param tipoAllegato the tipoAllegato to set
     */
    public void setTipoAllegato(String tipoAllegato) {
        this.tipoAllegato = tipoAllegato;
    }

    /**
     * @return the urlWeb
     */
    public String getUrlWeb() {
        return urlWeb;
    }

    /**
     * @param urlWeb the urlWeb to set
     */
    public void setUrlWeb(String urlWeb) {
        this.urlWeb = urlWeb;
    }

    /**
     * @return the nella
     */
    public String getNella() {
        return nella;
    }

    /**
     * @param nella the nella to set
     */
    public void setNella(String nella) {
        this.nella = nella;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the idUtente
     */
    public int getIdUtente() {
        return idUtente;
    }

    /**
     * @param idUtente the idUtente to set
     */
    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    /**
     * @return the linkFile
     */
    public String getLinkFile() {
        return linkFile;
    }

    /**
     * @param linkFile the linkFile to set
     */
    public void setLinkFile(String linkFile) {
        this.linkFile = linkFile;
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
    
}
