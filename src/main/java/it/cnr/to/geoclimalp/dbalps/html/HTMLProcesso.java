package it.cnr.to.geoclimalp.dbalps.html;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.Map;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.GoogleApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class HTMLProcesso {
    public static String dataFormattata(int formatoData, Timestamp data) {
        StringBuilder sb = new StringBuilder();
        String fd = String.valueOf(formatoData);
        Calendar cal = new GregorianCalendar();
        cal.setTime(data);
        if (fd.length() == 4) {
            if (fd.charAt(0) == '1') {
                sb.append(cal.get(Calendar.YEAR));
            }
            if (fd.charAt(1) == '1') {
                sb.append("-"
                        + cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ITALY));
            }
            if (fd.charAt(2) == '1') {
                sb.append("-" + cal.get(Calendar.DAY_OF_MONTH));
            }
            if (fd.charAt(3) == '1') {
                sb.append(" " + cal.get(Calendar.HOUR_OF_DAY));
                sb.append(":" + cal.get(Calendar.MINUTE));
            }
        }
        return sb.toString();
    }

    public static boolean campoData(String formato, int pos) {
        boolean ce = false;
        if (formato.charAt(pos) == '1') {
            ce = true;
        }
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
        for (int i = 1; i <= 12; i++) {
            sb.append("<option value=\"" + i + "\">" + i + "</option>");
        }
        sb.append("</select>");
        sb.append("</div>");
        sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"giorno\">Giorno</label> <select id=\"giorno\" name=\"giorno\" class=\"form-control\" placeholder=\"Giorno\">");
        sb.append("<option value=\"vuoto\"> </option>");
        for (int i = 1; i <= 31; i++) {
            sb.append("<option value=\"" + i + "\">" + i + "</option>");
        }
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
        for (TipologiaProcesso tp : ControllerDatabase.prendiTipologiaProcesso()) {
            sb.append("<input type=\"checkbox\" name=\"tpnome_IT\" value=\""
                    + tp.getNome_IT() + "\"/> " + tp.getNome_IT() + " ");
        }
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

    public static String modificaProcesso(Processo p, String path, String loc, Utente user, ControllerLingua locale) throws SQLException {
        StringBuilder sb = new StringBuilder();
        Calendar cal = new GregorianCalendar();
        cal.setTime(p.getData());
        cal.add(Calendar.MONTH, 1);

        sb.append(HTMLScript.scriptData("data"));

        if (locale.getLanguage().equals("it")) {
            loc = "IT";
        } else {
            loc = "ENG";
        }

        sb.append(HTMLScript.scriptAutocompleteLocAmm(ControllerJson.getJsonLocazioneAmminitrativa(path)));
        sb.append(HTMLScript.scriptAutocompleteLocIdro(ControllerJson.getJsonLocazioneIdrologica(path)));
        sb.append(HTMLScript.scriptAutocompleteProprietaTermiche(ControllerJson.getJsonProprietaTermiche(path, loc), loc));
        sb.append(HTMLScript.scriptAutocompleteStatoFratturazione(ControllerJson.getJsonStatoFratturazione(path, loc), loc));
        sb.append(HTMLScript.scriptAutocompleteClasseVolume(ControllerJson.getJsonClasseVolume(path)));
        sb.append(HTMLScript.scriptAutcompleteLitologia(ControllerJson.getJsonLitologia(path, loc), loc));
        sb.append(HTMLScript.scriptAutocompleteSitoProcesso(ControllerJson.getJsonSitoProcesso(path, loc), loc));
        sb.append(HTMLScript.dialogMaps());

        sb.append("<form action=\"Servlet\" name=\"dati\" method=\"POST\" role=\"form\">");

        sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sul Processo</h4>");

        sb.append("<div class=\"row\">");
        sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"nome\">Nome Del Processo</label> <input type=\"text\" name=\"nome\" id=\"nome\" class=\"form-control\" value=\"" + p.getNome() + "\" ></div>");
        sb.append("</div>");
        sb.append("<br><div class=\"row \">");
        int a = cal.get(Calendar.YEAR);
        sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"anno\">Anno</label> <input type=\"text\" id=\"anno\" name=\"anno\" class=\"form-control\" value=\"" + a + "\"></div>");

        sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"mese\">Mese</label> <select id=\"mese\" name=\"mese\" class=\"form-control\" >");
        sb.append("<option value=\"vuoto\"> </option>");
        for (int i = 1; i <= 12; i++) {
            if (cal.get(Calendar.MONTH) != i) {
                sb.append("<option value=\"" + i + "\">" + i + "</option>");
            } else {
                sb.append("<option selected=\"selected\" value=\"" + i + "\">" + i + "</option>");
            }
        }
        sb.append("</select>");
        sb.append("</div>");
        sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"giorno\">Giorno</label> <select id=\"giorno\" name=\"giorno\" class=\"form-control\" >");
        sb.append("<option value=\"vuoto\"> </option>");
        for (int i = 1; i <= 31; i++) {
            if (cal.get(Calendar.DAY_OF_MONTH) != i) {
                sb.append("<option value=\"" + i + "\">" + i + "</option>");
            } else {
                sb.append("<option selected=\"selected\" value=\"" + i + "\">" + i + "</option>");
            }
        }
        sb.append("</select>");
        sb.append(" <input type=\"hidden\" id=\"datepicker\" />");
        sb.append("</div>");
        sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"ora\">Ora</label> <input type=\"text\" id=\"ora\" name=\"ora\"  class=\"form-control\" placeholder=\"ora\"></div> ");
        sb.append("</div>");
        sb.append("<br><div class=\"row\">");
        sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"superficie\">Superficie</label><input type=\"text\" name=\"superficie\" id=\"superficie\" class=\"form-control\" value=\"" + p.getAttributiProcesso().getSuperficie() + " \"></div>");
        sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"larghezza\">Larghezza</label><input type=\"text\" name=\"larghezza\" id=\"larghezza\" class=\"form-control\" value=\"" + p.getAttributiProcesso().getLarghezza() + " \"></div>");
        sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"altezza\">Altezza</label><input type=\"text\" name=\"altezza\" id=\"altezza\" class=\"form-control\" value=\"" + p.getAttributiProcesso().getAltezza() + " \"></div>");
        sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"volumeSpecifico\">Volume Specifico</label><input type=\"text\" name=volumespecifico id=\"volumeSpecifico\"class=\"form-control\" value=\"" + p.getAttributiProcesso().getVolume_specifico() + "\" ></div>");
        if (p.getAttributiProcesso().getClasseVolume().getIntervallo() == null) {
            p.getAttributiProcesso().getClasseVolume().setIntervallo("");
        }
        sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"intervallo\">Classe Volume</label><input type=\"text\" id=\"intervallo\" name=intervallo class=\"form-control\" value=\"" + p.getAttributiProcesso().getClasseVolume().getIntervallo() + "\"></div>");
        sb.append("<input type=\"hidden\" id=\"idclasseVolume\" name=\"idclasseVolume\" value=\"" + p.getAttributiProcesso().getClasseVolume().getIdClasseVolume() + "\" />");
        sb.append("</div>");
        sb.append("<br><div class =\"row\">");
        sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"caratteristicaSito_" + loc + "\">Caratteristica del sito</label><input type=\"text\" id=\"caratteristicaSito_" + loc + "\" name=\"caratteristicaSito_" + loc + "\" class=\"form-control\"value=\"" + p.getAttributiProcesso().getSitoProcesso().getCaratteristicaSito_IT() + "\"/></div>");
        sb.append("<input type=\"hidden\" id=\"idsito\" name=\"idsito\" value=\"" + p.getAttributiProcesso().getSitoProcesso().getIdSito() + "\"/>");
        sb.append("</div>");
        sb.append("<br><div class=\"wrapper\">");
        sb.append("<div class=\"content-main\"><label for=\"descrizione\">Descrizione</label></div>");
        sb.append("<div class=\"content-secondary\"><textarea rows=\"5\" cols=\"100\" name=\"descrizione\" id=\"descrizione\" class=\"textarea\" placeholder=\"Descrizione\"> " + p.getAttributiProcesso().getDescrizione() + " </textarea></div>");
        sb.append("</div>");
        sb.append("<br><h4>Tipologia Processo</h4>");
        sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\">");

        sb.append("<p>");
        for (TipologiaProcesso tp : ControllerDatabase.prendiTipologiaProcesso()) {

            boolean inserito = false;
            for (TipologiaProcesso tpp : p.getAttributiProcesso().getTipologiaProcesso()) {
                if (tp.getNome_IT().equals(tpp.getNome_IT())) {
                    sb.append("<input type=\"checkbox\" checked=\"checked\" name=\"tpnome_IT\" value=\"" + tp.getNome_IT() + "\"/> " + tp.getNome_IT() + " ");

                    inserito = true;
                }
            }
            if (inserito == false) {
                sb.append("<input type=\"checkbox\" name=\"tpnome_IT\" value=\"" + tp.getNome_IT() + "\" /> " + tp.getNome_IT() + "");
            }

        }
        sb.append("</p>");

        sb.append("</div> </div>");

        sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sull'ubicazione</h4>");
        sb.append("<div class=\"row\">");
        sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"sottobacino\">Sottobacino</label><input type=\"text\" id=\"sottobacino\" name=\"sottobacino\" class=\"form-control\" value=\"" + p.getUbicazione().getLocIdro().getSottobacino() + "\"/></div>");
        sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"bacino\">Bacino</label><input readonly=\"readonly\" type=\"text\"id=\"bacino\" name=\"bacino\" class=\"form-control\" value=\"" + p.getUbicazione().getLocIdro().getBacino() + "\"/></div> ");
        sb.append("<input type=\"hidden\" id=\"idSottobacino\" name=\"idSottobacino\" value=\"" + p.getUbicazione().getLocIdro().getIdSottobacino() + "\"/>");
        sb.append("</div>");
        sb.append("<br><div class=\"row\">");
        sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"comune\">Comune</label><input type=\"text\" id=\"comune\" name=\"comune\" class=\"form-control\" value=\"" + p.getUbicazione().getLocAmm().getComune() + "\"/></div>");
        sb.append("<input  type=\"hidden\" id=\"idcomune\" name=\"idcomune\" value=\"" + p.getUbicazione().getLocAmm().getIdComune() + "\" />");
        sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"provincia\">Provincia</label><input readonly=\"readonly\" type=\"text\" id=\"provincia\" name=\"provincia\" class=\"form-control\" value=\"" + p.getUbicazione().getLocAmm().getProvincia() + "\"/></div>");
        sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"regione\">Regione</label><input readonly=\"readonly\" type=\"text\" id=\"regione\" name=\"regione\" class=\"form-control\" value=\"" + p.getUbicazione().getLocAmm().getRegione() + "\" /> </div>");
        sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"nazione\">Nazione</label><input readonly=\"readonly\" type=\"text\" id=\"nazione\" name=\"nazione\"class=\"form-control\" value=\"" + p.getUbicazione().getLocAmm().getNazione() + "\" /></div>");
        sb.append("</div>");
        sb.append("<div id=\"controls\">");
        sb.append("<br><div class=\"row\">");
        sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"latitudine\">Latitudine</label><input type=\"text\" id=\"latitudine\"name=\"latitudine\" class=\"form-control\" value=\"" + p.getUbicazione().getCoordinate().getX() + "\"/></div>");
        sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"longitudine\">Longitudine</label><input type=\"text\" id=\"longitudine\" name=\"longitudine\" class=\"form-control\" value=\"" + p.getUbicazione().getCoordinate().getY() + "\"/></div>");
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
        sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"quota\">Quota</label> <input type=\"text\" id=\"quota\"name=\"quota\" class=\"form-control\" value=\"" + p.getUbicazione().getQuota() + "\"/></div>");
        sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"esposizione\">Esposizione</label> <input type=\"text\" id=\"esposizione\" name=\"esposizione\" class=\"form-control\" value=\"" + p.getUbicazione().getEsposizione() + "\"/></div>");
        sb.append("</div>");

        sb.append("</div> </div>");

        sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\">");
        sb.append("<h4>Danni</h4>");
        sb.append("<p>");
        for (Danni d : ControllerDatabase.prendiDanni()) {
            boolean inserito = false;
            for (Danni da : p.getAttributiProcesso().getDanni()) {
                if (d.getTipo_IT().equals(da.getTipo_IT())) {
                    sb.append("<input type=\"checkbox\"  name=\"dtipo_IT\" value=\"" + d.getTipo_IT() + "\" checked=\"checked\" /> " + d.getTipo_IT() + "");
                    inserito = true;
                }
            }
            if (inserito == false) {
                sb.append("<input type=\"checkbox\" name=\"dtipo_IT\" value=\"" + d.getTipo_IT() + "\" /> " + d.getTipo_IT() + "");
            }
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
            if (inserito == false) {
                sb.append("<input type=\"checkbox\" name=\"emtipo_IT\" value=\""
                        + em.getTipo_IT() + "\" /> " + em.getTipo_IT() + "");
            }
        }
        sb.append("</p>");
        sb.append("<br><div class=\"row\">");
        sb.append("<div class=\"col-xs-6 col-md-6\">");
        sb.append("<label for=\"gradoDanno\">" + locale.getWord("gradoDanno") + "</label> ");
        sb.append("<select class=\"form-control\" name=\"gradoDanno\" id=\"gradoDanno\">");
        if (p.getAttributiProcesso().getGradoDanno().equals("")) {
            sb.append("<option value=\"\"></option> selected=\"selected\"");
        } else {
            sb.append("<option value=\"\"></option>");
        }
        if (p.getAttributiProcesso().getGradoDanno().equals(locale.getWord("danneggiato"))) {
            sb.append("<option value=\"" + locale.getWord("danneggiato") + "\" selected=\"selected\">" + locale.getWord("danneggiato") + "</option>");
        } else {
            sb.append("<option value=\"" + locale.getWord("danneggiato") + "\">" + locale.getWord("danneggiato") + "</option>");
        }
        if (p.getAttributiProcesso().getGradoDanno().equals(locale.getWord("distrutto"))) {
            sb.append("<option value=\"" + locale.getWord("distrutto") + "\" selected=\"selected\">" + locale.getWord("distrutto") + "</option>");
        } else {
            sb.append("<option value=\"" + locale.getWord("distrutto") + "\">" + locale.getWord("distrutto") + "</option>");
        }
        if (p.getAttributiProcesso().getGradoDanno().equals(locale.getWord("minacciato"))) {
            sb.append("<option value=\"" + locale.getWord("minacciato") + "\"selected=\"selected\">" + locale.getWord("minacciato") + "</option>");
        } else {
            sb.append("<option value=\"" + locale.getWord("minacciato") + "\">" + locale.getWord("minacciato") + "</option>");
        }
        sb.append("</select>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div> </div>");

        sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sulla litologia</h4>");
        sb.append("<label for=\"nomeLitologia_" + loc + "\">Litologia</label>:<input type=\"text\" id=\"nomeLitologia_" + loc + "\" name=\"nomeLitologia_" + loc + "\" class=\"form-control\" value=\"" + p.getAttributiProcesso().getLitologia().getNomeLitologia_IT() + "\"/></p>");

        sb.append("<input type=\"hidden\" id=\"idLitologia\" name=\"idLitologia\" value\"" + p.getAttributiProcesso().getLitologia().getidLitologia() + "\"/>");
        sb.append("<br><div class=\"row\">");
        sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"proprietaTermiche_" + loc + "\">Propriet� Termiche</label><input type=\"text\" id=\"proprietaTermiche_" + loc + "\" name=\"proprietaTermiche_" + loc + "\" class=\"form-control\" value=\"" + p.getAttributiProcesso().getProprietaTermiche().getProprieta_termiche_IT() + "\"/></div>");
        sb.append("<input type=\"hidden\" id=\"idProprietaTermiche\" name=\"idProprietaTermiche\" value\"" + p.getAttributiProcesso().getProprietaTermiche().getIdProprieta_termiche() + " />");
        sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"statoFratturazione_" + loc + "\">Stato Fratturazione</label><input type=\"text\" id=\"statoFratturazione_" + loc + "\" name=\"statoFratturazione_" + loc + "\" class=\"form-control\" value=\"" + p.getAttributiProcesso().getStatoFratturazione().getStato_fratturazione_IT() + "\" /></div>");
        sb.append("<input type=\"hidden\" id=\"idStatoFratturazione\" name=\"idStatoFratturazione\" value=\"" + p.getAttributiProcesso().getStatoFratturazione().getIdStato_fratturazione() + "\" />");
        sb.append("</div>");
        sb.append("</div> </div>");

        sb.append("<br><div class=\"wrapper\">");
        sb.append("<div class=\"content-main\"><label for=\"note\">Note</label></div>");
        sb.append("<div class=\"content-secondary\"><textarea rows=\"5\" cols=\"100\" name=\"note\" id=\"note\" class=\"textarea\" placeholder=\"Note\"> " + p.getAttributiProcesso().getNote() + " </textarea></div>");
        sb.append("</div>");

        sb.append("<input type=\"hidden\" name=\"operazione\" value=\"modificaProcesso\">");
        sb.append("<input type=\"hidden\" name=\"idProcesso\" value=\"" + p.getIdProcesso() + "\"/>");
        sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">");
        sb.append("</div> </div>");
        sb.append("</form>");

        return sb.toString();
    }

    public static String mostraCerchioProcesso(int id, String loc)
            throws SQLException {
        StringBuilder sb = new StringBuilder();

        sb.append("<form action=\"Servlet\" id=\"insertStazione\"  name=\"dati\" method=\"POST\">"
                +"<div class=\"form-group\" >"
                + "raggio<input type=\"text\" name=\"raggio\" id=\"raggio\" class=\"form-control\" > <br>"
                + "<input type=\"hidden\" name=\"operazione\" value=\"mostraStazioniRaggio\">"
                + "  <input type=\"hidden\" name=\"id\" value=\"" + id + "\"  />"
                + "<input type=\"submit\" name =\"submit\" value=\"OK\">" + ""
                +"    </div>"
                + "</form>");
        return sb.toString();
    }

    public static String mostraStazioniRaggio(Processo p, String loc, int raggio)
            throws SQLException {
        StringBuilder sb = new StringBuilder();
        double x = p.getUbicazione().getCoordinate().getX();
        double y = p.getUbicazione().getCoordinate().getY();
        ArrayList<StazioneMetereologica> s = ControllerDatabase.prendiStazionidaRaggio(x, y, p, raggio);
         sb.append("<form action=\"Servlet\" name=\"dati\" method=\"POST\">");
         sb.append("<div class=\"form-group\">");
        sb.append("<table class=\"table\"> <thead> <tr> <th>distanza</th> <th>quota</th> <th>nome</th> </tr> </thead>");
        
       
         sb.append("<tbody>");
         
        for (StazioneMetereologica stazione : s) {
           
            sb.append(" <tr> <td>" + stazione.getDistanzaProcesso() + " </td> <td>"
                    + stazione.getUbicazione().getQuota() + "</td> <td>"
                    + stazione.getNome()
                    + " </td> <td>" );
            sb.append("<div class=\"checkbox\">\n" +
                "   <label>");
            sb.append( "<input type=\"checkbox\" name=\"id\" value=\""
                    + stazione.getIdStazioneMetereologica() + "\" "
                    + "data-bv-choice=\"true\"\n" +
                            " data-bv-choice-min=\"1\"\n" +
                        "    data-bv-choice-max=\"10\"\n" +
                    "   data-bv-choice-message=\"you must insert al least one process tipology\""
                    + ""
                    + ">");
            sb.append("    </label>\n" +
                      "    </div>");
            sb.append("</td></tr>");
        }
         sb.append("</tbody>");
        sb.append("</table>");
        
          
        sb.append("<div id=\"pager\" class=\"pager\">"+
		"<img src=\"img/first.png\" class=\"first\"/>"+
		"<img src=\"img/prev.png\" class=\"prev\"/>"+
		"<input type=\"text\" class=\"pagedisplay\"/>"+
		"<img src=\"img/next.png\" class=\"next\"/>"+
		"<img src=\"img/last.png\" class=\"last\"/>"+
		"<select class=\"pagesize\">"+
		"	<option selected=\"selected\"  value=\"10\">10</option>"+
		"	<option value=\"20\">20</option>"+
		"	<option value=\"30\">30</option>"+
		"	<option  value=\"40\">40</option>"+
		"</select>"+
                "</div>");
        sb.append("<div class=\"row\">");
        if (s.size() != 0) {
            sb.append("<input type=\"hidden\" name=\"idProcesso\" value=\""
                    + p.getIdProcesso() + "\">");
            sb.append("<div class=\"col-xs-6 col-md-4\"><input type=\"submit\" name =\"operazione\" value=\"scegliTemperature\"> </div>");

            sb.append("<div class=\"col-xs-6 col-md-4\"><input type=\"submit\" name =\"operazione\" value=\"scegliDeltaT\"> </div>");

            sb.append("<div class=\"col-xs-6 col-md-4\"><input type=\"submit\" name =\"operazione\" value=\"scegliPrecipitazioni\"> </div>");
            sb.append("</div>");
        }
            sb.append("</div>");
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
        for (int i = 0; i < p.size(); i++) {
            sb.append("stazioni[" + i + "] = {");
            sb.append(" nome: \" " + p.get(i).getNome() + " \",");
            sb.append(" comune: \" " + p.get(i).getUbicazione().getLocAmm().getComune() + " \",");
            sb.append(" x: " + p.get(i).getUbicazione().getCoordinate().getX() + ",");
            sb.append(" y: " + p.get(i).getUbicazione().getCoordinate().getY() + "");
            sb.append(" };");
        }
        sb.append("for (var i = 0; i < " + p.size() + "; i++) { ");
        sb.append("pos = new google.maps.LatLng( stazioni[i].x , stazioni[i].y );");
        sb.append("marker = new google.maps.Marker({");
        sb.append("position:pos,");
        sb.append("map:map,");
        sb.append("title:'Title'");
        sb.append("});");
        sb.append("var infowindow = new google.maps.InfoWindow();");
        sb.append("google.maps.event.addListener(marker, 'click', (function(marker, i) {");
        sb.append("	return function() {");
        sb.append("infowindow.setContent(\"nome: \"+stazioni[i].nome+\" <br> comune: \"+stazioni[i].comune+\"\");");
        sb.append("infowindow.open(map, marker);");
        sb.append("}");
        sb.append("})(marker, i));");
        sb.append("marker_list.push(marker);");
        sb.append("}");
        sb.append("var markerCluster = new MarkerClusterer(map, marker_list, {");
        sb.append("gridSize:40,");
        sb.append("minimumClusterSize: 4,");
        sb.append("calculator: function(markers, numStyles) {");
        sb.append("return {");
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
    public static String listaQueryProcesso() {
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
        sb.append("			  <a href=\"mappaProcessiStazioni.jsp\" class=\"list-group-item\"> Mappa con processi e stazioni</a>");
        sb.append(" 			<a href=\"Servlet?operazione=formCercaProcessi\" class=\"list-group-item\"> ricerca processo</a>");
        sb.append("  		</div>");
        sb.append("  		</div>	");
        return sb.toString();
    }

    public static String formCercaSingola(String attributi, String path, String loc) {
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
        for (int i = 0; i < attributiArray.length; i++) {
            System.out.println(attributiArray[i]);
            if (attributiArray[i].equals("idcomune")) {
                sb.append("<input  type=\"hidden\" id=\"idcomune\" name=\"idcomune\" />");
            } else if (attributiArray[i].equals("idSottobacino")) {
                sb.append("<input type=\"hidden\" name=\"idSottobacino\" id=\"idSottobacino\">");
            } else if(attributiArray[i].equals("nome")){
                    sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"" + attributiArray[i] + "\">" + attributiArray[i] + "</label> <select type=\"text\" name=\"" + attributiArray[i] + "\" id=\"" + attributiArray[i] + "\" class=\"form-control\" placeholder=\"" + attributiArray[i] + "\" ></select></div>");
            } else {
                sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"" + attributiArray[i] + "\">" + attributiArray[i] + "</label> <input type=\"text\" name=\"" + attributiArray[i] + "\" id=\"" + attributiArray[i] + "\" class=\"form-control\" placeholder=\"" + attributiArray[i] + "\" ></div>");
            }
        }
        sb.append("</div>");
        sb.append("</div>");
        sb.append("<input type=\"hidden\" name=\"operazione\" value=\"cercaProcesso\">");
        sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">");
        sb.append("</form>");

        return sb.toString();
    }

    public static String mostraTuttiProcessiAllega(ControllerLingua l) throws SQLException {
        ArrayList<Processo> ap = ControllerDatabase.prendiTuttiProcessi();
        StringBuilder sb = new StringBuilder();

        /* script per google maps */// centrerei la mappa al centro delle alpi
        sb.append(HTMLScript.scriptFilter());
        sb.append("<h3>"+l.getWord("ScegliProcessoAllegato")+"</h3>");
        sb.append("<table id=\"tabella\" class=\"table table-striped table-bordered table-condensed\">");
          sb.append(" <tr> <th>"+l.getWord("nome")+"</th> <th>"+l.getWord("data")+"</th> <th>"+l.getWord("comune")+"</th>  <th> Report </th> <th> "+l.getWord("allega")+"</th></tr>");
        for (Processo p : ap) {
            sb.append("<tr> <td>" + p.getNome() + " </td> ");
            sb.append("	<td>" + dataFormattata(p.getFormatoData(), p.getData()) + "</td>");
            sb.append("	<td>" + p.getUbicazione().getLocAmm().getComune() + "</td>");
            sb.append("<td><a href=\"Servlet?operazione=mostraProcesso&idProcesso=" + p.getIdProcesso() + "\"><span class=\"fa fa-search\" ></span></a></td>");
            sb.append("<td><a href=\"Servlet?operazione=formAllegatoProcesso&idprocesso=" + p.getIdProcesso() + "\"><span class=\"fa fa-paperclip\"></span></a> </td>");
            sb.append("</tr>");
        }
        sb.append("</table></div>");
        return sb.toString();
    }

    public static String formAllegatoProcesso(int idprocesso, Utente user,ControllerLingua locale) throws SQLException {
        StringBuilder sb = new StringBuilder();
        Processo p = ControllerDatabase.prendiProcesso(idprocesso);

        sb.append("<form class=\"form-horizontal\" action=\"Servlet\" name=\"dati\" method=\"POST\" enctype=\"multipart/form-data\" >");
        sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>"+locale.getWord("allegaFileAProcesso")+" "+ p.getNome() + "</h4></div>");
        sb.append("<br>");
        sb.append("<div class=\"form-group\">");
        sb.append("<label for=\"autore\" class=\"col-sm-2 control-label\">"+locale.getWord("autore")+"</label>");
        sb.append("<div class=\"col-sm-10\">");
        sb.append("<input type=\"text\" name=\"autore\" id=\"autore\" class=\"form-control\">");
        sb.append("</div>");
        sb.append("</div>");

        sb.append("<div class=\"form-group\">");
        sb.append("<label for=\"anno\" class=\"col-sm-2 control-label\">"+locale.getWord("anno")+"</label>");
        sb.append("<div class=\"col-sm-10\">");
        sb.append("<input type=\"text\" name=\"anno\" id=\"anno\" class=\"form-control\">");
        sb.append("</div>");
        sb.append("</div>");

        sb.append("<div class=\"form-group\">");
        sb.append("<label for=\"titolo\" class=\"col-sm-2 control-label\">"+locale.getWord("titolo")+"</label>");
        sb.append("<div class=\"col-sm-10\">");
        sb.append("<input type=\"text\" name=\"titolo\" id=\"titolo\" class=\"form-control\">");
        sb.append("</div>");
        sb.append("</div>");

        sb.append("<div class=\"form-group\">");
        sb.append("<label for=\"in\" class=\"col-sm-2 control-label\">"+locale.getWord("in")+"</label>");
        sb.append("<div class=\"col-sm-10\">");
        sb.append("<input type=\"text\" name=\"in\" id=\"in\" class=\"form-control\">");
        sb.append("</div>");
        sb.append("</div>");

        sb.append("<div class=\"form-group\">");
        sb.append("<label for=\"fonte\" class=\"col-sm-2 control-label\">"+locale.getWord("fonte")+"</label>");
        sb.append("<div class=\"col-sm-10\">");
        sb.append("<input type=\"text\" name=\"fonte\" id=\"fonte\" class=\"form-control\" >");
        sb.append("</div>");
        sb.append("</div>");

        sb.append("<div class=\"form-group\">");
        sb.append("<label for=\"urlWeb\" class=\"col-sm-2 control-label\">"+locale.getWord("urlDelSito")+"</label>");
        sb.append("<div class=\"col-sm-10\">");
        sb.append("<input type=\"text\" name=\"urlWeb\" id=\"urlWeb\" class=\"form-control\" >");
        sb.append("</div>");
        sb.append("</div>");

        sb.append("<div class=\"form-group\">");
        sb.append("<label for=\"note\" class=\"col-sm-2 control-label\">"+locale.getWord("note")+"</label>");
        sb.append("<div class=\"col-sm-10\">");
        sb.append("<input type=\"text\" name=\"note\" id=\"note\" class=\"form-control\" >");
        sb.append("</div>");
        sb.append("</div>");

        sb.append("<div class=\"form-group\">");
        sb.append("<label for=\"tipo\" class=\"col-sm-2 control-label\">"+locale.getWord("tipo")+"</label>");
        sb.append("<div class=\"col-sm-10\">");
        sb.append("<select class=\"form-control\" name=\"tipo\" id=\"tipo\">");
        sb.append("<option value=\"document\">Document</option>");
        sb.append("<option value=\"map\">Map </option>");
        sb.append("<option value=\"image\">Image</option>");
        sb.append("<option value=\"Link\">Link</option>");
        sb.append("</select>");
        sb.append("</div>");
        sb.append("</div>");

        sb.append("<div class=\"form-group\">");
        sb.append("<label for=\"uploadFile\" class=\"col-sm-2 control-label\">"+locale.getWord("caricaFile")+"</label>");
        sb.append("<div class=\"col-sm-10\">");
        sb.append("<input type=\"file\" name=\"uploadFile\" id=\"uploadFile\" class=\"form-control\" >");
        sb.append("</div>");
        sb.append("</div>");

        sb.append("<input type=\"hidden\" name=\"idprocesso\" value=\"" + p.getIdProcesso() + "\">");
        sb.append("<input type=\"hidden\" name=\"operazione\" value=\"uploadAllegatoProcesso\">");
        sb.append("<div class=\"form-group\">");
        sb.append("<div class=\"col-sm-10\">");
        sb.append(" <button type=\"submit\" class=\"btn btn-default\">"+locale.getWord("allega")+"</button>");
        sb.append("</div>");
        sb.append("</div>");

        sb.append("</div>");
        sb.append("</form>");

        return sb.toString();
    }

    private static final String pdfFile = "//Users//daler//Desktop//p.pdf";
    private static final Font TITOLO = FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD);
    private static final Font BOLD = FontFactory.getFont(FontFactory.TIMES_ROMAN, 17, Font.BOLD);
    private static final Font ATTRIBUTO = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);

    public static ByteArrayOutputStream createPDFProcesso(Processo p) {
        System.out.println("sono dentro create con "+p.getNome());
        try {
              ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document();
            document.setPageSize(PageSize.A4);
            float margin = Utilities.millimetersToPoints(20);
            float marginTop = Utilities.millimetersToPoints(25);
            document.setMargins(margin, margin, margin, margin);
            PdfWriter.getInstance(document, baos);
            document.open();

            Paragraph nome = new Paragraph();
            nome.setSpacingAfter(10);
            nome.add(new Phrase("Nome", TITOLO));
            nome.add(new Phrase(p.getNome()));
            document.add(nome);

            //data
            Paragraph data = new Paragraph();
            data.setSpacingAfter(10);
            data.add(new Chunk("Data", BOLD));
            data.add(new Chunk(dataFormattata(p.getFormatoData(), p.getData())));
            document.add(data);

            //Descrizione
            Paragraph descrizione = new Paragraph();
            descrizione.setSpacingAfter(10);
            descrizione.add(new Phrase("Descrizione", BOLD));
            descrizione.add(new Paragraph(p.getAttributiProcesso().getDescrizione()));
            document.add(descrizione);

            //Ubicazione  e dettagli
            PdfPTable ubicazione = new PdfPTable(2);
            ubicazione.setSpacingAfter(10);
            ubicazione.setWidthPercentage(100);
            PdfPCell ubicazioneCell = ubicazione.getDefaultCell();
            ubicazioneCell.setBorder(PdfPCell.NO_BORDER);
            PdfPTable amministrazione = new PdfPTable(2);
            amministrazione.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell amministrazioneCell = amministrazione.getDefaultCell();
            amministrazioneCell.setBorder(PdfPCell.NO_BORDER);
            amministrazione.setSpacingAfter(10);
            amministrazione.addCell(new Phrase("Ubicazione", BOLD));
            amministrazione.completeRow();

            amministrazione.completeRow();
            amministrazione.addCell(new Phrase("Sottobacino", ATTRIBUTO));
            amministrazione.addCell(new Phrase(p.getUbicazione().getLocIdro().getSottobacino()));

            amministrazione.addCell(new Phrase("Bacino", ATTRIBUTO));
            amministrazione.addCell(new Phrase(p.getUbicazione().getLocIdro().getBacino()));

            amministrazione.addCell(new Phrase("Comune", ATTRIBUTO));
            amministrazione.addCell(new Phrase(p.getUbicazione().getLocAmm().getComune()));

            amministrazione.addCell(new Phrase("Provincia", ATTRIBUTO));
            amministrazione.addCell(new Phrase(p.getUbicazione().getLocAmm().getProvincia()));

            amministrazione.addCell(new Phrase("Regione", ATTRIBUTO));
            amministrazione.addCell(new Phrase(p.getUbicazione().getLocAmm().getRegione()));
            amministrazione.completeRow();

            amministrazione.addCell(new Phrase("Stato", ATTRIBUTO));
            amministrazione.addCell(new Phrase(p.getUbicazione().getLocAmm().getNazione()));
            amministrazione.completeRow();

            PdfPTable dettagliUbicazione = new PdfPTable(2);
            dettagliUbicazione.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell dettagliUbicazioneCell = dettagliUbicazione.getDefaultCell();
            dettagliUbicazioneCell.setBorder(PdfPCell.NO_BORDER);
            dettagliUbicazione.addCell("");
            dettagliUbicazione.addCell("");
            dettagliUbicazione.addCell(new Phrase("Quota", ATTRIBUTO));
            dettagliUbicazione.addCell(new Phrase("35.000 m"));
            dettagliUbicazione.addCell(new Phrase("Esposizion", ATTRIBUTO));
            dettagliUbicazione.addCell(new Phrase("225.000 m"));
            dettagliUbicazione.addCell(new Phrase("Latitudine", ATTRIBUTO));
            dettagliUbicazione.addCell(new Phrase("850.000 m^2"));
            dettagliUbicazione.addCell(new Phrase("Longitudine", ATTRIBUTO));
            dettagliUbicazione.addCell(new Phrase("Torino"));
            dettagliUbicazione.addCell(new Phrase("Affidabilità Coordinate", ATTRIBUTO));
            dettagliUbicazione.addCell(new Phrase("Torino"));

            ubicazione.addCell(amministrazione);
            ubicazione.addCell(dettagliUbicazione);
            document.add(ubicazione);

            PdfPTable litologiaDettagli = new PdfPTable(2);
            litologiaDettagli.setSpacingAfter(10);
            litologiaDettagli.setWidthPercentage(100);
            PdfPCell litologiaDettagliCell = litologiaDettagli.getDefaultCell();
            litologiaDettagliCell.setBorder(PdfPCell.NO_BORDER);
            PdfPTable tableDettagli = new PdfPTable(2);
            tableDettagli.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell DettagliiCell = tableDettagli.getDefaultCell();
            DettagliiCell.setBorder(PdfPCell.NO_BORDER);
            PdfPCell titoloDettagli = new PdfPCell(new Phrase("Dettagli Processo", BOLD));
            titoloDettagli.setColspan(2);
            titoloDettagli.setBorder(PdfPCell.NO_BORDER);
            tableDettagli.addCell(titoloDettagli);

            tableDettagli.addCell(new Phrase("Altezza", ATTRIBUTO));
            tableDettagli.addCell(new Phrase("sottopo"));
            tableDettagli.addCell(new Phrase("Larghezza", ATTRIBUTO));
            tableDettagli.addCell(new Phrase("po"));
            tableDettagli.addCell(new Phrase("Superficie", ATTRIBUTO));
            tableDettagli.addCell(new Phrase("Cantagallo"));
            tableDettagli.addCell(new Phrase("Volume", ATTRIBUTO));
            tableDettagli.addCell(new Phrase("Cantagallo"));
            tableDettagli.addCell(new Phrase("ClasseVolume", ATTRIBUTO));
            tableDettagli.addCell(new Phrase("Cantagallo"));
            tableDettagli.addCell(new Phrase("Tipologia", ATTRIBUTO));
            tableDettagli.addCell(new Phrase("Cantagallo"));
            tableDettagli.addCell(new Phrase("Sito", ATTRIBUTO));
            tableDettagli.addCell(new Phrase("Cantagallo"));

            PdfPTable tableLitologia = new PdfPTable(2);
            PdfPCell tableLitologiaCell = tableLitologia.getDefaultCell();
            tableLitologiaCell.setBorder(PdfPCell.NO_BORDER);
            tableLitologia.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell titoloDanni = new PdfPCell(new Phrase("Litologia", BOLD));
            titoloDanni.setColspan(2);
            titoloDanni.setBorder(PdfPCell.NO_BORDER);
            tableLitologia.addCell(titoloDanni);
            tableLitologia.completeRow();
            tableLitologia.addCell(new Phrase("Litologia", ATTRIBUTO));
            tableLitologia.addCell(new Phrase("35.000 m"));
            tableLitologia.addCell(new Phrase("Proprietà Termiche", ATTRIBUTO));
            tableLitologia.addCell(new Phrase("225.000 m"));
            tableLitologia.addCell(new Phrase("Stato Fratturazione", ATTRIBUTO));
            tableLitologia.addCell(new Phrase("Cantagallo"));

            litologiaDettagli.addCell(tableDettagli);
            litologiaDettagli.addCell(tableLitologia);
            document.add(litologiaDettagli);

            Paragraph danni = new Paragraph();

            danni.setSpacingAfter(10);
            danni.setAlignment(Element.ALIGN_LEFT);
            PdfPTable tableDanni = new PdfPTable(2);
            tableDanni.setWidthPercentage(50);
            tableDanni.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell danniCell = tableDanni.getDefaultCell();
            danniCell.setBorder(PdfPCell.NO_BORDER);
            tableDanni.setSpacingAfter(10);
            dettagliUbicazione.addCell(new Phrase("Danni", BOLD));
            dettagliUbicazione.completeRow();
            tableDanni.addCell(new Phrase("Danni", ATTRIBUTO));
            tableDanni.addCell(new Phrase("Rotto"));
            tableDanni.addCell(new Phrase("Effetti Morfologici", ATTRIBUTO));
            tableDanni.addCell(new Phrase("Rotto"));
            tableDanni.addCell(new Phrase("Grado Danno", ATTRIBUTO));
            tableDanni.addCell(new Phrase("Rotto"));
            danni.add(tableDanni);
            document.add(danni);

            String urlMappa = "https://www.google.com/maps/place/@45.041549,7.653984,17z/data=!3m1!1e3";
            Paragraph mappa = new Paragraph("Link alla Mappa  " + shortenUrl(urlMappa) + "");

            document.add(mappa);

            document.close();
            return baos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }

    public static String shortenUrl(String longUrl) {
        OAuthService oAuthService = new ServiceBuilder().provider(GoogleApi.class)
                .apiKey("anonymous")
                .apiSecret("anonymous")
                .scope("https://www.googleapis.com/auth/urlshortener")
                .build();

        OAuthRequest oAuthRequest = new OAuthRequest(Verb.POST, "https://www.googleapis.com/urlshortener/v1/url");

        oAuthRequest.addHeader("Content-Type", "application/json");

        String json = "{\"longUrl\": \"" + longUrl + "\"}";

        oAuthRequest.addPayload(json);

        Response response = oAuthRequest.send();

        java.lang.reflect.Type typeOfMap = new TypeToken<Map<String, String>>() {
        }.getType();

        Map<String, String> responseMap = new GsonBuilder().create().fromJson(response.getBody(), typeOfMap);

        return responseMap.get("id");
    }
    
    
  

}
