package it.cnr.to.geoclimalp.dbalps.controller;

import it.cnr.to.geoclimalp.dbalps.bean.ubicazione.Ubicazione;
import it.cnr.to.geoclimalp.dbalps.bean.ubicazione.Coordinate;
import it.cnr.to.geoclimalp.dbalps.bean.ubicazione.LocazioneIdrologica;
import it.cnr.to.geoclimalp.dbalps.bean.ubicazione.LocazioneAmministrativa;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;


public class ControllerUbicazione {

  public static Ubicazione inputUbicazione(HttpServletRequest request) throws SQLException {
    Ubicazione u = new Ubicazione();
    Coordinate coord = new Coordinate();
    LocazioneAmministrativa locAmm = new LocazioneAmministrativa();
    LocazioneIdrologica locIdro = new LocazioneIdrologica();
    if (!(request.getParameter("longitudine") == null)) {
      if (!(request.getParameter("longitudine").equals(""))) {
        Double y = Double.parseDouble(request.getParameter("longitudine"));
        coord.setY(y);
      }
    }
    if (!(request.getParameter("latitudine") == null)) {
      if (!(request.getParameter("latitudine").equals(""))) {
        Double x = Double.parseDouble(request.getParameter("latitudine"));
        coord.setX(x);
      }
    }
    u.setCoordinate(coord);
    if (!(request.getParameter("esposizione") == null)) {
      if (!(request.getParameter("esposizione").equals(""))) {
        u.setEsposizione(request.getParameter("esposizione"));
      }
    }
    if (!(request.getParameter("attendibilita") == null)) {
        if (!(request.getParameter("attendibilita").equals(""))) {
          u.setAttendibilita(request.getParameter("attendibilita"));
        }
      }
    
    if (!(request.getParameter("quota") == null)) {
      if (!(request.getParameter("quota").equals(""))) {
        u.setQuota(Double.parseDouble((String) request.getParameter("quota")));
      }
    }
    if (!(request.getParameter("idcomune") == null)) {
      if (!(request.getParameter("idcomune").equals(""))) {
        locAmm = ControllerDatabase.prendiLocAmministrativa(Integer.parseInt(request.getParameter("idcomune")));
      }
    }
    u.setLocAmm(locAmm);
    if(!(request.getParameter("idSottobacino") == null) ){
      
      if (!(request.getParameter("idSottobacino").equals(""))) {
        if (!(Integer.parseInt(request.getParameter("idSottobacino"))==0)) {
          locIdro = ControllerDatabase.prendiLocIdrologica(Integer.parseInt(request.getParameter("idSottobacino")));
        }
      }
    }
    
    u.setLocIdro(locIdro);
    return u;
  }

  public static Ubicazione nuovaUbicazione(HttpServletRequest request) throws SQLException {
    Ubicazione u = inputUbicazione(request);
    u.setIdUbicazione(ControllerDatabase.salvaUbicazione(u).getIdUbicazione());
    return u;
  }
}
