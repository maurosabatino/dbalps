package it.cnr.to.geoclimalp.dbalps.controller;



import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import it.cnr.to.geoclimalp.dbalps.bean.processo.*;
import it.cnr.to.geoclimalp.dbalps.bean.processo.attributiProcesso.*;
import it.cnr.to.geoclimalp.dbalps.bean.Utente.Utente;




public class ControllerProcesso {

  public static Processo inputProcesso(HttpServletRequest request,ControllerLingua loc) throws ParseException, SQLException {//qui creo le parti solo del processo
    Processo p = new ProcessoCompleto();
    if (!(request.getParameter("nome") == null)){
      p.setNome(request.getParameter("nome"));
    }
    String data = "";
    String ora = "00:00";
    StringBuilder formatoData = new StringBuilder();
    String anno = "0000";
    String mese = "01";
    String giorno = "01";

    if (!(request.getParameter("anno") == null)) {
      if (!(request.getParameter("anno").equals(""))) {
        formatoData.append("1");
        anno = request.getParameter("anno");
      } else {
        formatoData.append("0");
      }
    } else {
      formatoData.append("0");
    }

    if (!(request.getParameter("mese") == null)) {
      if (!(request.getParameter("mese").equals("vuoto"))) {
        mese = request.getParameter("mese");
        formatoData.append("1");
      } else {
        formatoData.append("0");
      }
    } else {
      formatoData.append("0");
    }

    if (!(request.getParameter("giorno") == null)) {
      if (!(request.getParameter("giorno").equals("vuoto"))) {
        giorno = request.getParameter("giorno");
        formatoData.append("1");
      } else {
        formatoData.append("0");
      }
    } else {
      formatoData.append("0");
    }
    data = "" + anno + "-" + mese + "-" + giorno + "";
    if (!(request.getParameter("ora") == null)) {
      if (!(request.getParameter("ora").equals(""))) {
        ora = request.getParameter("ora");
        formatoData.append("1");
      } else {
        formatoData.append("0");
      }
    } else {
      formatoData.append("0");
    }
    String dataCompleta = "" + data + " " + ora + ":00";
    p.setData((Timestamp.valueOf(dataCompleta)));
    if (!(formatoData.toString().equals(""))) {
      p.setFormatoData(Integer.parseInt(formatoData.toString()));
    }
    p.setAttributiProcesso(inputAttributiProcesso(request, loc));
    return p;
  }
  
  public static AttributiProcesso inputAttributiProcesso(HttpServletRequest request, ControllerLingua loc) throws SQLException{
    AttributiProcesso  ap = new AttributiProcesso();
    if (!(request.getParameter("descrizione") == null)) {
      String descrizione = request.getParameter("descrizione");
      ap.setDescrizione(descrizione);
    }
    if (!(request.getParameter("note") == null)) {
      ap.setNote(request.getParameter("note"));
    }
    if (!(request.getParameter("altezza") == null)) {
      if (!(request.getParameter("altezza").equals(""))) {
        ap.setAltezza(Double.parseDouble(request.getParameter("altezza")));
      }
    }
    if (!(request.getParameter("larghezza") == null)) {
      if (!(request.getParameter("larghezza").equals(""))) {
        ap.setLarghezza(Double.parseDouble(request.getParameter("larghezza")));
      }
    }
    if (!(request.getParameter("superficie") == null)) {
      if (!(request.getParameter("superficie").equals(""))) {
        ap.setSuperficie(Double.parseDouble(request.getParameter("superficie")));
      }
    }
    if (!(request.getParameter("volumespecifico") == null)) {
      if (!(request.getParameter("volumespecifico").equals(""))) {
        ap.setVolume_specifico(Double.parseDouble(request.getParameter("volumespecifico")));
      }
    }
    if (!(request.getParameter("gradoDanno") == null)) {
        String gradoDanno = request.getParameter("gradoDanno");
        ap.setGradoDanno(gradoDanno);
      }
    if (!(request.getParameter("runout") == null)) {
        if (!(request.getParameter("runout").equals(""))) {
       ap.setRunout(Double.parseDouble(request.getParameter("runout")));
       }
      }
    if (!(request.getParameter("volumeaccumulo") == null)) {
        if (!(request.getParameter("volumeaccumulo").equals(""))) {
        ap.setVolumeAccumulo(Double.parseDouble(request.getParameter("volumeaccumulo")));
        }
      }
    if (!(request.getParameter("superficieaccumulo") == null)) {
        if (!(request.getParameter("superficieaccumulo").equals(""))) {
        ap.setSuperficieAccumulo(Double.parseDouble(request.getParameter("superficieaccumulo")));
        }
      }
    if (!(request.getParameter("fonte") == null)) {
        ap.setFonte(request.getParameter("fonte"));
      }
    if (!(request.getParameterValues("pubblico") == null)) {
        ap.setPubblico(true);
      }else ap.setPubblico(false);
    
    SitoProcesso sp = creaSito(request, loc);
    ClasseVolume cv = creaClasseVolume(request);
    System.out.println("id classe volume da controller: "+cv.getIdClasseVolume());
    ArrayList<EffettiMorfologici> em = creaEffettiMorfologici(request, loc);
    ArrayList<Danni> d = creaDanni(request, loc);
    ArrayList<TipologiaProcesso> tp = creaTipologiaProcesso(request, loc);
    Litologia l = creaLitologia(request, loc);
    StatoFratturazione sf = creaStatoFratturazione(request, loc);
    ProprietaTermiche pt = creaProprietaTermiche(request, loc);
    ap.setProprietaTermiche(pt);
    ap.setStatoFratturazione(sf);
    ap.setLitologia(l);
    ap.setTipologiaProcesso(tp);
    ap.setDanni(d);
    ap.setEffetti(em);
    ap.setClasseVolume(cv);
    ap.setSitoProcesso(sp);
    return ap;
  }

