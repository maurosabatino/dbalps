/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.to.geoclimalp.dbalps.managedBean;

import it.cnr.to.geoclimalp.dbalps.bean.processo.AttributiProcesso;
import it.cnr.to.geoclimalp.dbalps.bean.processo.Processo;
import it.cnr.to.geoclimalp.dbalps.bean.processo.ProcessoCompleto;
import it.cnr.to.geoclimalp.dbalps.bean.processo.Segnalazione;
import it.cnr.to.geoclimalp.dbalps.bean.processo.attributiProcesso.*;
import it.cnr.to.geoclimalp.dbalps.bean.ubicazione.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Mauro
 */
@ManagedBean(name = "processoManagedBean",eager = true)
@ViewScoped

public class processoManagedBean implements Serializable{
    @ManagedProperty("#{ubicazione}")
    Ubicazione ubicazione;
     private static final long serialVersionUID = 1L;
    static String url = "jdbc:postgresql://localhost:5432/dbalps";//dbalps
    static String usr = "postgres";//potstgres
    static String pwd = "guido2014";//guido2014
    public List<Processo> getProcessi() throws SQLException{
      List<Processo> al = new ArrayList<Processo>();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        StringBuilder sb = new StringBuilder();
        int idprocesso = 0;
        sb.append("select *,st_x(coordinate::geometry) as x ,st_y(coordinate::geometry) as y from processo as proc "
                + "  left join ubicazione u on (proc.idubicazione=u.idubicazione)"
                + "left join comune c on (c.idcomune=u.idcomune)"
                + "left join caratteristiche_processo ca on (ca.idprocesso=proc.idprocesso)"
                + "left join tipologia_processo ti on (ti.idtipologiaprocesso=ca.idtipologiaprocesso)");
        ResultSet rs = st.executeQuery(sb.toString());
        while (rs.next()) {
            Processo p;
            if (idprocesso == rs.getInt("idProcesso")) {
                idprocesso = rs.getInt("idProcesso");
                TipologiaProcesso ti = new TipologiaProcesso();
                ti.setIdTipologiaProcesso(rs.getInt("idtipologiaprocesso"));
                ti.setNome_ENG(rs.getString("nome_eng"));
                ti.setNome_IT(rs.getString("nome_it"));
                al.get(al.size() - 1).getAttributiProcesso().getTipologiaProcesso().add(ti);

            } else {

                if (rs.getBoolean("convalidato")) {
                    p = new ProcessoCompleto();
                } else {
                    p = new Segnalazione();
                }
                idprocesso = rs.getInt("idProcesso");
                
                Ubicazione u = new Ubicazione();
                Coordinate coord = new Coordinate();
                coord.setX(rs.getDouble("x"));
                coord.setY(rs.getDouble("y"));
                u.setCoordinate(coord);
                LocazioneAmministrativa locAmm = new LocazioneAmministrativa();
                AttributiProcesso ap = new AttributiProcesso();
                p.setIdProcesso(rs.getInt("idProcesso"));
                p.setNome(rs.getString("nome"));
                p.setData(rs.getTimestamp("data"));
                p.setFormatoData(rs.getInt("formatodata"));
                locAmm.setIdComune(rs.getInt("idcomune"));
                locAmm.setComune(rs.getString("nomecomune"));
                u.setLocAmm(locAmm);
                p.setUbicazione(u);
                ArrayList<TipologiaProcesso> tipo = new ArrayList<TipologiaProcesso>();
                TipologiaProcesso ti = new TipologiaProcesso();
                ti.setIdTipologiaProcesso(rs.getInt("idtipologiaprocesso"));
                ti.setNome_ENG(rs.getString("nome_eng"));
                ti.setNome_IT(rs.getString("nome_it"));
                tipo.add(ti);
                ap.setTipologiaProcesso(tipo);
                ap.setPubblico(rs.getBoolean("pubblico"));
                System.out.println("pubblico?"+ap.isPubblico());
                p.setUbicazione(u);
                p.setAttributiProcesso(ap);
                al.add(p);
            }
        }
        rs.close();
        st.close();
        conn.close();
        return al;
    }
    
}