<<<<<<< HEAD
package it.cnr.to.geoclimalp.dbalps.html;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import it.cnr.to.geoclimalp.dbalps.bean.stazione.*;
import it.cnr.to.geoclimalp.dbalps.bean.Utente.*;
import it.cnr.to.geoclimalp.dbalps.controller.*;

public class HTMLStazioneMetereologica {
	public static String mostraTutteStazioniMetereologiche(Utente part) throws SQLException{
		ArrayList<StazioneMetereologica>  ap = ControllerDatabase.prendiTutteStazioniMetereologiche(); 
		StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"table-responsive\"><table class=\"table\"> ");
		sb.append("<tr> <th>Nome</th>  <th>comune</th> <th> dettagli</th> ");
		if(part!=null && (part.getRuolo().equals(Role.AMMINISTRATORE)||part.getRuolo().equals(Role.AVANZATO))){
			sb.append("<th>modifica stazione</th><th> carica dati climatici</th>");
		}
		sb.append("</tr>");
		for(StazioneMetereologica s: ap){
			sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td>  ");
			sb.append("<td><a href=\"Servlet?operazione=mostraStazioneMetereologica&idStazioneMetereologica="+s.getIdStazioneMetereologica()+"\">dettagli</a></td>");
			if(part!=null && (part.getRuolo().equals(Role.AMMINISTRATORE)||part.getRuolo().equals(Role.AVANZATO))){
				sb.append("<td><a href=\"Servlet?operazione=modificaStazione&idStazioneMetereologica="+s.getIdStazioneMetereologica()+"\"> modifica</a></td>");
				sb.append("<td><a href=\"Servlet?operazione=caricaDatiClimatici&idStazioneMetereologica="+s.getIdStazioneMetereologica()+"\"> Carica</a></td>");
			}
			sb.append("</tr>");
		}
		sb.append("</table></div>");
		return sb.toString();
	}
	public static String mostraStazioneMetereologica(int idStazioneMetereologica,ControllerLingua loc) throws SQLException{
		System.out.println("prima");
		StazioneMetereologica s = ControllerDatabase.prendiStazioneMetereologica(idStazioneMetereologica);
		System.out.println("dopo");
		StringBuilder sb = new StringBuilder();
		sb.append("<table> <tr> <th>Nome</th>  <th>comune</th> </tr>");
		sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td></tr>");
		sb.append("</table>");
		return sb.toString();
	}
	public static String mostraCercaStazioneMetereologica(ArrayList<StazioneMetereologica> al) throws SQLException{
		
		StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"table-responsive\"><table class=\"table\">  <tr> <th>Nome</th> <th>data</th> <th>comune</th> <th> dettagli</th> </tr>");
		for(StazioneMetereologica s: al){
			sb.append(" <tr> <td>"+s.getNome()+" </td> <td> "+s.getDataInizio()+"</td> <td> "+s.getUbicazione().getLocAmm().getComune()+"</td>"
				/*?*/	+ "<td><a href=\"Servlet?operazione=mostraStazioneMetereologica&idStazioneMetereologica="+s.getIdStazioneMetereologica()+"\">dettagli</a></td></tr>");
		}
		sb.append("</table></div>");
		return sb.toString();
	}

	public static String formStazioneMetereologica(String path,Utente part) throws SQLException {
		StringBuilder sb = new StringBuilder();
		String loc ="IT";
		Calendar inizio = new GregorianCalendar();
		inizio.add(Calendar.MONTH, 1);
		sb.append(HTMLScript.scriptData("datainizio"));
		Calendar fine = new GregorianCalendar();
		fine.add(Calendar.MONTH, 1);
	 	sb.append(HTMLScript.scriptData("datafine"));
	 	sb.append(HTMLScript.scriptAperturaControlloInserimento());
	 	sb.append(HTMLScript.scriptControlloInserimento("nome"));
	 	sb.append(HTMLScript.scriptControlloInserimento("comune"));
	 	sb.append(HTMLScript.scriptChiusuraControlloInserimento());

		sb.append(HTMLScript.scriptAutocompleteLocAmm(ControllerJson.getJsonLocazioneAmminitrativa(path)));
		sb.append(HTMLScript.scriptAutocompleteSitoStazione(ControllerJson.getJsonSitoStazione(path, loc),loc));
		sb.append(HTMLScript.scriptAutocompleteEnte(ControllerJson.getJsonEnte(path)));
		sb.append(HTMLScript.scriptAutocompleteLocIdro(ControllerJson.getJsonLocazioneIdrologica(path)));
		if(part!=null && (part.getRuolo().equals(Role.AMMINISTRATORE)||part.getRuolo().equals(Role.AVANZATO))){
		sb.append("<form action=\"Servlet\" name=\"dati\" onSubmit=\"return verificaInserisci(this);\" method=\"POST\">" );	
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sulla Stazione</h4>");		
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-12\"><label for=\"nome\">Nome Della Stazione</label> <input type=\"text\" name=\"nome\" id=\"nome\" class=\"form-control\" placeholder=\"nome\" ></div>");
		sb.append("</div>");
		sb.append("<br>");
		sb.append("<div class=\"row\">");
		sb.append(	"<div class=\"col-xs-6 col-md-4\"><label for=\"aggregazionegiornaliera\">Aggregazione giornaliera:<input type=\"text\" name=\"aggregazioneGiornaliera\"  id=\"aggregazionegiornaliera\" class=\"form-control\" placeholder=\"aggregazione giornaliera\"></div>" );	
		sb.append(	"<div class=\"col-xs-6 col-md-3\"><label for=\"periodofunzionamento\">Periodo Funzionamento:<input type=\"text\" name=\"periodoFunzionamento\"  id=\"aggregazionegiornaliera\" class=\"form-control\" placeholder=\"periodo\"></div>" );
		sb.append("</div>");
		sb.append("<br>");
		/*sb.append("<div class=\"row\">");
		sb.append(	"<div class=\"col-xs-6 col-md-4\"><label for=\"oraria\">Oraria:<input type=\"radio\" name=\"oraria\"  id=\"oraria\" value=\"oraria\"  ></div>");
		sb.append(	"<div class=\"col-xs-6 col-md-4\"><label for=\"oraria\">Giornaliera:<input type=\"radio\" checked name=\"oraria\"  id=\"giornaliera\" value=\"giornaliera\" ></div>");
		sb.append("</div>");
		sb.append("<br>");*/
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"datainizio\">Data inizio</label> <input type=\"text\" id=\"datainizio\" name=\"datainizio\" class=\"form-control\" placeholder=\"datainizio\"></div>");
		sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"datafine\">Data fine</label> <input type=\"text\" id=\"datafine\" name=\"datafine\" class=\"form-control\" placeholder=\"datafine\"></div>");
		sb.append("</div>");
		sb.append("<br>");
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"ente\"> Ente:<input type=\"text\" id=\"ente\" name=\"ente\"  class=\"form-control\" placeholder=\"ente\"></div> ");
		sb.append("<input  type=\"hidden\" id=\"idEnte\" name=\"idEnte\" />");
		sb.append("</div>");
		sb.append("<br>");
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sull'ubicazione</h4>");
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"sottobacino\">Sottobacino</label><input type=\"text\" id=\"sottobacino\" name=\"sottobacino\" class=\"form-control\" placeholder=\"Sottobacino\"/></div>");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"bacino\">Bacino</label><input readonly=\"readonly\" type=\"text\"id=\"bacino\" name=\"bacino\" class=\"form-control\" placeholder=\"Bacino\"/></div> ");
		sb.append("<input type=\"hidden\" id=\"idSottobacino\" name=\"idSottobacino\" />");
		sb.append("</div>");
		sb.append("<br><div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"comune\">Comune</label><input type=\"text\" id=\"comune\" name=\"comune\" class=\"form-control\"placeholder=\"Comune\"/></div>");
		sb.append("<input  type=\"hidden\" id=\"idcomune\" name=\"idcomune\" />");
		sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"provincia\">Provincia</label><input readonly=\"readonly\" type=\"text\" id=\"provincia\" name=\"provincia\" class=\"form-control\"placeholder=\"Provincia\"/></div>");
		sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"regione\">Regione</label><input readonly=\"readonly\" type=\"text\" id=\"regione\" name=\"regione\" class=\"form-control\" placeholder=\"Regione\" /> </div>");
		sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"nazione\">Nazione</label><input readonly=\"readonly\" type=\"text\" id=\"nazione\" name=\"nazione\"class=\"form-control\" placeholder=\"Nazione\" /></div>");
		sb.append("</div>");	
		sb.append("<br><div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"latitudine\">Latitudine</label><input type=\"text\" id=\"latitudine\"name=\"latitudine\" class=\"form-control\" placeholder=\"Latitudine\"/></div>");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"longitudine\">Longitudine</label><input type=\"text\" id=\"longitudine\" name=\"longitudine\" class=\"form-control\" placeholder=\"Longitudine\"/></div>");
		sb.append("</div>");
		sb.append("<br><div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"quota\">Quota</label> <input type=\"text\" id=\"quota\"name=\"quota\" class=\"form-control\" placeholder=\"Quota\"/></div>");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"esposizione\">Esposizione</label> <input type=\"text\" id=\"esposizione\" name=\"esposizione\" class=\"form-control\" placeholder=\"Esposizione\" /></div>");
		sb.append("</div>");
		sb.append("<br><div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"caratteristiche_"+loc+"\"> Caratteristiche Sito <input type=\"text\" id=\"caratteristiche_"+loc+"\" name=\"caratteristicaSito_"+loc+"\" class=\"form-control\" placeholder=\"Caratteristiche \" /></div>");
		sb.append("<input type=\"hidden\" id=\"idsitostazione\" name=\"idsitostazione\" />");
		sb.append("</div>");
		sb.append("</div> </div>");	
		sb.append("<br>");
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Sensori</h4>");
		for(Sensori sens:ControllerDatabase.prendiTuttiSensori()){
			sb.append("<input type=\"checkbox\" id=\"sensori\" name=\"tipo_IT\" value=\""+sens.getSensori_IT()+"\" > "+sens.getSensori_IT()+" ");
		}
		sb.append("</div>");
		sb.append("</div>");
		sb.append("<br><div class=\"wrapper\">");
		sb.append("<div class=\"content-main\"><label for=\"note\">Note</label></div>");
		sb.append("<div class=\"content-secondary\"><textarea rows=\"5\" cols=\"140\" name=\"note\" id=\"note\" class=\"textarea\" placeholder=\"Note\">  </textarea></div>");
		sb.append("</div>");
		
		sb.append("<br>");
		
			sb.append("<input type=\"hidden\" name=\"operazione\" value=\"inserisciStazione\">" );
			sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">" );
			sb.append("</div>");
			sb.append(	"		</form>");}
		else {
				sb.append("<h1>Pagina delle segnalazioni(Da implementare)</h1>");
			}
		return sb.toString();
	}
	
	//modificato
	public static String modificaStazioneMetereologica(StazioneMetereologica s,String path,Utente part) throws SQLException {
		StringBuilder sb = new StringBuilder();
		String loc ="IT";
		Calendar inizio = new GregorianCalendar();
		if(part!=null && (part.getRuolo().equals(Role.AMMINISTRATORE)||part.getRuolo().equals(Role.AVANZATO))) {
		
		
		sb.append(HTMLScript.scriptData("datainizio"));
		sb.append(HTMLScript.scriptData("datafine"));
		sb.append(HTMLScript.scriptAutocompleteLocAmm(ControllerJson.getJsonLocazioneAmminitrativa(path)));
		sb.append(HTMLScript.scriptAutocompleteSitoStazione(ControllerJson.getJsonSitoStazione(path, loc),loc));
		sb.append(HTMLScript.scriptAutocompleteEnte(ControllerJson.getJsonEnte(path)));
		String temp;		
		sb.append("<form action=\"Servlet\" name=\"dati\" method=\"POST\">" );
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sulla Stazione</h4>");		
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-12\"><label for=\"nome\">Nome Della Stazione</label> <input type=\"text\" name=\"nome\" id=\"nome\" class=\"form-control\" placeholder=\"nome\" value=\""+s.getNome()+"\" ></div>");
		sb.append("</div>");
		sb.append("<br>");
		
		sb.append("<div class=\"row\">");
		sb.append(	"<div class=\"col-xs-6 col-md-4\"><label for=\"aggregazionegiornaliera\">Aggregazione giornaliera:<input type=\"text\" name=\"aggregazioneGiornaliera\"  id=\"aggregazionegiornaliera\" value=\""+s.getAggregazioneGiornaliera()+"\" class=\"form-control\" placeholder=\"aggregazione giornaliera\"></div>" );	
		/*errore?*/	sb.append(	"<div class=\"col-xs-6 col-md-3\"><label for=\"periodofunzionamento\">Periodo Funzionamento:<input type=\"text\" name=\"periodoFunzionamento\"  id=\"aggregazionegiornaliera\" class=\"form-control\" placeholder=\"periodo\"></div>" );
		sb.append("</div>");
		sb.append("<br>");
		
		sb.append("<br><div class=\"wrapper\">");
		sb.append("<div class=\"content-main\"><label for=\"note\">Note</label></div>");
		sb.append("<div class=\"content-secondary\"><textarea rows=\"5\" cols=\"100\" name=\"note\" id=\"note\"  class=\"textarea\" placeholder=\"Note\"> "+s.getNote()+" </textarea></div>");
		sb.append("</div>");
		
		sb.append("<br>");
		sb.append("<div class=\"row\">");
		
		
		if(s.getEnte().getEnte()!=null) temp=s.getEnte().getEnte();
		else temp="";
		System.out.println("Ente: "+temp);
		sb.append("<div class=\"col-xs-9 col-md-9\"><label for=\"ente\"> Ente:<input type=\"text\" id=\"ente\" value=\""+temp+"\"  name=\"ente\"  class=\"form-control\" placeholder=\"ente\"></div> ");
		sb.append("<input  type=\"hidden\" id=\"idEnte\" name=\"idEnte\" value="+s.getEnte().getIdEnte()+">");
		if(s.getSito().getCaratteristiche_IT()!=null) temp=s.getSito().getCaratteristiche_IT();
		else temp="";
		sb.append("</div> </div>");
		sb.append("</div>");
		sb.append("<br>");
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sull'ubicazione</h4>");
		sb.append("<div class=\"row\">");
                if(s.getUbicazione().getLocIdro().getSottobacino()!=null) temp=s.getUbicazione().getLocIdro().getSottobacino();
		else temp="";
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"sottobacino\">Sottobacino</label><input type=\"text\" id=\"sottobacino\" name=\"sottobacino\" class=\"form-control\" value=\""+temp+"\"/></div>");
		 if(s.getUbicazione().getLocIdro().getBacino()!=null) temp=s.getUbicazione().getLocIdro().getBacino();
		else temp="";
                sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"bacino\">Bacino</label><input readonly=\"readonly\" type=\"text\"id=\"bacino\" name=\"bacino\" class=\"form-control\" value=\""+temp+"\"/></div> ");
		sb.append("<input type=\"hidden\" id=\"idSottobacino\" name=\"idSottobacino\"  value=\""+s.getUbicazione().getLocIdro().getIdSottobacino()+"\"/>");
		sb.append("</div>");
		sb.append("<br><div class=\"row\">");
                if(s.getUbicazione().getLocAmm().getComune()!=null) temp=s.getUbicazione().getLocAmm().getComune();
		else temp="";
		sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"comune\">Comune</label><input type=\"text\" id=\"comune\" name=\"comune\" class=\"form-control\" value=\""+temp+"\"/></div>");
		sb.append("<input  type=\"hidden\" id=\"idcomune\" name=\"idcomune\" value=\""+s.getUbicazione().getLocAmm().getIdComune()+"\"/>");
		if(s.getUbicazione().getLocAmm().getProvincia()!=null) temp=s.getUbicazione().getLocAmm().getProvincia();
		else temp="";
                sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"provincia\">Provincia</label><input readonly=\"readonly\" type=\"text\" id=\"provincia\" name=\"provincia\" class=\"form-control\" value=\""+temp+"\"/></div>");
		if(s.getUbicazione().getLocAmm().getRegione()!=null) temp=s.getUbicazione().getLocAmm().getRegione();
		else temp="";
                sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"regione\">Regione</label><input readonly=\"readonly\" type=\"text\" id=\"regione\" name=\"regione\" class=\"form-control\" value=\""+temp+"\" /> </div>");
		if(s.getUbicazione().getLocAmm().getNazione()!=null) temp=s.getUbicazione().getLocAmm().getNazione();
		else temp="";
                sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"nazione\">Nazione</label><input readonly=\"readonly\" type=\"text\" id=\"nazione\" name=\"nazione\"class=\"form-control\" value=\""+temp+"\" /></div>");
		sb.append("</div>");
		sb.append("<div id=\"controls\">");
		sb.append("<br><div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"latitudine\">Latitudine</label><input type=\"text\" id=\"latitudine\"name=\"latitudine\" class=\"form-control\" value=\""+s.getUbicazione().getCoordinate().getX()+"\"/></div>");
		sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"longitudine\">Longitudine</label><input type=\"text\" id=\"longitudine\" name=\"longitudine\" class=\"form-control\" value=\""+s.getUbicazione().getCoordinate().getY()+"\"/></div>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("<div class=\"row\">");
                if(s.getSito().getCaratteristiche_IT()!=null) temp=s.getSito().getCaratteristiche_IT();
		else temp="";
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"caratteristiche_"+loc+"\"> Caratteristiche Sito <input type=\"text\" id=\"caratteristiche_"+loc+"\" name=\"caratteristicaSito_"+loc+"\" class=\"form-control\" value=\""+temp+" \" /></div>");
		sb.append("<input type=\"hidden\" id=\"idsitostazione\" name=\"idsitostazione\"  value=\""+s.getSito().getIdSitoStazioneMetereologica()+"\"/>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("<br><div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"quota\">Quota</label> <input type=\"text\" id=\"quota\"name=\"quota\" class=\"form-control\" value=\""+s.getUbicazione().getQuota()+"\"/></div>");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"esposizione\">Esposizione</label> <input type=\"text\" id=\"esposizione\" name=\"esposizione\" class=\"form-control\" value=\""+s.getUbicazione().getEsposizione()+"\"/></div>");
		sb.append("<br>");
		sb.append("<br><div class=\"row\">");
		
		
		
		
		sb.append("</div>");
		sb.append("</div>");

		sb.append("</div>");
		
		
		
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Sensori</h4>");
				for(Sensori sens:ControllerDatabase.prendiTuttiSensori()){
					boolean inserito=false;
					for(int i=0;i<s.getSensori().size();i++){
						if(sens.getSensori_IT().equals(s.getSensori().get(i).getSensori_IT())){
							sb.append("<input type=\"checkbox\" name=\"tipo_it\" value=\""+sens.getSensori_IT()+"\" checked=\"checked\" > "+sens.getSensori_IT()+" "); 
							inserito=true;

						}
					}
					if(inserito==false) sb.append("<input type=\"checkbox\" name=\"tipo_it\" value=\""+sens.getSensori_IT()+"\" > "+sens.getSensori_IT()+" ");
					inserito=false;
				}
				
				sb.append("</div>");
				
				sb.append("<div class=\"row\">");
				

				
				if(s.getDataInizio()!=null) {
					sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"datainizio\">Data inizio</label> <input type=\"text\" id=\"datainizio\" name=\"datainizio\" class=\"form-control\" placeholder=\"datainizio\"value=\""+s.getDataInizio()+"\"></div>");
				}
				else sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"datainizio\">Data inizio</label> <input type=\"text\" id=\"datainizio\" name=\"datainizio\" class=\"form-control\" placeholder=\"datainizio\"></div>");


				sb.append(		"<input type=\"hidden\"  name=\"enteVecchio\" value=\""+s.getEnte().getEnte()+"\">" );
				sb.append(		"<input type=\"hidden\" name=\"idStazione\" value=\""+s.getIdStazioneMetereologica()+"\">");
				
		     
				if(s.getDataFine()!=null) {
					sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"datafine\">Data fine</label> <input type=\"text\" id=\"datafine\" name=\"datafine\" class=\"form-control\" placeholder=\"datafine\"value=\""+s.getDataFine()+"\"></div>");
				}
				else sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"datafine\">Data fine</label> <input type=\"text\" id=\"datafine\" name=\"datafine\" class=\"form-control\" placeholder=\"datafine\"></div>");

				
				sb.append("</div>");
				sb.append("<br>");
				
				sb.append("	<input type=\"hidden\" name=\"operazione\" value=\"inserisciStazioneModificata\">" );
                                sb.append("	<input type=\"hidden\" name=\"idstazionemetereologica\" value=\""+s.getIdStazioneMetereologica()+"\">" );
				sb.append("	<input type=\"submit\" name =\"submit\" value=\"OK\">" );
				sb.append("	</form>");
		}else {
			sb.append("<h1>Pagina delle segnalazioni(Da implementare)</h1>");
		}
		return sb.toString();
	}
	
	
	public static String formRicercaMetereologica(String path) throws SQLException {
		StringBuilder sb = new StringBuilder();
		String loc ="IT";
		Calendar inizio = new GregorianCalendar();
		inizio.add(Calendar.MONTH, 1);
		sb.append(HTMLScript.scriptData("datainizio"));
		Calendar fine = new GregorianCalendar();
		fine.add(Calendar.MONTH, 1);
	 	sb.append(HTMLScript.scriptData("datafine"));
	 
		sb.append(HTMLScript.scriptAutocompleteLocAmm(ControllerJson.getJsonLocazioneAmminitrativa(path)));
		sb.append(HTMLScript.scriptAutocompleteSitoStazione(ControllerJson.getJsonSitoStazione(path, loc),loc));
		sb.append(HTMLScript.scriptAutocompleteEnte(ControllerJson.getJsonEnte(path)));
		sb.append(HTMLScript.scriptAutocompleteLocIdro(ControllerJson.getJsonLocazioneIdrologica(path)));
	
		sb.append("<form action=\"Servlet\" name=\"dati\" onSubmit=\"return verificaInserisci(this);\" method=\"POST\">" );		
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sulla Stazione</h4>");		
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-12\"><label for=\"nome\">Nome Della Stazione</label> <input type=\"text\" name=\"nome\" id=\"nome\" class=\"form-control\" placeholder=\"nome\" ></div>");
		sb.append("</div>");
		sb.append("<br>");
		sb.append("<div class=\"row\">");
		sb.append(	"<div class=\"col-xs-6 col-md-4\"><label for=\"aggregazionegiornaliera\">Aggregazione giornaliera:<input type=\"text\" name=\"aggregazioneGiornaliera\"  id=\"aggregazionegiornaliera\" class=\"form-control\" placeholder=\"aggregazione giornaliera\"></div>" );	
		sb.append(	"<div class=\"col-xs-6 col-md-3\"><label for=\"periodofunzionamento\">Periodo Funzionamento:<input type=\"text\" name=\"periodoFunzionamento\"  id=\"aggregazionegiornaliera\" class=\"form-control\" placeholder=\"periodo\"></div>" );
		sb.append("</div>");
		sb.append("<br>");
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"datainizio\">Data inizio</label> <input type=\"text\" id=\"datainizio\" name=\"datainizio\" class=\"form-control\" placeholder=\"datainizio\"></div>");
		sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"datafine\">Data fine</label> <input type=\"text\" id=\"datafine\" name=\"datafine\" class=\"form-control\" placeholder=\"datafine\"></div>");
		sb.append("</div>");
		sb.append("<br>");
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"ente\"> Ente:<input type=\"text\" id=\"ente\" name=\"ente\"  class=\"form-control\" placeholder=\"ente\"></div> ");
		sb.append("<input  type=\"hidden\" id=\"idEnte\" name=\"idEnte\" />");
		sb.append("</div>");
		sb.append("<br>");
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sull'ubicazione</h4>");
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"sottobacino\">Sottobacino</label><input type=\"text\" id=\"sottobacino\" name=\"sottobacino\" class=\"form-control\" placeholder=\"Sottobacino\"/></div>");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"bacino\">Bacino</label><input  type=\"text\"id=\"bacino\" name=\"bacino\" class=\"form-control\" placeholder=\"Bacino\"/></div> ");
		sb.append("<input type=\"hidden\" id=\"idSottobacino\" name=\"idSottobacino\"/>");
		sb.append("</div>");
		sb.append("<br><div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"comune\">Comune</label><input type=\"text\" id=\"comune\" name=\"comune\" class=\"form-control\"placeholder=\"Comune\"/></div>");
		sb.append("<input  type=\"hidden\" id=\"idcomune\" name=\"idcomune\" />");
		sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"provincia\">Provincia</label> type=\"text\" id=\"provincia\" name=\"provincia\" class=\"form-control\"placeholder=\"Provincia\"/></div>");
		sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"regione\">Regione</label> type=\"text\" id=\"regione\" name=\"regione\" class=\"form-control\" placeholder=\"Regione\" /> </div>");
		sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"nazione\">Nazione</label> type=\"text\" id=\"nazione\" name=\"nazione\"class=\"form-control\" placeholder=\"Nazione\" /></div>");
		sb.append("</div>");		
		sb.append("<br><div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"latitudine\">Latitudine</label><input type=\"text\" id=\"latitudine\"name=\"latitudine\" class=\"form-control\" placeholder=\"Latitudine\"/></div>");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"longitudine\">Longitudine</label><input type=\"text\" id=\"longitudine\" name=\"longitudine\" class=\"form-control\" placeholder=\"Longitudine\"/></div>");
		sb.append("</div>");
		sb.append("<br><div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"quota\">Quota</label> <input type=\"text\" id=\"quota\"name=\"quota\" class=\"form-control\" placeholder=\"Quota\"/></div>");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"esposizione\">Esposizione</label> <input type=\"text\" id=\"esposizione\" name=\"esposizione\" class=\"form-control\" placeholder=\"Esposizione\" /></div>");
		sb.append("</div>");
		sb.append("<br><div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"caratteristiche_"+loc+"\"> Caratteristiche Sito <input type=\"text\" id=\"caratteristicaSito_"+loc+"\" name=\"caratteristicaSito_"+loc+"\" class=\"form-control\" placeholder=\"Caratteristiche \" /></div>");
		sb.append("<input type=\"hidden\" id=\"idsitostazione\" name=\"idsitostazione\" />");
		sb.append("</div>");
		sb.append("</div> </div>");
		sb.append("<br>");
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Sensori</h4>");
		for(Sensori sens:ControllerDatabase.prendiTuttiSensori()){
			sb.append("<input type=\"checkbox\" id=\"sensori\" name=\"tipo_"+loc+"\" value=\""+sens.getSensori_IT()+"\" > "+sens.getSensori_IT()+" ");
		}
		sb.append("</div>");
		sb.append("</div>");
		sb.append("<br><div class=\"wrapper\">");
		sb.append("<div class=\"content-main\"><label for=\"note\">Note</label></div>");
		sb.append("<div class=\"content-secondary\"><textarea rows=\"5\" cols=\"140\" name=\"note\" id=\"note\" class=\"textarea\" placeholder=\"Note\">  </textarea></div>");
		sb.append("</div>");
		sb.append("<br>");
		sb.append(	"<input type=\"hidden\" name=\"operazione\" value=\"ricercaStazione\">" );
		sb.append(	"<input type=\"submit\" name =\"submit\" value=\"OK\">" );
		sb.append("</div>");
		sb.append(	"		</form>");
		return sb.toString();
	}
	
	
	
	

	
	public static String scegliStazioniMetereologicheDeltaT() throws SQLException{
		ArrayList<StazioneMetereologica>  ap = ControllerDatabase.prendiTutteStazioniMetereologiche(); 
		StringBuilder sb = new StringBuilder();
		Calendar data = new GregorianCalendar();
		data.add(Calendar.MONTH, 1);
		sb.append(HTMLScript.scriptData("data"));
		sb.append(HTMLScript.scriptFilter());
		sb.append("<table> <tr> <th>Nome</th>  <th>comune</th> <th> seleziona</th> </tr>");
		for(StazioneMetereologica s: ap){
			sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\">");
			sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td> <td> <input type=\"checkbox\" name=\"id\" value=\""+s.getIdStazioneMetereologica()+"\" > </td> </tr>");
			
		}
		sb.append("</table>");
		sb.append("<p>finestra in giorni:<input type=\"text\" name=\"finestra\" \"></p>");
		sb.append("<p>aggregazione in giorni:<input type=\"text\" name=\"aggregazione\" \"></p>");
		sb.append("<p> data  <input type=\"text\" id=\"data\" name=\"data\" \"></p>");
		sb.append("			<input type=\"hidden\" name=\"operazione\" value=\"eleborazioniDeltaT\">");
		sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">" +
				"		</form>");
		return sb.toString();
	}
	
	public static String scegliStazioniMetereologicheT(String op) throws SQLException{
		ArrayList<StazioneMetereologica>  ap = ControllerDatabase.prendiTutteStazioniMetereologiche(); 
		StringBuilder sb = new StringBuilder();
		Calendar data = new GregorianCalendar();
		data.add(Calendar.MONTH, 1);
		sb.append(HTMLScript.scriptData("data"));
		sb.append(HTMLScript.scriptFilter());
		sb.append("<table> <tr> <th>Nome</th>  <th>comune</th> <th> seleziona</th> </tr>");
		for(StazioneMetereologica s: ap){
			sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\">");
			sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td> <td> <input type=\"checkbox\" name=\"id\" value=\""+s.getIdStazioneMetereologica()+"\" > </td> </tr>");
			
		}
		sb.append("</table>");
		sb.append("<p>aggregazione in giorni:<input type=\"text\" name=\"aggregazione\" \"></p>");
		sb.append("<p> data  <input type=\"text\" id=\"data\" name=\"data\" \"></p>");
		sb.append("			<input type=\"hidden\" name=\"operazione\" value=\""+op+"\">");
		sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">" +
				"		</form>");
		return sb.toString();
	}
	
	public static String scegliStazioniMetereologichePrecipitazioni() throws SQLException{
		ArrayList<StazioneMetereologica>  ap = ControllerDatabase.prendiTutteStazioniMetereologiche(); 
		StringBuilder sb = new StringBuilder();
		Calendar data = new GregorianCalendar();
		data.add(Calendar.MONTH, 1);
		sb.append(HTMLScript.scriptData("data"));
		sb.append(HTMLScript.scriptFilter());
		sb.append("<table> <tr> <th>Nome</th>  <th>comune</th> <th> seleziona</th> </tr>");
		for(StazioneMetereologica s: ap){
			sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\">");
			sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td> <td> <input type=\"checkbox\" name=\"id\" value=\""+s.getIdStazioneMetereologica()+"\" > </td> </tr>");
			
		}
		
		sb.append("</table>");
		sb.append("<p>aggregazione in giorni:<input type=\"text\" name=\"aggregazione\" \"></p>");
		sb.append("<p>finestra in giorni:<input type=\"text\" name=\"finestra\" \"></p>");
		sb.append("<p> data  <input type=\"text\" id=\"data\" name=\"data\" \"></p>");
		sb.append("			<input type=\"hidden\" name=\"operazione\" value=\"precipitazioni\">");
		sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">" +
				"		</form>");
		return sb.toString();
	}
	
	public static String mostraStazioniMaps() throws SQLException{
		ArrayList<StazioneMetereologica>  sm = ControllerDatabase.prendiTutteStazioniMetereologiche(); 
		StringBuilder sb=new StringBuilder();
		
		sb.append("<div id=\"gmap\" style=\"width:400px;height:500px\"></div>");
		sb.append("<script type=\"text/javascript\" src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyD2ZrcNbP1btezQE5gYgeA7_1IY0J8odCQ&sensor=false\"></script>");
		sb.append("<script type=\"text/javascript\" src=\"http://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerclusterer/src/markerclusterer.js\"></script>");
		sb.append("<script>");
		sb.append("var map_center = new google.maps.LatLng(0.1700235000, 20.7319823000);");
		sb.append("var map = new google.maps.Map(document.getElementById(\"gmap\"), {");
		sb.append("zoom:1,");
		sb.append("center:map_center,");
		sb.append("mapTypeId:google.maps.MapTypeId.HYBRID});");
		sb.append("");
		sb.append("var pos;");
		sb.append("var marker;");
		sb.append("var marker_list = [];");
		sb.append("var stazioni = {};");
		for(int i =0;i<sm.size();i++){
			sb.append("stazioni["+i+"] = {");
			sb.append(" nome: \" "+sm.get(i).getNome()+" \",");
			sb.append(" comune: \" "+sm.get(i).getUbicazione().getLocAmm().getComune()+" \",");
			sb.append(  " x: "+sm.get(i).getUbicazione().getCoordinate().getX()+",");
			sb.append(  " y: "+sm.get(i).getUbicazione().getCoordinate().getY()+"");
			sb.append(  " };");
		}
		sb.append("for (var i = 0; i < "+sm.size()+"; i++) { "); 
		sb.append("pos = new google.maps.LatLng( stazioni[i].x , stazioni[i].y );");
		sb.append("marker = new google.maps.Marker({");
		sb.append("position:pos,");
		sb.append("map:map,");
		sb.append("title:'Title'");
		sb.append("});");
		sb.append("var infowindow = new google.maps.InfoWindow();");
		sb.append("google.maps.event.addListener(marker, 'click', (function(marker, i) {");
		sb.append("	return function() {");
		sb.append("infowindow.setContent(\"nome: \"+stazioni[i].nome+\" <br> comune: \"+stazioni[i].comune+\"\");" );
		sb.append("infowindow.open(map, marker);");
		sb.append("}");
		sb.append("})(marker, i));");
		sb.append("marker_list.push(marker);");
		sb.append("}");
		sb.append("var markerCluster = new MarkerClusterer(map, marker_list, {");
		sb.append("gridSize:40,");
		sb.append("minimumClusterSize: 4,");
		sb.append("calculator: function(markers, numStyles) {");
		sb.append("return {" );
		sb.append("text: markers.length,");
		sb.append("index: numStyles");
		sb.append("};");
		sb.append("}");
		sb.append("});");
		sb.append("</script>");
		return sb.toString();
	}
	
	public static String UploadCSV(int idstazione,Utente part){
		StringBuilder sb = new StringBuilder();
		if(part!=null && (part.getRuolo().equals(Role.AMMINISTRATORE)||part.getRuolo().equals(Role.AVANZATO))) {
			sb.append(HTMLScript.controlloData());
		sb.append(""+HTMLScript.scriptData("data")+"");
		
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Upload</h4>");
		sb.append("<form  action=\"Servlet\" method=\"POST\" onSubmit=\"return verificaData(this);\"  enctype=\"multipart/form-data\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"files[]\"><input type=\"file\" name=\"files[]\" id=\"files[]\" class=\"form-control\" multiple></div>");
		sb.append("<input type=\"hidden\" name=\"idStazioneMetereologica\" value=\""+idstazione+"\">");

		sb.append("<br>");

		sb.append("<select name=\"tabella\" id=\"tabella\">");
		sb.append(" <option value=\"temperatura_avg$temperaturaavg\">temperatura media</option>");
		sb.append(" <option value=\"temperatura_min$temperaturamin\">temperatura minime</option>");
		sb.append(" <option value=\"temperatura_max$temperaturamax\">temperatura massima</option>");
		sb.append(" <option value=\"precipitazione$quantita\">precipitazioni</option>");
		sb.append(" </select> ");
		sb.append("<br>");


		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"data\"><input type=\"text\" id=\"data\" name=\"data\" ></div>");
		//sb.append("<input id=\"ora\" name=\"ora\"   >");
		
		sb.append("<input type=\"hidden\" name=\"operazione\" value=\"uploadCSVDatiClimatici\">");
		sb.append("<br>");

		sb.append("<input type=\"submit\" name=\"invia\" value=\"carica\"/>");

		sb.append("</form>");
		sb.append("</div>");
		}else {
			sb.append("<h1>Pagina delle segnalazioni(Da implementare)</h1>");
		}
		return sb.toString();
	}
	public static String temperatureDaProcesso(ArrayList<StazioneMetereologica> s,int idProcesso){ 
		StringBuilder sb = new StringBuilder();
		Calendar data = new GregorianCalendar();
		data.add(Calendar.MONTH, 1);
		sb.append(HTMLScript.scriptData("data"));
		sb.append(HTMLScript.controlloCampi());
		sb.append("<form action=\"/DBAlps/Servlet\" onSubmit=\"return verificaInserisci(this);\" name=\"dati\" method=\"POST\">");
		sb.append("<table class=\"table\"> <tr> <th>nome</th>  </tr>");
		for(StazioneMetereologica stazione: s){
			sb.append(" <tr> <td>"+stazione.getNome()+" </td>     </tr>");
		}
		sb.append("</table>");
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati elaborazione</h4>");
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"aggregazione\">aggregazione in giorni<input type=\"text\" id=\"aggregazione\" name=\"aggregazione\" class=\"form-control\" ></div>");
		sb.append("</div>");
		sb.append("<div class=\"row\">");
		
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"gradiente\">gradiente:<input type=\"text\" id=\"finestra\" name=\"gradiente\" value=0.6 class=\"form-control\" ></div>");
		sb.append("</div>");
		sb.append("<div class=\"row\">");

		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"data\"> data  <input type=\"text\" id=\"data\" name=\"data\" class=\"form-control\" ></div>");
		sb.append("</div>");
		
	
		sb.append("<input type=\"hidden\" name=\"idProcesso\" value=\""+idProcesso+"\">");
		sb.append("	<input type=\"hidden\" name=\"operazione\" value=\"temperatureDaProcesso\">");
		sb.append("media <input type=\"checkbox\" name=\"temperature\" value=\"avg\"  >");
		sb.append("min <input type=\"checkbox\" name=\"temperature\" value=\"min\"  >");
		sb.append("max <input type=\"checkbox\" name=\"temperature\" value=\"max\"  ><br>");
		sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">" );
		sb.append(		"		</form>");
		
		sb.append("</div>");
		
		
		
		return sb.toString();
	}
	public static String deltaTDaProcesso(ArrayList<StazioneMetereologica> s){	 
		StringBuilder sb = new StringBuilder();
		Calendar data = new GregorianCalendar();
		data.add(Calendar.MONTH, 1);
		sb.append(HTMLScript.scriptData("data"));
		sb.append(HTMLScript.controlloCampi());

		sb.append("<table class=\"table\"> <tr> <th>nome</th>  </tr>");
		for(StazioneMetereologica stazione: s){
			sb.append(" <tr> <td>"+stazione.getNome()+" </td>     </tr>");
		}
		sb.append("</table>");
		sb.append("<form action=\"/DBAlps/Servlet\" onSubmit=\"return verificaInserisci(this);\" name=\"dati\" method=\"POST\">");

		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati elaborazione</h4>");
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"aggregazione\">aggregazione in giorni<input type=\"text\" id=\"aggregazione\" name=\"aggregazione\" class=\"form-control\" ></div>");
		sb.append("</div>");
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"finestra\">finestra in giorni:<input type=\"text\" id=\"finestra\" name=\"finestra\" class=\"form-control\" ></div>");
		sb.append("</div>");
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"data\"> data  <input type=\"text\" id=\"data\" name=\"data\" class=\"form-control\" ></div>");
		sb.append("</div>");
		sb.append("	<input type=\"hidden\" name=\"operazione\" value=\"eleborazioniDeltaT\">");
		/*sb.append("media <input type=\"checkbox\" name=\"temperature\" value=\"avg\"  >");
		sb.append("min <input type=\"checkbox\" name=\"temperature\" value=\"min\"  >");
		sb.append("max <input type=\"checkbox\" name=\"temperature\" value=\"max\"  ><br>");*/
		sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">" );		
		sb.append(	"</form>");
		sb.append("</div>");
		return sb.toString();
		
		
		
	}
	public static String precipitazioniDaProcesso(ArrayList<StazioneMetereologica> s){
		StringBuilder sb = new StringBuilder();
		Calendar data = new GregorianCalendar();
		data.add(Calendar.MONTH, 1);
		sb.append(HTMLScript.scriptData("data"));
		sb.append(HTMLScript.controlloCampi());
		sb.append("<form action=\"/DBAlps/Servlet\" onSubmit=\"return verificaInserisci(this);\" name=\"dati\" method=\"POST\">");
		sb.append("<table class=\"table\"> <tr> <th>nome</th>  </tr>");
		for(StazioneMetereologica stazione: s){
			sb.append(" <tr> <td>"+stazione.getNome()+" </td>     </tr>");
		}
		sb.append("</table>");
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati elaborazione</h4>");
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"aggregazione\">aggregazione in giorni<input type=\"text\" id=\"aggregazione\" name=\"aggregazione\" class=\"form-control\" ></div>");
		sb.append("</div>");
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"finestra\">finestra in giorni:<input type=\"text\" id=\"finestra\" name=\"finestra\" class=\"form-control\" ></div>");
		sb.append("</div>");
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"data\"> data  <input type=\"text\" id=\"data\" name=\"data\" class=\"form-control\" ></div>");
		sb.append("</div>");
		sb.append("<input type=\"hidden\" name=\"operazione\" value=\"elaborazioniPrecipitazioni\">");
		sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">" +
				"		</form>");
		sb.append("</div>");
		return sb.toString();
	}
	
	
	public static String scegliStazioniQuery(String op) throws SQLException{
		ArrayList<StazioneMetereologica>  ap = ControllerDatabase.prendiTutteStazioniMetereologiche(); 
		StringBuilder sb = new StringBuilder();
			sb.append("<table> <tr> <th>Nome</th>  <th>comune</th> <th> seleziona</th> </tr>");
		for(StazioneMetereologica s: ap){
			sb.append("<form action=\"Servlet\" name=\"dati\" method=\"POST\">");
			if(op.equals("datiTemperaturaEPrecipitazioneAnno"))
			sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td> <td> <input type=\"radio\" name=\"id\" value=\""+s.getIdStazioneMetereologica()+"\" > </td> </tr>");
			else 			sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td> <td> <input type=\"checkbox\" name=\"id\" value=\""+s.getIdStazioneMetereologica()+"\" > </td> </tr>");

		}
		sb.append("<script src=\"js/jquery-1.10.2.js\"></script>");
			sb.append("<script src=\"js/jquery.filtertable.js\"></script>");
				sb.append("<script>");


					sb.append("$(document).ready(function() {");
					sb.append("$('table').filterTable();");
					sb.append("});");
					sb.append("</script>");
		sb.append("</table>");
		sb.append("			<input type=\"hidden\" name=\"operazione\" value=\""+op+"\">");
		sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">" +
				"		</form>");
		return sb.toString();
	}
	

	public static String listaQueryStazione(){
		StringBuilder sb = new StringBuilder();

		sb.append("	<div class=\"row\">");
		sb.append("	<div class=\"col-xs-6 col-md-11 col-md-push-1\"><h3>Query sulla stazione metereologica</h3></div>");
		sb.append("	</div>");
		sb.append("	<br>");
		sb.append("	<div class=\"list-group\">");
		sb.append("	<div class=\"row\">");
		sb.append("  	<div class=\"col-xs-6 col-md-4  col-md-push-1\"><a href=\"Servlet?operazione=mostraTutteStazioniMetereologiche\" class=\"list-group-item\"> mostra tutte le stazioni</a></div>");
		sb.append("  	</div>	");
		sb.append("  	<div class=\"row\">");
		sb.append("  	<div class=\"col-xs-6 col-md-4  col-md-push-1\"><a href=\"Servlet?operazione=mostraStazioniMaps\" class=\"list-group-item\"> mostra stazioni su mappa</a></div>");
		sb.append("  	</div>");
		sb.append("  	<div class=\"row\">");
		sb.append("  	<div class=\"col-xs-6 col-md-4  col-md-push-1\"><a href=\"Servlet?operazione=formRicercaStazione\" class=\"list-group-item\"> ricerca stazione</a></div>");
		sb.append("  	</div>");
		sb.append("  	</div>");
		return sb.toString();
	}
	public static String caricaDatiMetereologici() throws SQLException {
		ArrayList<StazioneMetereologica>  ap = ControllerDatabase.prendiTutteStazioniMetereologiche(); 
		StringBuilder sb = new StringBuilder();
		sb.append(HTMLScript.scriptFilter()); 
		sb.append("<div class=\"table-responsive\"><table class=\"table\"> ");
		sb.append("<tr> <th>Nome</th>  <th>comune</th> <th> dettagli</th> ");
		sb.append("<th> carica dati climatici</th>");
		sb.append("</tr>");
		for(StazioneMetereologica s: ap){
			sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td>  ");
			sb.append("<td><a href=\"Servlet?operazione=mostraStazioneMetereologica&idStazioneMetereologica="+s.getIdStazioneMetereologica()+"\">dettagli</a></td>");
			sb.append("<td><a href=\"Servlet?operazione=caricaDatiClimatici&idStazioneMetereologica="+s.getIdStazioneMetereologica()+"\"> Carica</a></td>");
		}
			sb.append("</tr>");
		
		sb.append("</table></div>");
		return sb.toString();
	}
	public static String scegliStazioneModifica() throws SQLException {
		ArrayList<StazioneMetereologica>  ap = ControllerDatabase.prendiTutteStazioniMetereologiche(); 
		StringBuilder sb = new StringBuilder();
		sb.append(HTMLScript.scriptFilter()); 
		sb.append("<div class=\"table-responsive\"><table class=\"table\"> ");
		sb.append("<tr> <th>Nome</th>  <th>comune</th> <th> dettagli</th> ");
		sb.append("<th>Modifica</th>");
		sb.append("</tr>");
		for(StazioneMetereologica s: ap){
			sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td>  ");
			sb.append("<td><a href=\"Servlet?operazione=mostraStazioneMetereologica&idStazioneMetereologica="+s.getIdStazioneMetereologica()+"\">dettagli</a></td>");
			sb.append("<td><a href=\"Servlet?operazione=modificaStazione&idStazioneMetereologica="+s.getIdStazioneMetereologica()+"\">Modifica</a></td>");
		}
			sb.append("</tr>");
		
		sb.append("</table></div>");
		return sb.toString();
	}

	public static String scegliStazioneAllegati() throws SQLException {
		ArrayList<StazioneMetereologica>  ap = ControllerDatabase.prendiTutteStazioniMetereologiche(); 
		StringBuilder sb = new StringBuilder();
		sb.append(HTMLScript.scriptFilter()); 
		sb.append("<h3>Scegli una stazione a cui allegare un file</h3>");
		sb.append("<div class=\"table-responsive\"><table class=\"table\"> ");
		sb.append("<tr> <th>Nome</th>  <th>comune</th> <th> dettagli</th> ");
		sb.append("<th>Allega</th>");
		sb.append("</tr>");
		for(StazioneMetereologica s: ap){
			sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td>  ");
			sb.append("<td><a href=\"Servlet?operazione=mostraStazioneMetereologica&idStazioneMetereologica="+s.getIdStazioneMetereologica()+"\">dettagli</a></td>");
			sb.append("<td><a href=\"Servlet?operazione=formAllegatoStazione&idstazione="+s.getIdStazioneMetereologica()+"\">Allega</a></td>");
		}
			sb.append("</tr>");
		
		sb.append("</table></div>");
		return sb.toString();
	}
	public static String formAllegatoStazione(int idstazione,Utente part,ControllerLingua locale) throws SQLException{
		StringBuilder sb = new StringBuilder();
		
		StazioneMetereologica sm = ControllerDatabase.prendiStazioneMetereologica(idstazione);
		sb.append("<form class=\"form-horizontal\" action=\"Servlet\" name=\"dati\" method=\"POST\" enctype=\"multipart/form-data\" >");
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Allegu un file alla stazione "+sm.getNome()+"</h4>");
		sb.append("<br>");
		sb.append("<div class=\"form-group\">");
		sb.append("<label for=\"autore\" class=\"col-sm-2 control-label\">Autore</label>");
		sb.append("<div class=\"col-sm-10\">");
		sb.append("<input type=\"text\" name=\"autore\" id=\"autore\" class=\"form-control\">");
		sb.append("</div>");
		sb.append("</div>");
		
		sb.append("<div class=\"form-group\">");
		sb.append("<label for=\"anno\" class=\"col-sm-2 control-label\">Anno</label>");
		sb.append("<div class=\"col-sm-10\">");
		sb.append("<input type=\"text\" name=\"anno\" id=\"anno\" class=\"form-control\">");
		sb.append("</div>");
		sb.append("</div>");
		
		sb.append("<div class=\"form-group\">");
		sb.append("<label for=\"titolo\" class=\"col-sm-2 control-label\">Titolo</label>");
		sb.append("<div class=\"col-sm-10\">");
		sb.append("<input type=\"text\" name=\"titolo\" id=\"titolo\" class=\"form-control\">");
		sb.append("</div>");
		sb.append("</div>");
		
		sb.append("<div class=\"form-group\">");
		sb.append("<label for=\"in\" class=\"col-sm-2 control-label\">In:</label>");
		sb.append("<div class=\"col-sm-10\">");
		sb.append("<input type=\"text\" name=\"in\" id=\"in\" class=\"form-control\">");
		sb.append("</div>");
		sb.append("</div>");
		
		sb.append("<div class=\"form-group\">");
		sb.append("<label for=\"fonte\" class=\"col-sm-2 control-label\">Fonte</label>");
		sb.append("<div class=\"col-sm-10\">");
		sb.append("<input type=\"text\" name=\"fonte\" id=\"fonte\" class=\"form-control\" >");
		sb.append("</div>");
		sb.append("</div>");
		
		sb.append("<div class=\"form-group\">");
		sb.append("<label for=\"urlWeb\" class=\"col-sm-2 control-label\">URL del sito</label>");
		sb.append("<div class=\"col-sm-10\">");
		sb.append("<input type=\"text\" name=\"urlWeb\" id=\"urlWeb\" class=\"form-control\" >");
		sb.append("</div>");
		sb.append("</div>");
		
		sb.append("<div class=\"form-group\">");
		sb.append("<label for=\"note\" class=\"col-sm-2 control-label\">Note</label>");
		sb.append("<div class=\"col-sm-10\">");
		sb.append("<input type=\"text\" name=\"note\" id=\"note\" class=\"form-control\" >");
		sb.append("</div>");
		sb.append("</div>");
		
		
		sb.append("<div class=\"form-group\">");
		sb.append("<label for=\"tipo\" class=\"col-sm-2 control-label\">Tipo</label>");
		sb.append("<div class=\"col-sm-10\">");
		sb.append("<select class=\"form-control\" name=\"tipo\" id=\"tipo\">");
		sb.append("<option value=\"document\">Document</option>");
		sb.append("<option value=\"map\">Map </option>");
		sb.append("<option value=\"image\">Image</option>");
		sb.append("<option value=\"photo\">Photo</option>");
		sb.append("</select>");
		sb.append("</div>");
		sb.append("</div>");
		
		sb.append("<div class=\"form-group\">");
		sb.append("<label for=\"uploadFile\" class=\"col-sm-2 control-label\">Carica il File</label>");
		sb.append("<div class=\"col-sm-10\">");
		sb.append("<input type=\"file\" name=\"uploadFile\" id=\"uploadFile\" class=\"form-control\" >");
		sb.append("</div>");
		sb.append("</div>");
		
		sb.append("<input type=\"hidden\" name=\"idstazione\" value=\""+sm.getIdStazioneMetereologica()+"\">");
		sb.append("<input type=\"hidden\" name=\"operazione\" value=\"uploadAllegatoStazione\">");
		sb.append("<div class=\"form-group\">");
		sb.append("<div class=\"col-sm-10\">");
		sb.append(" <button type=\"submit\" class=\"btn btn-default\">Allega</button>");
		sb.append("</div>");
		sb.append("</div>");
		
		sb.append("</div>");
		sb.append("</form>");
		
		
		return sb.toString();
	}
}
=======
package it.cnr.to.geoclimalp.dbalps.html;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import it.cnr.to.geoclimalp.dbalps.bean.stazione.*;
import it.cnr.to.geoclimalp.dbalps.bean.Utente.*;
import it.cnr.to.geoclimalp.dbalps.controller.*;

