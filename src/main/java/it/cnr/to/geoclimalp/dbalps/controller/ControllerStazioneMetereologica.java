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
		if(!(request.getParameterValues("tipo_"+loc+"")==null)){
			s.setSensori(creaSensori(request,loc));
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
	
	public static ArrayList<Sensori> creaSensori(HttpServletRequest request,String loc) throws SQLException{
		ArrayList<Sensori> sensori=new ArrayList<Sensori>();
		int n=0;
		String[] sensoriScelti= request.getParameterValues("tipo_IT");
		int idsensore=0;
				
		if(loc.equals("IT")){			
		if(!(sensoriScelti==null)){
			for(int i=0;i<sensoriScelti.length;i++){
				idsensore=ControllerDatabase.idSensore(sensoriScelti[i],loc);	
				Sensori s=new Sensori();
				s.setIdsensori(idsensore);
				s.setSensori_IT(sensoriScelti[i]);
				sensori.add(s);
			}
			
		}
		}	else {
			if(!(sensoriScelti==null)){
				for(int i=0;i<n-1;i++){
					idsensore=ControllerDatabase.idSensore(sensoriScelti[i],loc);	
					Sensori s=new Sensori();
					s.setIdsensori(idsensore);
					s.setSensori_ENG(sensoriScelti[i]);
					sensori.add(s);
				}
			}
		}
		for(Sensori s:sensori){
			System.out.println("crea sensore "+s.getSensori_IT());
		}
		return sensori;
	}
		
	
	
	
}
