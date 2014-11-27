package it.cnr.to.geoclimalp.dbalps.bean.processo;

import it.cnr.to.geoclimalp.dbalps.bean.Allegato;
import it.cnr.to.geoclimalp.dbalps.bean.processo.attributiProcesso.*;
import java.util.ArrayList;

public class AttributiProcesso {
  private String descrizione;
	private String note;
	private Double superficie;
	private Double larghezza;
	private Double altezza;
	private Double volume_specifico;
        private Litologia litologia;
	private ProprietaTermiche proprietaTermiche;
	private StatoFratturazione statoFratturazione;
	private SitoProcesso SitoProcesso;
	private ClasseVolume classeVolume;
	private ArrayList<EffettiMorfologici> effetti;
	private ArrayList <Danni> danni;
	String gradoDanno;
	private ArrayList<TipologiaProcesso> tipologiaProcesso;
        private ArrayList<Allegato> allegati;
        private int idUtente;
        private double runout;
        private double volumeAccumulo;
        private double superficieAccumulo;
        private boolean pubblico;
        private String fonte;
        
    public AttributiProcesso(){
       descrizione = "";
        note = "";
        superficie = 0.0;
        larghezza = 0.0;
        altezza = 0.0;
        volume_specifico = 0.0;
        classeVolume = new ClasseVolume();
        litologia = new Litologia();
        statoFratturazione=new StatoFratturazione();
        proprietaTermiche = new ProprietaTermiche();
        SitoProcesso = new SitoProcesso();
        effetti = new ArrayList<>();
        danni = new ArrayList<>();
        allegati = new ArrayList<>();
        gradoDanno="";
        tipologiaProcesso = new ArrayList<>();
        idUtente=0;
        runout=0.0;
        superficieAccumulo=0.0;
        volumeAccumulo=0.0;
        pubblico = false;
        fonte = "";
    }

  /**
   * @return the descrizione
   */
  public String getDescrizione() {
    return descrizione;
  }

  /**
   * @param descrizione the descrizione to set
   */
  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
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
   * @return the superficie
   */
  public Double getSuperficie() {
    return superficie;
  }

  /**
   * @param superficie the superficie to set
   */
  public void setSuperficie(Double superficie) {
    this.superficie = superficie;
  }

  /**
   * @return the larghezza
   */
  public Double getLarghezza() {
    return larghezza;
  }

  /**
   * @param larghezza the larghezza to set
   */
  public void setLarghezza(Double larghezza) {
    this.larghezza = larghezza;
  }

  /**
   * @return the altezza
   */
  public Double getAltezza() {
    return altezza;
  }

  /**
   * @param altezza the altezza to set
   */
  public void setAltezza(Double altezza) {
    this.altezza = altezza;
  }

  /**
   * @return the volume_specifico
   */
  public Double getVolume_specifico() {
    return volume_specifico;
  }

  /**
   * @param volume_specifico the volume_specifico to set
   */
  public void setVolume_specifico(Double volume_specifico) {
    this.volume_specifico = volume_specifico;
  }

  /**
   * @return the litologia
   */
  public Litologia getLitologia() {
    return litologia;
  }

  /**
   * @param litologia the litologia to set
   */
  public void setLitologia(Litologia litologia) {
    this.litologia = litologia;
  }

  /**
   * @return the proprietaTermiche
   */
  public ProprietaTermiche getProprietaTermiche() {
    return proprietaTermiche;
  }

  /**
   * @param proprietaTermiche the proprietaTermiche to set
   */
  public void setProprietaTermiche(ProprietaTermiche proprietaTermiche) {
    this.proprietaTermiche = proprietaTermiche;
  }

  /**
   * @return the statoFratturazione
   */
  public StatoFratturazione getStatoFratturazione() {
    return statoFratturazione;
  }

  /**
   * @param statoFratturazione the statoFratturazione to set
   */
  public void setStatoFratturazione(StatoFratturazione statoFratturazione) {
    this.statoFratturazione = statoFratturazione;
  }

  /**
   * @return the SitoProcesso
   */
  public SitoProcesso getSitoProcesso() {
    return SitoProcesso;
  }

  /**
   * @param SitoProcesso the SitoProcesso to set
   */
  public void setSitoProcesso(SitoProcesso SitoProcesso) {
    this.SitoProcesso = SitoProcesso;
  }

  /**
   * @return the classeVolume
   */
  public ClasseVolume getClasseVolume() {
    return classeVolume;
  }

  /**
   * @param classeVolume the classeVolume to set
   */
  public void setClasseVolume(ClasseVolume classeVolume) {
    this.classeVolume = classeVolume;
  }

  /**
   * @return the effetti
   */
  public ArrayList<EffettiMorfologici> getEffetti() {
    return effetti;
  }

  /**
   * @param effetti the effetti to set
   */
  public void setEffetti(ArrayList<EffettiMorfologici> effetti) {
    this.effetti = effetti;
  }

  /**
   * @return the danni
   */
  public ArrayList <Danni> getDanni() {
    return danni;
  }

  /**
   * @param danni the danni to set
   */
  public void setDanni(ArrayList <Danni> danni) {
    this.danni = danni;
  }
  
  

  public String getGradoDanno() {
	return gradoDanno;
}

public void setGradoDanno(String gradoDanno) {
	this.gradoDanno = gradoDanno;
}

/**
   * @return the tipologiaProcesso
   */
  public ArrayList<TipologiaProcesso> getTipologiaProcesso() {
    return tipologiaProcesso;
  }

  /**
   * @param tipologiaProcesso the tipologiaProcesso to set
   */
  public void setTipologiaProcesso(ArrayList<TipologiaProcesso> tipologiaProcesso) {
    this.tipologiaProcesso = tipologiaProcesso;
  }

    public ArrayList<Allegato> getAllegati() {
        return allegati;
    }

    public void setAllegati(ArrayList<Allegato> allegati) {
        this.allegati = allegati;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public double getRunout() {
        return runout;
    }

    public void setRunout(double runout) {
        this.runout = runout;
    }

    public double getVolumeAccumulo() {
        return volumeAccumulo;
    }

    public void setVolumeAccumulo(double volumeAccumulo) {
        this.volumeAccumulo = volumeAccumulo;
    }

    public double getSuperficieAccumulo() {
        return superficieAccumulo;
    }

    public void setSuperficieAccumulo(double superficieAccumulo) {
        this.superficieAccumulo = superficieAccumulo;
    }

    public boolean isPubblico() {
        return pubblico;
    }

    public void setPubblico(boolean pubblico) {
        this.pubblico = pubblico;
    }

    public String getFonte() {
        return fonte;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }
  
  
    
}