  public static Processo nuovoProcesso(HttpServletRequest request, ControllerLingua loc,Utente usr) throws ParseException, SQLException { 
    Processo p = inputProcesso(request,loc);
    p.setUbicazione(ControllerUbicazione.nuovaUbicazione(request));
    p = ControllerDatabase.salvaProcesso(p, usr);
    ControllerDatabase.salvaEffetti(p.getIdProcesso(), p.getAttributiProcesso().getEffetti(), p.getAttributiProcesso().getDanni());
    ControllerDatabase.salvaTipologiaProcesso(p.getIdProcesso(), p.getAttributiProcesso().getTipologiaProcesso());
    return p;
  }
  
  public static Processo modificaProcesso(HttpServletRequest request,ControllerLingua loc,Utente usr) throws ParseException, SQLException{
    Processo p = inputProcesso(request,loc);
    p.setIdProcesso(Integer.parseInt(request.getParameter("idProcesso")));
    p.setUbicazione(ControllerUbicazione.inputUbicazione(request));
    p.getUbicazione().setIdUbicazione(ControllerDatabase.getIdUbicazione(p.getIdProcesso()));
    ControllerDatabase.modificaProcesso(p,usr);
    return p;
  }
  
  public static Processo eliminaProcesso(HttpServletRequest request) throws SQLException{
    Processo p = ControllerDatabase.prendiProcesso(Integer.parseInt(request.getParameter("idProcesso")));
    ControllerDatabase.eliminaProcesso(Integer.parseInt(request.getParameter("idProcesso")),p.getUbicazione().getIdUbicazione());
    return p;
  }
  public Processo fromDB(int idProcesso) throws SQLException{
    return ControllerDatabase.prendiProcesso(idProcesso);
  }
  
  
  
  
  
  
  /*
   * caratteristiche del processo
   */
  public static SitoProcesso creaSito(HttpServletRequest request, ControllerLingua loc){
    SitoProcesso sp = new SitoProcesso();
    if (!(request.getParameter("idsito") == null) && !(request.getParameter("idsito").equals("0 "))) {
      if (!(request.getParameter("idsito").equals(""))) {
        sp.setIdSito(Integer.parseInt(request.getParameter("idsito")));
      }
      if (loc.getLanguage().equals("it")) {
        sp.setCaratteristicaSito_IT(request.getParameter("caratteristicaSito_IT"));
      }
      if (loc.getLanguage().equals("en")) {
        sp.setCaratteristicaSito_ENG(request.getParameter("caratteristicaSito_ENG"));
      }
    }
    return sp;
  }

