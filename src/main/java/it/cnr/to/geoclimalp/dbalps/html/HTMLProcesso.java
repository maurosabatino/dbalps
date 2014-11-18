package it.cnr.to.geoclimalp.dbalps.html;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;


import it.cnr.to.geoclimalp.dbalps.bean.processo.Processo;
import it.cnr.to.geoclimalp.dbalps.bean.stazione.StazioneMetereologica;
import it.cnr.to.geoclimalp.dbalps.bean.processo.attributiProcesso.*;
import it.cnr.to.geoclimalp.dbalps.bean.Utente.*;
import it.cnr.to.geoclimalp.dbalps.controller.ControllerDatabase;
import it.cnr.to.geoclimalp.dbalps.controller.ControllerJson;
import it.cnr.to.geoclimalp.dbalps.controller.ControllerLingua;

public class HTMLProcesso {

	public static String formInserisciProcesso(String path, ControllerLingua locale,Utente user) throws SQLException {
      String loc;
      if(locale.getLanguage().equals("it")) loc="IT";
              else loc = "ENG";
      StringBuilder sb = new StringBuilder();
     if(user!=null && (user.getRuolo().equals(Role.AMMINISTRATORE)|| user.getRuolo().equals(Role.AVANZATO))){
			sb.append(HTMLScript.scriptData("data"));
			sb.append(HTMLScript.scriptAutocompleteLocAmm(ControllerJson.getJsonLocazioneAmminitrativa(path)));
			sb.append(HTMLScript.scriptAutocompleteProprietaTermiche(ControllerJson.getJsonProprietaTermiche(path, loc), loc));
			sb.append(HTMLScript.scriptAutocompleteStatoFratturazione(
					ControllerJson.getJsonStatoFratturazione(path, loc), loc));
			sb.append(HTMLScript.scriptAutocompleteClasseVolume(ControllerJson
					.getJsonClasseVolume(path)));
			sb.append(HTMLScript.scriptAutcompleteLitologia(
					ControllerJson.getJsonLitologia(path, loc), loc));
			sb.append(HTMLScript.scriptAutocompleteSitoProcesso(
					ControllerJson.getJsonSitoProcesso(path, loc), loc));
			sb.append(HTMLScript.scriptAutocompleteLocIdro(ControllerJson
					.getJsonLocazioneIdrologica(path)));
			sb.append(HTMLScript.dialogMaps());
			
			
              

				sb.append("<form action=\"Servlet\" class=\"insertProcesso\"  method=\"POST\" role=\"form\">");

				sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>"+locale.getWord("titoloProcesso")+"</h4>");

                 sb.append("<div class=\"form-group\" >");
				sb.append("<div class=\"row\">");
     
				sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"nome\">"+locale.getWord("nome")+"</label> <input type=\"text\" name=\"nome\" id=\"nome\" class=\"form-control\" placeholder=\""+locale.getWord("nome")+"\" ></div>");
				sb.append("</div>");
				sb.append("<br><div class=\"row \">");
				sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"anno\">"+locale.getWord("anno")+"</label> <input type=\"text\" id=\"anno\" name=\"anno\"  class=\"form-control\" placeholder=\""+locale.getWord("anno")+"\"></div>");

				sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"mese\">"+locale.getWord("mese")+"</label> <select id=\"mese\" name=\"mese\" class=\"form-control\" placeholder=\""+locale.getWord("mese")+"\">");
				sb.append("<option value=\"vuoto\"> </option>");
				for (int i = 1; i <= 12; i++)
					sb.append("<option value=\"" + i + "\">" + i + "</option>");
				sb.append("</select>");
				sb.append("</div>");
				sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"giorno\">"+locale.getWord("giorno")+"</label> <select id=\"giorno\" name=\"giorno\" class=\"form-control\" placeholder=\""+locale.getWord("giorno")+"\">");
				sb.append("<option value=\"vuoto\"> </option>");
				for (int i = 1; i <= 31; i++)
					sb.append("<option value=\"" + i + "\">" + i + "</option>");
				sb.append("</select>");
				sb.append(" <input type=\"hidden\" id=\"datepicker\" />");
				sb.append("</div>");
				sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"ora\">"+locale.getWord("ora")+"</label> <input type=\"text\" id=\"ora\" name=\"ora\"  class=\"form-control\" placeholder=\""+locale.getWord("ora")+"\"></div> ");
				sb.append("</div>");
               
				sb.append("<br><div class=\"row\">");
				sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"superficie\">"+locale.getWord("superficie")+"</label><input type=\"text\" name=\"superficie\" onkeypress=\"return numberOnly(event)\" id=\"superficie\" class=\"form-control\"placeholder=\""+locale.getWord("superficie")+"\" ></div>");
				sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"larghezza\">"+locale.getWord("larghezza")+"</label><input type=\"text\" name=\"larghezza\" id=\"larghezza\" onkeypress=\"return numberOnly(event)\" class=\"form-control\" placeholder=\""+locale.getWord("larghezza")+"\"></div>");
				sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"altezza\">"+locale.getWord("altezza")+"</label><input type=\"text\" name=\"altezza\" id=\"altezza\" onkeypress=\"return numberOnly(event)\" class=\"form-control\"  placeholder=\""+locale.getWord("altezza")+"\"></div>");
				sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"volumeSpecifico\">"+locale.getWord("volumeSpecifico")+"</label><input type=\"text\" name=volumespecifico id=\"volumeSpecifico\" onkeypress=\"return numberOnly(event)\" class=\"form-control\" placeholder=\""+locale.getWord("volumeSpecifico")+"\" ></div>");
				sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"intervallo\">"+locale.getWord("intervallo")+"</label><input type=\"text\" id=\"intervallo\" name=intervallo class=\"form-control\" placeholder=\""+locale.getWord("intervallo")+"\"></div>");
				sb.append("<input type=\"hidden\" id=\"idclasseVolume\" name=\"idclasseVolume\"  />");
				sb.append("</div>");
              
				sb.append("<br><div class =\"row\">");
				sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\""+locale.getWord("caratteristicaSito")+"\">"+locale.getWord("sito")+"</label><input type=\"text\" id=\""+locale.getWord("caratteristicaSito")+"\" name=\""+locale.getWord("caratteristicaSito")+"\" class=\"form-control\"placeholder=\""+locale.getWord("sito")+"\"/></div>");
				sb.append("<input type=\"hidden\" id=\"idsito\" name=\"idsito\"/>");
				sb.append("</div>");
				sb.append("<br><div class=\"wrapper\">");
				sb.append("<div class=\"content-main\"><label for=\"descrizione\">"+locale.getWord("descrizione")+"</label></div>");
				sb.append("<div class=\"content-secondary\"><textarea rows=\"5\" cols=\"100\" name=\"descrizione\" id=\"descrizione\" class=\"textarea\" placeholder=\""+locale.getWord("descrizione")+"\"></textarea></div>");
				sb.append("</div>");
				sb.append("<br><h4>"+locale.getWord("tipologiaProcesso")+"</h4>");
				sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\">");

				sb.append("<p>");
				for (TipologiaProcesso tp : ControllerDatabase.prendiTipologiaProcesso()){
                  String tipoProc;
                  if(locale.getLanguage().equals("it"))
                    tipoProc= tp.getNome_IT();
                  else tipoProc= tp.getNome_ENG();
					sb.append("<input type=\"checkbox\" name=\""+locale.getWord("tipoProcesso")+"\" value=\""+ tipoProc + "\"/> " + tipoProc + " ");
                }
				sb.append("</p>");

				sb.append("</div> </div>");
                sb.append("</div>");

				sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sull'ubicazione</h4>");
				sb.append("<div class=\"row\">");
                sb.append("<div class=\"form-group\" >");
				sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"sottobacino\">"+locale.getWord("sottobacino")+"</label><input type=\"text\" id=\"sottobacino\" name=\"sottobacino\" class=\"form-control\" placeholder=\""+locale.getWord("sottobacino")+"\"/></div>");
				sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"bacino\">"+locale.getWord("bacino")+"</label><input readonly=\"readonly\" type=\"text\"id=\"bacino\" name=\"bacino\" class=\"form-control\" placeholder=\""+locale.getWord("bacino")+"\"/></div> ");
				sb.append("<input type=\"hidden\" id=\"idSottobacino\" name=\"idSottobacino\"/>");
				sb.append("</div>");
				sb.append("<br><div class=\"row\">");
				sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"comune\">"+locale.getWord("comune")+"</label><input type=\"text\" id=\"comune\" name=\"comune\" class=\"form-control\"placeholder=\""+locale.getWord("comune")+"\"/></div>");
				sb.append("<input  type=\"hidden\" id=\"idcomune\" name=\"idcomune\" />");
				sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"provincia\">"+locale.getWord("provincia")+"</label><input readonly=\"readonly\" type=\"text\" id=\"provincia\" name=\"provincia\" class=\"form-control\"placeholder=\""+locale.getWord("provincia")+"\"/></div>");
				sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"regione\">"+locale.getWord("regione")+"</label><input readonly=\"readonly\" type=\"text\" id=\"regione\" name=\"regione\" class=\"form-control\" placeholder=\""+locale.getWord("regione")+"\" /> </div>");
				sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"nazione\">"+locale.getWord("nazione")+"</label><input readonly=\"readonly\" type=\"text\" id=\"nazione\" name=\"nazione\"class=\"form-control\" placeholder=\""+locale.getWord("nazione")+"\" /></div>");
				sb.append("</div>");
               
      			sb.append("<div id=\"controls\">");
				sb.append("<br><div class=\"row\">");
				sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"latitudine\">"+locale.getWord("latitudine")+"</label><input type=\"text\" id=\"latitudine\"name=\"latitudine\" class=\"form-control\" placeholder=\""+locale.getWord("latitudine")+"\"/></div>");
				sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"longitudine\">"+locale.getWord("longitudine")+"</label><input type=\"text\" id=\"longitudine\"name=\"longitudine\" class=\"form-control\" placeholder=\""+locale.getWord("longitudine")+"\"/></div>");
				sb.append("<div class=\"col-xs-6 col-md-4\"><button type=\"button\" class=\"round-button\"name=\"showMap\" id=\"showMap\"><img src=\"img/map-marker-th.png\"/></button></div>");
				sb.append("</div>");
				sb.append("</div>");
                sb.append("</div>");
				sb.append(" <div id=\"map_container\" title=\"Location Map\">");
				sb.append("<div id=\"map_canvas\" style=\"width:100%;height:80%;\"></div>");
				sb.append("<div class=\"row\">");
				sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"lati\">"+locale.getWord("latitudine")+"</label><input type=\"text\" id =\"lati\"name=\"lati\" class=\"form-control\" placeholder=\""+locale.getWord("latitudine")+"\"></div>");
				sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"long\">"+locale.getWord("longitudine")+"</label><input type=\"text\" id=\"long\" name=\"long\" class=\"form-control\"  placeholder=\""+locale.getWord("longitudine")+"\"></div>");
				sb.append("</div>");
				sb.append("</div>");
				sb.append("<br><div class=\"row\">");
				sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"quota\">"+locale.getWord("quota")+"</label> <input type=\"text\" id=\"quota\"name=\"quota\" class=\"form-control\" placeholder=\""+locale.getWord("quota")+"\"/></div>");
				sb.append("<div class=\"col-xs-6 col-md-6\">");
                sb.append("<label for=\"esposizione\">"+locale.getWord("esposizione")+"</label> ");
                sb.append("<select class=\"form-control\" name=\"esposizione\" id=\"esposizione\">");
                sb.append("<option value=\"\"></option>");
                sb.append("<option value=\""+locale.getWord("n")+"\">"+locale.getWord("n")+"</option>");
                sb.append("<option value=\""+locale.getWord("ne")+"\">"+locale.getWord("ne")+"</option>");
                sb.append("<option value=\""+locale.getWord("e")+"\">"+locale.getWord("e")+"</option>");
                sb.append("<option value=\""+locale.getWord("se")+"\">"+locale.getWord("se")+"</option>");
                sb.append("<option value=\""+locale.getWord("s")+"\">"+locale.getWord("s")+"</option>");
                sb.append("<option value=\""+locale.getWord("so")+"\">"+locale.getWord("so")+"</option>");
                sb.append("<option value=\""+locale.getWord("o")+"\">"+locale.getWord("o")+"</option>");
                sb.append("<option value=\""+locale.getWord("no")+"\">"+locale.getWord("no")+"</option>");
                sb.append("</select>");
				sb.append("</div>");
                sb.append("</div>");
                sb.append("<br><div class=\"row\">");
                sb.append("<div class=\"col-xs-6 col-md-6\">");
                sb.append("<label for=\"attendibilita\">"+locale.getWord("attendibilita")+"</label> ");
                sb.append("<select class=\"form-control\" name=\"attendibilita\" id=\"attendibilita\">");
                sb.append("<option value=\"\"></option>");
                sb.append("<option value=\""+locale.getWord("puntuale")+"\">"+locale.getWord("puntuale")+"</option>");
                sb.append("<option value=\""+locale.getWord("areale")+"\">"+locale.getWord("areale")+"</option>");
                sb.append("<option value=\""+locale.getWord("indicativa")+"\">"+locale.getWord("indicativa")+"</option>");
                sb.append("</select>");
                sb.append("</div>");
                sb.append("</div>");
				sb.append("</div> </div>");

				sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\">");
				sb.append("<h4>"+locale.getWord("danni")+"</h4>");
				sb.append("<p>");
				for (Danni d : ControllerDatabase.prendiDanni()) {
                  String tipoDanno;
                  if(locale.getLanguage().equals("it"))
                    tipoDanno= d.getTipo_IT();
                  else tipoDanno= d.getTipo_ENG();
                  sb.append("<input type=\"checkbox\" name=\""+locale.getWord("tipoDanno")+"\" value=\""+ tipoDanno + "\"/> " + tipoDanno+ " ");
				}
				sb.append("</p>");
				sb.append("<p>");
				sb.append("<h4>Effetti Morfologici</h4>");
				for (EffettiMorfologici em : ControllerDatabase
						.prendiEffettiMOrfologici()) {
                  String effMorfologici;
                  if(locale.getLanguage().equals("it"))
                    effMorfologici= em.getTipo_IT();
                  else effMorfologici= em.getTipo_ENG();
					sb.append("<input type=\"checkbox\" name=\""+locale.getWord("effMorfologici")+"\" value=\""
							+ effMorfologici + "\" /> " +effMorfologici+ " ");
				}
				sb.append("</p>");
				sb.append("<br><div class=\"row\">");
				sb.append("<div class=\"col-xs-6 col-md-6\">");
					sb.append("<label for=\"gradoDanno\">"+locale.getWord("gradoDanno")+"</label> ");
			 sb.append("<select class=\"form-control\" name=\"gradoDanno\" id=\"gradoDanno\">");
                sb.append("<option value=\"\"></option>");
                 sb.append("<option value=\""+locale.getWord("danneggiato")+"\">"+locale.getWord("danneggiato")+"</option>");
                 sb.append("<option value=\""+locale.getWord("distrutto")+"\">"+locale.getWord("distrutto")+"</option>");
                 sb.append("<option value=\""+locale.getWord("minacciato")+"\">"+locale.getWord("minacciato")+"</option>");
                sb.append("</select>");
	                sb.append("</div>");
	                sb.append("</div>");
				sb.append("</div> </div>");

				sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>"+locale.getWord("litologiaTitolo")+"</h4>");
				sb.append("<label for=\""+locale.getWord("lito")+"\">"+locale.getWord("litologia")+"</label> <input type=\"text\" id=\""+locale.getWord("lito")+"\" name=\""+locale.getWord("lito")+"\" class=\"form-control\" placeholder=\""+locale.getWord("litologia")+"\"/></p>");
				sb.append("<input type=\"hidden\" id=\"idLitologia\" name=\"idLitologia\" />");
				sb.append("<br><div class=\"row\">");
				sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\""+locale.getWord("proprietaTermiche")+"\">"+locale.getWord("propTermiche")+"</label><input type=\"text\" id=\""+locale.getWord("proprietaTermiche")+"\" name=\""+locale.getWord("proprietaTermiche")+"\" class=\"form-control\" placeholder=\""+locale.getWord("propTermiche")+"\"/></div>");
				sb.append("<input type=\"hidden\" id=\"idProprietaTermiche\" name=\"idProprietaTermiche\" />");
				sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\""+locale.getWord("statoFrattura")+"\">"+locale.getWord("statoFratturazione")+"</label><input type=\"text\" id=\""+locale.getWord("statoFrattura")+"\" name=\""+locale.getWord("statoFrattura")+"\" class=\"form-control\" placeholder=\""+locale.getWord("statoFratturazione")+"\"/></div>");
				sb.append("<input type=\"hidden\" id=\"idStatoFratturazione\" name=\"idStatoFratturazione\" />");
				sb.append("</div>");
				sb.append("</div> </div>");
 
				sb.append("<br><div class=\"wrapper\">");
				sb.append("<div class=\"content-main\"><label for=\"note\">"+locale.getWord("note")+"</label></div>");
				sb.append("<div class=\"content-secondary\"><textarea rows=\"5\" cols=\"100\" name=\"note\" id=\"note\" class=\"textarea\" placeholder=\"Note\"></textarea></div>");
				sb.append("</div>");

				sb.append("<input type=\"hidden\" name=\"operazione\" value=\"inserisciProcesso\">");
				sb.append("<button type=\"submit\" class=\"btn btn-default\">Inserisci il processo</button>");
				sb.append("</div> </div>");
				sb.append("</form>");
     }else{
       sb.append("<h3>Spiacente non hai i privilegi sufficienti per inserire un processo</h3>");
     }
		
		return sb.toString();
	}

	public static String mostraTuttiProcessi() throws SQLException {
		ArrayList<Processo> ap = ControllerDatabase.prendiTuttiProcessi();
		StringBuilder sb = new StringBuilder();

		/* script per google maps */// centrerei la mappa al centro delle alpi

		sb.append(HTMLScript.scriptFilter());   
		sb.append("<div class=\"table-responsive\"><table class=\"table\"> <tr> <th>Nome</th> <th>data</th> <th>comune</th> <th>nazione</th> <th> Report </th> <th> Modifica</th><th>Elimina</th></tr>");
		for (Processo p : ap) {
			sb.append("<tr> <td>" + p.getNome() + " </td> ");
			sb.append("	<td>"	+ dataFormattata(p.getFormatoData(), p.getData()) + "</td>" );
			sb.append("	<td>"+ p.getUbicazione().getLocAmm().getComune() + "</td>");
			sb.append("<td>" + p.getUbicazione().getLocAmm().getNazione() + "</td> ");
			sb.append("<td><a href=\"Servlet?operazione=mostraProcesso&idProcesso="
					+ p.getIdProcesso() + "\">dettagli</a></td>");
			sb.append("<td><a href=\"Servlet?operazione=mostraModificaProcesso&idProcesso="
					+ p.getIdProcesso() + "\">modifica</a> </td>");
			sb.append("<td><a href=\"Servlet?operazione=eliminaProcesso&idProcesso="
					+ p.getIdProcesso() + "\">Elimina</a> </td>");
			sb.append("</tr>");
		}
		sb.append("</table></div>");
		return sb.toString();
	}
	
    public static String mostraTuttiProcessiModifica(Utente user) throws SQLException{
		ArrayList<Processo> ap = ControllerDatabase.prendiTuttiProcessi();
		StringBuilder sb = new StringBuilder();

		/* script per google maps */// centrerei la mappa al centro delle alpi
		
		sb.append(HTMLScript.scriptFilter());   
		sb.append("<h3>Scegli un Processo da modificare</h3>");
		sb.append("<div class=\"table-responsive\"><table class=\"table\"> <tr> <th>Nome</th> <th>data</th> <th>comune</th> <th>nazione</th> <th> Report </th> <th> Modifica</th></tr>");
		for (Processo p : ap) {
			sb.append("<tr> <td>" + p.getNome() + " </td> ");
			sb.append("	<td>"+ dataFormattata(p.getFormatoData(), p.getData())+"</td>" );
			sb.append("	<td>"+ p.getUbicazione().getLocAmm().getComune() + "</td>");
			sb.append("<td>" + p.getUbicazione().getLocAmm().getNazione() + "</td> ");
			sb.append("<td><a href=\"Servlet?operazione=mostraProcesso&idProcesso="+ p.getIdProcesso() + "\">dettagli</a></td>");
			sb.append("<td><a href=\"Servlet?operazione=mostraModificaProcesso&idProcesso="+ p.getIdProcesso() + "\">modifica</a> </td>");
			sb.append("</tr>");
		}
		
		sb.append("</table></div>");
	
		
		return sb.toString();
	}

	public static String mostraProcesso(int idProcesso,ControllerLingua locale) throws SQLException {
      Processo p = ControllerDatabase.prendiProcesso(idProcesso);
      StringBuilder sb = new StringBuilder();
      
      sb.append(" <div class=\"container-fluid\">");
      sb.append("  <div class=\"row\">");
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-9 col-md-push-3\"><h1>"+p.getNome()+"</h1></div>");
      sb.append("      <div class=\"col-md-3 col-md-pull-9\"><h1>"+locale.getWord("processo")+"</h1> </div>");
      sb.append("    </div>");
      sb.append("    <div class=\"row\">");
       
       
      sb.append("      <div class=\"col-md-9 col-md-push-3\"><h1>"+dataFormattata(p.getFormatoData(), p.getData())+"</h1></div>");
      sb.append("      <div class=\"col-md-3 col-md-pull-9\"><h1>"+locale.getWord("data")+"</h1> </div>");
      sb.append("    </div>");
      sb.append("  </div>");
      sb.append("    <div class=\"row\">");
      sb.append("  <h2>"+locale.getWord("descrizione")+"</h2>");
      sb.append("  <p>"+p.getAttributiProcesso().getDescrizione()+"</p>");
      sb.append("</div>");
      sb.append("  </div>");
      sb.append("<div class=\"container-fluid\">");
      sb.append(" ");
      sb.append("  <div class=\"col-md-5\">");
      sb.append("  <div class=\"row\">");
      sb.append("    <h2>"+locale.getWord("titoloUbicazione")+"</h2>");
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-9 col-md-push-4\"><p>"+p.getUbicazione().getLocAmm().getComune()+"</p> </div>");
      sb.append("      <div class=\"col-md-3 col-md-pull-9\"><strong>"+locale.getWord("comune")+"</strong> </div>");
      sb.append("    </div>");
       
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-9 col-md-push-4\"><p>"+p.getUbicazione().getLocAmm().getProvincia()+"</p> </div>");
      sb.append("      <div class=\"col-md-3 col-md-pull-9\"><strong>"+locale.getWord("provincia")+"</strong> </div>");
      sb.append("    </div>");
     
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-9 col-md-push-4\"><p>"+p.getUbicazione().getLocAmm().getRegione()+"</p> </div>");
      sb.append("      <div class=\"col-md-3 col-md-pull-9\"><strong>"+locale.getWord("regione")+"</strong> </div>");
      sb.append("    </div>");
      
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-9 col-md-push-4\"><p>"+p.getUbicazione().getLocAmm().getNazione()+"</p> </div>");
      sb.append("      <div class=\"col-md-3 col-md-pull-9\"><strong>"+locale.getWord("nazione")+"</strong> </div>");
      sb.append("    </div>");
     
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-6 col-md-push-4\"><p>"+p.getUbicazione().getLocIdro().getSottobacino()+"</p> </div>");
      sb.append("      <div class=\"col-md-5 col-md-pull-6\"><strong>"+locale.getWord("sottobacino")+"</strong> </div>");
      sb.append("    </div>");
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-6 col-md-push-4\"><p>"+p.getUbicazione().getLocIdro().getBacino()+"</p> </div>");
      sb.append("      <div class=\"col-md-5 col-md-pull-6\"><strong>"+locale.getWord("bacino")+"</strong> </div>");
      sb.append("    </div>");
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-6 col-md-push-4\"><p>"+p.getUbicazione().getQuota()+"</div>");
      sb.append("      <div class=\"col-md-5 col-md-pull-6\"><strong>"+locale.getWord("quota")+"</strong> </div>");
      sb.append("    </div>");
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-6 col-md-push-4\"><p>"+p.getUbicazione().getEsposizione()+"</p> </div>");
      sb.append("      <div class=\"col-md-5 col-md-pull-6\"><strong>"+locale.getWord("esposizione")+"</strong> </div>");
      sb.append("    </div>");
     
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-6 col-md-push-4\"><p>"+p.getUbicazione().getCoordinate().getX()+"</p> </div>");
      sb.append("      <div class=\"col-md-5 col-md-pull-6\"><strong>"+locale.getWord("latitudine")+"</strong> </div>");
      sb.append("    </div>");
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-6 col-md-push-4\"><p>"+p.getUbicazione().getCoordinate().getY()+"</p> </div>");
      sb.append("      <div class=\"col-md-5 col-md-pull-6\"><strong>"+locale.getWord("longitudine")+"</strong> </div>");
      sb.append("    </div>");
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-6 col-md-push-4\"><p>"+p.getUbicazione().getAttendibilita()+"</p> </div>");
      sb.append("      <div class=\"col-md-4 col-md-pull-6\"><strong>"+locale.getWord("attendibilita")+"</strong> </div>");
      sb.append("    </div>    ");
      sb.append("  </div>");
      sb.append("    ");
      sb.append("  <div class=\"row\">");
     
      sb.append("    <h2>"+locale.getWord("dettagliProcesso")+"</h2>");
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-9 col-md-push-4\"><p>"+p.getAttributiProcesso().getAltezza()+" m</p> </div>");
      sb.append("      <div class=\"col-md-3 col-md-pull-9\"><strong>"+locale.getWord("altezza")+"</strong> </div>");
      sb.append("    </div>");
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-9 col-md-push-4\"><p>"+p.getAttributiProcesso().getLarghezza()+" m</p> </div>");
      sb.append("      <div class=\"col-md-3 col-md-pull-9\"><strong>"+locale.getWord("larghezza")+"</strong> </div>");
      sb.append("    </div>");
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-9 col-md-push-4\"><p>"+p.getAttributiProcesso().getSuperficie()+" m<sup>2</sup></p> </div>");
      sb.append("      <div class=\"col-md-3 col-md-pull-9\"><strong>"+locale.getWord("superficie")+"</strong> </div>");
      sb.append("    </div>");
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-9 col-md-push-4\"><p>"+p.getAttributiProcesso().getVolume_specifico()+" m<sup>3</sup></p> </div>");
      sb.append("      <div class=\"col-md-3 col-md-pull-9\"><strong>"+locale.getWord("volumeSpecifico")+"</strong> </div>");
      sb.append("    </div>");
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-6 col-md-push-4\"><p>"+p.getAttributiProcesso().getClasseVolume().getIntervallo()+"</p> </div>");
      sb.append("      <div class=\"col-md-5 col-md-pull-6\"><strong>"+locale.getWord("intervallo")+"</strong> </div>");
      sb.append("    </div>");
      sb.append("  </div>");
      sb.append("  <div class=\"row\">");
      sb.append("    <div class=\"row\">");

      ArrayList<String> tipo = new ArrayList<>();
      for(TipologiaProcesso s :p.getAttributiProcesso().getTipologiaProcesso()){
          if(locale.getLanguage().equals("en")) tipo.add(s.getNome_ENG());
          else tipo.add(s.getNome_IT());
      }
      
      String sito;
      if(locale.getLanguage().equals("it")){
        sito = p.getAttributiProcesso().getSitoProcesso().getCaratteristicaSito_IT();
        System.out.println("sito italiano: "+sito);
      }
      else{
        sito = p.getAttributiProcesso().getSitoProcesso().getCaratteristicaSito_ENG();
        System.out.println("sito inglese: "+sito);
      }
      
      
      sb.append("      <div class=\"col-md-9 col-md-push-4\"><p>"+tipo.toString()+"</p> </div>");
      sb.append("      <div class=\"col-md-3 col-md-pull-9\"><strong>"+locale.getWord("tipologiaProcesso")+"</strong> </div>");
      sb.append("    </div>");
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-9 col-md-push-4\"><p>"+sito+"</p> </div>");
      sb.append("      <div class=\"col-md-3 col-md-pull-9\"><strong>"+locale.getWord("sito")+"</strong> </div>");
      sb.append("    </div>");
      sb.append("  </div>");
      sb.append("   ");
      sb.append("  <div class=\"row\">");
      String lito;
      if(locale.getLanguage().equals("it")){
        lito = p.getAttributiProcesso().getLitologia().getNomeLitologia_IT();
 
      }
      else{
        lito = p.getAttributiProcesso().getLitologia().getNomeLitologia_IT();
     
      }
      sb.append("    <h2>"+locale.getWord("litologia")+"</h2>");
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-9 col-md-push-4\"><p>"+lito+"</p></div>");
      sb.append("      <div class=\"col-md-3 col-md-pull-9\"><strong>"+locale.getWord("litologia")+"</strong> </div>");
      sb.append("    </div>");
      
      String propTermiche;
      if(locale.getLanguage().equals("it")){
    	  propTermiche = p.getAttributiProcesso().getProprietaTermiche().getProprieta_termiche_IT();
       
      }
      else{
    	  propTermiche = p.getAttributiProcesso().getProprietaTermiche().getProprieta_termiche_ENG();
      }
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-6 col-md-push-4\"><p>"+propTermiche+"</p> </div>");
      sb.append("      <div class=\"col-md-5 col-md-pull-6\"><strong>"+locale.getWord("propTermiche")+"</strong> </div>");
      sb.append("    </div>");
      String statoFratturazione;
      if(locale.getLanguage().equals("it")){
    	  statoFratturazione = p.getAttributiProcesso().getStatoFratturazione().getStato_fratturazione_IT();
       
      }
      else{
    	  statoFratturazione = p.getAttributiProcesso().getStatoFratturazione().getStato_fratturazione_ENG();
      }
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-6 col-md-push-4\"><p> "+statoFratturazione+"</p> </div>");
      sb.append("      <div class=\"col-md-5 col-md-pull-6\"><strong>"+locale.getWord("statoFratturazione")+"</strong> </div>");
      sb.append("    </div>");
      sb.append("  </div>");
      
      ArrayList<String> danno = new ArrayList<>();
      for(Danni d : p.getAttributiProcesso().getDanni()){
          if(locale.getLanguage().equals("en")) danno.add(d.getTipo_ENG());
          else{
        	  danno.add(d.getTipo_IT());
          }
      }
      sb.append("  <div class=\"row\">");
      sb.append("    <h2>"+locale.getWord("danni")+"</h2>");
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-9 col-md-push-4\"><p>"+danno.toString()+"</p> </div>");
      sb.append("      <div class=\"col-md-3 col-md-pull-9\"><strong>"+locale.getWord("danni")+"</strong> </div>");
      sb.append("    </div>");
      
      ArrayList<String> effetti = new ArrayList<>();
      for(EffettiMorfologici ef : p.getAttributiProcesso().getEffetti()){
          if(locale.getLanguage().equals("en")) effetti.add(ef.getTipo_ENG());
          else effetti.add(ef.getTipo_IT());
      }
      
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-6 col-md-push-4\"><p>"+effetti.toString()+"</p> </div>");
      sb.append("      <div class=\"col-md-5 col-md-pull-6\"><strong>"+locale.getWord("effettiMorfologici")+"</strong> </div>");
      sb.append("    </div>");
      
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-6 col-md-push-4\"><p>"+p.getAttributiProcesso().getGradoDanno()+"</p> </div>");
      sb.append("      <div class=\"col-md-5 col-md-pull-6\"><strong>"+locale.getWord("gradoDanno")+"</strong> </div>");
      sb.append("    </div>");
      sb.append("  </div>");
      sb.append("  </div>");
      sb.append("  ");
      sb.append("  <div class=\"col-md-7\"> ");
      sb.append("  <div class=\"google-maps\">");
      sb.append("    <div id=\"map-canvas\" class=\"map-canvas\"></div>");
      sb.append("  </div>");
      sb.append("    ");
      sb.append("  </div>");
      sb.append("  ");
      sb.append("  ");
      sb.append("  ");
      sb.append("  ");
      sb.append("</div> <!--fine parte centrale-->");
      /*sb.append("   <div class=\"container-fluid\">");
      sb.append("  ");
      sb.append("    <h2>"+locale.getWord("allegati")+"</h2>");
      sb.append("    <div class=\"col-md-6\"> ");
      sb.append("    <h3>"+locale.getWord("immagini")+"</h3>");
      sb.append("    <ol>");
      sb.append("  ");
      sb.append("     <div class=\"col-md-12\">");
      sb.append("      <div class=\"row\">");
      sb.append("     <div class=\"col-md-6 col-md-push-4\"><p >veduta aerea</p> </div>");
      sb.append("     <div class=\"col-md-5 col-md-pull-6\"><li><strong>"+locale.getWord("nome")+"</strong></li></div>");
      sb.append("    </div>");
      sb.append("     ");
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-6 col-md-push-4\"><p>23-10-2014</p> </div>");
      sb.append("      <div class=\"col-md-5 col-md-pull-6\"><strong>"+locale.getWord("data")+"</strong> </div>");
      sb.append("    </div>");
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-6 col-md-push-4\"><p>IRPI</p> </div>");
      sb.append("      <div class=\"col-md-5 col-md-pull-6\"><strong>"+locale.getWord("fonte")+"</strong> </div>");
      sb.append("    </div>");
      sb.append("     </div>");
      sb.append("     ");
      sb.append("    ");
      sb.append("    </ol>");
      sb.append("    </div>");
      sb.append("    ");
      sb.append("    <div class=\"col-md-7\"> ");
      sb.append("    <h3>PDF</h3>");
      sb.append("    <ol>");
      sb.append("  ");
      sb.append("     <div class=\"col-md-12\">");
      sb.append("      <div class=\"row\">");
      sb.append("     <div class=\"col-md-6 col-md-push-4\"><p >veduta aerea</p> </div>");
      sb.append("     <div class=\"col-md-5 col-md-pull-6\"><li><strong>Nome</strong></li></div>");
      sb.append("    </div>");
      sb.append("     ");
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-6 col-md-push-4\"><p>23-10-2014</p> </div>");
      sb.append("      <div class=\"col-md-5 col-md-pull-6\"><strong>Data</strong> </div>");
      sb.append("    </div>");
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-6 col-md-push-4\"><p>IRPI</p> </div>");
      sb.append("      <div class=\"col-md-5 col-md-pull-6\"><strong>Fonte</strong> </div>");
      sb.append("    </div>");
      sb.append("     </div>");
      sb.append("     ");
      sb.append("    ");
      sb.append("    </ol>");
      sb.append("    </div>");
      sb.append("    <div class=\"col-md-5\"> ");
      sb.append("    <h3>Link</h3>");
      sb.append("    <ol>");
      sb.append("  ");
      sb.append("     <div class=\"col-md-12\">");
      sb.append("      <div class=\"row\">");
      sb.append("        <div class=\"col-md-6 col-md-push-4\"><a href=\"#\"> veduta aerea</a></div>");
      sb.append("     <div class=\"col-md-5 col-md-pull-6\"><li><strong>Nome</strong></li></div>");
      sb.append("    </div>");
      sb.append("     ");
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-6 col-md-push-4\"><p>23-10-2014</p> </div>");
      sb.append("      <div class=\"col-md-5 col-md-pull-6\"><strong>Data</strong> </div>");
      sb.append("    </div>");
      sb.append("    <div class=\"row\">");
      sb.append("      <div class=\"col-md-6 col-md-push-4\"><p>IRPI</p> </div>");
      sb.append("      <div class=\"col-md-5 col-md-pull-6\"><strong>Fonte</strong> </div>");
      sb.append("    </div>");
      sb.append("     </div>");
      sb.append("     ");
      sb.append("    ");
      sb.append("    </ol>");
      sb.append("    </div>");
      */sb.append("    ");
      sb.append("  </div>");
     
    
      return sb.toString();
	}

	public static String dataFormattata(int formatoData, Timestamp data) {
		StringBuilder sb = new StringBuilder();
		String fd = String.valueOf(formatoData);
		Calendar cal = new GregorianCalendar();
		cal.setTime(data);
		if (fd.length()==4) {
			if (fd.charAt(0) == '1') {
				sb.append(cal.get(Calendar.YEAR));
			}
			if (fd.charAt(1) == '1') {
				sb.append("-"
						+ cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ITALY));
			}
			if (fd.charAt(2) == '1') {
				sb.append("-"+ cal.get(Calendar.DAY_OF_MONTH));
			}
			if (fd.charAt(3) == '1') {
				sb.append(" " + cal.get(Calendar.HOUR_OF_DAY));
				sb.append(":" + cal.get(Calendar.MINUTE));
			}
		}
		return sb.toString();
	}
        public static boolean campoData(String formato,int pos){
            boolean ce=false;
            if(formato.charAt(pos)=='1') ce=true;
            return ce;
        }

	public static String formCercaProcessi(String path, String loc) throws SQLException {
		StringBuilder sb = new StringBuilder();
		sb.append(HTMLScript.scriptData("data"));
		sb.append(HTMLScript.scriptAutocompleteLocAmm(ControllerJson.getJsonLocazioneAmminitrativa(path)));
		sb.append(HTMLScript.scriptAutocompleteProprietaTermiche(ControllerJson.getJsonProprietaTermiche(path, loc), loc));
		sb.append(HTMLScript.scriptAutocompleteStatoFratturazione(ControllerJson.getJsonStatoFratturazione(path, loc), loc));
		sb.append(HTMLScript.scriptAutocompleteClasseVolume(ControllerJson.getJsonClasseVolume(path)));
		sb.append(HTMLScript.scriptAutcompleteLitologia(ControllerJson.getJsonLitologia(path, loc), loc));
		sb.append(HTMLScript.scriptAutocompleteSitoProcesso(ControllerJson.getJsonSitoProcesso(path, loc), loc));
		sb.append(HTMLScript.scriptAutocompleteLocIdro(ControllerJson.getJsonLocazioneIdrologica(path)));
		

			sb.append("<form action=\"Servlet\" name=\"dati\" method=\"POST\" role=\"form\">");

			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sul Processo</h4>");

			sb.append("<div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"nome\">Nome Del Processo</label> <input type=\"text\" name=\"nome\" id=\"nome\" class=\"form-control\" placeholder=\"nome\" ></div>");
			sb.append("</div>");
			sb.append("<br><div class=\"row \">");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"anno\">Anno</label> <input type=\"text\" id=\"anno\" name=\"anno\" class=\"form-control\" placeholder=\"Anno\"></div>");

			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"mese\">Mese</label> <select id=\"mese\" name=\"mese\" class=\"form-control\" placeholder=\"Mese\">");
			sb.append("<option value=\"vuoto\"> </option>");
			for (int i = 1; i <= 12; i++)
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
			sb.append("</select>");
			sb.append("</div>");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"giorno\">Giorno</label> <select id=\"giorno\" name=\"giorno\" class=\"form-control\" placeholder=\"Giorno\">");
			sb.append("<option value=\"vuoto\"> </option>");
			for (int i = 1; i <= 31; i++)
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
			sb.append("</select>");
			sb.append("</div>");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"ora\">Ora</label> <input type=\"text\" id=\"ora\" name=\"ora\"  class=\"form-control\" placeholder=\"ora\"></div> ");
			sb.append("</div>");

			sb.append("<br><div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"superficie\">Superficie</label><input type=\"text\" name=\"superficie\" id=\"superficie\" class=\"form-control\"placeholder=\"Superficie\" ></div>");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"larghezza\">Larghezza</label><input type=\"text\" name=\"larghezza\" id=\"larghezza\" class=\"form-control\" placeholder=\"Larghezza\"></div>");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"altezza\">Altezza</label><input type=\"text\" name=\"altezza\" id=\"altezza\" class=\"form-control\" placeholder=\"Altezza\"></div>");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"volumeSpecifico\">Volume Specifico</label><input type=\"number\" name=volumespecifico id=\"volumeSpecifico\"class=\"form-control\" placeholder=\"Volume\" ></div>");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"intervallo\">Classe Volume</label><input type=\"text\" id=\"intervallo\" name=intervallo class=\"form-control\" placeholder=\"Intervallo\"></div>");
			sb.append("<input type=\"hidden\" id=\"idclasseVolume\" name=\"idclasseVolume\"  />");
			sb.append("</div>");
			sb.append("<br><div class =\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"caratteristicaSito_"
					+ loc
					+ "\">Caratteristica del sito</label><input type=\"text\" id=\"caratteristicaSito_"
					+ loc
					+ "\" name=\"caratteristicaSito_"
					+ loc
					+ "\" class=\"form-control\"placeholder=\"Caratteristica del Sito\"/></div>");
			sb.append("<input type=\"hidden\" id=\"idsito\" name=\"idsito\"/>");
			sb.append("</div>");
			sb.append("<br><div class=\"wrapper\">");
			sb.append("<div class=\"content-main\"><label for=\"descrizione\">Descrizione</label></div>");
			sb.append("<div class=\"content-secondary\"><textarea rows=\"5\" cols=\"140\" name=\"descrizione\" id=\"descrizione\" class=\"textarea\" placeholder=\"Descrizione\"></textarea></div>");
			sb.append("</div>");
			sb.append("<br><h4>Tipologia Processo</h4>");
			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\">");

			sb.append("<p>");
			for (TipologiaProcesso tp : ControllerDatabase.prendiTipologiaProcesso())
				sb.append("<input type=\"checkbox\" name=\"tpnome_IT\" value=\""
						+ tp.getNome_IT() + "\"/> " + tp.getNome_IT() + " ");
			sb.append("</p>");

			sb.append("</div> </div>");

			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sull'ubicazione</h4>");
			sb.append("<div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"sottobacino\">Sottobacino</label><input type=\"text\" id=\"sottobacino\" name=\"sottobacino\" class=\"form-control\" placeholder=\"Sottobacino\"/></div>");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"bacino\">Bacino</label><input readonly=\"readonly\" type=\"text\"id=\"bacino\" name=\"bacino\" class=\"form-control\" placeholder=\"Bacino\"/></div> ");
			sb.append("<input type=\"hidden\" id=\"idSottobacino\" name=\"idSottobacino\"/>");
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

			sb.append("</div> </div>");

			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\">");
			sb.append("<h4>Danni</h4>");
			sb.append("<p>");
			for (Danni d : ControllerDatabase.prendiDanni()) {
				sb.append("<input type=\"checkbox\" name=\"dtipo_IT\" value=\""
						+ d.getTipo_IT() + "\"/> " + d.getTipo_IT() + " ");
			}
			sb.append("</p>");
			sb.append("<p>");
			sb.append("<h4>Effetti Morfologici</h4>");
			for (EffettiMorfologici em : ControllerDatabase
					.prendiEffettiMOrfologici()) {
				sb.append("<input type=\"checkbox\" name=\"emtipo_IT\" value=\""
						+ em.getTipo_IT() + "\" /> " + em.getTipo_IT() + " ");
			}
			sb.append("</p>");
			sb.append("</div> </div>");

			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sulla litologia</h4>");
			sb.append("<label for=\"nomeLitologia_" + loc
					+ "\">Litologia</label>:<input type=\"text\" id=\"nomeLitologia_"
					+ loc + "\" name=\"nomeLitologia_" + loc
					+ "\" class=\"form-control\" placeholder=\"Litologia\"/></p>");
			sb.append("<input type=\"hidden\" id=\"idLitologia\" name=\"idLitologia\" />");
			sb.append("<br><div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"proprietaTermiche_"
					+ loc
					+ "\">Propriet� Termiche</label><input type=\"text\" id=\"proprietaTermiche_"
					+ loc
					+ "\" name=\"proprietaTermiche_"
					+ loc
					+ "\" class=\"form-control\" placeholder=\"Propriet� Termiche\"/></div>");
			sb.append("<input type=\"hidden\" id=\"idProprietaTermiche\" name=\"idProprietaTermiche\" />");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"statoFratturazione_"
					+ loc
					+ "\">Stato Fratturazione</label><input type=\"text\" id=\"statoFratturazione_"
					+ loc
					+ "\" name=\"statoFratturazione_"
					+ loc
					+ "\" class=\"form-control\" placeholder=\"Stato Fratturazione\"/></div>");
			sb.append("<input type=\"hidden\" id=\"idStatoFratturazione\" name=\"idStatoFratturazione\" />");
			sb.append("</div>");
			sb.append("</div> </div>");

			sb.append("<br><div class=\"wrapper\">");
			sb.append("<div class=\"content-main\"><label for=\"note\">Note</label></div>");
			sb.append("<div class=\"content-secondary\"><textarea rows=\"5\" cols=\"140\" name=\"note\" id=\"note\" class=\"textarea\" placeholder=\"Note\"></textarea></div>");
			sb.append("</div>");

			sb.append("<input type=\"hidden\" name=\"operazione\" value=\"cercaProcesso\">");
			sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">");
			sb.append("</div> </div>");
			sb.append("</form>");
		
		return sb.toString();
	}

	public static String mostraCercaProcessi(ArrayList<Processo> ap) throws SQLException {
		StringBuilder sb = new StringBuilder();
		sb.append("<table class=\"table\"> <tr> <th>Nome</th> <th>data</th> <th>comune</th> <th> dettagli</th> </tr>");
		for (Processo p : ap) {
			sb.append(" <tr> <td>" + p.getNome() + " </td> <td> " + p.getData()
					+ "</td> <td>" + p.getUbicazione().getLocAmm().getComune() + "</td>"
					+ "<td><a href=\"Servlet?operazione=mostraProcesso&idProcesso="
					+ p.getIdProcesso() + "\">dettagli</a></td></tr>");
		}
		return sb.toString();
	}

	public static String modificaProcesso(Processo p, String path, String loc,Utente user,ControllerLingua locale) throws SQLException {
		StringBuilder sb = new StringBuilder();
		Calendar cal = new GregorianCalendar();
		cal.setTime(p.getData());
		cal.add(Calendar.MONTH, 1);
		
		sb.append(HTMLScript.scriptData("data"));

	      if(locale.getLanguage().equals("it")) loc="IT";
	              else loc = "ENG";

		sb.append(HTMLScript.scriptAutocompleteLocAmm(ControllerJson.getJsonLocazioneAmminitrativa(path)));
		sb.append(HTMLScript.scriptAutocompleteLocIdro(ControllerJson.getJsonLocazioneIdrologica(path)));
		sb.append(HTMLScript.scriptAutocompleteProprietaTermiche(ControllerJson.getJsonProprietaTermiche(path, loc), loc));
		sb.append(HTMLScript.scriptAutocompleteStatoFratturazione(ControllerJson.getJsonStatoFratturazione(path, loc), loc));
		sb.append(HTMLScript.scriptAutocompleteClasseVolume(ControllerJson.getJsonClasseVolume(path)));
		sb.append(HTMLScript.scriptAutcompleteLitologia(ControllerJson.getJsonLitologia(path, loc), loc));
		sb.append(HTMLScript.scriptAutocompleteSitoProcesso(ControllerJson.getJsonSitoProcesso(path, loc), loc));
		sb.append(HTMLScript.dialogMaps());
                
                String temp="";

			sb.append("<form action=\"Servlet\" name=\"dati\" class=\"insertProcesso\" method=\"POST\" role=\"form\">");

			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sul Processo</h4>");
                 sb.append("<div class=\"form-group\" >");
			sb.append("<div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"nome\">Nome Del Processo</label> <input type=\"text\" name=\"nome\" id=\"nome\" class=\"form-control\" value=\""+ p.getNome() + "\" ></div>");
			sb.append("</div>");
			sb.append("<br><div class=\"row \">");
			int a=cal.get(Calendar.YEAR);
                        if(a!=1) temp=Integer.toString(a);
                        else temp="";
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"anno\">Anno</label> <input type=\"text\" id=\"anno\" name=\"anno\" class=\"form-control\" value=\""+temp+"\"></div>");

			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"mese\">Mese</label> <select id=\"mese\" name=\"mese\" class=\"form-control\" >");
			sb.append("<option value=\"vuoto\"> </option>");
			for (int i = 1; i <= 12; i++){
				if(cal.get(Calendar.MONTH)!=i)
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
				else
					sb.append("<option selected=\"selected\" value=\"" + i + "\">" + i + "</option>");
			}
			sb.append("</select>");
			sb.append("</div>");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"giorno\">Giorno</label> <select id=\"giorno\" name=\"giorno\" class=\"form-control\" >");
			sb.append("<option value=\"vuoto\"> </option>");
                        for (int i = 1; i <= 31; i++){
				if(cal.get(Calendar.DAY_OF_MONTH)!=i)
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
				else
					sb.append("<option selected=\"selected\" value=\"" + i + "\">" + i + "</option>");
			}
			sb.append("</select>");
			sb.append(" <input type=\"hidden\" id=\"datepicker\" />");
			sb.append("</div>");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"ora\">Ora</label> <input type=\"text\" id=\"ora\" name=\"ora\"  class=\"form-control\" placeholder=\"ora\"></div> ");
			sb.append("</div>");
			sb.append("<br><div class=\"row\">");
                         if(p.getAttributiProcesso().getSuperficie()!=0.0) temp=Double.toString(p.getAttributiProcesso().getSuperficie());
                        else temp="";
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"superficie\">Superficie</label><input type=\"text\" name=\"superficie\" id=\"superficie\" class=\"form-control\" value=\""+temp+" \"></div>");
			 if(p.getAttributiProcesso().getLarghezza()!=0.0) temp=Double.toString(p.getAttributiProcesso().getLarghezza());
                        else temp="";
                        sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"larghezza\">Larghezza</label><input type=\"text\" name=\"larghezza\" id=\"larghezza\" class=\"form-control\" value=\""+temp+" \"></div>");
			 if(p.getAttributiProcesso().getAltezza()!=0.0) temp=Double.toString(p.getAttributiProcesso().getAltezza());
                        else temp="";
                        sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"altezza\">Altezza</label><input type=\"text\" name=\"altezza\" id=\"altezza\" class=\"form-control\" value=\""+temp+" \"></div>");
			 if(p.getAttributiProcesso().getVolume_specifico()!=0.0) temp=Double.toString(p.getAttributiProcesso().getVolume_specifico());
                        else temp="";
                        sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"volumeSpecifico\">Volume Specifico</label><input type=\"text\" name=volumespecifico id=\"volumeSpecifico\"class=\"form-control\" value=\""+temp+"\" ></div>");
			if(p.getAttributiProcesso().getClasseVolume().getIntervallo()==null) p.getAttributiProcesso().getClasseVolume().setIntervallo("");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"intervallo\">Classe Volume</label><input type=\"text\" id=\"intervallo\" name=intervallo class=\"form-control\" value=\""+p.getAttributiProcesso().getClasseVolume().getIntervallo()+"\"></div>");
			sb.append("<input type=\"hidden\" id=\"idclasseVolume\" name=\"idclasseVolume\" value=\""+p.getAttributiProcesso().getClasseVolume().getIdClasseVolume()+"\" />");
			sb.append("</div>");
			sb.append("<br><div class =\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"caratteristicaSito_"+loc+ "\">Caratteristica del sito</label><input type=\"text\" id=\"caratteristicaSito_"+ loc+ "\" name=\"caratteristicaSito_"+ loc+ "\" class=\"form-control\"value=\""+p.getAttributiProcesso().getSitoProcesso().getCaratteristicaSito_IT()+"\"/></div>");
			sb.append("<input type=\"hidden\" id=\"idsito\" name=\"idsito\" value=\""+p.getAttributiProcesso().getSitoProcesso().getIdSito()+"\"/>");
			sb.append("</div>");
			sb.append("<br><div class=\"wrapper\">");
			sb.append("<div class=\"content-main\"><label for=\"descrizione\">Descrizione</label></div>");
			sb.append("<div class=\"content-secondary\"><textarea rows=\"5\" cols=\"100\" name=\"descrizione\" id=\"descrizione\" class=\"textarea\" placeholder=\"Descrizione\"> "+p.getAttributiProcesso().getDescrizione()+" </textarea></div>");
			sb.append("</div>");
			sb.append("<br><h4>Tipologia Processo</h4>");
			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\">");

			sb.append("<p>");
			for (TipologiaProcesso tp : ControllerDatabase.prendiTipologiaProcesso()){
				
				boolean inserito = false;
				for(TipologiaProcesso tpp:p.getAttributiProcesso().getTipologiaProcesso()){
					if (tp.getNome_IT().equals(tpp.getNome_IT())) {
					sb.append("<input type=\"checkbox\" checked=\"checked\" name=\"tpnome_IT\" value=\""+ tp.getNome_IT() + "\"/> " + tp.getNome_IT() + " ");
				
					inserito = true;
					}
				}
					if (inserito == false)
						sb.append("<input type=\"checkbox\" name=\"tpnome_IT\" value=\""+ tp.getNome_IT() + "\" /> " + tp.getNome_IT() + "");
				
			}
			sb.append("</p>");

			sb.append("</div> </div>");

			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sull'ubicazione</h4>");
			sb.append("<div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"sottobacino\">Sottobacino</label><input type=\"text\" id=\"sottobacino\" name=\"sottobacino\" class=\"form-control\" value=\""+p.getUbicazione().getLocIdro().getSottobacino()+"\"/></div>");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"bacino\">Bacino</label><input readonly=\"readonly\" type=\"text\"id=\"bacino\" name=\"bacino\" class=\"form-control\" value=\""+p.getUbicazione().getLocIdro().getBacino()+"\"/></div> ");
			sb.append("<input type=\"hidden\" id=\"idSottobacino\" name=\"idSottobacino\" value=\""+p.getUbicazione().getLocIdro().getIdSottobacino()+"\"/>");
			sb.append("</div>");
			sb.append("<br><div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"comune\">Comune</label><input type=\"text\" id=\"comune\" name=\"comune\" class=\"form-control\" value=\""+p.getUbicazione().getLocAmm().getComune()+"\"/></div>");
			sb.append("<input  type=\"hidden\" id=\"idcomune\" name=\"idcomune\" value=\""+p.getUbicazione().getLocAmm().getIdComune()+"\" />");
			sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"provincia\">Provincia</label><input readonly=\"readonly\" type=\"text\" id=\"provincia\" name=\"provincia\" class=\"form-control\" value=\""+p.getUbicazione().getLocAmm().getProvincia()+"\"/></div>");
			sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"regione\">Regione</label><input readonly=\"readonly\" type=\"text\" id=\"regione\" name=\"regione\" class=\"form-control\" value=\""+p.getUbicazione().getLocAmm().getRegione()+"\" /> </div>");
			sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"nazione\">Nazione</label><input readonly=\"readonly\" type=\"text\" id=\"nazione\" name=\"nazione\"class=\"form-control\" value=\""+p.getUbicazione().getLocAmm().getNazione()+"\" /></div>");
			sb.append("</div>");
			sb.append("<div id=\"controls\">");
			sb.append("<br><div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"latitudine\">Latitudine</label><input type=\"text\" id=\"latitudine\"name=\"latitudine\" class=\"form-control\" value=\""+p.getUbicazione().getCoordinate().getX()+"\"/></div>");
			sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"longitudine\">Longitudine</label><input type=\"text\" id=\"longitudine\" name=\"longitudine\" class=\"form-control\" value=\""+p.getUbicazione().getCoordinate().getY()+"\"/></div>");
			sb.append("<div class=\"col-xs-6 col-md-4\"><br><input type=\"button\" name=\"showMap\" value=\"Prendi Le Coordinate Dalla Mappa\" id=\"showMap\" /></div>");
			sb.append("</div>");
			sb.append("</div>");

			sb.append(" <div id=\"map_container\" title=\"Location Map\">");
			sb.append("<div id=\"map_canvas\" style=\"width:100%;height:80%;\"></div>");
			sb.append("<div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"lati\">Latitudine</label><input type=\"text\" id =\"lati\"name=\"lati\" class=\"form-control\" placeholder=\"latitudine\"></div>");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"long\">Longitudine</label><input type=\"text\" id=\"long\" name=\"long\" class=\"form-control\"  placeholder=\"longitudine\"></div>");
			sb.append("</div>");
			sb.append("</div>");
			sb.append("<br><div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"quota\">Quota</label> <input type=\"text\" id=\"quota\"name=\"quota\" class=\"form-control\" value=\""+p.getUbicazione().getQuota()+"\"/></div>");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"esposizione\">Esposizione</label> <input type=\"text\" id=\"esposizione\" name=\"esposizione\" class=\"form-control\" value=\""+p.getUbicazione().getEsposizione()+"\"/></div>");
			sb.append("</div>");

			sb.append("</div> </div>");

			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\">");
			sb.append("<h4>Danni</h4>");
			sb.append("<p>");
			for (Danni d : ControllerDatabase.prendiDanni()) {
				boolean inserito = false;
				for(Danni da : p.getAttributiProcesso().getDanni()){
					if (d.getTipo_IT().equals(da.getTipo_IT())) {
						sb.append("<input type=\"checkbox\"  name=\"dtipo_IT\" value=\""+ d.getTipo_IT() + "\" checked=\"checked\" /> "+ d.getTipo_IT() + "");
						inserito = true;
					}
				}
				if (inserito == false)
					sb.append("<input type=\"checkbox\" name=\"dtipo_IT\" value=\""+ d.getTipo_IT() + "\" /> " + d.getTipo_IT() + "");
			}
					
				
			
			sb.append("</p>");
			sb.append("<p>");
			sb.append("<h4>Effetti Morfologici</h4>");
			for (EffettiMorfologici em : ControllerDatabase.prendiEffettiMOrfologici()) {
				boolean inserito = false;
				for (EffettiMorfologici emp : p.getAttributiProcesso().getEffetti()) {
					if (emp.getTipo_IT().equals(em.getTipo_IT())) {
						sb.append("<input type=\"checkbox\"  name=\"emtipo_IT\" value=\""
								+ em.getTipo_IT() + "\" checked=\"checked\" /> "
								+ em.getTipo_IT() + "");
						inserito = true;
					}
				}
				if (inserito == false)
					sb.append("<input type=\"checkbox\" name=\"emtipo_IT\" value=\""
							+ em.getTipo_IT() + "\" /> " + em.getTipo_IT() + "");
			}
			sb.append("</p>");
			sb.append("<br><div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-6\">");
			sb.append("<label for=\"gradoDanno\">"+locale.getWord("gradoDanno")+"</label> ");
			 sb.append("<select class=\"form-control\" name=\"gradoDanno\" id=\"gradoDanno\">");
                if(p.getAttributiProcesso().getGradoDanno().equals(""))sb.append("<option value=\"\"></option> selected=\"selected\"");
                else sb.append("<option value=\"\"></option>");
                if(p.getAttributiProcesso().getGradoDanno().equals(locale.getWord("danneggiato")))sb.append("<option value=\""+locale.getWord("danneggiato")+"\" selected=\"selected\">"+locale.getWord("danneggiato")+"</option>");
                else sb.append("<option value=\""+locale.getWord("danneggiato")+"\">"+locale.getWord("danneggiato")+"</option>");
                if(p.getAttributiProcesso().getGradoDanno().equals(locale.getWord("distrutto")))sb.append("<option value=\""+locale.getWord("distrutto")+"\" selected=\"selected\">"+locale.getWord("distrutto")+"</option>");
                else sb.append("<option value=\""+locale.getWord("distrutto")+"\">"+locale.getWord("distrutto")+"</option>");
                if(p.getAttributiProcesso().getGradoDanno().equals(locale.getWord("minacciato")))sb.append("<option value=\""+locale.getWord("minacciato")+"\"selected=\"selected\">"+locale.getWord("minacciato")+"</option>");
                else sb.append("<option value=\""+locale.getWord("minacciato")+"\">"+locale.getWord("minacciato")+"</option>");
                sb.append("</select>");
                sb.append("</div>");
                sb.append("</div>");
			sb.append("</div> </div>");

			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sulla litologia</h4>");
			sb.append("<label for=\"nomeLitologia_" + loc+ "\">Litologia</label>:<input type=\"text\" id=\"nomeLitologia_"+ loc + "\" name=\"nomeLitologia_" + loc+ "\" class=\"form-control\" value=\""+p.getAttributiProcesso().getLitologia().getNomeLitologia_IT()+"\"/></p>");
		
			sb.append("<input type=\"hidden\" id=\"idLitologia\" name=\"idLitologia\" value\""+p.getAttributiProcesso().getLitologia().getidLitologia()+"\"/>");
			sb.append("<br><div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"proprietaTermiche_"+ loc+ "\">Propriet� Termiche</label><input type=\"text\" id=\"proprietaTermiche_"+ loc+ "\" name=\"proprietaTermiche_"+ loc+ "\" class=\"form-control\" value=\""+p.getAttributiProcesso().getProprietaTermiche().getProprieta_termiche_IT()+"\"/></div>");
			sb.append("<input type=\"hidden\" id=\"idProprietaTermiche\" name=\"idProprietaTermiche\" value\""+p.getAttributiProcesso().getProprietaTermiche().getIdProprieta_termiche()+" />");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"statoFratturazione_"+ loc+ "\">Stato Fratturazione</label><input type=\"text\" id=\"statoFratturazione_"+ loc+ "\" name=\"statoFratturazione_"+ loc+ "\" class=\"form-control\" value=\""+p.getAttributiProcesso().getStatoFratturazione().getStato_fratturazione_IT()+"\" /></div>");
			sb.append("<input type=\"hidden\" id=\"idStatoFratturazione\" name=\"idStatoFratturazione\" value=\""+p.getAttributiProcesso().getStatoFratturazione().getIdStato_fratturazione()+"\" />");
			sb.append("</div>");
			sb.append("</div> </div>");

			sb.append("<br><div class=\"wrapper\">");
			sb.append("<div class=\"content-main\"><label for=\"note\">Note</label></div>");
			sb.append("<div class=\"content-secondary\"><textarea rows=\"5\" cols=\"100\" name=\"note\" id=\"note\" class=\"textarea\" placeholder=\"Note\"> "+p.getAttributiProcesso().getNote()+" </textarea></div>");
			sb.append("</div>");

			sb.append("<input type=\"hidden\" name=\"operazione\" value=\"modificaProcesso\">");
			sb.append("<input type=\"hidden\" name=\"idProcesso\" value=\""+p.getIdProcesso()+"\"/>");
                        
                        sb.append("<button type=\"submit\" class=\"btn btn-default\">OK</button>");

			sb.append("</div> </div>");
                        sb.append("</div>");
			sb.append("</form>");
		
		return sb.toString();
	}

	public static String mostraCerchioProcesso(int id, String loc)
			throws SQLException {
		StringBuilder sb = new StringBuilder();

		sb.append("<form action=\"Servlet\" name=\"dati\" method=\"POST\">"
				+ "raggio<input type=\"text\" name=\"raggio\" > <br>"
				+ "<input type=\"hidden\" name=\"operazione\" value=\"mostraStazioniRaggio\">"
				+ "  <input type=\"hidden\" name=\"id\" value=\"" + id + "\"  />"
				+ "<input type=\"submit\" name =\"submit\" value=\"OK\">" + "</form>");
		return sb.toString();
	}

	public static String mostraStazioniRaggio(Processo p, String loc, int raggio)
			throws SQLException {
		StringBuilder sb = new StringBuilder();
		double x = p.getUbicazione().getCoordinate().getX();
		double y = p.getUbicazione().getCoordinate().getY();
		ArrayList<StazioneMetereologica> s = ControllerDatabase
				.prendiStazionidaRaggio(x, y, p, raggio);
		sb.append("<table class=\"table\"> <tr> <th>distanza</th> <th>quota</th> <th>nome</th> </tr>");
		sb.append("<form action=\"Servlet\" name=\"dati\" method=\"POST\">");
		for (StazioneMetereologica stazione : s) {
			sb.append(" <tr> <td>" + stazione.getDistanzaProcesso() + " </td> <td>"
					+ stazione.getUbicazione().getQuota() + "</td> <td>"
					+ stazione.getNome()
					+ " </td> <td> <input type=\"checkbox\" name=\"id\" value=\""
					+ stazione.getIdStazioneMetereologica() + "\" > </td></tr>");
		}
		sb.append("</table>");
		sb.append("<div class=\"row\">");
		if(s.size()!=0){
		sb.append("<input type=\"hidden\" name=\"idProcesso\" value=\""
				+ p.getIdProcesso() + "\">");
		sb.append("<div class=\"col-xs-6 col-md-4\"><input type=\"submit\" name =\"operazione\" value=\"scegliTemperature\"> </div>");

		sb.append("<div class=\"col-xs-6 col-md-4\"><input type=\"submit\" name =\"operazione\" value=\"scegliDeltaT\"> </div>");

		sb.append("<div class=\"col-xs-6 col-md-4\"><input type=\"submit\" name =\"operazione\" value=\"scegliPrecipitazioni\"> </div>");
		sb.append("</div>");
		}
		sb.append("</form>");
		return sb.toString();
	}

	public static String mostraProcessiMaps() throws SQLException {
		ArrayList<Processo> p = ControllerDatabase.prendiTuttiProcessi();
		StringBuilder sb = new StringBuilder();

		sb.append("<div id=\"gmap\" style=\"width:400px;height:500px\"></div>");
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
		for(int i =0;i<p.size();i++){
			sb.append("stazioni["+i+"] = {");
			sb.append(" nome: \" "+p.get(i).getNome()+" \",");
			sb.append(" comune: \" "+p.get(i).getUbicazione().getLocAmm().getComune()+" \",");
			sb.append(  " x: "+p.get(i).getUbicazione().getCoordinate().getX()+",");
			sb.append(  " y: "+p.get(i).getUbicazione().getCoordinate().getY()+"");
			sb.append(  " };");
		}
		sb.append("for (var i = 0; i < "+p.size()+"; i++) { "); 
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
	
	/*
	 * query delle ricerche S...
	 */
	public static String listaQueryProcesso(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("<div class=\"panel panel-default\">");
		sb.append("	<h3>Query sui processi</h3>");
		
		sb.append("		<div class=\"list-group\">");
		sb.append("  			<a href=\"Servlet?operazione=formRicercaSingola&attributi=nome\" class=\"list-group-item\">Cerca per nome</a>");
		sb.append("  			<a href=\"Servlet?operazione=formRicercaSingola&attributi=anno\" class=\"list-group-item\">Cerca per anno</a>");
		sb.append("  			<a href=\"Servlet?operazione=formRicercaSingola&attributi=caratteristicaSito_IT\" class=\"list-group-item\">Cerca per caratteristica sito</a>");
		sb.append("  			<a href=\"Servlet?operazione=formRicercaSingola&attributi=tpnome_IT\" class=\"list-group-item\">cerca per tipologia processo</a>");
		sb.append("  			<a href=\"Servlet?operazione=formRicercaSingola&attributi=idSottobacino-idcomune-comune-provincia-regione-nazione-sottobacino-bacino\" class=\"list-group-item\">ricerca territoriale</a>");
		sb.append("  			<a href=\"Servlet?operazione=formRicercaSingola&attributi=quota\" class=\"list-group-item\">ricerca per quota</a>");
		sb.append("  			<a href=\"Servlet?operazione=formRicercaSingola&attributi=dtipo_IT\" class=\"list-group-item\">ricerca per danni</a>");
		sb.append("  			<a href=\"Servlet?operazione=formRicercaSingola&attributi=nomeLitologia_IT-proprietaTermiche_IT-statoFratturazione_IT\" class=\"list-group-item\">ricerca per litologia</a>");
		sb.append("  			<a href=\"Servlet?operazione=formRicercaSingola&attributi=\" class=\"list-group-item\">ricerca sulla mappa(da implementare)</a>");
		sb.append("       <a href=\"Servlet?operazione=mostraTuttiProcessi\" class=\"list-group-item\"> mostra tutti i processi</a>");
		sb.append("			  <a href=\"Servlet?operazione=mostraProcessiMaps\" class=\"list-group-item\"> mostra processi sulla mappa</a>");
		sb.append(" 			<a href=\"Servlet?operazione=formCercaProcessi\" class=\"list-group-item\"> ricerca processo</a>");
                sb.append(" 			<a href=\"Servlet?operazione=formRicercaProcessoPerStagione\" class=\"list-group-item\"> ricerca processo per stagione</a>");
                sb.append(" 			<a href=\"Servlet?operazione=formRicercaProcessoPerMese\" class=\"list-group-item\"> ricerca processo per mese</a>");
		sb.append("  		</div>");
		sb.append("  		</div>	");             		
		return sb.toString();
	}
	
    public static String formCercaSingola(String attributi,String path,String loc){
		StringBuilder sb = new StringBuilder();
		StringBuilder attributiBulder = new StringBuilder();
		attributiBulder.append(attributi);
		String[] attributiArray = attributi.split("-");
		
		sb.append(HTMLScript.scriptData("data"));
		
		
		sb.append(HTMLScript.scriptAutocompleteLocAmm(ControllerJson.getJsonLocazioneAmminitrativa(path)));
		sb.append(HTMLScript.scriptAutocompleteProprietaTermiche(ControllerJson.getJsonProprietaTermiche(path, loc), loc));
		sb.append(HTMLScript.scriptAutocompleteStatoFratturazione(ControllerJson.getJsonStatoFratturazione(path, loc), loc));
		sb.append(HTMLScript.scriptAutocompleteClasseVolume(ControllerJson.getJsonClasseVolume(path)));
		sb.append(HTMLScript.scriptAutcompleteLitologia(ControllerJson.getJsonLitologia(path, loc), loc));
		sb.append(HTMLScript.scriptAutocompleteSitoProcesso(ControllerJson.getJsonSitoProcesso(path, loc), loc));
		sb.append(HTMLScript.scriptAutocompleteLocIdro(ControllerJson.getJsonLocazioneIdrologica(path)));
		sb.append("<form action=\"Servlet\" name=\"dati\" method=\"POST\" role=\"form\">");

		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Ricerca tra i Processi</h4>");
		
			
		
		sb.append("<div class=\"row\">");
		for(int i = 0;i<attributiArray.length;i++){
          System.out.println(attributiArray[i]);
			if(attributiArray[i].equals("idcomune")){
             sb.append("<input  type=\"hidden\" id=\"idcomune\" name=\"idcomune\" />");
            }
            else if(attributiArray[i].equals("idSottobacino")) sb.append("<input type=\"hidden\" name=\"idSottobacino\" id=\"idSottobacino\">");
            else sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\""+attributiArray[i]+"\">"+attributiArray[i]+"</label> <input type=\"text\" name=\""+attributiArray[i]+"\" id=\""+attributiArray[i]+"\" class=\"form-control\" placeholder=\""+attributiArray[i]+"\" ></div>");
		}
		sb.append("</div>");
		sb.append("</div>");
		sb.append("<input type=\"hidden\" name=\"operazione\" value=\"cercaProcesso\">");
		sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">");
		sb.append("</form>");
		
		
		return sb.toString();
	}
    
    
	public static String mostraTuttiProcessiAllega() throws SQLException{
		ArrayList<Processo> ap = ControllerDatabase.prendiTuttiProcessi();
		StringBuilder sb = new StringBuilder();

		/* script per google maps */// centrerei la mappa al centro delle alpi

		sb.append(HTMLScript.scriptFilter());   
		sb.append("<h3>Scegli un Processo a cui allegare un file</h3>");
		sb.append("<div class=\"table-responsive\"><table class=\"table\"> <tr> <th>Nome</th> <th>data</th> <th>comune</th> <th>nazione</th> <th> Report </th> <th> Allega</th></tr>");
		for (Processo p : ap) {
			sb.append("<tr> <td>" + p.getNome() + " </td> ");
			sb.append("	<td>"	+ dataFormattata(p.getFormatoData(), p.getData()) + "</td>" );
			sb.append("	<td>"+ p.getUbicazione().getLocAmm().getComune() + "</td>");
			sb.append("<td>" + p.getUbicazione().getLocAmm().getNazione() + "</td> ");
			sb.append("<td><a href=\"Servlet?operazione=mostraProcesso&idProcesso="+ p.getIdProcesso() + "\">dettagli</a></td>");
			sb.append("<td><a href=\"Servlet?operazione=formAllegatoProcesso&idprocesso="+ p.getIdProcesso() + "\">Allega</a> </td>");
			sb.append("</tr>");
		}
		sb.append("</table></div>");
		return sb.toString();
	}
	
	public static String formAllegatoProcesso(int idprocesso,Utente user) throws SQLException{
		StringBuilder sb = new StringBuilder();
		Processo p = ControllerDatabase.prendiProcesso(idprocesso);
		
		sb.append("<form class=\"form-horizontal\" action=\"Servlet\" name=\"dati\" method=\"POST\" enctype=\"multipart/form-data\" >");
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Allega un file al processo "+p.getNome()+"</h4></div>");
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
		
		sb.append("<input type=\"hidden\" name=\"idprocesso\" value=\""+p.getIdProcesso()+"\">");
		sb.append("<input type=\"hidden\" name=\"operazione\" value=\"uploadAllegatoProcesso\">");
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