public class HTMLStazioneMetereologica {
	public static String mostraTutteStazioniMetereologiche(Utente part) throws SQLException{
		ArrayList<StazioneMetereologica>  ap = ControllerDatabase.prendiTutteStazioniMetereologiche(); 
		StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"table-responsive\"><table class=\"table\"> ");
		sb.append("<tr> <th>Nome</th>  <th>comune</th> <th> dettagli</th> ");
		if(part!=null && (part.getRuolo().equals(Role.AMMINISTRATORE)||part.getRuolo().equals(Role.AVANZATO))){
			sb.append("<th>modifica stazione</th><th> carica dati climatici</th>");
		}
		sb.append("</tr>");
		for(StazioneMetereologica s: ap){
			sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td>  ");
			sb.append("<td><a href=\"Servlet?operazione=mostraStazioneMetereologica&idStazioneMetereologica="+s.getIdStazioneMetereologica()+"\">dettagli</a></td>");
			if(part!=null && (part.getRuolo().equals(Role.AMMINISTRATORE)||part.getRuolo().equals(Role.AVANZATO))){
				sb.append("<td><a href=\"Servlet?operazione=modificaStazione&idStazioneMetereologica="+s.getIdStazioneMetereologica()+"\"> modifica</a></td>");
				sb.append("<td><a href=\"Servlet?operazione=caricaDatiClimatici&idStazioneMetereologica="+s.getIdStazioneMetereologica()+"\"> Carica</a></td>");
			}
			sb.append("</tr>");
		}
		sb.append("</table></div>");
		return sb.toString();
	}
	public static String mostraStazioneMetereologica(int idStazioneMetereologica,ControllerLingua loc) throws SQLException{
		System.out.println("prima");
		StazioneMetereologica s = ControllerDatabase.prendiStazioneMetereologica(idStazioneMetereologica,loc);
		System.out.println("dopo");
		StringBuilder sb = new StringBuilder();
		sb.append("<table> <tr> <th>Nome</th>  <th>comune</th> </tr>");
		sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td></tr>");
		sb.append("</table>");
		return sb.toString();
	}
	public static String mostraCercaStazioneMetereologica(ArrayList<StazioneMetereologica> al) throws SQLException{
		
		StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"table-responsive\"><table class=\"table\">  <tr> <th>Nome</th> <th>data</th> <th>comune</th> <th> dettagli</th> </tr>");
		for(StazioneMetereologica s: al){
			sb.append(" <tr> <td>"+s.getNome()+" </td> <td> "+s.getDataInizio()+"</td> <td> "+s.getUbicazione().getLocAmm().getComune()+"</td>"
				/*?*/	+ "<td><a href=\"Servlet?operazione=mostraStazioneMetereologica&idStazioneMetereologica="+s.getIdStazioneMetereologica()+"\">dettagli</a></td></tr>");
		}
		sb.append("</table></div>");
		return sb.toString();
	}

	public static String formStazioneMetereologica(String path,Utente part) throws SQLException {
		StringBuilder sb = new StringBuilder();
		String loc ="IT";
		Calendar inizio = new GregorianCalendar();
		inizio.add(Calendar.MONTH, 1);
		sb.append(HTMLScript.scriptData("datainizio"));
		Calendar fine = new GregorianCalendar();
		fine.add(Calendar.MONTH, 1);
	 	sb.append(HTMLScript.scriptData("datafine"));
	 	sb.append(HTMLScript.scriptAperturaControlloInserimento());
	 	sb.append(HTMLScript.scriptControlloInserimento("nome"));
	 	sb.append(HTMLScript.scriptControlloInserimento("comune"));
	 	sb.append(HTMLScript.scriptChiusuraControlloInserimento());

		sb.append(HTMLScript.scriptAutocompleteLocAmm(ControllerJson.getJsonLocazioneAmminitrativa(path)));
		sb.append(HTMLScript.scriptAutocompleteSitoStazione(ControllerJson.getJsonSitoStazione(path, loc),loc));
		sb.append(HTMLScript.scriptAutocompleteEnte(ControllerJson.getJsonEnte(path)));
		sb.append(HTMLScript.scriptAutocompleteLocIdro(ControllerJson.getJsonLocazioneIdrologica(path)));
		if(part!=null && (part.getRuolo().equals(Role.AMMINISTRATORE)||part.getRuolo().equals(Role.AVANZATO))){
				sb.append("<form action=\"Servlet\" class=\"insertStazione\"  method=\"POST\" role=\"form\">");
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sulla Stazione</h4>");		
              sb.append("<div class=\"form-group\" >");

                sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"nome\">nome</label> <input type=\"text\" name=\"nome\" id=\"nome\" class=\"form-control\" placeholder=nome ></div>");
		sb.append("</div>");
		sb.append("<br>");
		sb.append("<div class=\"row\">");
		sb.append(	"<div class=\"col-xs-6 col-md-4\"><label for=\"aggregazionegiornaliera\">Aggregazione temporale:<input type=\"text\" name=\"aggregazioneGiornaliera\"  id=\"aggregazionegiornaliera\" class=\"form-control\" placeholder=\"aggregazione giornaliera\"></div>" );	
		sb.append(	"<div class=\"col-xs-6 col-md-3\"><label for=\"periodofunzionamento\">tipo di aggregazione giornaliera:<input type=\"text\" name=\"periodoFunzionamento\"  id=\"aggregazionegiornaliera\" class=\"form-control\" placeholder=\"periodo\"></div>" );
		sb.append("</div>");
		sb.append("<br>");
		/*sb.append("<div class=\"row\">");
		sb.append(	"<div class=\"col-xs-6 col-md-4\"><label for=\"oraria\">Oraria:<input type=\"radio\" name=\"oraria\"  id=\"oraria\" value=\"oraria\"  ></div>");
		sb.append(	"<div class=\"col-xs-6 col-md-4\"><label for=\"oraria\">Giornaliera:<input type=\"radio\" checked name=\"oraria\"  id=\"giornaliera\" value=\"giornaliera\" ></div>");
		sb.append("</div>");
		sb.append("<br>");*/
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"datainizio\">Data inizio <input type=\"text\"  id=\"datainizio\" name=\"datainizio\" class=\"form-control\" placeholder=\"datainizio\"></div>");
		sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"datafine\">Data fine <input type=\"text\" id=\"datafine\" name=\"datafine\" class=\"form-control\" placeholder=\"datafine\"></div>");
		sb.append("</div>");
		sb.append("<br>");
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"ente\"> Ente:<input type=\"text\" id=\"ente\" name=\"ente\"  class=\"form-control\" placeholder=\"ente\"></div> ");
		sb.append("<input  type=\"hidden\" id=\"idEnte\" name=\"idEnte\" />");
		sb.append("</div>");
		sb.append("<br>");
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sull'ubicazione</h4>");
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"sottobacino\">Sottobacino</label><input type=\"text\" id=\"sottobacino\" name=\"sottobacino\" class=\"form-control\" placeholder=\"Sottobacino\"/></div>");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"bacino\">Bacino</label><input readonly=\"readonly\" type=\"text\"id=\"bacino\" name=\"bacino\" class=\"form-control\" placeholder=\"Bacino\"/></div> ");
		sb.append("<input type=\"hidden\" id=\"idSottobacino\" name=\"idSottobacino\" />");
		sb.append("</div>");
		sb.append("<br><div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"comune\">Comune</label><input type=\"text\" id=\"comune\" name=\"comune\" class=\"form-control\"placeholder=\"Comune\"/></div>");
		sb.append("<input  type=\"hidden\" id=\"idcomune\" name=\"idcomune\" />");
		sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"provincia\">Provincia</label><input readonly=\"readonly\" type=\"text\" id=\"provincia\" name=\"provincia\" class=\"form-control\"placeholder=\"Provincia\"/></div>");
		sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"regione\">Regione</label><input readonly=\"readonly\" type=\"text\" id=\"regione\" name=\"regione\" class=\"form-control\" placeholder=\"Regione\" /> </div>");
		sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"nazione\">Nazione</label><input readonly=\"readonly\" type=\"text\" id=\"nazione\" name=\"nazione\"class=\"form-control\" placeholder=\"Nazione\" /></div>");
		sb.append("</div>");	
		sb.append("<br><div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"latitudine\">latitudine</label><input type=\"text\" id=\"latitudine\"name=\"latitudine\" class=\"form-control\" placeholder=\"latitudine\"/></div>");
		sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"longitudine\">longitudine</label><input type=\"text\" id=\"longitudine\" name=\"longitudine\" class=\"form-control\" placeholder=\"longitudine\"/></div>");
		sb.append("</div>");
		sb.append("<br><div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"quota\">Quota</label> <input type=\"text\" id=\"quota\"name=\"quota\" class=\"form-control\" placeholder=\"Quota\"/></div>");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"esposizione\">Esposizione</label> <input type=\"text\" id=\"esposizione\" name=\"esposizione\" class=\"form-control\" placeholder=\"Esposizione\" /></div>");
		sb.append("</div>");
		sb.append("<br><div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"caratteristiche_"+loc+"\"> Caratteristiche Sito <input type=\"text\" id=\"caratteristiche_"+loc+"\" name=\"caratteristicaSito_"+loc+"\" class=\"form-control\" placeholder=\"Caratteristiche \" /></div>");
		sb.append("<input type=\"hidden\" id=\"idsitostazione\" name=\"idsitostazione\" />");
		sb.append("</div>");
		sb.append("</div> </div>");	
		sb.append("<br>");
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Sensori</h4>");
		for(Sensori sens:ControllerDatabase.prendiTuttiSensori()){
			sb.append("<input type=\"checkbox\" id=\"sensori\" name=\"tipo_IT\" value=\""+sens.getSensori_IT()+"\" > "+sens.getSensori_IT()+" ");
		}
		sb.append("</div>");
		sb.append("</div>");
		sb.append("<br><div class=\"wrapper\">");
		sb.append("<div class=\"content-main\"><label for=\"note\">Note</label></div>");
		sb.append("<div class=\"content-secondary\"><textarea rows=\"5\" cols=\"140\" name=\"note\" id=\"note\" class=\"textarea\" placeholder=\"Note\">  </textarea></div>");
		sb.append("</div>");
		
		sb.append("<br>");
		
			sb.append("<input type=\"hidden\" name=\"operazione\" value=\"inserisciStazione\">" );
                    sb.append("<button type=\"submit\" class=\"btn btn-default\">Inserisci stazione</button>");

			sb.append("</div>");
                        sb.append("</div>");                        


			sb.append("</form>");}
		else {
				sb.append("<h1>Pagina delle segnalazioni(Da implementare)</h1>");
			}
		return sb.toString();
	}
	
	//modificato
	public static String modificaStazioneMetereologica(StazioneMetereologica s,String path,Utente part) throws SQLException {
		StringBuilder sb = new StringBuilder();
		String loc ="IT";
		Calendar inizio = new GregorianCalendar();
		if(part!=null && (part.getRuolo().equals(Role.AMMINISTRATORE)||part.getRuolo().equals(Role.AVANZATO))) {
		
		
		sb.append(HTMLScript.scriptData("datainizio"));
		sb.append(HTMLScript.scriptData("datafine"));
		sb.append(HTMLScript.scriptAutocompleteLocAmm(ControllerJson.getJsonLocazioneAmminitrativa(path)));
		sb.append(HTMLScript.scriptAutocompleteSitoStazione(ControllerJson.getJsonSitoStazione(path, loc),loc));
		sb.append(HTMLScript.scriptAutocompleteEnte(ControllerJson.getJsonEnte(path)));

                
                
		String temp;		
		sb.append("<form action=\"Servlet\" name=\"dati\" method=\"POST\">" );
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sulla Stazione</h4>");		
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-12\"><label for=\"nome\">Nome Della Stazione</label> <input type=\"text\" name=\"nome\" id=\"nome\" class=\"form-control\" placeholder=\"nome\" value=\""+s.getNome()+"\" ></div>");
		sb.append("</div>");
		sb.append("<br>");
		
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"aggregazionegiornaliera\">Aggregazione temporale:<input type=\"text\" name=\"aggregazioneGiornaliera\"  id=\"aggregazionegiornaliera\" value=\""+s.getAggregazioneGiornaliera()+"\" class=\"form-control\" placeholder=\"aggregazione giornaliera\"></div>" );	
		/*errore?*/	sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"periodofunzionamento\">Tipo di aggregazione giornaliera:<input type=\"text\" name=\"periodoFunzionamento\"  id=\"aggregazionegiornaliera\" class=\"form-control\" placeholder=\"periodo\"></div>" );
		sb.append("</div>");
		sb.append("<br>");
		
		sb.append("<br><div class=\"wrapper\">");
		sb.append("<div class=\"content-main\"><label for=\"note\">Note</label></div>");
		sb.append("<div class=\"content-secondary\"><textarea rows=\"5\" cols=\"100\" name=\"note\" id=\"note\"  class=\"textarea\" placeholder=\"Note\"> "+s.getNote()+" </textarea></div>");
		sb.append("</div>");
		
		sb.append("<br>");
		sb.append("<div class=\"row\">");
		
		
		if(s.getEnte().getEnte()!=null) temp=s.getEnte().getEnte();
		else temp="";
		System.out.println("Ente: "+temp);
		sb.append("<div class=\"col-xs-9 col-md-9\"><label for=\"ente\"> Ente:<input type=\"text\" id=\"ente\" value=\""+temp+"\"  name=\"ente\"  class=\"form-control\" placeholder=\"ente\"></div> ");
		sb.append("<input  type=\"hidden\" id=\"idEnte\" name=\"idEnte\" value="+s.getEnte().getIdEnte()+">");
		if(s.getSito().getCaratteristiche_IT()!=null) temp=s.getSito().getCaratteristiche_IT();
		else temp="";
		sb.append("</div> </div>");
		sb.append("</div>");
		sb.append("<br>");
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sull'ubicazione</h4>");
		sb.append("<div class=\"row\">");
                if(s.getUbicazione().getLocIdro().getSottobacino()!=null) temp=s.getUbicazione().getLocIdro().getSottobacino();
		else temp="";
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"sottobacino\">Sottobacino</label><input type=\"text\" id=\"sottobacino\" name=\"sottobacino\" class=\"form-control\" value=\""+temp+"\"/></div>");
		 if(s.getUbicazione().getLocIdro().getBacino()!=null) temp=s.getUbicazione().getLocIdro().getBacino();
		else temp="";
                sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"bacino\">Bacino</label><input readonly=\"readonly\" type=\"text\"id=\"bacino\" name=\"bacino\" class=\"form-control\" value=\""+temp+"\"/></div> ");
		sb.append("<input type=\"hidden\" id=\"idSottobacino\" name=\"idSottobacino\"  value=\""+s.getUbicazione().getLocIdro().getIdSottobacino()+"\"/>");
		sb.append("</div>");
		sb.append("<br><div class=\"row\">");
                if(s.getUbicazione().getLocAmm().getComune()!=null) temp=s.getUbicazione().getLocAmm().getComune();
		else temp="";
		sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"comune\">Comune</label><input type=\"text\" id=\"comune\" name=\"comune\" class=\"form-control\" value=\""+temp+"\"/></div>");
		sb.append("<input  type=\"hidden\" id=\"idcomune\" name=\"idcomune\" value=\""+s.getUbicazione().getLocAmm().getIdComune()+"\"/>");
		if(s.getUbicazione().getLocAmm().getProvincia()!=null) temp=s.getUbicazione().getLocAmm().getProvincia();
		else temp="";
                sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"provincia\">Provincia</label><input readonly=\"readonly\" type=\"text\" id=\"provincia\" name=\"provincia\" class=\"form-control\" value=\""+temp+"\"/></div>");
		if(s.getUbicazione().getLocAmm().getRegione()!=null) temp=s.getUbicazione().getLocAmm().getRegione();
		else temp="";
                sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"regione\">Regione</label><input readonly=\"readonly\" type=\"text\" id=\"regione\" name=\"regione\" class=\"form-control\" value=\""+temp+"\" /> </div>");
		if(s.getUbicazione().getLocAmm().getNazione()!=null) temp=s.getUbicazione().getLocAmm().getNazione();
		else temp="";
                sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"nazione\">Nazione</label><input readonly=\"readonly\" type=\"text\" id=\"nazione\" name=\"nazione\"class=\"form-control\" value=\""+temp+"\" /></div>");
		sb.append("</div>");
		sb.append("<div id=\"controls\">");
		sb.append("<br><div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"latitudine\">Latitudine</label><input type=\"text\" id=\"latitudine\"name=\"latitudine\" class=\"form-control\" value=\""+s.getUbicazione().getCoordinate().getX()+"\"/></div>");
		sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"longitudine\">Longitudine</label><input type=\"text\" id=\"longitudine\" name=\"longitudine\" class=\"form-control\" value=\""+s.getUbicazione().getCoordinate().getY()+"\"/></div>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("<div class=\"row\">");
                if(s.getSito().getCaratteristiche_IT()!=null) temp=s.getSito().getCaratteristiche_IT();
		else temp="";
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"caratteristiche_"+loc+"\"> Caratteristiche Sito <input type=\"text\" id=\"caratteristiche_"+loc+"\" name=\"caratteristicaSito_"+loc+"\" class=\"form-control\" value=\""+temp+" \" /></div>");
		sb.append("<input type=\"hidden\" id=\"idsitostazione\" name=\"idsitostazione\"  value=\""+s.getSito().getIdSitoStazioneMetereologica()+"\"/>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("<br><div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"quota\">Quota</label> <input type=\"text\" id=\"quota\"name=\"quota\" class=\"form-control\" value=\""+s.getUbicazione().getQuota()+"\"/></div>");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"esposizione\">Esposizione</label> <input type=\"text\" id=\"esposizione\" name=\"esposizione\" class=\"form-control\" value=\""+s.getUbicazione().getEsposizione()+"\"/></div>");
		sb.append("<br>");
		sb.append("<br><div class=\"row\">");
		
		
		
		
		sb.append("</div>");
		sb.append("</div>");

		sb.append("</div>");
		
		
		
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Sensori</h4>");
				for(Sensori sens:ControllerDatabase.prendiTuttiSensori()){
					boolean inserito=false;
					for(int i=0;i<s.getSensori().size();i++){
						if(sens.getSensori_IT().equals(s.getSensori().get(i).getSensori_IT())){
							sb.append("<input type=\"checkbox\" name=\"tipo_it\" value=\""+sens.getSensori_IT()+"\" checked=\"checked\" > "+sens.getSensori_IT()+" "); 
							inserito=true;

						}
					}
					if(inserito==false) sb.append("<input type=\"checkbox\" name=\"tipo_it\" value=\""+sens.getSensori_IT()+"\" > "+sens.getSensori_IT()+" ");
					inserito=false;
				}
				
				sb.append("</div>");
				
				sb.append("<div class=\"row\">");
				

				
				if(s.getDataInizio()!=null) {
					sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"datainizio\">Data inizio</label> <input type=\"text\" id=\"datainizio\" name=\"datainizio\" class=\"form-control\" placeholder=\"datainizio\"value=\""+s.getDataInizio()+"\"></div>");
				}
				else sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"datainizio\">Data inizio</label> <input type=\"text\" id=\"datainizio\" name=\"datainizio\" class=\"form-control\" placeholder=\"datainizio\"></div>");


				sb.append(		"<input type=\"hidden\"  name=\"enteVecchio\" value=\""+s.getEnte().getEnte()+"\">" );
				sb.append(		"<input type=\"hidden\" name=\"idStazione\" value=\""+s.getIdStazioneMetereologica()+"\">");
				
		     
				if(s.getDataFine()!=null) {
					sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"datafine\">Data fine</label> <input type=\"text\" id=\"datafine\" name=\"datafine\" class=\"form-control\" placeholder=\"datafine\"value=\""+s.getDataFine()+"\"></div>");
				}
				else sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"datafine\">Data fine</label> <input type=\"text\" id=\"datafine\" name=\"datafine\" class=\"form-control\" placeholder=\"datafine\"></div>");

				
				sb.append("</div>");
				sb.append("<br>");
				
				sb.append("	<input type=\"hidden\" name=\"operazione\" value=\"inserisciStazioneModificata\">" );
                                sb.append("	<input type=\"hidden\" name=\"idstazionemetereologica\" value=\""+s.getIdStazioneMetereologica()+"\">" );
				sb.append("	<input type=\"submit\" name =\"submit\" value=\"OK\">" );
				sb.append("	</form>");
		}else {
			sb.append("<h1>Pagina delle segnalazioni(Da implementare)</h1>");
		}
		return sb.toString();
	}
	
	
	public static String formRicercaMetereologica(String path) throws SQLException {
		StringBuilder sb = new StringBuilder();
		String loc ="IT";
		Calendar inizio = new GregorianCalendar();
		inizio.add(Calendar.MONTH, 1);
		sb.append(HTMLScript.scriptData("datainizio"));
		Calendar fine = new GregorianCalendar();
		fine.add(Calendar.MONTH, 1);
	 	sb.append(HTMLScript.scriptData("datafine"));
	 
		sb.append(HTMLScript.scriptAutocompleteLocAmm(ControllerJson.getJsonLocazioneAmminitrativa(path)));
		sb.append(HTMLScript.scriptAutocompleteSitoStazione(ControllerJson.getJsonSitoStazione(path, loc),loc));
		sb.append(HTMLScript.scriptAutocompleteEnte(ControllerJson.getJsonEnte(path)));
		sb.append(HTMLScript.scriptAutocompleteLocIdro(ControllerJson.getJsonLocazioneIdrologica(path)));
	
		sb.append("<form action=\"Servlet\" name=\"dati\" onSubmit=\"return verificaInserisci(this);\" method=\"POST\">" );		
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sulla Stazione</h4>");		
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-12\"><label for=\"nome\">Nome Della Stazione</label> <input type=\"text\" name=\"nome\" id=\"nome\" class=\"form-control\" placeholder=\"nome\" ></div>");
		sb.append("</div>");
		sb.append("<br>");
		sb.append("<div class=\"row\">");
		sb.append(	"<div class=\"col-xs-6 col-md-4\"><label for=\"aggregazionegiornaliera\">Aggregazione giornaliera:<input type=\"text\" name=\"aggregazioneGiornaliera\"  id=\"aggregazionegiornaliera\" class=\"form-control\" placeholder=\"aggregazione giornaliera\"></div>" );	
		sb.append(	"<div class=\"col-xs-6 col-md-3\"><label for=\"periodofunzionamento\">Periodo Funzionamento:<input type=\"text\" name=\"periodoFunzionamento\"  id=\"aggregazionegiornaliera\" class=\"form-control\" placeholder=\"periodo\"></div>" );
		sb.append("</div>");
		sb.append("<br>");
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"datainizio\">Data inizio</label> <input type=\"text\" id=\"datainizio\" name=\"datainizio\" class=\"form-control\" placeholder=\"datainizio\"></div>");
		sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"datafine\">Data fine</label> <input type=\"text\" id=\"datafine\" name=\"datafine\" class=\"form-control\" placeholder=\"datafine\"></div>");
		sb.append("</div>");
		sb.append("<br>");
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"ente\"> Ente:<input type=\"text\" id=\"ente\" name=\"ente\"  class=\"form-control\" placeholder=\"ente\"></div> ");
		sb.append("<input  type=\"hidden\" id=\"idEnte\" name=\"idEnte\" />");
		sb.append("</div>");
		sb.append("<br>");
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sull'ubicazione</h4>");
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"sottobacino\">Sottobacino</label><input type=\"text\" id=\"sottobacino\" name=\"sottobacino\" class=\"form-control\" placeholder=\"Sottobacino\"/></div>");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"bacino\">Bacino</label><input  type=\"text\"id=\"bacino\" name=\"bacino\" class=\"form-control\" placeholder=\"Bacino\"/></div> ");
		sb.append("<input type=\"hidden\" id=\"idSottobacino\" name=\"idSottobacino\"/>");
		sb.append("</div>");
		sb.append("<br><div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"comune\">Comune</label><input type=\"text\" id=\"comune\" name=\"comune\" class=\"form-control\"placeholder=\"Comune\"/></div>");
		sb.append("<input  type=\"hidden\" id=\"idcomune\" name=\"idcomune\" />");
		sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"provincia\">Provincia</label> type=\"text\" id=\"provincia\" name=\"provincia\" class=\"form-control\"placeholder=\"Provincia\"/></div>");
		sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"regione\">Regione</label> type=\"text\" id=\"regione\" name=\"regione\" class=\"form-control\" placeholder=\"Regione\" /> </div>");
		sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"nazione\">Nazione</label> type=\"text\" id=\"nazione\" name=\"nazione\"class=\"form-control\" placeholder=\"Nazione\" /></div>");
		sb.append("</div>");		
		sb.append("<br><div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"latitudine\">Latitudine</label><input type=\"text\" id=\"latitudine\"name=\"latitudine\" class=\"form-control\" placeholder=\"Latitudine\"/></div>");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"longitudine\">Longitudine</label><input type=\"text\" id=\"longitudine\" name=\"longitudine\" class=\"form-control\" placeholder=\"Longitudine\"/></div>");
		sb.append("</div>");
		sb.append("<br><div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"quota\">Quota</label> <input type=\"text\" id=\"quota\"name=\"quota\" class=\"form-control\" placeholder=\"Quota\"/></div>");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"esposizione\">Esposizione</label> <input type=\"text\" id=\"esposizione\" name=\"esposizione\" class=\"form-control\" placeholder=\"Esposizione\" /></div>");
		sb.append("</div>");
		sb.append("<br><div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"caratteristiche_"+loc+"\"> Caratteristiche Sito <input type=\"text\" id=\"caratteristicaSito_"+loc+"\" name=\"caratteristicaSito_"+loc+"\" class=\"form-control\" placeholder=\"Caratteristiche \" /></div>");
		sb.append("<input type=\"hidden\" id=\"idsitostazione\" name=\"idsitostazione\" />");
		sb.append("</div>");
		sb.append("</div> </div>");
		sb.append("<br>");
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Sensori</h4>");
		for(Sensori sens:ControllerDatabase.prendiTuttiSensori()){
			sb.append("<input type=\"checkbox\" id=\"sensori\" name=\"tipo_"+loc+"\" value=\""+sens.getSensori_IT()+"\" > "+sens.getSensori_IT()+" ");
		}
		sb.append("</div>");
		sb.append("</div>");
		sb.append("<br><div class=\"wrapper\">");
		sb.append("<div class=\"content-main\"><label for=\"note\">Note</label></div>");
		sb.append("<div class=\"content-secondary\"><textarea rows=\"5\" cols=\"140\" name=\"note\" id=\"note\" class=\"textarea\" placeholder=\"Note\">  </textarea></div>");
		sb.append("</div>");
		sb.append("<br>");
		sb.append(	"<input type=\"hidden\" name=\"operazione\" value=\"ricercaStazione\">" );
		sb.append(	"<input type=\"submit\" name =\"submit\" value=\"OK\">" );
		sb.append("</div>");
		sb.append(	"		</form>");
		return sb.toString();
	}
	
	
	
	

	
	public static String scegliStazioniMetereologicheDeltaT() throws SQLException{
		ArrayList<StazioneMetereologica>  ap = ControllerDatabase.prendiTutteStazioniMetereologiche(); 
		StringBuilder sb = new StringBuilder();
		Calendar data = new GregorianCalendar();
		data.add(Calendar.MONTH, 1);
		sb.append(HTMLScript.scriptData("data"));
		sb.append(HTMLScript.scriptFilter());
		sb.append("<table> <tr> <th>Nome</th>  <th>comune</th> <th> seleziona</th> </tr>");
		for(StazioneMetereologica s: ap){
			sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\">");
			sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td> <td> <input type=\"checkbox\" name=\"id\" value=\""+s.getIdStazioneMetereologica()+"\" > </td> </tr>");
			
		}
		sb.append("</table>");
		sb.append("<p>finestra in giorni:<input type=\"text\" name=\"finestra\" \"></p>");
		sb.append("<p>aggregazione in giorni:<input type=\"text\" name=\"aggregazione\" \"></p>");
		sb.append("<p> data  <input type=\"text\" id=\"data\" name=\"data\" \"></p>");
		sb.append("			<input type=\"hidden\" name=\"operazione\" value=\"eleborazioniDeltaT\">");
		sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">" +
				"		</form>");
		return sb.toString();
	}
	
	public static String scegliStazioniMetereologicheT(String op) throws SQLException{
		ArrayList<StazioneMetereologica>  ap = ControllerDatabase.prendiTutteStazioniMetereologiche(); 
		StringBuilder sb = new StringBuilder();
		Calendar data = new GregorianCalendar();
		data.add(Calendar.MONTH, 1);
		sb.append(HTMLScript.scriptData("data"));
		sb.append(HTMLScript.scriptFilter());
		sb.append("<table> <tr> <th>Nome</th>  <th>comune</th> <th> seleziona</th> </tr>");
		for(StazioneMetereologica s: ap){
			sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\">");
			sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td> <td> <input type=\"checkbox\" name=\"id\" value=\""+s.getIdStazioneMetereologica()+"\" > </td> </tr>");
			
		}
		sb.append("</table>");
		sb.append("<p>aggregazione in giorni:<input type=\"text\" name=\"aggregazione\" \"></p>");
		sb.append("<p> data  <input type=\"text\" id=\"data\" name=\"data\" \"></p>");
		sb.append("			<input type=\"hidden\" name=\"operazione\" value=\""+op+"\">");
		sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">" +
				"		</form>");
		return sb.toString();
	}
	
	public static String scegliStazioniMetereologichePrecipitazioni() throws SQLException{
		ArrayList<StazioneMetereologica>  ap = ControllerDatabase.prendiTutteStazioniMetereologiche(); 
		StringBuilder sb = new StringBuilder();
		Calendar data = new GregorianCalendar();
		data.add(Calendar.MONTH, 1);
		sb.append(HTMLScript.scriptData("data"));
		sb.append(HTMLScript.scriptFilter());
		sb.append("<table> <tr> <th>Nome</th>  <th>comune</th> <th> seleziona</th> </tr>");
		for(StazioneMetereologica s: ap){
			sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\">");
			sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td> <td> <input type=\"checkbox\" name=\"id\" value=\""+s.getIdStazioneMetereologica()+"\" > </td> </tr>");
			
		}
		
		sb.append("</table>");
		sb.append("<p>aggregazione in giorni:<input type=\"text\" name=\"aggregazione\" \"></p>");
		sb.append("<p>finestra in giorni:<input type=\"text\" name=\"finestra\" \"></p>");
		sb.append("<p> data  <input type=\"text\" id=\"data\" name=\"data\" \"></p>");
		sb.append("			<input type=\"hidden\" name=\"operazione\" value=\"precipitazioni\">");
		sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">" +
				"		</form>");
		return sb.toString();
	}
	
	public static String mostraStazioniMaps() throws SQLException{
		ArrayList<StazioneMetereologica>  sm = ControllerDatabase.prendiTutteStazioniMetereologiche(); 
		StringBuilder sb=new StringBuilder();
		
		sb.append("<div id=\"gmap\" style=\"width:400px;height:500px\"></div>");
		sb.append("<script type=\"text/javascript\" src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyD2ZrcNbP1btezQE5gYgeA7_1IY0J8odCQ&sensor=false\"></script>");
		sb.append("<script type=\"text/javascript\" src=\"http://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerclusterer/src/markerclusterer.js\"></script>");
		sb.append("<script>");
		sb.append("var map_center = new google.maps.LatLng(0.1700235000, 20.7319823000);");
		sb.append("var map = new google.maps.Map(document.getElementById(\"gmap\"), {");
		sb.append("zoom:1,");
		sb.append("center:map_center,");
		sb.append("mapTypeId:google.maps.MapTypeId.HYBRID});");
		sb.append("");
		sb.append("var pos;");
		sb.append("var marker;");
		sb.append("var marker_list = [];");
		sb.append("var stazioni = {};");
		for(int i =0;i<sm.size();i++){
			sb.append("stazioni["+i+"] = {");
			sb.append(" nome: \" "+sm.get(i).getNome()+" \",");
			sb.append(" comune: \" "+sm.get(i).getUbicazione().getLocAmm().getComune()+" \",");
			sb.append(  " x: "+sm.get(i).getUbicazione().getCoordinate().getX()+",");
			sb.append(  " y: "+sm.get(i).getUbicazione().getCoordinate().getY()+"");
			sb.append(  " };");
		}
		sb.append("for (var i = 0; i < "+sm.size()+"; i++) { "); 
		sb.append("pos = new google.maps.LatLng( stazioni[i].x , stazioni[i].y );");
		sb.append("marker = new google.maps.Marker({");
		sb.append("position:pos,");
		sb.append("map:map,");
		sb.append("title:'Title'");
		sb.append("});");
		sb.append("var infowindow = new google.maps.InfoWindow();");
		sb.append("google.maps.event.addListener(marker, 'click', (function(marker, i) {");
		sb.append("	return function() {");
		sb.append("infowindow.setContent(\"nome: \"+stazioni[i].nome+\" <br> comune: \"+stazioni[i].comune+\"\");" );
		sb.append("infowindow.open(map, marker);");
		sb.append("}");
		sb.append("})(marker, i));");
		sb.append("marker_list.push(marker);");
		sb.append("}");
		sb.append("var markerCluster = new MarkerClusterer(map, marker_list, {");
		sb.append("gridSize:40,");
		sb.append("minimumClusterSize: 4,");
		sb.append("calculator: function(markers, numStyles) {");
		sb.append("return {" );
		sb.append("text: markers.length,");
		sb.append("index: numStyles");
		sb.append("};");
		sb.append("}");
		sb.append("});");
		sb.append("</script>");
		return sb.toString();
	}
	
	public static String UploadCSV(int idstazione,Utente part){
		StringBuilder sb = new StringBuilder();
		if(part!=null && (part.getRuolo().equals(Role.AMMINISTRATORE)||part.getRuolo().equals(Role.AVANZATO))) {
			sb.append(HTMLScript.controlloData());
		sb.append(""+HTMLScript.scriptData("data")+"");
		
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Upload</h4>");
		sb.append("<form  action=\"Servlet\" method=\"POST\" onSubmit=\"return verificaData(this);\"  enctype=\"multipart/form-data\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"files[]\"><input type=\"file\" name=\"files[]\" id=\"files[]\" class=\"form-control\" multiple></div>");
		sb.append("<input type=\"hidden\" name=\"idStazioneMetereologica\" value=\""+idstazione+"\">");

		sb.append("<br>");

		sb.append("<select name=\"tabella\" id=\"tabella\">");
		sb.append(" <option value=\"temperatura_avg$temperaturaavg\">temperatura media</option>");
		sb.append(" <option value=\"temperatura_min$temperaturamin\">temperatura minime</option>");
		sb.append(" <option value=\"temperatura_max$temperaturamax\">temperatura massima</option>");
		sb.append(" <option value=\"precipitazione$quantita\">precipitazioni</option>");
		sb.append(" </select> ");
		sb.append("<br>");


		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"data\"><input type=\"text\" id=\"data\" name=\"data\" ></div>");
		//sb.append("<input id=\"ora\" name=\"ora\"   >");
		
		sb.append("<input type=\"hidden\" name=\"operazione\" value=\"uploadCSVDatiClimatici\">");
		sb.append("<br>");

		sb.append("<input type=\"submit\" name=\"invia\" value=\"carica\"/>");

		sb.append("</form>");
		sb.append("</div>");
		}else {
			sb.append("<h1>Pagina delle segnalazioni(Da implementare)</h1>");
		}
		return sb.toString();
	}
	public static String temperatureDaProcesso(ArrayList<StazioneMetereologica> s,int idProcesso){ 
		StringBuilder sb = new StringBuilder();
		Calendar data = new GregorianCalendar();
		data.add(Calendar.MONTH, 1);
		sb.append(HTMLScript.scriptData("data"));
		sb.append(HTMLScript.controlloCampi());
		sb.append("<form action=\"/DBAlps/Servlet\" onSubmit=\"return verificaInserisci(this);\" name=\"dati\" method=\"POST\">");
		sb.append("<table class=\"table\"> <tr> <th>nome</th>  </tr>");
		for(StazioneMetereologica stazione: s){
			sb.append(" <tr> <td>"+stazione.getNome()+" </td>     </tr>");
		}
		sb.append("</table>");
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati elaborazione</h4>");
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"aggregazione\">aggregazione in giorni<input type=\"text\" id=\"aggregazione\" name=\"aggregazione\" class=\"form-control\" ></div>");
		sb.append("</div>");
		sb.append("<div class=\"row\">");
		
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"gradiente\">gradiente:<input type=\"text\" id=\"finestra\" name=\"gradiente\" value=0.6 class=\"form-control\" ></div>");
		sb.append("</div>");
		sb.append("<div class=\"row\">");

		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"data\"> data  <input type=\"text\" id=\"data\" name=\"data\" class=\"form-control\" ></div>");
		sb.append("</div>");
		
	
		sb.append("<input type=\"hidden\" name=\"idProcesso\" value=\""+idProcesso+"\">");
		sb.append("	<input type=\"hidden\" name=\"operazione\" value=\"temperatureDaProcesso\">");
		sb.append("media <input type=\"checkbox\" name=\"temperature\" value=\"avg\"  >");
		sb.append("min <input type=\"checkbox\" name=\"temperature\" value=\"min\"  >");
		sb.append("max <input type=\"checkbox\" name=\"temperature\" value=\"max\"  ><br>");
		sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">" );
		sb.append(		"		</form>");
		
		sb.append("</div>");
		
		
		
		return sb.toString();
	}
	public static String deltaTDaProcesso(ArrayList<StazioneMetereologica> s){	 
		StringBuilder sb = new StringBuilder();
		Calendar data = new GregorianCalendar();
		data.add(Calendar.MONTH, 1);
		sb.append(HTMLScript.scriptData("data"));
		sb.append(HTMLScript.controlloCampi());

		sb.append("<table class=\"table\"> <tr> <th>nome</th>  </tr>");
		for(StazioneMetereologica stazione: s){
			sb.append(" <tr> <td>"+stazione.getNome()+" </td>     </tr>");
		}
		sb.append("</table>");
		sb.append("<form action=\"/DBAlps/Servlet\" onSubmit=\"return verificaInserisci(this);\" name=\"dati\" method=\"POST\">");

		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati elaborazione</h4>");
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"aggregazione\">aggregazione in giorni<input type=\"text\" id=\"aggregazione\" name=\"aggregazione\" class=\"form-control\" ></div>");
		sb.append("</div>");
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"finestra\">finestra in giorni:<input type=\"text\" id=\"finestra\" name=\"finestra\" class=\"form-control\" ></div>");
		sb.append("</div>");
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"data\"> data  <input type=\"text\" id=\"data\" name=\"data\" class=\"form-control\" ></div>");
		sb.append("</div>");
		sb.append("	<input type=\"hidden\" name=\"operazione\" value=\"eleborazioniDeltaT\">");
		/*sb.append("media <input type=\"checkbox\" name=\"temperature\" value=\"avg\"  >");
		sb.append("min <input type=\"checkbox\" name=\"temperature\" value=\"min\"  >");
		sb.append("max <input type=\"checkbox\" name=\"temperature\" value=\"max\"  ><br>");*/
		sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">" );		
		sb.append(	"</form>");
		sb.append("</div>");
		return sb.toString();
		
		
		
	}
	public static String precipitazioniDaProcesso(ArrayList<StazioneMetereologica> s){
		StringBuilder sb = new StringBuilder();
		Calendar data = new GregorianCalendar();
		data.add(Calendar.MONTH, 1);
		sb.append(HTMLScript.scriptData("data"));
		sb.append(HTMLScript.controlloCampi());
		sb.append("<form action=\"/DBAlps/Servlet\" onSubmit=\"return verificaInserisci(this);\" name=\"dati\" method=\"POST\">");
		sb.append("<table class=\"table\"> <tr> <th>nome</th>  </tr>");
		for(StazioneMetereologica stazione: s){
			sb.append(" <tr> <td>"+stazione.getNome()+" </td>     </tr>");
		}
		sb.append("</table>");
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati elaborazione</h4>");
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"aggregazione\">aggregazione in giorni<input type=\"text\" id=\"aggregazione\" name=\"aggregazione\" class=\"form-control\" ></div>");
		sb.append("</div>");
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"finestra\">finestra in giorni:<input type=\"text\" id=\"finestra\" name=\"finestra\" class=\"form-control\" ></div>");
		sb.append("</div>");
		sb.append("<div class=\"row\">");
		sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"data\"> data  <input type=\"text\" id=\"data\" name=\"data\" class=\"form-control\" ></div>");
		sb.append("</div>");
		sb.append("<input type=\"hidden\" name=\"operazione\" value=\"elaborazioniPrecipitazioni\">");
		sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">" +
				"		</form>");
		sb.append("</div>");
		return sb.toString();
	}
	
	
	public static String scegliStazioniQuery(String op) throws SQLException{
		ArrayList<StazioneMetereologica>  ap = ControllerDatabase.prendiTutteStazioniMetereologiche(); 
		StringBuilder sb = new StringBuilder();
			sb.append("<table> <tr> <th>Nome</th>  <th>comune</th> <th> seleziona</th> </tr>");
		for(StazioneMetereologica s: ap){
			sb.append("<form action=\"Servlet\" name=\"dati\" method=\"POST\">");
			if(op.equals("datiTemperaturaEPrecipitazioneAnno"))
			sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td> <td> <input type=\"radio\" name=\"id\" value=\""+s.getIdStazioneMetereologica()+"\" > </td> </tr>");
			else 			sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td> <td> <input type=\"checkbox\" name=\"id\" value=\""+s.getIdStazioneMetereologica()+"\" > </td> </tr>");

		}
		sb.append("<script src=\"js/jquery-1.10.2.js\"></script>");
			sb.append("<script src=\"js/jquery.filtertable.js\"></script>");
				sb.append("<script>");


					sb.append("$(document).ready(function() {");
					sb.append("$('table').filterTable();");
					sb.append("});");
					sb.append("</script>");
		sb.append("</table>");
		sb.append("			<input type=\"hidden\" name=\"operazione\" value=\""+op+"\">");
		sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">" +
				"		</form>");
		return sb.toString();
	}
	

	public static String listaQueryStazione(){
		StringBuilder sb = new StringBuilder();

		sb.append("	<div class=\"row\">");
		sb.append("	<div class=\"col-xs-6 col-md-11 col-md-push-1\"><h3>Query sulla stazione metereologica</h3></div>");
		sb.append("	</div>");
		sb.append("	<br>");
		sb.append("	<div class=\"list-group\">");
		sb.append("	<div class=\"row\">");
		sb.append("  	<div class=\"col-xs-6 col-md-4  col-md-push-1\"><a href=\"Servlet?operazione=mostraTutteStazioniMetereologiche\" class=\"list-group-item\"> mostra tutte le stazioni</a></div>");
		sb.append("  	</div>	");
		sb.append("  	<div class=\"row\">");
		sb.append("  	<div class=\"col-xs-6 col-md-4  col-md-push-1\"><a href=\"Servlet?operazione=mostraStazioniMaps\" class=\"list-group-item\"> mostra stazioni su mappa</a></div>");
		sb.append("  	</div>");
		sb.append("  	<div class=\"row\">");
		sb.append("  	<div class=\"col-xs-6 col-md-4  col-md-push-1\"><a href=\"Servlet?operazione=formRicercaStazione\" class=\"list-group-item\"> ricerca stazione</a></div>");
		sb.append("  	</div>");
		sb.append("  	</div>");
		return sb.toString();
	}
	public static String caricaDatiMetereologici() throws SQLException {
		ArrayList<StazioneMetereologica>  ap = ControllerDatabase.prendiTutteStazioniMetereologiche(); 
		StringBuilder sb = new StringBuilder();
		sb.append(HTMLScript.scriptFilter()); 
		sb.append("<div class=\"table-responsive\"><table class=\"table\"> ");
		sb.append("<tr> <th>Nome</th>  <th>comune</th> <th> dettagli</th> ");
		sb.append("<th> carica dati climatici</th>");
		sb.append("</tr>");
		for(StazioneMetereologica s: ap){
			sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td>  ");
			sb.append("<td><a href=\"Servlet?operazione=mostraStazioneMetereologica&idStazioneMetereologica="+s.getIdStazioneMetereologica()+"\">dettagli</a></td>");
			sb.append("<td><a href=\"Servlet?operazione=caricaDatiClimatici&idStazioneMetereologica="+s.getIdStazioneMetereologica()+"\"> Carica</a></td>");
		}
			sb.append("</tr>");
		
		sb.append("</table></div>");
		return sb.toString();
	}
	public static String scegliStazioneModifica() throws SQLException {
		ArrayList<StazioneMetereologica>  ap = ControllerDatabase.prendiTutteStazioniMetereologiche(); 
		StringBuilder sb = new StringBuilder();
		sb.append(HTMLScript.scriptFilter()); 
		sb.append("<div class=\"table-responsive\"><table class=\"table\"> ");
		sb.append("<tr> <th>Nome</th>  <th>comune</th> <th> dettagli</th> ");
		sb.append("<th>Modifica</th>");
		sb.append("</tr>");
		for(StazioneMetereologica s: ap){
			sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td>  ");
			sb.append("<td><a href=\"Servlet?operazione=mostraStazioneMetereologica&idStazioneMetereologica="+s.getIdStazioneMetereologica()+"\">dettagli</a></td>");
			sb.append("<td><a href=\"Servlet?operazione=modificaStazione&idStazioneMetereologica="+s.getIdStazioneMetereologica()+"\">Modifica</a></td>");
		}
			sb.append("</tr>");
		
		sb.append("</table></div>");
		return sb.toString();
	}

	public static String scegliStazioneAllegati() throws SQLException {
		ArrayList<StazioneMetereologica>  ap = ControllerDatabase.prendiTutteStazioniMetereologiche(); 
		StringBuilder sb = new StringBuilder();
		sb.append(HTMLScript.scriptFilter()); 
		sb.append("<h3>Scegli una stazione a cui allegare un file</h3>");
		sb.append("<div class=\"table-responsive\"><table class=\"table\"> ");
		sb.append("<tr> <th>Nome</th>  <th>comune</th> <th> dettagli</th> ");
		sb.append("<th>Allega</th>");
		sb.append("</tr>");
		for(StazioneMetereologica s: ap){
			sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td>  ");
			sb.append("<td><a href=\"Servlet?operazione=mostraStazioneMetereologica&idStazioneMetereologica="+s.getIdStazioneMetereologica()+"\">dettagli</a></td>");
			sb.append("<td><a href=\"Servlet?operazione=formAllegatoStazione&idstazione="+s.getIdStazioneMetereologica()+"\">Allega</a></td>");
		}
			sb.append("</tr>");
		
		sb.append("</table></div>");
		return sb.toString();
	}
	public static String formAllegatoStazione(int idstazione,Utente part,ControllerLingua locale) throws SQLException{
		StringBuilder sb = new StringBuilder();
		
		StazioneMetereologica sm = ControllerDatabase.prendiStazioneMetereologica(idstazione, locale);
		sb.append("<form class=\"form-horizontal\" action=\"Servlet\" name=\"dati\" method=\"POST\" enctype=\"multipart/form-data\" >");
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Allegu un file alla stazione "+sm.getNome()+"</h4>");
		sb.append("<br>");
		sb.append("<div class=\"form-group\">");
		sb.append("<label for=\"autore\" class=\"col-sm-2 control-label\">Autore</label>");
		sb.append("<div class=\"col-sm-10\">");
		sb.append("<input type=\"text\" name=\"autore\" id=\"autore\" class=\"form-control\">");
		sb.append("</div>");
		sb.append("</div>");
		
		sb.append("<div class=\"form-group\">");
		sb.append("<label for=\"anno\" class=\"col-sm-2 control-label\">Anno</label>");
		sb.append("<div class=\"col-sm-10\">");
		sb.append("<input type=\"text\" name=\"anno\" id=\"anno\" class=\"form-control\">");
		sb.append("</div>");
		sb.append("</div>");
		
		sb.append("<div class=\"form-group\">");
		sb.append("<label for=\"titolo\" class=\"col-sm-2 control-label\">Titolo</label>");
		sb.append("<div class=\"col-sm-10\">");
		sb.append("<input type=\"text\" name=\"titolo\" id=\"titolo\" class=\"form-control\">");
		sb.append("</div>");
		sb.append("</div>");
		
		sb.append("<div class=\"form-group\">");
		sb.append("<label for=\"in\" class=\"col-sm-2 control-label\">In:</label>");
		sb.append("<div class=\"col-sm-10\">");
		sb.append("<input type=\"text\" name=\"in\" id=\"in\" class=\"form-control\">");
		sb.append("</div>");
		sb.append("</div>");
		
		sb.append("<div class=\"form-group\">");
		sb.append("<label for=\"fonte\" class=\"col-sm-2 control-label\">Fonte</label>");
		sb.append("<div class=\"col-sm-10\">");
		sb.append("<input type=\"text\" name=\"fonte\" id=\"fonte\" class=\"form-control\" >");
		sb.append("</div>");
		sb.append("</div>");
		
		sb.append("<div class=\"form-group\">");
		sb.append("<label for=\"urlWeb\" class=\"col-sm-2 control-label\">URL del sito</label>");
		sb.append("<div class=\"col-sm-10\">");
		sb.append("<input type=\"text\" name=\"urlWeb\" id=\"urlWeb\" class=\"form-control\" >");
		sb.append("</div>");
		sb.append("</div>");
		
		sb.append("<div class=\"form-group\">");
		sb.append("<label for=\"note\" class=\"col-sm-2 control-label\">Note</label>");
		sb.append("<div class=\"col-sm-10\">");
		sb.append("<input type=\"text\" name=\"note\" id=\"note\" class=\"form-control\" >");
		sb.append("</div>");
		sb.append("</div>");
		
		
		sb.append("<div class=\"form-group\">");
		sb.append("<label for=\"tipo\" class=\"col-sm-2 control-label\">Tipo</label>");
		sb.append("<div class=\"col-sm-10\">");
		sb.append("<select class=\"form-control\" name=\"tipo\" id=\"tipo\">");
		sb.append("<option value=\"document\">Document</option>");
		sb.append("<option value=\"map\">Map </option>");
		sb.append("<option value=\"image\">Image</option>");
		sb.append("<option value=\"link\">Link</option>");
		sb.append("</select>");
		sb.append("</div>");
		sb.append("</div>");
		
		sb.append("<div class=\"form-group\">");
		sb.append("<label for=\"uploadFile\" class=\"col-sm-2 control-label\">Carica il File</label>");
		sb.append("<div class=\"col-sm-10\">");
		sb.append("<input type=\"file\" name=\"uploadFile\" id=\"uploadFile\" class=\"form-control\" >");
		sb.append("</div>");
		sb.append("</div>");
		
		sb.append("<input type=\"hidden\" name=\"idstazione\" value=\""+sm.getIdStazioneMetereologica()+"\">");
		sb.append("<input type=\"hidden\" name=\"operazione\" value=\"uploadAllegatoStazione\">");
		sb.append("<div class=\"form-group\">");
		sb.append("<div class=\"col-sm-10\">");
		sb.append(" <button type=\"submit\" class=\"btn btn-default\">Allega</button>");
		sb.append("</div>");
		sb.append("</div>");
		
		sb.append("</div>");
		sb.append("</form>");
		
		
		return sb.toString();
	}
}
>>>>>>> masterTest