  public static Litologia creaLitologia(HttpServletRequest request, ControllerLingua loc) {
    Litologia l = new Litologia();
    if (!(request.getParameter("idLitologia") == null)) {
      if (!(request.getParameter("idLitologia").equals(""))) {
        l.setIdLitologia(Integer.parseInt(request.getParameter("idLitologia")));
      }
      if (loc.getLanguage().equals("it")) {
        l.setNomeLitologia_IT(request.getParameter("nomeLitologia_IT"));
      }
      if (loc.getLanguage().equals("en")) {
        l.setNomeLitologia_ENG(request.getParameter("nomeLitologia_ENG"));
      }
    }
    return l;
  }

  public static ClasseVolume creaClasseVolume(HttpServletRequest request) {
    ClasseVolume cv = new ClasseVolume();
    if (!(request.getParameter("idclasseVolume") == null)) {
      if (!(request.getParameter("idclasseVolume").equals(""))) {
        cv.setIdClasseVolume(Integer.parseInt(request.getParameter("idclasseVolume")));
      }
      cv.setIntervallo(request.getParameter("intervallo"));
    }
    return cv;
  }

  public static ProprietaTermiche creaProprietaTermiche(HttpServletRequest request, ControllerLingua loc) {
    ProprietaTermiche pt = new ProprietaTermiche();
    if (!(request.getParameter("idProprietaTermiche") == null)) {
      if (!(request.getParameter("idProprietaTermiche").equals(""))) {
        pt.setIdProprietaTermiche(Integer.parseInt(request.getParameter("idProprietaTermiche")));
      }
      if (loc.getLanguage().equals("it")) {
        pt.setProprietaTermiche_IT(request.getParameter("proprietaTermiche_IT"));
      }
      if (loc.getLanguage().equals("en")) {
        pt.setProprietaTermiche_ENG(request.getParameter("proprietaTermiche_ENG"));
      }
    }
    return pt;
  }

  public static StatoFratturazione creaStatoFratturazione(HttpServletRequest request, ControllerLingua loc) {
    StatoFratturazione pt = new StatoFratturazione();
    if (!(request.getParameter("idStatoFratturazione") == null)) {
      if (!(request.getParameter("idStatoFratturazione").equals(""))) {
        pt.setIdStatoFratturazione(Integer.parseInt(request.getParameter("idStatoFratturazione")));
      }
      if (loc.getLanguage().equals("it")) {
        pt.setStatoFratturazione_IT(request.getParameter("statoFratturazione_IT"));
      }
      if (loc.getLanguage().equals("en")) {
        pt.setStatoFratturazione_ENG(request.getParameter("statoFratturazione_ENG"));
      }
    }
    return pt;
  }

  public static ArrayList<EffettiMorfologici> creaEffettiMorfologici(HttpServletRequest request, ControllerLingua loc) throws SQLException {
    ArrayList<EffettiMorfologici> em = new ArrayList<>();
    String[] emtipo_it;
    String[] emtipo_eng;

    if (loc.getLanguage().equals("it")) {
      if (!(request.getParameter("emtipo_IT") == null)) {
        if (!(request.getParameter("emtipo_IT") == null)) {
          emtipo_it = request.getParameterValues("emtipo_IT");
          for (int i = 0; i < emtipo_it.length; i++) {
            EffettiMorfologici e = new EffettiMorfologici();
            e.setIdEffettiMOrfologici(ControllerDatabase.prendiIdEffettiMorfologici(emtipo_it[i], "IT"));
            e.setTipo_IT(emtipo_it[i]);
            em.add(e);
          }
        }
      }
    }
    if (loc.getLanguage().equals("en")) {
      if (!(request.getParameter("emtipo_ENG") == null)) {
        if (!(request.getParameter("emtipo_ENG") == null)) {
          emtipo_eng = request.getParameterValues("emtipo_ENG");
          for (int i = 0; i < emtipo_eng.length; i++) {
            EffettiMorfologici e = new EffettiMorfologici();
            e.setIdEffettiMOrfologici(ControllerDatabase.prendiIdEffettiMorfologici(emtipo_eng[i], "ENG"));
            e.setTipo_ENG(emtipo_eng[i]);
            em.add(e);
          }
        }
      }
    }

    return em;
  }

