/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.to.geoclimalp.dbalps.Servlet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.cnr.to.geoclimalp.dbalps.bean.processo.Processo;
import it.cnr.to.geoclimalp.dbalps.bean.processo.attributiProcesso.*;
import it.cnr.to.geoclimalp.dbalps.bean.stazione.StazioneMetereologica;
import it.cnr.to.geoclimalp.dbalps.controller.ControllerDatabase;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mauro
 */
@WebServlet(name = "servletJson", urlPatterns = {"/servletJson"})
public class servletJson extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String path = System.getProperty("catalina.base") + "\\resources\\";
        String op = request.getParameter("op");
        if (op.equals("processi")) {
            giveProcessi(response);
        } else if (op.equals("processo")) {
            int idProcesso = Integer.parseInt(request.getParameter("idProcesso"));
        } else if (op.equals("litologia")) {
            giveLitologia(response, path);
        } else if (op.equals("statoFratturazione")) {
            giveStatoFratturazione(response, path);
        } else if (op.equals("proprietaTermiche")) {
            giveProprietaTermiche(response, path);
        } else if (op.equals("sitoProcesso")) {
            giveSitoProcesso(response, path);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(servletJson.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(servletJson.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void giveProcesso(HttpServletResponse response, int idProcesso) throws IOException, SQLException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Processo p = ControllerDatabase.prendiProcesso(idProcesso);
        String json = new Gson().toJson(p);
        System.out.println("json: " + json);
        response.getWriter().write(new Gson().toJson(p));
    }

    private void giveProcessi(HttpServletResponse response) throws SQLException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ArrayList<Processo> processi = ControllerDatabase.prendiTuttiProcessi();
        response.getWriter().write(new Gson().toJson(processi));
    }

    private void giveLitologia(HttpServletResponse response, String path) throws FileNotFoundException, IOException {
        path += "json/litologia.json";
        BufferedReader br = new BufferedReader(new FileReader(path));
        ArrayList<Litologia> litologia = new Gson().fromJson(br, new TypeToken<ArrayList<Litologia>>() {
        }.getType());
        response.getWriter().write(new Gson().toJson(litologia));
    }

    private void giveStatoFratturazione(HttpServletResponse response, String path) throws FileNotFoundException, IOException {
        path += "json/statoFratturazione.json";
        BufferedReader br = new BufferedReader(new FileReader(path));
        ArrayList<StatoFratturazione> statoFratturazione = new Gson().fromJson(br, new TypeToken<ArrayList<StatoFratturazione>>() {
        }.getType());
        response.getWriter().write(new Gson().toJson(statoFratturazione));
    }

    private void giveProprietaTermiche(HttpServletResponse response, String path) throws FileNotFoundException, IOException {
        path += "json/proprietaTermiche.json";
        BufferedReader br = new BufferedReader(new FileReader(path));
        ArrayList<ProprietaTermiche> proprietaTermiche = new Gson().fromJson(br, new TypeToken<ArrayList<ProprietaTermiche>>() {
        }.getType());
        response.getWriter().write(new Gson().toJson(proprietaTermiche));
    }

    private void giveSitoProcesso(HttpServletResponse response, String path) throws FileNotFoundException, IOException {
        path += "json/SitoProcesso.json";
        BufferedReader br = new BufferedReader(new FileReader(path));
        ArrayList<SitoProcesso> sitoProcesso = new Gson().fromJson(br, new TypeToken<ArrayList<SitoProcesso>>() {}.getType());
        response.getWriter().write(new Gson().toJson(sitoProcesso));
    }

}
