package it.cnr.to.geoclimalp.dbalps.bean.processo;
import it.cnr.to.geoclimalp.dbalps.bean.ubicazione.Ubicazione;
import java.sql.Timestamp;


public class ProcessoCompleto implements Processo {
	private int idProcesso;
	private String nome;
	private Timestamp data;
	private int formatoData;
	private AttributiProcesso attributiProcesso;
    private Ubicazione ubicazione;
    
    //arraylist allegati
    
    public ProcessoCompleto() {     
        idProcesso=0;
        nome = "";
        data = Timestamp.valueOf("0001-01-01 00:00:00");
        formatoData=00000;
        attributiProcesso = new AttributiProcesso();
    }

  /**
   * @return the idProcesso
   */
    @Override
  public int getIdProcesso() {
    return idProcesso;
  }

  /**
   * @param idProcesso the idProcesso to set
   */
  @Override
  public void setIdProcesso(int idProcesso) {
    this.idProcesso = idProcesso;
  }

  /**
   * @return the nome
   */
  @Override
  public String getNome() {
    return nome;
  }

  /**
   * @param nome the nome to set
   */
  @Override
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   * @return the data
   */
  @Override
  public Timestamp getData() {
    return data;
  }

  /**
   * @param data the data to set
   */
  @Override
  public void setData(Timestamp data) {
    this.data = data;
  }

  /**
   * @return the formatoData
   */
  @Override
  public int getFormatoData() {
    return formatoData;
  }

  /**
   * @param formatoData the formatoData to set
   */
  @Override
  public void setFormatoData(int formatoData) {
    this.formatoData = formatoData;
  }

  /**
   * @return the attributiProcesso
   */
  @Override
  public AttributiProcesso getAttributiProcesso() {
    return attributiProcesso;
  }

  /**
   * @param attributiProcesso the attributiProcesso to set
   */
  @Override
  public void setAttributiProcesso(AttributiProcesso attributiProcesso) {
    this.attributiProcesso = attributiProcesso;
  }

  /**
   * @return the ubicazione
   */
  @Override
  public Ubicazione getUbicazione() {
    return ubicazione;
  }

  /**
   * @param ubicazione the ubicazione to set
   */
  @Override
  public void setUbicazione(Ubicazione ubicazione) {
    this.ubicazione = ubicazione;
  }
  
  @Override
  public boolean isWarning(){
    return false;
  }
  

}
   