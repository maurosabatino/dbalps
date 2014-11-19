package it.cnr.to.geoclimalp.dbalps.controller;



import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import it.cnr.to.geoclimalp.dbalps.bean.Utente.Utente;
import it.cnr.to.geoclimalp.dbalps.bean.stazione.*;
import it.cnr.to.geoclimalp.dbalps.bean.ubicazione.Ubicazione;
import static java.lang.Integer.parseInt;

// modificato
public class ControllerStazioneMetereologica {
	
	public static StazioneMetereologica creaStazioneMetereologica(HttpServletRequest request) throws ParseException{
		StazioneMetereologica s=new StazioneMetereologica();
		s.setNome(request.getParameter("nome"));
	
		String ora="00:00:00";
		s.setAggregazioneGiornaliera(request.getParameter("aggregazioneGiornaliera"));
		s.setNote(request.getParameter("note"));
		if(!(request.getParameter("datainizio").equals(""))){
			String dataInizio = request.getParameter("datainizio");
			s.setDataInizio(dataInizio);
		}else s.setDataInizio(null);
		
		if(!(request.getParameter("datafine").equals(""))){ 
			String fine=request.getParameter("datafine");
			s.setDataFine(fine);
		}else s.setDataFine(null);
		if(!(request.getParameter("tipoaggregazione").equals(""))){
                    s.setTipoAggregazione(request.getParameter("tipoaggregazione"));
                }
		
		return s;
	}
	
	public static StazioneMetereologica nuovaStazioneMetereologica(HttpServletRequest request,String loc,Ubicazione u,Utente part) throws SQLException, ParseException{
		StazioneMetereologica s= creaStazioneMetereologica(request);
		s.setUbicazione(u);
                
		
		if(!(request.getParameter("idsitostazione").equals(""))){
		SitoStazioneMetereologica sito=creaSitoStazione(request,loc);
		s.setSito(sito);}
		
		if(!(request.getParameter("idEnte").equals(""))&&!(request.getParameter("idEnte").equals("0/"))){
			Ente ente=creaEnte(request);
			s.setEnte(ente);
		}
                
		if(request.getParameterValues("sensori")!=null){
			s.setSensori(creaSensori(request));
		}
		s.setIdUtente(part.getIdUtente());	
		return s;
	}
	
	public static SitoStazioneMetereologica creaSitoStazione(HttpServletRequest request,String loc){
			SitoStazioneMetereologica s=new SitoStazioneMetereologica();
		   s.setIdSitoStazioneMetereologica(Integer.parseInt(request.getParameter("idsitostazione")));
		   if(loc.equals("IT")) s.setCaratteristiche_IT(request.getParameter("caratteristiche_it"));
		   else s.setCaratteristiche_ENG(request.getParameter("caratteristiche_eng"));
		   return s;
	}
	
	public static Ente creaEnte(HttpServletRequest request){
		Ente ente=new Ente();
		ente.setEnte(request.getParameter("ente"));
		ente.setIdEnte(Integer.parseInt(request.getParameter("idEnte")));
		return ente;
	}
	
	public static ArrayList<Sensori> creaSensori(HttpServletRequest request) throws SQLException{
		ArrayList<Sensori> sensori=new ArrayList<Sensori>();
		String[] sensoriScelti= request.getParameterValues("sensori");
		if(!(sensoriScelti==null)){		
				sensori=ControllerDatabase.prendiSensore(sensoriScelti);	
		}
		return sensori;

        }
}
