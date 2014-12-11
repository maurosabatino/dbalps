package it.cnr.to.geoclimalp.dbalps.Servlet;

import com.google.gson.Gson;
import it.cnr.to.geoclimalp.dbalps.bean.Allegato;
import it.cnr.to.geoclimalp.dbalps.html.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.jasypt.util.password.StrongPasswordEncryptor;
import it.cnr.to.geoclimalp.dbalps.bean.Dati;
import it.cnr.to.geoclimalp.dbalps.bean.Grafici;
import it.cnr.to.geoclimalp.dbalps.bean.HTMLContent;
import it.cnr.to.geoclimalp.dbalps.bean.processo.*;
import it.cnr.to.geoclimalp.dbalps.bean.stazione.*;
import it.cnr.to.geoclimalp.dbalps.bean.ubicazione.*;
import it.cnr.to.geoclimalp.dbalps.bean.Utente.*;
import it.cnr.to.geoclimalp.dbalps.bean.datoClimatico;
import it.cnr.to.geoclimalp.dbalps.controller.*;
import java.io.ByteArrayOutputStream;
import static java.lang.Integer.parseInt;
import javax.servlet.ServletOutputStream;

/**
 * Servlet implementation class Servlet
 */
@MultipartConfig
public class Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static StrongPasswordEncryptor passwordEncryptor; 

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
        passwordEncryptor = new StrongPasswordEncryptor();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       try {
            processRequest(request, response);
        } catch (SQLException e) {
        } catch (ParseException e) {
        } catch (Exception e) {
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
        } catch (ParseException e) {
        } catch (Exception e) {
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String operazione = request.getParameter("operazione");
        String path = System.getProperty("catalina.base") + "\\resources\\";
        String loc;
        ControllerLingua locale;
        HttpSession session = request.getSession();
        if (session.getAttribute("loc") == null || session.getAttribute("loc").equals("")) {
            loc = "en-US";
            locale = new ControllerLingua(Locale.forLanguageTag(loc));
            session.setAttribute("locale", locale);
            session.setAttribute("loc", loc);
        } else {
            loc = (String) session.getAttribute("loc");
            locale = new ControllerLingua(Locale.forLanguageTag(loc));
            session.setAttribute("locale", locale);
        }
        System.out.println("operazione eseguita: " + operazione);
        /*
         * Processo
         */
        if (operazione.equals("formInserisciProcesso")) {
            forward(request, response, "/inserisciProcesso.jsp");
        } else if (operazione.equals("inserisciProcesso")) {
            inserisciProcesso(request,response,session,locale);
        } else if (operazione.equals("mostraProcesso")) {
            int idProcesso = Integer.parseInt(request.getParameter("idProcesso"));
            mostraProcesso(request,response,idProcesso);
        } else if (operazione.equals("mostraTuttiProcessi")) {
            ArrayList<Processo> processo = ControllerDatabase.prendiTuttiProcessi();
            request.setAttribute("processo", processo);
            forward(request, response, "/visualizzaTuttiProcessi.jsp");
        } else if (operazione.equals("mostraTuttiProcessiModifica")) {
            mostraTuttiProcessi(request,response,session);
        } else if (operazione.equals("queryProcesso")) {
            String content = HTMLProcesso.listaQueryProcesso();
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/processo.jsp");
        } else if (operazione.equals("formCercaProcessi")) {
            String content = HTMLProcesso.formCercaProcessi(path, loc);
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/processo.jsp");
        } else if (operazione.equals("cercaProcesso")) {
            Processo p = ControllerProcesso.inputProcesso(request, locale);
            Ubicazione u = ControllerUbicazione.inputUbicazione(request);
            System.out.println("id ubicazione servlet" + u.getLocAmm().getIdComune());
            ArrayList<Processo> ap = ControllerDatabase.ricercaProcesso(p, u);
            request.setAttribute("processo", ap);
            forward(request, response, "/visualizzaTuttiProcessi.jsp");
            
        } else if (operazione.equals("mostraModificaProcesso")) {
            int idProcesso = Integer.parseInt(request.getParameter("idProcesso"));
            Processo p = ControllerDatabase.prendiProcesso(idProcesso);
            request.setAttribute("processo", p);
            forward(request, response, "/modificaProcesso.jsp");

        } else if (operazione.equals("modificaProcesso")) {
            Utente user = (Utente) session.getAttribute("partecipante");
            Processo p = ControllerProcesso.modificaProcesso(request, locale, user);
            mostraProcesso(request, response,p.getIdProcesso());
        } else if (operazione.equals("eliminaProcesso")) {
            ControllerProcesso.eliminaProcesso(request);
        } else if (operazione.equals("mostraProcessiMaps")) {
            forward(request, response, "/mappaProcessi.jsp");
        } else if (operazione.equals("formRicercaSingola")) {
            String attributi = request.getParameter("attributi");
            String content = HTMLProcesso.formCercaSingola(attributi, path, loc);
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/processo.jsp");
        } else if (operazione.equals("formRicercaProcessoPerStagione")) {
            forward(request, response, "/ricercaStagione.jsp");

        } else if (operazione.equals("ricercaProcessoPerStagione")) {
            String stagione = request.getParameter("stagione");
            ArrayList<Processo> processo = ControllerDatabase.prendiTuttiProcessiStagioni(stagione);
            request.setAttribute("processo", processo);
            forward(request, response, "/visualizzaTuttiProcessi.jsp");

        } else if (operazione.equals("formRicercaProcessoPerMese")) {
            forward(request, response, "/ricercaMese.jsp");

        } else if (operazione.equals("ricercaProcessoPerMese")) {
            String mese = request.getParameter("mese");
            ArrayList<Processo> processo = ControllerDatabase.prendiTuttiProcessiMese(mese);
            request.setAttribute("processo", processo);
            forward(request, response, "/visualizzaTuttiProcessi.jsp");

        } /*
         * Stazione metereologica
         */ else if (operazione.equals("formInserisciStazione")) {
            forward(request, response, "/inserisciStazione.jsp");
        } else if (operazione.equals("inserisciStazione")) {
            Utente part = (Utente) session.getAttribute("partecipante");
            Ubicazione u = ControllerUbicazione.nuovaUbicazione(request);
            //ControllerDatabase.salvaUbicazione(u);

            StazioneMetereologica s = ControllerStazioneMetereologica.nuovaStazioneMetereologica(request, loc, u, part);
            ControllerDatabase.salvaStazione(s, part);
            String content = HTMLStazioneMetereologica.mostraStazioneMetereologica(s.getIdStazioneMetereologica(), locale);
            String op = "inserita stazione";
            ControllerUtente.aggiornaTracciaStazione(part, s, op);
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/stazione.jsp");

        } else if (operazione.equals("mostraStazioneMetereologica")) {
            int idStazioneMetereologica = Integer.parseInt(request.getParameter("idStazioneMetereologica"));
            StazioneMetereologica stazione = ControllerDatabase.prendiStazioneMetereologica(idStazioneMetereologica);
            Ubicazione ubicazione = stazione.getUbicazione();
            ArrayList<Allegato> allegati = ControllerDatabase.cercaAllegatoStazione(idStazioneMetereologica);
            request.setAttribute("ubicazione", ubicazione);
            stazione.setAllegati(allegati);
            request.setAttribute("stazione", stazione);
            forward(request, response, "/visualizzaStazione.jsp");
        } else if (operazione.equals("mostraTutteStazioniMetereologiche")) {
            Utente part = (Utente) session.getAttribute("partecipante");
            ArrayList<StazioneMetereologica> stazione = ControllerDatabase.prendiTutteStazioniMetereologiche();
            request.setAttribute("stazione", stazione);
            forward(request, response, "/visualizzaTutteStazioni.jsp");
        } else if (operazione.equals("ricercaStazione")) {

            Ubicazione u = ControllerUbicazione.inputUbicazione(request);
            Utente part = (Utente) session.getAttribute("partecipante");

            StazioneMetereologica s = ControllerStazioneMetereologica.nuovaStazioneMetereologica(request, loc, u, part);
            ArrayList<StazioneMetereologica> ap = ControllerDatabase.ricercaStazioneMetereologica(s, u);
            String content = HTMLStazioneMetereologica.mostraCercaStazioneMetereologica(ap);
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/stazione.jsp");
        } else if (operazione.equals("formRicercaStazione")) {
            String content = HTMLStazioneMetereologica.formRicercaMetereologica(path);
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/stazione.jsp");
        } else if (operazione.equals("elencoStazioneModifica")) {
            String content = HTMLStazioneMetereologica.scegliStazioneModifica();
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/stazione.jsp");
        } else if (operazione.equals("modificaStazione")) {
            StazioneMetereologica s = ControllerDatabase.prendiStazioneMetereologica(Integer.parseInt(request.getParameter("idStazioneMetereologica")));
            Ubicazione ubicazione = s.getUbicazione();
            request.setAttribute("stazione", s);
            request.setAttribute("ubicazione", ubicazione);
            forward(request, response, "/modificaStazione.jsp");
        } else if (operazione.equals("inserisciStazioneModificata")) {

            Ubicazione u = ControllerUbicazione.inputUbicazione(request);
            //ControllerDatabase.salvaUbicazione(u);
            Utente part = (Utente) session.getAttribute("partecipante");
            int idStazione = Integer.parseInt(request.getParameter("idStazione"));
            StazioneMetereologica s = ControllerStazioneMetereologica.nuovaStazioneMetereologica(request, loc, u, part);
            s.setIdStazioneMetereologica(idStazione);
            System.out.println("id:"+idStazione);
            String enteVecchio = request.getParameter("enteVecchio");
                        System.out.println("ente:"+enteVecchio);

            s.getUbicazione().setIdUbicazione(ControllerDatabase.getIdUbicazioneStazione(idStazione));

            ControllerDatabase.modificaStazioneMetereologica(s, enteVecchio, idStazione);
            String content = HTMLStazioneMetereologica.mostraStazioneMetereologica(idStazione, locale);
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/stazione.jsp");
        } else if (operazione.equals("queryStazione")) {
            String content = HTMLStazioneMetereologica.listaQueryStazione();
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/stazione.jsp");
        } else if (operazione.equals("mostraStazioniMaps")) {

            forward(request, response, "/mappaStazioni.jsp");
        } else if (operazione.equals("eliminaStazione")) {

            int id = Integer.parseInt(request.getParameter("idstazione"));
            System.out.println(request.getParameter("idstazione"));
            StazioneMetereologica s = ControllerDatabase.prendiStazioneMetereologica(id);
            ControllerDatabase.eliminaStazione(id, s.getUbicazione().getIdUbicazione());

        } //elaborazioni
        else if (operazione.equals("scegliRaggio")) {
            int idProcesso = Integer.parseInt(request.getParameter("idProcesso"));
            request.setAttribute("idProcesso", idProcesso);
            //Processo p  = ControllerDatabase.prendiProcesso(idProcesso);
            String content = HTMLProcesso.mostraCerchioProcesso(idProcesso, loc);
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/processo.jsp");
        } else if (operazione.equals("mostraStazioniRaggio")) {
            int idProcesso = Integer.parseInt(request.getParameter("id"));
            //Integer.parseInt(request.getParameter("id"));
            Processo p = ControllerDatabase.prendiProcesso(idProcesso);
            int raggio = Integer.parseInt(request.getParameter("raggio"));
            String content = HTMLProcesso.mostraStazioniRaggio(p, loc, raggio);
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("idProcesso", idProcesso);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/processo.jsp");

        } else if (operazione.equals("scegliTemperature") || operazione.equals("scegliDeltaT") || operazione.equals("scegliPrecipitazioni")) {
            String[] id = request.getParameterValues("id");
            int idProcesso = Integer.parseInt(request.getParameter("idProcesso"));
            ArrayList<StazioneMetereologica> stazione = new ArrayList<StazioneMetereologica>();
            String content = "";

            for (int i = 0; i < id.length; i++) {
                StazioneMetereologica s = new StazioneMetereologica();
                s.setNome(ControllerDatabase.prendiNome(Integer.parseInt(id[i])));
                s.setIdStazioneMetereologica(Integer.parseInt(id[i]));
                stazione.add(s);
            }
            session.setAttribute("stazione", stazione);
            if (operazione.equals("scegliTemperature")) {
                content = HTMLStazioneMetereologica.temperatureDaProcesso(stazione, idProcesso);
            } else if (operazione.equals("scegliDeltaT")) {
                content = HTMLStazioneMetereologica.deltaTDaProcesso(stazione);
            } else {
                content = HTMLStazioneMetereologica.precipitazioniDaProcesso(stazione);
            }
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("idProcesso", idProcesso);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/elaborazioni.jsp");
        } else if (operazione.equals("scegliStazioniDeltaT")) {
            String content = HTMLStazioneMetereologica.scegliStazioniMetereologicheDeltaT();
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/stazione.jsp");

        } else if (operazione.equals("scegliStazioniT")) {
            String op = "eleborazioniTemperatura";
            String content = HTMLStazioneMetereologica.scegliStazioniMetereologicheT(op);
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/stazione.jsp");

        } else if (operazione.equals("scegliStazioniPrecipitazioni")) {

            String content = HTMLStazioneMetereologica.scegliStazioniMetereologichePrecipitazioni();
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/stazione.jsp");

        } else if (operazione.equals("eleborazioniDeltaT")) {
            ArrayList<StazioneMetereologica> stazione = (ArrayList<StazioneMetereologica>) session.getAttribute("stazione");
            //String [] id= request.getParameterValues("id");
            ArrayList<String> ok = new ArrayList<String>();
            StringBuilder content = new StringBuilder();
            HTMLContent c;
            String[] temperature = request.getParameterValues("temperature");
            int finestra = Integer.parseInt(request.getParameter("finestra"));
            int aggregazione = Integer.parseInt(request.getParameter("aggregazione")) / 2;
            String ora = "00:00:00";
            Timestamp data = Timestamp.valueOf("" + request.getParameter("data") + " " + ora);
            String titolo = "DeltaT";
            String unita = "(Â°C)";
            ArrayList<Grafici> g = ProvaController.deltaT(stazione, finestra, aggregazione, data, locale);
            if (g != null) {

                for (int i = 0; i < g.size(); i++) {
                    if (g.get(i).getOk() == false) {
                        ok.add(g.get(i).getNome());
                    }
                }

                content.append(HTMLElaborazioni.grafici(g, titolo, unita));
                c = new HTMLContent();
                c.setContent(content.toString());
            } else {
                content.append("errore");
                c = new HTMLContent();
                c.setContent(content.toString());
                request.setAttribute("HTMLc", c);

            }
            session.setAttribute("grafici", g);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/elaborazioni.jsp");
        } else if (operazione.equals("eleborazioniTemperatura")) {
            HTMLContent c = new HTMLContent();

            ArrayList<StazioneMetereologica> stazione = (ArrayList<StazioneMetereologica>) session.getAttribute("stazione");
            System.out.println("staz size" + stazione.size());
            String[] tipi = request.getParameterValues("temperature");

            int aggregazione = Integer.parseInt(request.getParameter("aggregazione"));
            String ora = "00:00:00";
            Timestamp data = Timestamp.valueOf("" + request.getParameter("data") + " " + ora);
            ArrayList<Grafici> g = ProvaController.mediaTemperaturaNoGradiente(stazione, aggregazione, data, tipi, locale);
            if (g != null) {
                String titolo = "Temperatura";
                String unita = "(°C)";
                String content = HTMLElaborazioni.grafici(g, titolo, unita);
                c.setContent(content);
                session.setAttribute("grafici", g);

            } else {
                StringBuilder content = new StringBuilder();
                content.append("errore");
                c = new HTMLContent();
                c.setContent(content.toString());
                request.setAttribute("HTMLc", c);

            }

            request.setAttribute("HTMLc", c);

            forward(request, response, "/elaborazioni.jsp");
        } else if (operazione.equals("elaborazioniPrecipitazioni")) {
            //String [] id= request.getParameterValues("id");
            ArrayList<String> ok = new ArrayList<String>();
            StringBuilder content = new StringBuilder();
            HTMLContent c;
            ArrayList<StazioneMetereologica> stazione = (ArrayList<StazioneMetereologica>) session.getAttribute("stazione");
            System.out.println("stazione size" + stazione.size());
            int finestra = Integer.parseInt(request.getParameter("finestra"));
            int aggregazione = Integer.parseInt(request.getParameter("aggregazione"));
            String ora = "00:00:00";
            Timestamp data = Timestamp.valueOf("" + request.getParameter("data") + " " + ora);

            ArrayList<Grafici> g = ProvaController.mediaPrecipitazioni(stazione, finestra, aggregazione / 2, data);
            if (g != null) {
                System.out.println("diverso da null");
                String titolo = "Precipitazioni";
                String unita = "(mm)";
                for (int i = 0; i < g.size(); i++) {
                    if (g.get(i).getOk() == false) {
                        ok.add(g.get(i).getNome());
                    }
                }
                if (ok.size() == 0) {
                    content.append(HTMLElaborazioni.grafici(g, titolo, unita));
                    c = new HTMLContent();
                    c.setContent(content.toString());
                } else {
                    content.append("errore ");
                    for (int i = 0; i < ok.size(); i++) {
                        content.append("" + ok.get(i));
                    }
                    c = new HTMLContent();
                    c.setContent(content.toString());
                }
                session.setAttribute("grafici", g);
                request.setAttribute("HTMLc", c);
            } else {
                content.append("errore");
                c = new HTMLContent();
                c.setContent(content.toString());
                request.setAttribute("HTMLc", c);

            }
            forward(request, response, "/elaborazioni.jsp");
        } else if (operazione.equals("temperatureDaProcesso")) {
            ArrayList<StazioneMetereologica> stazione = (ArrayList<StazioneMetereologica>) session.getAttribute("stazione");
            //String [] id= (String[]) request.getParameterValues("id");
            String[] tipi = request.getParameterValues("temperature");
            int idProcesso = Integer.parseInt(request.getParameter("idProcesso"));
            Processo p = ControllerDatabase.prendiProcesso(idProcesso);
            double gradiente = 0;
            double quota = p.getUbicazione().getQuota();
            if (!(request.getParameter("gradiente").equals(""))) {
                gradiente = Double.parseDouble(request.getParameter("gradiente"));
            } else {
                gradiente = -9999;
            }
            HTMLContent c = new HTMLContent();

            int aggregazione = Integer.parseInt(request.getParameter("aggregazione"));
            String ora = "00:00:00";
            Timestamp data = Timestamp.valueOf("" + request.getParameter("data") + " " + ora);
            ArrayList<Grafici> g = ProvaController.mediaTemperatura(stazione, aggregazione, data, gradiente, quota, tipi, locale);
            if (g != null) {
                String titolo = "Temperatura";
                String unita = "(°C)";
                String content = HTMLElaborazioni.grafici(g, titolo, unita);
                c.setContent(content);
                session.setAttribute("grafici", g);
            } else {
                StringBuilder content = new StringBuilder();
                content.append("errore");
                c = new HTMLContent();
                c.setContent(content.toString());
                request.setAttribute("HTMLc", c);

            }
            request.setAttribute("HTMLc", c);
            forward(request, response, "/elaborazioni.jsp");

        } else if (operazione.equals("stazioniPrecipitazioni")) {
            String[] id = (String[]) request.getParameterValues("id");
            ArrayList<StazioneMetereologica> stazione = new ArrayList<StazioneMetereologica>();
            for (int i = 0; i < id.length; i++) {
                StazioneMetereologica s = ControllerDatabase.prendiStazioneMetereologica(Integer.parseInt(id[i]));
                stazione.add(s);
            }
            session.setAttribute("stazione", stazione);
            String content = HTMLStazioneMetereologica.precipitazioniDaProcesso(stazione);
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/elaborazioni.jsp");
        } else if (operazione.equals("stazioniTemperature")) {
            String[] id = (String[]) request.getParameterValues("id");
            ArrayList<StazioneMetereologica> stazione = new ArrayList<StazioneMetereologica>();
            for (int i = 0; i < id.length; i++) {
                StazioneMetereologica s = ControllerDatabase.prendiStazioneMetereologica(Integer.parseInt(id[i]));
                stazione.add(s);
            }
            session.setAttribute("stazione", stazione);
            String content = HTMLStazioneMetereologica.temperatureDati(stazione);
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/elaborazioni.jsp");
        } else if (operazione.equals("stazioniDeltaT")) {
            String[] id = (String[]) request.getParameterValues("id");
            ArrayList<StazioneMetereologica> stazione = new ArrayList<StazioneMetereologica>();
            for (int i = 0; i < id.length; i++) {
                StazioneMetereologica s = ControllerDatabase.prendiStazioneMetereologica(Integer.parseInt(id[i]));
                stazione.add(s);
            }
            session.setAttribute("stazione", stazione);
            String content = HTMLStazioneMetereologica.deltaTDaProcesso(stazione);
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/elaborazioni.jsp");
        } // upload dati climatici
        else if (operazione.equals("elencocaricaDatiClimatici")) {
            String content = HTMLStazioneMetereologica.caricaDatiMetereologici();
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/stazione.jsp");
        } else if (operazione.equals("caricaDatiClimatici")) {
            int idstazione = Integer.parseInt(request.getParameter("idStazioneMetereologica"));
            Utente part = (Utente) session.getAttribute("partecipante");

            String content = HTMLStazioneMetereologica.UploadCSV(idstazione, part);
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/stazione.jsp");
        } else if (operazione.equals("uploadCSVDatiClimatici")) {
            StringBuilder content = new StringBuilder();
            int idStazione = Integer.parseInt(request.getParameter("idStazioneMetereologica"));
            String data = "00-00-00";
            String ora = "00:00";
            Timestamp dataInizio = null;
            if (!(request.getParameter("data").equals(""))) {
                data = request.getParameter("data");
                if (!(request.getParameter("data").equals(""))) {
                    if (!(request.getParameter("ora") == null)) {
                        if (!(request.getParameter("ora").equals(""))) {
                            ora = request.getParameter("ora");
                        }
                    } else {
                        ora = "00:00";
                    }
                    String dataCompleta = "" + data + " " + ora + ":00";
                    System.out.println("data daala request: " + dataCompleta);
                    dataInizio = Timestamp.valueOf(dataCompleta);
                }
            }
            String tabella = request.getParameter("tabella");
            String attributo = tabella.substring(tabella.indexOf('$') + 1, tabella.length());
            tabella = tabella.substring(0, tabella.indexOf('$'));
            StazioneMetereologica sm = ControllerDatabase.prendiStazioneMetereologica(idStazione);
            String pathStaz = path + "/" + sm.getNome() + "";
            File theDir = new File(pathStaz);
            if (!theDir.exists()) {
                theDir.mkdir();
            }
            List<File> csv = uploadByJavaServletAPI(request, pathStaz);
            content.append("<h4>stazione " + sm.getNome() + "</h4>");
            for (File f : csv) {
                int count = ControllerDatabase.lettoreCSVT(f, tabella, attributo, idStazione, dataInizio);
                content.append("<h5>hai caricato " + count + " dati climatici nella tabella " + tabella + " dal file " + f.getName() + "</h5>");
            }
            HTMLContent c = new HTMLContent();
            c.setContent(content.toString());
            request.setAttribute("HTMLc", c);
            forward(request, response, "/stazione.jsp");
        } else if (operazione.equals("listaElaborazioni")) {
            String content = HTMLElaborazioni.listaElaborazioni();
            HTMLContent c = new HTMLContent();
            c.setContent(content.toString());
            request.setAttribute("HTMLc", c);
            forward(request, response, "/elaborazioni.jsp");
        } //utente
        else if (operazione.equals("formCreaUtente")) {
            String content = HTMLUtente.creaUtente();
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/utente.jsp");
        } else if (operazione.equals("inserisciUtente")) {
            Utente utente = ControllerUtente.nuovoUtente(request, passwordEncryptor);
            String content = "<h2> l'utente " + utente.getUsername() + " ï¿½ stato creato correttamente</h2>";
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/utente.jsp");
        } else if (operazione.equals("formLogin")) {
            String content = HTMLUtente.login();
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "utente.jsp");
        } else if (operazione.equals("login")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (ControllerDatabase.login(username, password, passwordEncryptor)) {

                Utente utente = ControllerDatabase.prendiUtente(username);
                session.setAttribute("partecipante", utente);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(new Gson().toJson("ok"));
            } else {
                String content = "<h2>spiacente, il login non  Ã¨ corretto</h2>";
                HTMLContent c = new HTMLContent();
                c.setContent(content);
                request.setAttribute("HTMLc", c);
                forward(request, response, "/utente.jsp");
            }
        } else if (operazione.equals("logout")) {
            response.setHeader("Cache-Control", "no-cache, no-store");
            response.setHeader("Pragma", "no-cache");
            request.getSession().invalidate();
            if(loc.equals("en-US"))
            response.sendRedirect(request.getContextPath() + "/indexENG.jsp");
            else response.sendRedirect(request.getContextPath() + "/indexENG.jsp");
        } else if (operazione.equals("visualizzaTuttiUtenti")) {
            ArrayList<Utente> utenti = ControllerDatabase.PrendiTuttiUtenti();
            request.setAttribute("utenti", utenti);
            forward(request, response, "/visualizzaTuttiUtenti.jsp");
        } else if (operazione.equals("mostraUtente")) {
            Utente utente = ControllerDatabase.prendiUtente(request.getParameter("user"));
            utente.setOperazioni(ControllerDatabase.prendiOperazioniUtente(utente.getIdUtente()));
            request.setAttribute("utente", utente);
            forward(request, response, "/visualizzaUtente.jsp");
        } else if (operazione.equals("abilitaUtente")) {
            Utente u = new Utente();
            u.setIdUtente(Integer.parseInt(request.getParameter("id")));
            u.setAttivo(Boolean.parseBoolean(request.getParameter("abilitato")));
            ControllerDatabase.abilita(u);
        } //query
        else if (operazione.equals("queryClimatiche")) {
            String content = HTMLElaborazioni.sceltaQuery();
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/elaborazioni.jsp");

        } else if (operazione.equals("datiQueryPrecipitazioniAnno") || operazione.equals("datiQueryTemperaturaAnno")) {
            String[] id = request.getParameterValues("id");
            String content;

            ArrayList<StazioneMetereologica> stazione = new ArrayList<StazioneMetereologica>();
            for (int i = 0; i < id.length; i++) {
                StazioneMetereologica s = new StazioneMetereologica();
                s.setNome(ControllerDatabase.prendiNome(Integer.parseInt(id[i])));
                s.setIdStazioneMetereologica(Integer.parseInt(id[i]));
                stazione.add(s);
            }
            if (operazione.equals("datiQueryPrecipitazioniAnno")) {
                content = HTMLElaborazioni.datiAnno(stazione, "queryPrecipitazioniAnno");
            } else {
                content = HTMLElaborazioni.datiAnno(stazione, "queryTemperaturaAnno");
            }

            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/elaborazioni.jsp");
        } else if (operazione.equals("queryPrecipitazioniAnno")) {
            String[] id = request.getParameterValues("id");
            //String aggregazione=request.getParameter("aggregazione");
            String anno = request.getParameter("anno");
            String content = ControllerDatiClimatici.precipitazioniQuery(anno, id);
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/elaborazioni.jsp");
        } else if (operazione.equals("precipitazioniMese") || operazione.equals("precipitazioniTrimestre") || operazione.equals("precipitazioniAnno") || operazione.equals("temperaturaEPrecipitazioneAnno") || operazione.equals("temperaturaAnno") || operazione.equals("temperaturaTrimestre")) {
            String op;
            String tabella = "";
            if (operazione.equals("precipitazioniMese")) {
                op = "datiQueryPrecipitazioniMese";
                tabella = "where idstazionemetereologica in(select distinct(idstazionemetereologica) from precipitazione)";
            } else if (operazione.equals("precipitazioniTrimestre")) {
                op = "datiQueryPrecipitazioniTrimestre";
                tabella = "where idstazionemetereologica in(select distinct(idstazionemetereologica) from precipitazione)";
            } else if (operazione.equals("precipitazioniAnno")) {
                op = "datiQueryPrecipitazioniAnno";
                tabella = "where idstazionemetereologica in(select distinct(idstazionemetereologica) from precipitazione)";
            } else if (operazione.equals("temperaturaAnno")) {
                op = "datiQueryTemperaturaAnno";
                tabella = "where staz.idstazionemetereologica in(Select distinct(idstazionemetereologica) as id from temperatura_max "
                        + "                            UNION ALL Select distinct(idstazionemetereologica) as id From temperatura_min union all  select distinct (idstazionemetereologica) as id From temperatura_avg)";
            } else if (operazione.equals("temperaturaTrimestre")) {
                op = "datiQueryTemperaturaTrimestre";
                tabella = "where staz.idstazionemetereologica in(Select distinct(idstazionemetereologica) as id from temperatura_max "
                        + "                            UNION ALL Select distinct(idstazionemetereologica) as id From temperatura_min union all  select distinct (idstazionemetereologica) as id From temperatura_avg)";
            } else {
                op = "datiTemperaturaEPrecipitazioneAnno";
                tabella = "where staz.idstazionemetereologica in(Select distinct(idstazionemetereologica) as id from temperatura_max "
                        + "        UNION ALL Select distinct(idstazionemetereologica) as id From temperatura_min union all  select distinct (idstazionemetereologica) as id From temperatura_avg"
                        + " UNION ALL Select distinct(idstazionemetereologica) as id From precipitazione)";
            }
            String content = HTMLStazioneMetereologica.scegliStazioniQuery(op, tabella);
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/elaborazioni.jsp");
        } else if (operazione.equals("datiQueryPrecipitazioniMese")) {
            String[] id = request.getParameterValues("id");
            ArrayList<StazioneMetereologica> stazione = new ArrayList<StazioneMetereologica>();
            for (int i = 0; i < id.length; i++) {
                StazioneMetereologica s = new StazioneMetereologica();
                s.setNome(ControllerDatabase.prendiNome(Integer.parseInt(id[i])));
                s.setIdStazioneMetereologica(Integer.parseInt(id[i]));
                stazione.add(s);
            }
            String op = "queryPrecipitazioniMese";
            String content = HTMLElaborazioni.DatiTrimestre(stazione, op);
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/elaborazioni.jsp");
        } else if (operazione.equals("queryPrecipitazioniMese")) {
            String[] id = request.getParameterValues("id");
            String mese = request.getParameter("mese");
            String anno = request.getParameter("anno");
            String content = ControllerDatiClimatici.precipitazioniQueryMese(mese, anno, id);
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/elaborazioni.jsp");
        } else if (operazione.equals("datiQueryPrecipitazioniTrimestre") || operazione.equals("datiQueryTemperaturaTrimestre")) {
            String[] id = request.getParameterValues("id");
            ArrayList<StazioneMetereologica> stazione = new ArrayList<StazioneMetereologica>();
            for (int i = 0; i < id.length; i++) {
                StazioneMetereologica s = new StazioneMetereologica();
                s.setNome(ControllerDatabase.prendiNome(Integer.parseInt(id[i])));
                s.setIdStazioneMetereologica(Integer.parseInt(id[i]));
                stazione.add(s);
            }
            String op;
            if (operazione.equals("datiQueryPrecipitazioniTrimestre")) {
                op = "queryPrecipitazioniTrimestre";
            } else {
                op = "queryTemperaturaTrimestre";
            }
            String content = HTMLElaborazioni.DatiTrimestre(stazione, op);
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/elaborazioni.jsp");
        } else if (operazione.equals("queryPrecipitazioniTrimestre") || operazione.equals("queryTemperaturaTrimestre")) {
            String[] id = request.getParameterValues("id");
            String mese = request.getParameter("mese");
            String anno = request.getParameter("anno");
            String content;
            String titolo;
            String unita;
            ArrayList<Grafici> g = new ArrayList<Grafici>();
            if (operazione.equals("queryPrecipitazioniTrimestre")) {
                g = ControllerDatiClimatici.precipitazioniQueryTrimestre(mese, anno, id);
                titolo = "precipitazioni";
                unita = "mm";
                ArrayList<Dati> d = new ArrayList<Dati>();//guarda!!
                content = HTMLElaborazioni.graficiMultipliPrecipitazioni(g, "column", titolo, unita, unita, titolo, "cumulata", anno, mese, d);
            } else {
                g = ControllerDatiClimatici.queryTemperaturaTrimestre(mese, anno, id);
                content = HTMLElaborazioni.graficiCategorie(g, "line", "temperatura", "C", anno, mese);
            }

            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/elaborazioni.jsp");
        } else if (operazione.equals("datiTemperaturaEPrecipitazioneAnno")) {
            String[] id = request.getParameterValues("id");
            ArrayList<StazioneMetereologica> stazione = new ArrayList<StazioneMetereologica>();
            for (int i = 0; i < id.length; i++) {
                StazioneMetereologica s = new StazioneMetereologica();
                s.setNome(ControllerDatabase.prendiNome(Integer.parseInt(id[i])));
                s.setIdStazioneMetereologica(Integer.parseInt(id[i]));
                stazione.add(s);
            }
            String content = HTMLElaborazioni.datiAnno(stazione, "queryPrecipitazioniTemperaturaAnno");
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/elaborazioni.jsp");
        } else if (operazione.equals("queryPrecipitazioniTemperaturaAnno")) {
            String[] id = request.getParameterValues("id");
            //String aggregazione=request.getParameter("aggregazione");
            String anno = request.getParameter("anno");
            String content = ControllerDatiClimatici.precipitazioniTemperaturaQueryAnno(anno, id);
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/elaborazioni.jsp");
        } else if (operazione.equals("queryTemperaturaAnno")) {
            String[] id = request.getParameterValues("id");
            //String aggregazione=request.getParameter("aggregazione");
            String anno = request.getParameter("anno");
            ArrayList<Grafici> g = ControllerDatiClimatici.queryTemperaturaAnno(anno, id);

            String content = HTMLElaborazioni.graficiCategorie(g, "line", "temperatura", "C", anno, "1");
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/elaborazioni.jsp");
        } //download
        else if (operazione.equals("download")) {

            ArrayList<Grafici> g = (ArrayList<Grafici>) session.getAttribute("grafici");
            session.setAttribute("grafici", g);
            String titolo = request.getParameter("titolo");
            String test = "/" + titolo + ".csv";
            FileWriter outFile = new FileWriter(test, false);
            PrintWriter out = new PrintWriter(outFile);
            int cont = 0;
            for (Grafici gra : g) {

                out.print("" + gra.getNome() + ";\n");
                out.print("\n");
                for (int i = 0; i < gra.getX().size(); i++) {
                    if (i != (gra.getX().size() - 1)) {
                        if ((gra.getX().get(i).equals(gra.getX().get(i + 1))) == false) {
                            out.println("" + gra.getX().get(i) + ";" + gra.getY().get(cont) + ";");
                            cont++;
                        }
                    } else {
                        out.println("" + gra.getX().get(i) + ";" + gra.getY().get(cont) + ";");
                        cont++;
                    }

                }
                cont = 0;
                out.print("\n");
            }
          
            out.close();

            File downloadFile = new File(test);
            FileInputStream inStream = new FileInputStream(downloadFile);

            // obtains ServletContext
            ServletContext context = getServletContext();

            // gets MIME type of the file
            String mimeType = context.getMimeType(test);
            if (mimeType == null) {
                // set to binary type if MIME mapping not found
                mimeType = "application/octet-stream";
            }

            // modifies response
            response.setContentType(mimeType);
            response.setContentLength((int) downloadFile.length());

            // forces download
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);

            // obtains response's output stream
            OutputStream outStream = response.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            inStream.close();
            outStream.close();
        }
         else if (operazione.equals("downloadPdf")) {
          
         String id=request.getParameter("processoPdf");
         int idp=Integer.parseInt(id);
	Processo p=ControllerDatabase.prendiProcesso(idp);
		ServletOutputStream out = response.getOutputStream();
                
		
                 ByteArrayOutputStream baos = HTMLProcesso.createPDFProcesso(p);
           
       
 
            // setting some response headers
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control",
                "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            // setting the content type
            response.setContentType("application/pdf");
            // the contentlength
            response.setContentLength(baos.size());
            // write ByteArrayOutputStream to the ServletOutputStream
            OutputStream os = response.getOutputStream();
            baos.writeTo(out);
            os.flush();
            os.close();
 
	  
   
        }
        
        
        else if (operazione.equals("scegliProcessoAllegato")) {
            String content = HTMLProcesso.mostraTuttiProcessiAllega(locale);
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/processo.jsp");
        } else if (operazione.equals("formAllegatoProcesso")) {
            int idprocesso = Integer.parseInt(request.getParameter("idprocesso"));
            Utente part = (Utente) session.getAttribute("partecipante");

            String content = HTMLProcesso.formAllegatoProcesso(idprocesso, part,locale);
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/processo.jsp");
        } else if (operazione.equals("uploadAllegatoProcesso")) {
            String uploadPath = path + "\\" + "allegatiProcesso";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            int idProcesso = Integer.parseInt(request.getParameter("idprocesso"));
            Processo p = ControllerDatabase.prendiProcesso(idProcesso);
            String autore = "";
            String anno = "";
            String titolo = "";
            String in = "";
            String fonte = "";
            String urlWeb = "";
            String note = "";
            String tipo = "";
            Utente part = (Utente) session.getAttribute("partecipante");
            String uploadPathProcesso = path + "\\" + "allegatiProcesso\\" + "" + p.getNome() + "";
            File uploadDirP = new File(uploadPathProcesso);
            if (!uploadDirP.exists()) {
                uploadDirP.mkdir();
            }

            List<File> uploadFile = uploadByJavaServletAPI(request, uploadPathProcesso);
            if (!(uploadFile.isEmpty())) {
                for (File f : uploadFile) {
                    autore = request.getParameter("autore");
                    anno = request.getParameter("anno");
                    titolo = request.getParameter("titolo");
                    in = request.getParameter("in");
                    fonte = request.getParameter("fonte");
                    urlWeb = request.getParameter("urlWeb");
                    note = request.getParameter("note");
                    tipo = request.getParameter("tipo");
                    ControllerDatabase.salvaAllegatoProcesso(idProcesso, part.getIdUtente(), autore, anno, titolo, in, fonte, urlWeb, note, tipo, "\\resources\\" + "allegatiProcesso\\" + "" + p.getNome() + "");
                }
            }
            String content = "<h5>allegato il file per il proceso: " + p.getNome() + "</h5>";
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/processo.jsp");
        } else if (operazione.equals("scegliStazioneAllegato")) {
            String content = HTMLStazioneMetereologica.scegliStazioneAllegati(locale);
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/stazione.jsp");
        } else if (operazione.equals("formAllegatoStazione")) {
            int idstazione = Integer.parseInt(request.getParameter("idstazione"));
            Utente part = (Utente) session.getAttribute("partecipante");

            String content = HTMLStazioneMetereologica.formAllegatoStazione(idstazione, part, locale);
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/stazione.jsp");
        } else if (operazione.equals("uploadAllegatoStazione")) {
            String uploadPath = path + "\\" + "allegatiStazione";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            int idstazione = Integer.parseInt(request.getParameter("idstazione"));
            StazioneMetereologica sm = ControllerDatabase.prendiStazioneMetereologica(idstazione);
            String autore = "";
            String anno = "";
            String titolo = "";
            String in = "";
            String fonte = "";
            String urlWeb = "";
            String note = "";
            String tipo = "";
            Utente part = (Utente) session.getAttribute("partecipante");
            String uploadPathStazione = path + "\\" + "allegatiStazione\\" + "" + sm.getNome() + "";
            File uploadDirP = new File(uploadPathStazione);
            if (!uploadDirP.exists()) {
                uploadDirP.mkdir();
            }

            List<File> uploadFile = uploadByJavaServletAPI(request, uploadPathStazione);
            if (!(uploadFile.isEmpty())) {
                for (File f : uploadFile) {
                    autore = request.getParameter("autore");
                    anno = request.getParameter("anno");
                    titolo = request.getParameter("titolo");
                    in = request.getParameter("in");
                    fonte = request.getParameter("fonte");
                    urlWeb = request.getParameter("urlWeb");
                    note = request.getParameter("note");
                    tipo = request.getParameter("tipo");
                    ControllerDatabase.salvaAllegatoStazione(idstazione, part.getIdUtente(), autore, anno, titolo, in, fonte, urlWeb, note, tipo, "\\resources\\"+ "allegatiStazione\\" + "" + sm.getNome() + "");
                }
            }
            String content = "<h5>allegato il file per la stazione: " + sm.getNome() + "</h5>";
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/stazione.jsp");

        }  else if (operazione.equals("ricaricaJson")) {
            ControllerJson.creaJson(path);
            if(loc.equals("en-US"))
            forward(request, response, "/indexENG.jsp");
            else forward(request, response, "/indexENG.jsp");
        } else if (operazione.equals("changeLanguage")) {
            loc = request.getParameter("loc");
            locale = new ControllerLingua(Locale.forLanguageTag(loc));
            session.setAttribute("loc", loc);
            session.setAttribute("locale", locale);
        } else if (operazione.equals("downloadAllegato")) {
            OutputStream out = response.getOutputStream();
            String file = request.getParameter("file");
            FileInputStream in = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.flush();
        }
        else if(operazione.equals("mostraAllegatiProcesso")){
            int idProcesso=Integer.parseInt(request.getParameter("idProcesso"));
            ArrayList<Allegato> allegati=ControllerDatabase.cercaAllegatoProcesso(idProcesso);
            request.setAttribute("allegati",allegati);
             forward(request, response, "/allegatiProcesso.jsp");
        }
        else if(operazione.equals("modificaAllegato")){
            int idAllegato=Integer.parseInt(request.getParameter("idAllegato"));
            Allegato allegato=ControllerDatabase.cercaAllegato(idAllegato);
            request.setAttribute("allegato",allegato);
            forward(request, response, "/modificaAllegato.jsp");
        }
        else if(operazione.equals("modificaAllegatoSuDB")){
            int idAllegato = Integer.parseInt(request.getParameter("idAllegato"));
            Allegato a = new Allegato();
            a.setAutore(request.getParameter("autore"));
            a.setAnno(request.getParameter("anno"));
            a.setTitolo(request.getParameter("titolo"));
            a.setNella(request.getParameter("in"));
            a.setFonte(request.getParameter("fonte"));
            a.setUrlWeb(request.getParameter("urlWeb"));
            a.setNote(request.getParameter("note"));
            a.setTipoAllegato(request.getParameter("tipo"));
            a.setId(idAllegato);
            ControllerDatabase.modificaAllegato(a);
            String content = "<h5> modificato allegato </h5>";
            HTMLContent c = new HTMLContent();
            c.setContent(content);
            request.setAttribute("HTMLc", c);
            forward(request, response, "/processo.jsp");
        }
        else if(operazione.equals("mostraDatiClimaticiPrecipitazione")){
            int idStazione=Integer.parseInt(request.getParameter("idStazione"));
            ArrayList<datoClimatico> dati=ControllerDatabase.prendiDatiClimaticiPrecipitazioni(idStazione);
            request.setAttribute("dati",dati);
            request.setAttribute("id",idStazione);
            String op="precipitazione";
            request.setAttribute("op",op);
            forward(request, response, "/dati.jsp");            
        }
        else if(operazione.equals("mostraDatiClimaticiAvg")){
            int idStazione=Integer.parseInt(request.getParameter("idStazione"));
            ArrayList<datoClimatico> dati=ControllerDatabase.prendiDatiClimaticiAvg(idStazione);
            request.setAttribute("dati",dati);
            request.setAttribute("id",idStazione);
            String op="avg";
            request.setAttribute("op",op);
            forward(request,response,"/dati.jsp");
            
        }
        else if(operazione.equals("mostraDatiClimaticiMin")){
            int idStazione=Integer.parseInt(request.getParameter("idStazione"));
            ArrayList<datoClimatico> dati=ControllerDatabase.prendiDatiClimaticiMin(idStazione);
            request.setAttribute("dati",dati);
            request.setAttribute("id",idStazione);
            String op="min";
            request.setAttribute("op",op);
            forward(request,response,"/dati.jsp");
            
        }
         else if(operazione.equals("mostraDatiClimaticiMax")){
            int idStazione=Integer.parseInt(request.getParameter("idStazione"));
            ArrayList<datoClimatico> dati=ControllerDatabase.prendiDatiClimaticiMax(idStazione);
            request.setAttribute("dati",dati);
            request.setAttribute("id",idStazione);
            String op="max";
            request.setAttribute("op",op);

            forward(request,response,"/dati.jsp");
            
        }else if(operazione.equals("scaricaDatiClimatici")){
            int idStazione=Integer.parseInt(request.getParameter("idStazione"));
            String op=request.getParameter("op");
             ArrayList<datoClimatico> dati=new ArrayList<datoClimatico> ();
            if(op.equals("min"))    dati=ControllerDatabase.prendiDatiClimaticiMin(idStazione);
            else if(op.equals("max")) dati=ControllerDatabase.prendiDatiClimaticiMax(idStazione);
             else if(op.equals("avg")) dati=ControllerDatabase.prendiDatiClimaticiAvg(idStazione);
             else if(op.equals("precipitazione")) dati=ControllerDatabase.prendiDatiClimaticiPrecipitazioni(idStazione);
           
            
            String test = "/" + op + ".csv";
            FileWriter outFile = new FileWriter(test, false);
            PrintWriter out = new PrintWriter(outFile);
            for (datoClimatico d : dati) {
                            out.println("" + d.getDato() + ";"+d.getData()+";" );     
                }
              
          
            out.close();

            File downloadFile = new File(test);
            FileInputStream inStream = new FileInputStream(downloadFile);

            // obtains ServletContext
            ServletContext context = getServletContext();

            // gets MIME type of the file
            String mimeType = context.getMimeType(test);
            if (mimeType == null) {
                // set to binary type if MIME mapping not found
                mimeType = "application/octet-stream";
            }

            // modifies response
            response.setContentType(mimeType);
            response.setContentLength((int) downloadFile.length());

            // forces download
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);

            // obtains response's output stream
            OutputStream outStream = response.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            inStream.close();
            outStream.close();
           
        }else if(operazione.equals("allegatiStazione")){
            int idStazione=Integer.parseInt(request.getParameter("idStazioneMetereologica"));
            ArrayList<Allegato> allegati=ControllerDatabase.cercaAllegatoStazione(idStazione);
            request.setAttribute("allegati",allegati);
             forward(request, response, "/allegatiProcesso.jsp");
        }else if(operazione.equals("visualizzaAllegato")){
              int idAllegato=Integer.parseInt(request.getParameter("idAllegato"));
               Allegato allegato=ControllerDatabase.cercaAllegato(idAllegato);
               request.setAttribute("allegato",allegato);
             forward(request, response, "/allegato.jsp");
        }


    }

    private void forward(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(page);
        rd.forward(request, response);
    }

    public static List<File> uploadByJavaServletAPI(HttpServletRequest request, String path) throws IOException, ServletException {
        OutputStream out = null;
        InputStream filecontent = null;
        List<File> files = new LinkedList<File>();
        Collection<Part> parts = request.getParts();
        for (Part part : parts) {
            if (part.getContentType() != null) {
                try {
                    String fileName = getFilename(part);
                    File file = new File(path + File.separator + fileName);
                    out = new FileOutputStream(file);
                    filecontent = part.getInputStream();
                    int read = 0;
                    final byte[] bytes = new byte[1024];
                    while ((read = filecontent.read(bytes)) != -1) {
                        out.write(bytes, 0, read);
                    }
                    files.add(file);
                } catch (FileNotFoundException fne) {

                } finally {
                    if (out != null) {
                        out.close();
                    }
                    if (filecontent != null) {
                        filecontent.close();
                    }
                }
            }
        }
        return files;
    }

    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    private void inserisciProcesso(HttpServletRequest request, HttpServletResponse response, HttpSession session,ControllerLingua locale) throws ServletException, IOException, ParseException, SQLException {
        Utente user = (Utente) session.getAttribute("partecipante");
        Processo p = ControllerProcesso.nuovoProcesso(request, locale, user);
        String op = "inserito processo";
        ControllerUtente.aggiornaTracciaProcesso(user, p, op);
        mostraProcesso(request, response, p.getIdProcesso());
    }
    private void mostraProcesso(HttpServletRequest request, HttpServletResponse response, int idProcesso) throws SQLException, ServletException, IOException {
        Processo processo = ControllerDatabase.prendiProcesso(idProcesso);
        Ubicazione ubicazione = processo.getUbicazione();
        ArrayList<Allegato> allegati = ControllerDatabase.cercaAllegatoProcesso(idProcesso);
        request.setAttribute("ubicazione", ubicazione);
        processo.getAttributiProcesso().setAllegati(allegati);
        request.setAttribute("processo", processo);
        forward(request, response, "/visualizzaProcesso.jsp");
    }

    private void mostraTuttiProcessi(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException, ServletException, IOException {
        Utente part = (Utente) session.getAttribute("partecipante");
        ArrayList<Processo> processo = ControllerDatabase.prendiTuttiProcessi();
        request.setAttribute("processo", processo);
        forward(request, response, "/visualizzaTuttiProcessi.jsp");
    }

}
