package it.cnr.to.geoclimalp.dbalps.bean.processo;

import it.cnr.to.geoclimalp.dbalps.bean.ubicazione.Ubicazione;
import java.sql.Timestamp;


public interface Processo {
  void setIdProcesso(int idProcesso);
  int getIdProcesso();
  
  void setNome(String nome);
  String getNome();
  
  void setData(Timestamp data);
  Timestamp getData();
  
  void setFormatoData(int formatoData);
  int getFormatoData();
  
  void setAttributiProcesso(AttributiProcesso attributiProcesso);
  AttributiProcesso getAttributiProcesso();
  
  void setUbicazione(Ubicazione ubicazione);
  Ubicazione getUbicazione();
  
  
}
   