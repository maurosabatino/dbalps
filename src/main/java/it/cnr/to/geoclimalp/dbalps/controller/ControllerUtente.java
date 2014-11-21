package it.cnr.to.geoclimalp.dbalps.controller;

import it.cnr.to.geoclimalp.dbalps.bean.OperazioneUtente;
import it.cnr.to.geoclimalp.dbalps.bean.Utente.*;
import it.cnr.to.geoclimalp.dbalps.bean.processo.Processo;
import it.cnr.to.geoclimalp.dbalps.bean.stazione.StazioneMetereologica;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import org.jasypt.util.password.StrongPasswordEncryptor;



public class ControllerUtente {

	public static Utente creazioneUtente(HttpServletRequest request) throws ParseException{
		Utente partecipante = new Utente();
		switch(request.getParameter("ruolo")){
		case "amministratore" : partecipante.setRuolo(Role.AMMINISTRATORE); break;
		case "avanzato" : partecipante.setRuolo(Role.AVANZATO); break;
		case "base" : partecipante.setRuolo(Role.BASE); break;
		}
		partecipante.setDataCreazione(Timestamp.valueOf(data()));
		partecipante.setNome(request.getParameter("nome"));
		partecipante.setCognome(request.getParameter("cognome"));
		partecipante.setEmail(request.getParameter("email"));
		partecipante.setUsername(request.getParameter("username"));
        String password = request.getParameter("password");
		partecipante.setPassword(request.getParameter("password"));
		return partecipante;
	}
	public static String data(){
		Calendar cal = new GregorianCalendar();
	    int giorno = cal.get(Calendar.DAY_OF_MONTH);
	    int mese = cal.get(Calendar.MONTH)+1;
	    int anno = cal.get(Calendar.YEAR);
	    int ora = cal.get(Calendar.HOUR_OF_DAY);
	    int min = cal.get(Calendar.MINUTE);
	    int sec = cal.get(Calendar.SECOND);
	    return (anno+"-"+mese+"-"+giorno+" "+ora+":"+min+":"+sec);
	}
	
	public static Utente nuovoUtente(HttpServletRequest request,StrongPasswordEncryptor  passwordEncryptor) throws ParseException, SQLException{
		Utente partecipante=creazioneUtente(request);
		partecipante=ControllerDatabase.salvaUtente(partecipante,passwordEncryptor);
		return partecipante;
	}

    public static void aggiornaTracciaProcesso(Utente user,Processo p, String operazione) throws SQLException {
        OperazioneUtente op=new OperazioneUtente();
              Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime(); 
        Timestamp t = new Timestamp(now.getTime());
            op.setData(t);
            op.setIdProcesso(p.getIdProcesso());
            int idutente=user.getIdUtente();
            op.setIdUtente(idutente);
            op.setTabella("processo");
            op.setNomeProcesso(p.getNome());
            op.setOperazione(operazione);
            int ok=ControllerDatabase.aggiornaTracciaUtente(op);
    }
    public static void aggiornaTracciaStazione(Utente user,StazioneMetereologica s, String operazione) throws SQLException {
        OperazioneUtente op=new OperazioneUtente();
        
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime(); 
        Timestamp t = new Timestamp(now.getTime());
            op.setData(t);
            op.setIdStazione(s.getIdStazioneMetereologica());
            int idutente=user.getIdUtente();
            op.setIdUtente(idutente);
            op.setTabella("stazione_metereologica");
            op.setNomeProcesso(s.getNome());
            op.setOperazione(operazione);
            int ok=ControllerDatabase.aggiornaTracciaUtente(op);
    }
}