  public static ArrayList<Danni> creaDanni(HttpServletRequest request, ControllerLingua loc) throws SQLException {
    ArrayList<Danni> d = new ArrayList<>();
    String[] dtipo_it;
    String[] dtipo_eng;
    if (loc.getLanguage().equals("it")) {
      if (!(request.getParameterValues("dtipo_IT") == null)) {
        dtipo_it = request.getParameterValues("dtipo_IT");
        for (int i = 0; i < dtipo_it.length; i++) {
          Danni da = new Danni();
          da.setIdDanni(ControllerDatabase.prendiIdDanni(dtipo_it[i], "IT"));
          da.setTipo_IT(dtipo_it[i]);
          d.add(da);
        }
      }
    }
    if (loc.getLanguage().equals("en")) {
      if (!(request.getParameter("dtipo_ENG") == null)) {
        dtipo_eng = request.getParameterValues("dtipo_ENG");
        for (int i = 0; i < dtipo_eng.length; i++) {
          Danni da = new Danni();
          da.setIdDanni(ControllerDatabase.prendiIdDanni(dtipo_eng[i], "ENG"));
          da.setTipo_ENG(dtipo_eng[i]);
          d.add(da);
        }
      }
    }
    return d;
  }

  public static ArrayList<TipologiaProcesso> creaTipologiaProcesso(HttpServletRequest request, ControllerLingua loc) throws SQLException {
    ArrayList<TipologiaProcesso> t = new ArrayList<>();
    String[] tpnome_it;
    String[] tpnome_eng;
    if (loc.getLanguage().equals("it")) {
      if (!(request.getParameterValues("tpnome_IT") == null)) {
        tpnome_it = request.getParameterValues("tpnome_IT");
        for (String tpnome_it1 : tpnome_it) {
          TipologiaProcesso tp = new TipologiaProcesso();
          tp.setIdTipologiaProcesso(ControllerDatabase.prendiIdTipologiaProcesso(tpnome_it1,"IT"));
          System.out.println("idTipologia: "+tp.getIdTipologiaProcesso());
          tp.setNome_IT(tpnome_it1);
          t.add(tp);
        }
      }
    }
    if (loc.getLanguage().equals("en")) {
      if (!(request.getParameterValues("tpnome_ENG") == null)) {
        tpnome_eng = request.getParameterValues("tpnome_ENG");
        for (String tpnome_eng1 : tpnome_eng) {
          TipologiaProcesso tp = new TipologiaProcesso();
          tp.setIdTipologiaProcesso(ControllerDatabase.prendiIdTipologiaProcesso(tpnome_eng1,"ENG"));
          tp.setNome_ENG(tpnome_eng1);
          t.add(tp);
        }
      }
    }
      System.out.println("tipologia: "+t.toString());
    return t;
  }

  public static void fileInput(HttpServletRequest request, String path) throws IllegalStateException, IOException, ServletException {
    final Part filePart = request.getPart("file");
    final String fileName = getFileName(filePart);
    OutputStream out = null;
    InputStream filecontent = null;

    try {
      System.out.println(path);
      File file = new File(path + File.separator + fileName);
      out = new FileOutputStream(file);
      filecontent = filePart.getInputStream();
      int read = 0;
      final byte[] bytes = new byte[1024];
      while ((read = filecontent.read(bytes)) != -1) {
        out.write(bytes, 0, read);
      }

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

  private static String getFileName(final Part part) {
    final String partHeader = part.getHeader("content-disposition");
    for (String content : part.getHeader("content-disposition").split(";")) {
      if (content.trim().startsWith("filename")) {
        return content.substring(
                content.indexOf('=') + 1).trim().replace("\"", "");
      }
    }
    return null;
  }

  public static void fileMultiplo(HttpServletRequest request) throws Exception {
    ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
    JsonArray json = new JsonArray();
    try {
      List<FileItem> items = uploadHandler.parseRequest(request);
      for (FileItem item : items) {
        if (!item.isFormField()) {
          File file = new File(request.getServletContext().getRealPath("/") + "imgs/", item.getName());
          item.write(file);
          JsonObject jsono = new JsonObject();
          jsono.addProperty("name", item.getName());
          jsono.addProperty("size", item.getSize());
          jsono.addProperty("url", "UploadServlet?getfile=" + item.getName());
          jsono.addProperty("thumbnail_url", "UploadServlet?getthumb=" + item.getName());
          jsono.addProperty("delete_url", "UploadServlet?delfile=" + item.getName());
          jsono.addProperty("delete_type", "GET");
          json.add(jsono);
          System.out.println(json.toString());
        }
      }
    } catch (FileUploadException e) {
      throw new RuntimeException(e);
    }
  }
}
