package it.cnr.to.geoclimalp.dbalps.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.jasypt.util.password.StrongPasswordEncryptor;

import it.cnr.to.geoclimalp.dbalps.bean.*;
import it.cnr.to.geoclimalp.dbalps.bean.Utente.*;
import it.cnr.to.geoclimalp.dbalps.bean.processo.*;
import it.cnr.to.geoclimalp.dbalps.bean.processo.attributiProcesso.*;
import it.cnr.to.geoclimalp.dbalps.bean.stazione.*;
import it.cnr.to.geoclimalp.dbalps.bean.ubicazione.*;
import it.cnr.to.geoclimalp.dbalps.html.HTMLProcesso;

public class ControllerDatabase {

    static String url = "jdbc:postgresql://localhost:5432/dbalps";//dbalps
    static String usr = "postgres";//potstgres
    static String pwd = "guido2014";//guido2014

    /**
     * Processo
     */
    //modificato per i nuovi attributi del processo
    public static Processo salvaProcesso(Processo p, Utente user) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        String sql = "INSERT INTO processo(idUbicazione,idSito,nome,data,descrizione,note,altezza,larghezza,superficie,volumespecifico,"
                    +"idutentecreatore,idutentemodifica,convalidato,idclassevolume,idlitologia,idproprietatermiche,idstatofratturazione,"
                    + "formatodata,gradodanno,runout,volumeaccumulo,superficieaccumulo,pubblico,fonte)"
                    +" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        if (p.getUbicazione().getIdUbicazione() != 0) {
            ps.setInt(1, p.getUbicazione().getIdUbicazione());
        } else {
            ps.setNull(1, Types.INTEGER);
        }
        if (p.getAttributiProcesso().getSitoProcesso().getIdSito() != 0) {
            ps.setInt(2, p.getAttributiProcesso().getSitoProcesso().getIdSito());
        } else {
            ps.setNull(2, Types.INTEGER);
        }
        ps.setString(3, p.getNome());
        ps.setTimestamp(4, p.getData());
        ps.setString(5, p.getAttributiProcesso().getDescrizione());
        ps.setString(6, p.getAttributiProcesso().getNote());
        ps.setDouble(7, p.getAttributiProcesso().getAltezza());
        ps.setDouble(8, p.getAttributiProcesso().getLarghezza());
        ps.setDouble(9, p.getAttributiProcesso().getSuperficie());
        ps.setDouble(10, p.getAttributiProcesso().getVolume_specifico());
        if (usr != null) {
            ps.setInt(11, user.getIdUtente());
            ps.setInt(12, user.getIdUtente());
        } else {
            ps.setNull(11, Types.INTEGER);
            ps.setNull(12, Types.INTEGER);
        }

        if (!user.getRuolo().equals(Role.AMMINISTRATORE)) {
            ps.setBoolean(13, false);
        } else {
            ps.setBoolean(13, true);
        }
        System.out.println("id classe volume: " + p.getAttributiProcesso().getClasseVolume().getIdClasseVolume());
        if (p.getAttributiProcesso().getClasseVolume().getIdClasseVolume() != 0) {
            ps.setInt(14, p.getAttributiProcesso().getClasseVolume().getIdClasseVolume());
        } else {
            ps.setNull(14, Types.INTEGER);
        }

        if (p.getAttributiProcesso().getLitologia().getidLitologia() != 0) {
            ps.setInt(15, p.getAttributiProcesso().getLitologia().getidLitologia());
        } else {
            ps.setNull(15, Types.INTEGER);
        }

        if (p.getAttributiProcesso().getProprietaTermiche().getIdProprieta_termiche() != 0) {
            ps.setInt(16, p.getAttributiProcesso().getProprietaTermiche().getIdProprieta_termiche());
        } else {
            ps.setNull(16, Types.INTEGER);
        }

        if (p.getAttributiProcesso().getStatoFratturazione().getIdStato_fratturazione() != 0) {
            ps.setInt(17, p.getAttributiProcesso().getStatoFratturazione().getIdStato_fratturazione());
        } else {
            ps.setNull(17, Types.INTEGER);
        }
        ps.setInt(18, p.getFormatoData());
        ps.setString(19, p.getAttributiProcesso().getGradoDanno());
        
        ps.setDouble(20, p.getAttributiProcesso().getRunout());
        ps.setDouble(22, p.getAttributiProcesso().getSuperficieAccumulo());
        ps.setDouble(21, p.getAttributiProcesso().getVolumeAccumulo());
        ps.setBoolean(23, p.getAttributiProcesso().isPubblico());
        ps.setString(24, p.getAttributiProcesso().getFonte());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();

        while (rs.next()) {
            p.setIdProcesso(rs.getInt("idProcesso"));
        }

        rs.close();
        ps.close();
        conn.close();

        return p;
    }

    public static void salvaEffetti(int idProcesso, ArrayList<EffettiMorfologici> em, ArrayList<Danni> d) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        for (EffettiMorfologici eff : em) {
            st.executeUpdate("insert into effetti_processo(idprocesso,ideffettimorfologici) values(" + idProcesso + "," + eff.getIdEffettiMorfoligici() + ")");
        }
        for (Danni da : d) {
            System.out.println("danni:" + da.getTipo_IT());
            st.executeUpdate("insert into danni_processo(idprocesso,iddanno) values(" + idProcesso + "," + da.getIdDanni() + ")");

        }
        st.close();
        conn.close();
    }

    public static void salvaTipologiaProcesso(int idProcesso, ArrayList<TipologiaProcesso> tp) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        for (TipologiaProcesso pt : tp) {
            System.out.println("tip" + pt.getIdTipologiaProcesso());
            st.executeUpdate("insert into caratteristiche_processo(idprocesso,idtipologiaProcesso) values(" + idProcesso + "," + pt.getIdTipologiaProcesso() + ")");
        }
        st.close();
        conn.close();
    }
    //modificato per i nuovi attributi del processo
    public static Processo prendiProcesso(int idProcesso) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        Processo p = null;
        Ubicazione u = new Ubicazione();
        Coordinate coord = new Coordinate();
        LocazioneAmministrativa locAmm = new LocazioneAmministrativa();
        LocazioneIdrologica locIdro = new LocazioneIdrologica();
        StringBuilder sb = new StringBuilder();
        sb.append("select *,st_x(coordinate::geometry) as x ,st_y(coordinate::geometry) as y,quota as q, l.nome_it as lito_it,l.nome_eng as lito_eng,pt.nome_it as pt_it,pt.nome_eng as pt_eng,sf.nome_it as sf_it,sf.nome_eng as sf_eng,affidabilita as aff ");
        sb.append(" from processo proc ");
        sb.append(" left join sito_processo sp on (proc.idsito=sp.idsitoprocesso) ");
        sb.append(" left join classi_volume cv  on (proc.idclassevolume=cv.idclassevolume)");
        sb.append(" left join litologia l on(proc.idlitologia = l.idlitologia) ");
        sb.append(" left join proprieta_termiche pt on (pt.idproprietatermiche=proc.idproprietatermiche) ");
        sb.append(" left join stato_fratturazione sf on (proc.idstatofratturazione=sf.idstatofratturazione)");
        sb.append(" left join ubicazione u on (proc.idubicazione=u.idubicazione)");
        sb.append(" left join comune c on (c.idcomune=u.idcomune)");
        sb.append(" left join provincia p on (c.idProvincia=p.idProvincia)");
        sb.append(" left join regione r on ( r.idregione=p.idregione)");
        sb.append(" left join nazione n on (r.idnazione=n.idnazione)");
        sb.append(" left join sottobacino s on (s.idsottobacino=u.idsottobacino)");
        sb.append(" left join bacino b on (b.idbacino=s.idbacino)");
        sb.append(" where proc.idprocesso=" + idProcesso + "");

        System.out.println("query" + sb.toString());
        ResultSet rs = st.executeQuery(sb.toString());
        while (rs.next()) {
            if (rs.getBoolean("convalidato")) {
                p = new ProcessoCompleto();
            } else {
                p = new Segnalazione();
            }
            p.setIdProcesso(rs.getInt("idProcesso"));
            p.setNome(rs.getString("nome"));
            p.setData(rs.getTimestamp("data"));
            AttributiProcesso ap = new AttributiProcesso();
            ap.setDescrizione(rs.getString("descrizione"));
            ap.setNote(rs.getString("note"));
            ap.setGradoDanno(rs.getString("gradodanno"));
            ap.setAltezza(rs.getDouble("altezza"));
            ap.setLarghezza(rs.getDouble("larghezza"));
            ap.setSuperficie(rs.getDouble("superficie"));
            p.setFormatoData(rs.getInt("formatodata"));
            ap.setVolume_specifico(rs.getDouble("volumespecifico"));
            ap.setRunout(rs.getDouble("runout"));
            ap.setVolumeAccumulo(rs.getDouble("volumeaccumulo"));
            ap.setSuperficieAccumulo(rs.getDouble("superficieaccumulo"));
            ap.setFonte(rs.getString("fonte"));
            ap.setPubblico(rs.getBoolean("pubblico"));
            coord.setX(rs.getDouble("x"));
            coord.setY(rs.getDouble("y"));
            locAmm.setIdComune(rs.getInt("idcomune"));
            locAmm.setComune(rs.getString("nomecomune"));
            locAmm.setProvincia(rs.getString("nomeprovincia"));
            locAmm.setRegione(rs.getString("nomeregione"));
            locAmm.setNazione(rs.getString("nomenazione"));
            locIdro.setIdSottoBacino(rs.getInt("idsottobacino"));
            locIdro.setBacino(rs.getString("nomebacino"));
            locIdro.setSottobacino(rs.getString("nomesottobacino"));
            u.setIdUbicazione(rs.getInt("idubicazione"));
            u.setCoordinate(coord);
            u.setLocAmm(locAmm);
            u.setLocIdro(locIdro);
            u.setEsposizione(rs.getString("esposizione"));
            u.setAttendibilita(rs.getString("aff"));
            u.setQuota(rs.getDouble("q"));
            p.setUbicazione(u);
            ClasseVolume cv = new ClasseVolume();
            cv.setIdClasseVolume(rs.getInt("idclassevolume"));
            cv.setIntervallo(rs.getString("intervallo"));
            ap.setClasseVolume(cv);
            Litologia l = new Litologia();
            l.setIdLitologia(rs.getInt("idlitologia"));
            l.setNomeLitologia_IT(rs.getString("lito_it"));
            l.setNomeLitologia_ENG(rs.getString("lito_eng"));
            ap.setLitologia(l);
            ProprietaTermiche pt = new ProprietaTermiche();
            pt.setIdProprietaTermiche(rs.getInt("idproprietatermiche"));
            pt.setProprietaTermiche_IT(rs.getString("pt_it"));
            pt.setProprietaTermiche_ENG(rs.getString("pt_eng"));
            ap.setProprietaTermiche(pt);
            StatoFratturazione sf = new StatoFratturazione();
            sf.setIdStatoFratturazione(rs.getInt("idstatofratturazione"));
            sf.setStatoFratturazione_IT(rs.getString("sf_it"));
            sf.setStatoFratturazione_ENG(rs.getString("sf_eng"));
            ap.setStatoFratturazione(sf);

            SitoProcesso sp = new SitoProcesso();
            sp.setIdSito(rs.getInt("idsito"));
            sp.setCaratteristicaSito_IT(rs.getString("caratteristica_it"));
            sp.setCaratteristicaSito_ENG(rs.getString("caratteristica_eng"));
            ap.setSitoProcesso(sp);
            ap.setDanni(prendiDanniProcesso(idProcesso));
            ap.setEffetti(prendiEffettiProcesso(idProcesso));
            ap.setTipologiaProcesso(prendiCaratteristicheProcesso(idProcesso));
            p.setAttributiProcesso(ap);
            p.setUbicazione(u);
        }
        rs.close();
        st.close();
        conn.close();
        return p;
    }
//modificato 18/11 da daler		

    public static ArrayList<Processo> prendiTuttiProcessi() throws SQLException {
        ArrayList<Processo> al = new ArrayList<Processo>();
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

    public static ArrayList<Processo> ricercaProcesso(Processo p, Ubicazione u) throws SQLException {
        ArrayList<Processo> al = new ArrayList<Processo>();
        StringBuilder sb = new StringBuilder();
        StringBuilder su = new StringBuilder();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        AttributiProcesso ap = p.getAttributiProcesso();
        if (!(ap.getAltezza() == null || ap.getAltezza() == 0)) {
            if (!(sb.toString().equals(""))) {
                sb.append(" and altezza=" + ap.getAltezza() + "");
            } else if ((sb.toString().equals(""))) {
                sb.append(" where altezza=" + ap.getAltezza() + "");
            }
        }

        if (!(p.getNome() == null || p.getNome().equals(""))) {
            if (!(sb.toString().equals(""))) {
                sb.append(" and nome similar to '%" + p.getNome() + "%'");
            } else if ((sb.toString().equals(""))) {
                sb.append(" where nome similar to '%" + p.getNome() + "%'");
            }
        }

        if (!(ap.getDescrizione() == null || ap.getDescrizione().equals(""))) {
            if (!(sb.toString().equals(""))) {
                sb.append(" and descrizione similar to '%" + ap.getDescrizione() + "%'");
            } else if ((sb.toString().equals(""))) {
                sb.append(" where descrizione similar to '%" + ap.getDescrizione() + "%'");
            }
        }

        if (!(ap.getNote() == null || ap.getNote().equals(""))) {
            if (!(sb.toString().equals(""))) {
                sb.append(" and note similar to '%" + ap.getNote() + "%'");
            } else if ((sb.toString().equals(""))) {
                sb.append(" where note similar to '%" + ap.getNote() + "%'");
            }
        }

        if (!(ap.getSuperficie() == null || ap.getSuperficie() == 0)) {
            if (!(sb.toString().equals(""))) {
                sb.append(" and superficie=" + ap.getSuperficie() + "");
            } else if ((sb.toString().equals(""))) {
                sb.append(" where superficie=" + ap.getSuperficie() + "");
            }
        }
        if (!(ap.getLarghezza() == null || ap.getLarghezza() == 0)) {
            if (!(sb.toString().equals(""))) {
                sb.append(" and larghezza=" + ap.getLarghezza() + "");
            } else if ((sb.toString().equals(""))) {
                sb.append(" where larghezza=" + ap.getLarghezza() + "");
            }
        }
        System.out.println("data: " + p.getData().toString());
        if (!((p.getData() == null) || p.getData().toString().equals("0001-01-01 00:00:00.0") || p.getData().toString().equals("0001-01-01 01:00:00.0"))) {
            int formatoData = p.getFormatoData();
            System.out.println("formato data:" + formatoData);
            Calendar cal = new GregorianCalendar();
            cal.setTime(p.getData());
            if (formatoData == 1000) {
                if (!(sb.toString().equals(""))) {
                    sb.append(" and EXTRACT(year from data)=" + cal.get(Calendar.YEAR) + "");
                } else if ((sb.toString().equals(""))) {
                    sb.append(" where EXTRACT(year from data)=" + cal.get(Calendar.YEAR) + "");
                }
            } else {
                if (!(sb.toString().equals(""))) {
                    sb.append(" and data='" + p.getData().toString() + "'");
                } else if ((sb.toString().equals(""))) {
                    sb.append(" where data='" + p.getData().toString() + "'");
                }
            }
        }
        if (!(ap.getClasseVolume().getIdClasseVolume() == 0)) {
            if (!(sb.toString().equals(""))) {
                sb.append(" and idclassevolume=" + ap.getClasseVolume().getIdClasseVolume() + "");
            } else if ((sb.toString().equals(""))) {
                sb.append(" where idclassevolume=" + ap.getClasseVolume().getIdClasseVolume() + "");
            }
        }
        if (!(ap.getLitologia().getidLitologia() == 0)) {
            if (!(sb.toString().equals(""))) {
                sb.append(" and idlitologia=" + ap.getLitologia().getidLitologia() + "");
            } else if ((sb.toString().equals(""))) {
                sb.append(" where idlitologia=" + ap.getLitologia().getidLitologia() + "");
            }
        }
        if (!(ap.getProprietaTermiche().getIdProprieta_termiche() == 0)) {
            if (!(sb.toString().equals(""))) {
                sb.append(" and idproprietatermiche=" + ap.getProprietaTermiche().getIdProprieta_termiche() + "");
            } else if ((sb.toString().equals(""))) {
                sb.append(" where idproprietatermiche=" + ap.getProprietaTermiche().getIdProprieta_termiche() + "");
            }
        }
        if (!(ap.getStatoFratturazione().getIdStato_fratturazione() == 0)) {
            if (!(sb.toString().equals(""))) {
                sb.append(" and idstatofratturazione=" + ap.getStatoFratturazione().getIdStato_fratturazione() + "");
            } else if ((sb.toString().equals(""))) {
                sb.append(" where idstatofratturazione=" + ap.getStatoFratturazione().getIdStato_fratturazione() + "");
            }
        }
        if (!(ap.getSitoProcesso().getIdSito() == 0)) {
            if (!(sb.toString().equals(""))) {
                sb.append(" and idsito=" + ap.getSitoProcesso().getIdSito() + "");
            } else if ((sb.toString().equals(""))) {
                sb.append(" where idsito=" + ap.getSitoProcesso().getIdSito() + "");
            }
        }

        if (!(ap.getDanni().size() == 0)) {
            StringBuilder sd = new StringBuilder();
            if (!(sb.toString().equals(""))) {
                sd.append(" and idprocesso in(");
            } else if ((sb.toString().equals(""))) {
                sd.append(" where idprocesso in(");
            }
            for (int i = 0; i < ap.getDanni().size(); i++) {
                if (i == 0) {
                    sd.append("select distinct(idprocesso) from danni_processo where iddanno=" + ap.getDanni().get(i).getIdDanni() + "");
                } else {
                    sd.append(" and idprocesso in(select distinct(idprocesso) from danni_processo where iddanno=" + ap.getDanni().get(i).getIdDanni() + "");
                }
            }
            int i = 0;
            while (i != ap.getDanni().size()) {
                sd.append(")");
                i++;
            }
            if (!(sd.toString().equals(""))) {
                sb.append(sd);
            }
        }
        if (!(ap.getEffetti().size() == 0)) {
            StringBuilder se = new StringBuilder();
            if (!(sb.toString().equals(""))) {
                se.append(" and idprocesso in(");
            } else if ((sb.toString().equals(""))) {
                se.append(" where idprocesso in(");
            }
            for (int i = 0; i < ap.getEffetti().size(); i++) {
                if (i == 0) {
                    se.append("select distinct(idprocesso) from effetti_processo where ideffettimorfologici=" + ap.getEffetti().get(i).getIdEffettiMorfoligici() + "");
                } else {
                    se.append(" and idprocesso in(select distinct(idprocesso) from effetti_processo where ideffettimorfologici=" + ap.getEffetti().get(i).getIdEffettiMorfoligici() + "");
                }
            }
            int i = 0;
            while (i != ap.getEffetti().size()) {
                se.append(")");
                i++;
            }
            if (!(se.toString().equals(""))) {
                sb.append(se);
            }
        }

        if (!(ap.getTipologiaProcesso().size() == 0)) {
            StringBuilder stp = new StringBuilder();
            if (!(sb.toString().equals(""))) {
                stp.append(" and idprocesso in(");
            } else if ((sb.toString().equals(""))) {
                stp.append(" where idprocesso in(");
            }

            for (int i = 0; i < ap.getTipologiaProcesso().size(); i++) {
                if (i == 0) {
                    stp.append("select distinct(idprocesso) from caratteristiche_processo where idtipologiaprocesso=" + ap.getTipologiaProcesso().get(i).getIdTipologiaProcesso() + "");
                } else {
                    stp.append(" and idprocesso in(select distinct(idprocesso) from caratteristiche_processo where idtipologiaprocesso=" + ap.getTipologiaProcesso().get(i).getIdTipologiaProcesso() + "");
                }
            }
            int i = 0;
            while (i != ap.getTipologiaProcesso().size()) {
                stp.append(")");
                i++;
            }
            if (!(stp.toString().equals(""))) {
                sb.append(stp);
            }
        }

        if (!(u.getLocAmm().isEmpty())) {
            if (!(u.getLocAmm().getComune() == null || u.getLocAmm().getComune().equals(""))) {
                if (!(su.toString().equals(""))) {
                    su.append(" and c.nomecomune ='" + u.getLocAmm().getComune() + "'");
                } else if ((su.toString().equals(""))) {
                    su.append(" where c.nomecomune ='" + u.getLocAmm().getComune() + "'");
                }
            }
            if (!(u.getLocAmm().getProvincia() == null || u.getLocAmm().getProvincia().equals(""))) {
                if (!(su.toString().equals(""))) {
                    su.append(" and p.nomeprovincia = '" + u.getLocAmm().getProvincia() + "'");
                } else if ((su.toString().equals(""))) {
                    su.append(" where p.nomeprovincia = '" + u.getLocAmm().getProvincia() + "'");
                }
            }
            if (!(u.getLocAmm().getRegione() == null || u.getLocAmm().getRegione().equals(""))) {
                if (!(su.toString().equals(""))) {
                    su.append(" and r.nomeregione = '" + u.getLocAmm().getRegione() + "'");
                } else if ((su.toString().equals(""))) {
                    su.append(" where r.nomeregione = '" + u.getLocAmm().getRegione() + "'");
                }
            }
            if (!(u.getLocAmm().getNazione() == null || u.getLocAmm().getNazione().equals(""))) {
                if (!(su.toString().equals(""))) {
                    su.append(" and n.nomenazione ='" + u.getLocAmm().getNazione() + "'");
                } else if ((su.toString().equals(""))) {
                    su.append(" where n.nomenazione ='" + u.getLocAmm().getNazione() + "'");
                }
            }
        }

        if (!(u.getLocIdro().isEmpty())) {
            if (!(u.getLocIdro().getBacino() == null || u.getLocIdro().getBacino().equals(""))) {
                if (!(su.toString().equals(""))) {
                    su.append(" and b.nomebacino ='" + u.getLocIdro().getBacino() + "'");
                } else if ((su.toString().equals(""))) {
                    su.append(" where b.nomebacino ='" + u.getLocIdro().getBacino() + "'");
                }
            }
            if (!(u.getLocIdro().getSottobacino() == null || u.getLocIdro().getSottobacino().equals(""))) {
                if (!(su.toString().equals(""))) {
                    su.append(" and s.nomesottobacino ='" + u.getLocIdro().getSottobacino() + "'");
                } else if ((su.toString().equals(""))) {
                    su.append(" where s.nomesottobacino ='" + u.getLocIdro().getSottobacino() + "'");
                }
            }
        }
        if (!(u.getQuota() == null)) {
            if (!(su.toString().equals(""))) {
                su.append(" and u.quota=" + u.getQuota());
            } else if ((su.toString().equals(""))) {
                su.append("where u.quota=" + u.getQuota());
            }
        }

        ResultSet rs = null;
        StringBuilder query = new StringBuilder();
        query.append("select *,st_x(coordinate::geometry) as x ,st_y(coordinate::geometry) as y, l.nome_it as lito_it,l.nome_eng as lito_eng,pt.nome_it as pt_it,pt.nome_eng as pt_eng,sf.nome_it as sf_it,sf.nome_eng as sf_eng ");
        query.append(" from processo proc ");
        query.append(" left join sito_processo sp on (proc.idsito=sp.idsitoprocesso) ");
        query.append(" left join classi_volume cv  on (proc.idclassevolume=cv.idclassevolume)");
        query.append(" left join litologia l on(proc.idlitologia = l.idlitologia) ");
        query.append(" left join proprieta_termiche pt on (pt.idproprietatermiche=proc.idproprietatermiche) ");
        query.append(" left join stato_fratturazione sf on (proc.idstatofratturazione=sf.idstatofratturazione)");
        query.append(" left join ubicazione u on (proc.idubicazione=u.idubicazione)");
        query.append(" left join comune c on (c.idcomune=u.idcomune)");
        query.append(" left join provincia p on (c.idProvincia=p.idProvincia)");
        query.append(" left join regione r on ( r.idregione=p.idregione)");
        query.append(" left join nazione n on (r.idnazione=n.idnazione)");
        query.append(" left join sottobacino s on (s.idsottobacino=u.idsottobacino)");
        query.append(" left join bacino b on (b.idbacino=s.idbacino)");
        System.out.println("Ubicazione" + u.getLocAmm().getComune());
        if (u.isEmpty() == true) {
            System.out.println("Query: " + query.toString() + " " + sb.toString() + "");
            rs = st.executeQuery("" + query.toString() + " " + sb.toString() + " ");
        } else {
            System.out.println("Query: " + query.toString() + "  " + sb.toString() + " " + su.toString() + " ");
            rs = st.executeQuery("" + query.toString() + "  " + sb.toString() + " " + su.toString() + " ");
        }
        while (rs.next()) {
            Processo ps = new ProcessoCompleto();
            if (!(rs.getBoolean("convalidato"))) {
                p = new Segnalazione();
            }
            AttributiProcesso aps = new AttributiProcesso();
            Ubicazione ubi = new Ubicazione();
            Coordinate coord = new Coordinate();
            LocazioneAmministrativa locAmm = new LocazioneAmministrativa();
            LocazioneIdrologica locIdro = new LocazioneIdrologica();
            int idProcesso = rs.getInt("idProcesso");
            ps.setIdProcesso(idProcesso);
            ps.setNome(rs.getString("nome"));
            ps.setData(rs.getTimestamp("data"));
            aps.setDescrizione(rs.getString("descrizione"));
            aps.setNote(rs.getString("note"));
            aps.setAltezza(rs.getDouble("altezza"));
            aps.setLarghezza(rs.getDouble("larghezza"));
            aps.setSuperficie(rs.getDouble("superficie"));
            aps.setVolume_specifico(rs.getDouble("volumespecifico"));
            coord.setX(rs.getDouble("x"));
            coord.setY(rs.getDouble("y"));
            locAmm.setIdComune(rs.getInt("idcomune"));
            locAmm.setComune(rs.getString("nomecomune"));
            locAmm.setProvincia(rs.getString("nomeprovincia"));
            locAmm.setRegione(rs.getString("nomeregione"));
            locAmm.setNazione(rs.getString("nomenazione"));
            locIdro.setIdSottoBacino(rs.getInt("idsottobacino"));
            locIdro.setBacino(rs.getString("nomebacino"));
            locIdro.setSottobacino(rs.getString("nomesottobacino"));
            ubi.setCoordinate(coord);
            ubi.setLocAmm(locAmm);
            ubi.setLocIdro(locIdro);
            ubi.setEsposizione(rs.getString("esposizione"));
            ubi.setQuota(rs.getDouble("quota"));
            ps.setUbicazione(ubi);
            ClasseVolume cv = new ClasseVolume();
            cv.setIdClasseVolume(rs.getInt("idclassevolume"));
            cv.setIntervallo(rs.getString("intervallo"));
            aps.setClasseVolume(cv);
            Litologia l = new Litologia();
            l.setIdLitologia(rs.getInt("idlitologia"));
            l.setNomeLitologia_IT(rs.getString("lito_it"));
            l.setNomeLitologia_ENG(rs.getString("lito_eng"));
            aps.setLitologia(l);
            ProprietaTermiche pt = new ProprietaTermiche();
            pt.setIdProprietaTermiche(rs.getInt("idproprietatermiche"));
            pt.setProprietaTermiche_IT(rs.getString("pt_it"));
            pt.setProprietaTermiche_ENG(rs.getString("pt_eng"));
            aps.setProprietaTermiche(pt);
            StatoFratturazione sf = new StatoFratturazione();
            sf.setIdStatoFratturazione(rs.getInt("idstatofratturazione"));
            sf.setStatoFratturazione_IT(rs.getString("sf_it"));
            sf.setStatoFratturazione_ENG(rs.getString("sf_eng"));
            aps.setStatoFratturazione(sf);
            SitoProcesso sp = new SitoProcesso();
            sp.setIdSito(rs.getInt("idsito"));
            sp.setCaratteristicaSito_IT(rs.getString("caratteristica_it"));
            sp.setCaratteristicaSito_ENG(rs.getString("caratteristica_eng"));
            aps.setDanni(prendiDanniProcesso(rs.getInt("idprocesso")));
            aps.setEffetti(prendiEffettiProcesso(rs.getInt("idprocesso")));
            aps.setTipologiaProcesso(prendiCaratteristicheProcesso(rs.getInt("idprocesso")));
            al.add(ps);
        }
        rs.close();
        st.close();
        conn.close();
        return al;
    }

    public static void modificaProcesso(Processo p, Utente user) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        StringBuilder sb = new StringBuilder();
        StringBuilder su = new StringBuilder();

        String sql = "update processo set idSito=?,nome=?,data=?,descrizione=?,note=?,altezza=?,larghezza=?,superficie=?,volumespecifico=?,"
                + "idutentemodifica=?,convalidato=?,idclassevolume=?,idlitologia=?,idproprietatermiche=?,idstatofratturazione=?,formatodata=?,gradodanno=?,"
                + "runout = ?,volumeaccumulo=?,superficieaccumulo=?,pubblico=?,fonte=?"
                + "where idprocesso = ?";

        PreparedStatement ps = conn.prepareStatement(sql);

        if (p.getAttributiProcesso().getSitoProcesso().getIdSito() != 0) {
            ps.setInt(1, p.getAttributiProcesso().getSitoProcesso().getIdSito());
        } else {
            ps.setNull(1, Types.INTEGER);
        }
        ps.setString(2, p.getNome());
        ps.setTimestamp(3, p.getData());
        ps.setString(4, p.getAttributiProcesso().getDescrizione());
        ps.setString(5, p.getAttributiProcesso().getNote());
        ps.setDouble(6, p.getAttributiProcesso().getAltezza());
        ps.setDouble(7, p.getAttributiProcesso().getLarghezza());
        ps.setDouble(8, p.getAttributiProcesso().getSuperficie());
        ps.setDouble(9, p.getAttributiProcesso().getVolume_specifico());
        if (usr != null) {
            ps.setInt(10, user.getIdUtente());

        } else {
            ps.setNull(10, Types.INTEGER);
        }

        if (!user.getRuolo().equals(Role.AMMINISTRATORE)) {
            ps.setBoolean(11, false);
        } else {
            ps.setBoolean(11, true);
        }
        if (p.getAttributiProcesso().getClasseVolume().getIdClasseVolume() != 0) {
            ps.setInt(12, p.getAttributiProcesso().getClasseVolume().getIdClasseVolume());
        } else {
            ps.setNull(12, Types.INTEGER);
        }

        if (p.getAttributiProcesso().getLitologia().getidLitologia() != 0) {
            ps.setInt(13, p.getAttributiProcesso().getLitologia().getidLitologia());
        } else {
            ps.setNull(13, Types.INTEGER);
        }

        if (p.getAttributiProcesso().getProprietaTermiche().getIdProprieta_termiche() != 0) {
            ps.setInt(14, p.getAttributiProcesso().getProprietaTermiche().getIdProprieta_termiche());
        } else {
            ps.setNull(14, Types.INTEGER);
        }

        if (p.getAttributiProcesso().getStatoFratturazione().getIdStato_fratturazione() != 0) {
            ps.setInt(15, p.getAttributiProcesso().getStatoFratturazione().getIdStato_fratturazione());
        } else {
            ps.setNull(15, Types.INTEGER);
        }
        ps.setInt(16, p.getFormatoData());
        ps.setString(17, p.getAttributiProcesso().getGradoDanno());
        ps.setDouble(18, p.getAttributiProcesso().getRunout());
        ps.setDouble(19, p.getAttributiProcesso().getVolumeAccumulo());
        ps.setDouble(20, p.getAttributiProcesso().getSuperficieAccumulo());
        ps.setBoolean(21, p.getAttributiProcesso().isPubblico());
        ps.setString(22, p.getAttributiProcesso().getFonte());
        ps.setInt(23, p.getIdProcesso());
        System.out.println("query Processo: " + ps.toString());
        ps.executeUpdate();

        if (!(p.getAttributiProcesso().getEffetti().isEmpty() || p.getAttributiProcesso().getDanni().isEmpty() || p.getAttributiProcesso().getEffetti().size() == 0 || p.getAttributiProcesso().getDanni().size() == 0)) {
            st.executeUpdate("delete from danni_processo where idprocesso =" + p.getIdProcesso() + "");
            st.executeUpdate("delete from effetti_processo where idprocesso = " + p.getIdProcesso() + "");
            salvaEffetti(p.getIdProcesso(), p.getAttributiProcesso().getEffetti(), p.getAttributiProcesso().getDanni());
        } else {
            st.executeUpdate("delete from danni_processo where idprocesso =" + p.getIdProcesso() + "");
            st.executeUpdate("delete from effetti_processo where idprocesso = " + p.getIdProcesso() + "");
        }

        if (!(p.getAttributiProcesso().getTipologiaProcesso().isEmpty() || p.getAttributiProcesso().getTipologiaProcesso().size() == 0)) {
            st.executeUpdate("delete from caratteristiche_processo where idprocesso = " + p.getIdProcesso() + "");
            salvaTipologiaProcesso(p.getIdProcesso(), p.getAttributiProcesso().getTipologiaProcesso());
        } else {
            st.executeUpdate("delete from caratteristiche_processo where idprocesso = " + p.getIdProcesso() + "");
        }

        st.close();
        conn.close();

        if (p.getUbicazione() != null) {
            modificaUbicazione(p.getUbicazione());
        }

    }

    public static void eliminaProcesso(int idProcesso, int idUbicazione) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        conn.setAutoCommit(false);
        PreparedStatement psa = conn.prepareStatement("delete from allegati_processo where idprocesso = "+idProcesso+"");
        PreparedStatement psp = conn.prepareStatement("delete from processo where idprocesso=" + idProcesso + "");
        PreparedStatement psu = conn.prepareStatement("delete from ubicazione where idubicazione=" + idUbicazione + "");
        psa.executeUpdate();
        psp.executeUpdate();
        psu.executeUpdate();
        conn.commit();
        psp.close();
        psu.close();
        conn.close();
    }
    public static void eliminaStazione(int idStazione, int idUbicazione) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        PreparedStatement pss = conn.prepareStatement("delete from sensore_stazione where idstazionemetereologica="+idStazione+ "");
        PreparedStatement psst = conn.prepareStatement("delete from stazione_metereologica where idstazionemetereologica="+idStazione+"");
        PreparedStatement psu = conn.prepareStatement("delete from ubicazione where idubicazione=" + idUbicazione + "");
        pss.executeUpdate();
        psst.executeUpdate();
        psu.executeUpdate();
        pss.close();
        psst.close();
        psu.close();
        conn.close();
    }

    /*
     * caratteristiche del processo
     */
    public static ArrayList<EffettiMorfologici> prendiEffettiMOrfologici() throws SQLException {
        ArrayList<EffettiMorfologici> al = new ArrayList<EffettiMorfologici>();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from effetti_Morfologici");
        while (rs.next()) {
            EffettiMorfologici em = new EffettiMorfologici();
            em.setIdEffettiMOrfologici(rs.getInt("ideffettimorfologici"));
            em.setTipo_IT(rs.getString("tipo_it"));
            em.setTipo_ENG(rs.getString("tipo_eng"));
            al.add(em);
        }
        rs.close();
        st.close();
        conn.close();
        return al;
    }

    public static EffettiMorfologici prendiEffettoMorfologico(int idEffetto) throws SQLException {
        EffettiMorfologici em = new EffettiMorfologici();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from effetti_Morfologici where ideffettimorfologici=" + idEffetto + "");
        while (rs.next()) {
            em.setIdEffettiMOrfologici(rs.getInt("ideffettimorfologici"));
            em.setTipo_IT(rs.getString("tipo_it"));
            em.setTipo_ENG(rs.getString("tipo_eng"));
        }
        rs.close();
        st.close();
        conn.close();
        return em;
    }

    public static int prendiIdEffettiMorfologici(String effetto, String loc) throws SQLException {
        int i = 0;
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select idEffettimorfologici from effetti_morfologici where tipo_" + loc + "='" + effetto + "'");
        while (rs.next()) {
            i = rs.getInt("idEffettimorfologici");
        }
        rs.close();
        st.close();
        conn.close();
        return i;
    }

    public static ArrayList<EffettiMorfologici> prendiEffettiProcesso(int idProcesso) throws SQLException {
        ArrayList<EffettiMorfologici> al = new ArrayList<EffettiMorfologici>();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from effetti_morfologici where ideffettimorfologici in( select ideffettimorfologici from effetti_processo where idprocesso=" + idProcesso + ")");
        while (rs.next()) {
            EffettiMorfologici em = new EffettiMorfologici();
            em.setIdEffettiMOrfologici(rs.getInt("ideffettimorfologici"));
            em.setTipo_IT(rs.getString("tipo_it"));
            em.setTipo_ENG(rs.getString("tipo_eng"));
            al.add(em);
        }
        rs.close();
        st.close();
        conn.close();
        return al;
    }

    public static ArrayList<Danni> prendiDanni() throws SQLException {
        ArrayList<Danni> al = new ArrayList<Danni>();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from danno");
        while (rs.next()) {
            Danni d = new Danni();
            d.setIdDanni(rs.getInt("iddanno"));
            d.setTipo_IT(rs.getString("tipo_it"));
            d.setTipo_ENG(rs.getString("tipo_eng"));
            al.add(d);
        }
        rs.close();
        st.close();
        conn.close();
        return al;
    }

    public static Danni prendiDanno(int idDanno) throws SQLException {
        Danni d = new Danni();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from danno where iddanno=" + idDanno + "");
        while (rs.next()) {
            d.setIdDanni(rs.getInt("iddanno"));
            d.setTipo_IT(rs.getString("tipo_it"));
            d.setTipo_ENG(rs.getString("tipo_eng"));
        }
        rs.close();
        st.close();
        conn.close();
        return d;
    }

    public static int prendiIdDanni(String danno, String loc) throws SQLException {
        int i = 0;
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select iddanno from danno where tipo_" + loc + " = '" + danno + "'");
        while (rs.next()) {
            i = rs.getInt("iddanno");
        }
        rs.close();
        st.close();
        conn.close();
        return i;
    }

    public static ArrayList<Danni> prendiDanniProcesso(int idProcesso) throws SQLException {
        ArrayList<Danni> al = new ArrayList<Danni>();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from danno where iddanno in( select iddanno from danni_processo where idprocesso=" + idProcesso + ")");
        while (rs.next()) {
            Danni d = new Danni();
            d.setIdDanni(rs.getInt("iddanno"));
            d.setTipo_IT(rs.getString("tipo_it"));
            d.setTipo_ENG(rs.getString("tipo_eng"));
            al.add(d);
        }
        rs.close();
        st.close();
        conn.close();
        return al;
    }

    public static ArrayList<ProprietaTermiche> prendiProprietaTermiche() throws SQLException {
        ArrayList<ProprietaTermiche> al = new ArrayList<ProprietaTermiche>();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from proprieta_termiche");
        while (rs.next()) {
            ProprietaTermiche pt = new ProprietaTermiche();
            pt.setIdProprietaTermiche(rs.getInt("idproprietatermiche"));
            pt.setProprietaTermiche_IT(rs.getString("nome_it"));
            pt.setProprietaTermiche_ENG(rs.getString("nome_eng"));
            al.add(pt);
        }
        rs.close();
        st.close();
        conn.close();
        return al;
    }

    public static ProprietaTermiche prendiProprietaTermica(int idPropTermica) throws SQLException {
        ProprietaTermiche pt = new ProprietaTermiche();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from proprieta_termiche where idproprietatermiche=" + idPropTermica + "");
        while (rs.next()) {
            pt.setIdProprietaTermiche(rs.getInt("idproprietatermiche"));
            pt.setProprietaTermiche_IT(rs.getString("nome_it"));
            pt.setProprietaTermiche_ENG(rs.getString("nome_eng"));
        }
        rs.close();
        st.close();
        conn.close();
        return pt;
    }

    public static ArrayList<StatoFratturazione> prendiStatoFratturazione() throws SQLException {
        ArrayList<StatoFratturazione> al = new ArrayList<StatoFratturazione>();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from stato_fratturazione");
        while (rs.next()) {
            StatoFratturazione sf = new StatoFratturazione();
            sf.setIdStatoFratturazione(rs.getInt("idstatofratturazione"));
            sf.setStatoFratturazione_IT(rs.getString("nome_it"));
            sf.setStatoFratturazione_ENG(rs.getString("nome_ENG"));
            al.add(sf);
        }
        st.close();
        conn.close();
        return al;
    }

    public static StatoFratturazione prendiStatoFratturazione(int idStatofratturazione) throws SQLException {
        StatoFratturazione sf = new StatoFratturazione();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from stato_fratturazione where idstatofratturazione=" + idStatofratturazione + "");
        while (rs.next()) {
            sf.setIdStatoFratturazione(rs.getInt("idstatofratturazione"));
            sf.setStatoFratturazione_IT(rs.getString("nome_it"));
            sf.setStatoFratturazione_ENG(rs.getString("nome_ENG"));
        }
        rs.close();
        st.close();
        conn.close();
        return sf;
    }

    public static ArrayList<Litologia> prendiLitologia() throws SQLException {
        ArrayList<Litologia> al = new ArrayList<Litologia>();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from litologia");
        while (rs.next()) {
            Litologia l = new Litologia();
            l.setIdLitologia(rs.getInt("idlitologia"));
            l.setNomeLitologia_IT(rs.getString("nome_IT"));
            l.setNomeLitologia_ENG(rs.getString("nome_ENG"));
            al.add(l);
        }
        rs.close();
        st.close();
        conn.close();
        return al;
    }

    public static Litologia prendiLitologia(int idLitologia) throws SQLException {
        Litologia l = new Litologia();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from litologia where idlitologia=" + idLitologia + "");
        while (rs.next()) {
            l.setIdLitologia(rs.getInt("idlitologia"));
            l.setNomeLitologia_IT(rs.getString("nome_IT"));
            l.setNomeLitologia_ENG(rs.getString("nome_ENG"));
        }
        rs.close();
        st.close();
        conn.close();
        return l;
    }

    public static ArrayList<SitoProcesso> prendiSitoProcesso() throws SQLException {
        ArrayList<SitoProcesso> al = new ArrayList<SitoProcesso>();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from sito_processo");
        while (rs.next()) {
            SitoProcesso sp = new SitoProcesso();
            sp.setIdSito(rs.getInt("idsitoprocesso"));
            sp.setCaratteristicaSito_IT(rs.getString("caratteristica_IT"));
            sp.setCaratteristicaSito_ENG(rs.getString("caratteristica_eng"));
            al.add(sp);
        }
        rs.close();
        st.close();
        conn.close();
        return al;
    }

    public static SitoProcesso prendiSitoProcesso(int idSitoProcesso) throws SQLException {
        SitoProcesso sp = new SitoProcesso();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from sito_processo where idsitoprocesso=" + idSitoProcesso + " ");
        while (rs.next()) {
            sp.setIdSito(rs.getInt("idsitoprocesso"));
            sp.setCaratteristicaSito_IT(rs.getString("caratteristica_IT"));
            sp.setCaratteristicaSito_ENG(rs.getString("caratteristica_eng"));
        }
        rs.close();
        st.close();
        conn.close();
        return sp;
    }

    public static ArrayList<ClasseVolume> prendiClasseVolume() throws SQLException {
        ArrayList<ClasseVolume> al = new ArrayList<ClasseVolume>();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from classi_volume");
        while (rs.next()) {
            ClasseVolume cv = new ClasseVolume();
            cv.setIdClasseVolume(rs.getInt("idclassevolume"));
            cv.setIntervallo(rs.getString("intervallo"));
            al.add(cv);
        }
        rs.close();
        st.close();
        conn.close();
        return al;
    }

    public static ClasseVolume prendiClasseVolume(int idClasseVolume) throws SQLException {
        ClasseVolume cv = new ClasseVolume();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from classi_volume where idclassevolume=" + idClasseVolume + " ");
        while (rs.next()) {
            cv.setIdClasseVolume(rs.getInt("idclassevolume"));
            cv.setIntervallo(rs.getString("intervallo"));
        }
        rs.close();
        st.close();
        conn.close();
        return cv;
    }

    public static ArrayList<TipologiaProcesso> prendiTipologiaProcesso() throws SQLException {
        ArrayList<TipologiaProcesso> al = new ArrayList<TipologiaProcesso>();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from tipologia_processo");
        while (rs.next()) {
            TipologiaProcesso tp = new TipologiaProcesso();
            tp.setIdTipologiaProcesso(rs.getInt("idtipologiaprocesso"));
            tp.setNome_IT(rs.getString("nome_it"));
            tp.setNome_ENG(rs.getString("nome_eng"));
            al.add(tp);
        }
        rs.close();
        st.close();
        conn.close();
        return al;
    }

    public static TipologiaProcesso prendiTipologiaProcesso(int idTipologiaProcesso) throws SQLException {
        TipologiaProcesso tp = new TipologiaProcesso();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from tipologia_processo where idtipologiaprocesso=" + idTipologiaProcesso + " ");
        while (rs.next()) {
            tp.setIdTipologiaProcesso(rs.getInt("idtipologiaprocesso"));
            tp.setNome_IT(rs.getString("nome_it"));
            tp.setNome_ENG(rs.getString("nome_eng"));
        }
        rs.close();
        st.close();
        conn.close();
        return tp;
    }

    public static int prendiIdTipologiaProcesso(String tipologiaProcesso, String loc) throws SQLException {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(url, usr, pwd)) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select idtipologiaprocesso from tipologia_processo where nome_"+loc+" ='"+ tipologiaProcesso +"'");
            while (rs.next()) {
                i = rs.getInt("idtipologiaprocesso");
            }
            rs.close();
            st.close();
        }
        return i;
    }

    public static ArrayList<TipologiaProcesso> prendiCaratteristicheProcesso(int idProcesso) throws SQLException {
        ArrayList<TipologiaProcesso> al = new ArrayList<TipologiaProcesso>();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from tipologia_processo where idtipologiaprocesso in( select idtipologiaprocesso from caratteristiche_processo where idprocesso=" + idProcesso + ")");
        while (rs.next()) {
            TipologiaProcesso tp = new TipologiaProcesso();
            tp.setIdTipologiaProcesso(rs.getInt("idtipologiaprocesso"));
            tp.setNome_IT(rs.getString("nome_it"));
            tp.setNome_ENG(rs.getString("nome_eng"));
            al.add(tp);
        }
        rs.close();
        st.close();
        conn.close();
        return al;
    }

    /*
     * stazione metereologica
     */
    public static void salvaStazione(StazioneMetereologica s, Utente part) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();

        String sql = "insert into stazione_metereologica  (nome,aggregazionegiornaliera,note,datainizio,datafine,idsitostazione,idente,idutentecreatore,tipoaggregazione,idubicazione) values(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, s.getNome());
        ps.setString(2, s.getAggregazioneGiornaliera());
        ps.setString(3, s.getNote());
        ps.setString(4, s.getDataInizio());
        ps.setString(5, s.getDataFine());
        if (s.sito.getIdSitoStazioneMetereologica() != 0) {
            ps.setInt(6, s.sito.getIdSitoStazioneMetereologica());
        } else {
            ps.setNull(6, Types.INTEGER);
        }
        if (s.ente.getIdEnte() != 0) {
            ps.setInt(7, s.ente.getIdEnte());
        } else {
            ps.setNull(7, Types.INTEGER);
        }
        ps.setInt(8, s.getIdUtente());
        ps.setString(9, s.getTipoAggregazione());
        ps.setInt(10, s.getUbicazione().getIdUbicazione());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();

        while (rs.next()) {
            s.setIdStazioneMetereologica(rs.getInt("idstazionemetereologica"));
        }

        if (s.getSensori().size() != 0) {
            salvaSensoriStazione(s);
        }
        rs.close();
        st.close();
        conn.close();
    }

    public static ArrayList<StazioneMetereologica> prendiTutteStazioniMetereologiche() throws SQLException {
        ArrayList<StazioneMetereologica> al = new ArrayList<StazioneMetereologica>();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery("select *,st_x(coordinate::geometry) as x ,st_y(coordinate::geometry) as y,e.nome as enome  from stazione_metereologica staz "
                + "left join sito_stazione ss on (staz.idsitostazione=ss.idsitostazione)"
                + "left join ente e  on (staz.idente=e.idente)"
                + "left join ubicazione u on (staz.idubicazione=u.idubicazione)"
                + "left join comune c on (c.idcomune=u.idcomune)"
                + "left join provincia p on (c.idProvincia=p.idProvincia)"
                + "left join regione r on ( r.idregione=p.idregione)"
                + "left join nazione n on (r.idnazione=n.idnazione)"
                + "left join sottobacino s on (s.idsottobacino=u.idsottobacino) "
                + "left join bacino b on (b.idbacino=s.idbacino)");
        while (rs.next()) {
            StazioneMetereologica s = new StazioneMetereologica();
            Ubicazione u = new Ubicazione();
            Coordinate coord = new Coordinate();
            LocazioneAmministrativa locAmm = new LocazioneAmministrativa();
            LocazioneIdrologica locIdro = new LocazioneIdrologica();
            s.setIdStazioneMetereologica(rs.getInt("idStazioneMetereologica"));
            s.setAggregazioneGiornaliera(rs.getString("aggregazioneGiornaliera"));
            s.setNote(rs.getString("note"));
            s.setTipoAggregazione(rs.getString("tipoaggregazione"));

            //	s.setOraria(rs.getBoolean("oraria"));
            s.setDataInizio(rs.getString("datainizio"));
            s.setDataFine(rs.getString("datafine"));
            s.setNome(rs.getString("nome"));
            coord.setX(rs.getDouble("x"));
            coord.setY(rs.getDouble("y"));
            locAmm.setIdComune(rs.getInt("idcomune"));
            locAmm.setComune(rs.getString("nomecomune"));
            locAmm.setProvincia(rs.getString("nomeprovincia"));
            locAmm.setRegione(rs.getString("nomeregione"));
            locAmm.setNazione(rs.getString("nomenazione"));
            locIdro.setIdSottoBacino(rs.getInt("idsottobacino"));
            locIdro.setBacino(rs.getString("nomebacino"));
            locIdro.setSottobacino(rs.getString("nomesottobacino"));
            u.setCoordinate(coord);
            u.setLocAmm(locAmm);
            u.setLocIdro(locIdro);
            u.setEsposizione(rs.getString("esposizione"));
            u.setQuota(rs.getDouble("quota"));
            s.setUbicazione(u);
            Ente e = new Ente();
            e.setIdEnte(rs.getInt("idente"));
            e.setEnte(rs.getString("enome"));
            s.setEnte(e);
            SitoStazioneMetereologica sito = new SitoStazioneMetereologica();
            sito.setIdSitoStazioneMetereologica(rs.getInt("idsitostazione"));
            sito.setCaratteristiche_IT(rs.getString("caratteristiche_it"));
            sito.setCaratteristiche_ENG(rs.getString("caratteristiche_eng"));
            s.setSito(sito);
            s.setIdUtente(rs.getInt("idutentecreatore"));
            al.add(s);
        }
        rs.close();
        st.close();
        conn.close();
        return al;

    }

    public static ArrayList<StazioneMetereologica> prendiTutteStazioniMetereologicheConDati(String tabella) throws SQLException {
        ArrayList<StazioneMetereologica> al = new ArrayList<StazioneMetereologica>();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        System.out.println("select *,st_x(coordinate::geometry) as x ,st_y(coordinate::geometry) as y,e.nome as enome  from stazione_metereologica staz "
                + "left join sito_stazione ss on (staz.idsitostazione=ss.idsitostazione)"
                + "left join ente e  on (staz.idente=e.idente)"
                + "left join ubicazione u on (staz.idubicazione=u.idubicazione)"
                + "left join comune c on (c.idcomune=u.idcomune)"
                + "left join provincia p on (c.idProvincia=p.idProvincia)"
                + "left join regione r on ( r.idregione=p.idregione)"
                + "left join nazione n on (r.idnazione=n.idnazione)"
                + "left join sottobacino s on (s.idsottobacino=u.idsottobacino) "
                + "left join bacino b on (b.idbacino=s.idbacino)"
                + " " + tabella);
        ResultSet rs = st.executeQuery("select *,st_x(coordinate::geometry) as x ,st_y(coordinate::geometry) as y,e.nome as enome  from stazione_metereologica staz "
                + "left join sito_stazione ss on (staz.idsitostazione=ss.idsitostazione)"
                + "left join ente e  on (staz.idente=e.idente)"
                + "left join ubicazione u on (staz.idubicazione=u.idubicazione)"
                + "left join comune c on (c.idcomune=u.idcomune)"
                + "left join provincia p on (c.idProvincia=p.idProvincia)"
                + "left join regione r on ( r.idregione=p.idregione)"
                + "left join nazione n on (r.idnazione=n.idnazione)"
                + "left join sottobacino s on (s.idsottobacino=u.idsottobacino) "
                + "left join bacino b on (b.idbacino=s.idbacino)"
                + " " + tabella);
        while (rs.next()) {
            StazioneMetereologica s = new StazioneMetereologica();
            Ubicazione u = new Ubicazione();
            Coordinate coord = new Coordinate();
            LocazioneAmministrativa locAmm = new LocazioneAmministrativa();
            LocazioneIdrologica locIdro = new LocazioneIdrologica();
            s.setIdStazioneMetereologica(rs.getInt("idStazioneMetereologica"));
            s.setAggregazioneGiornaliera(rs.getString("aggregazioneGiornaliera"));
            s.setNote(rs.getString("note"));
            s.setTipoAggregazione(rs.getString("tipoaggregazione"));

            //	s.setOraria(rs.getBoolean("oraria"));
            s.setDataInizio(rs.getString("datainizio"));
            s.setDataFine(rs.getString("datafine"));
            s.setNome(rs.getString("nome"));
            coord.setX(rs.getDouble("x"));
            coord.setY(rs.getDouble("y"));
            locAmm.setIdComune(rs.getInt("idcomune"));
            locAmm.setComune(rs.getString("nomecomune"));
            locAmm.setProvincia(rs.getString("nomeprovincia"));
            locAmm.setRegione(rs.getString("nomeregione"));
            locAmm.setNazione(rs.getString("nomenazione"));
            locIdro.setIdSottoBacino(rs.getInt("idsottobacino"));
            locIdro.setBacino(rs.getString("nomebacino"));
            locIdro.setSottobacino(rs.getString("nomesottobacino"));
            u.setCoordinate(coord);
            u.setLocAmm(locAmm);
            u.setLocIdro(locIdro);
            u.setEsposizione(rs.getString("esposizione"));
            u.setQuota(rs.getDouble("quota"));
            s.setUbicazione(u);
            Ente e = new Ente();
            e.setIdEnte(rs.getInt("idente"));
            e.setEnte(rs.getString("enome"));
            s.setEnte(e);
            SitoStazioneMetereologica sito = new SitoStazioneMetereologica();
            sito.setIdSitoStazioneMetereologica(rs.getInt("idsitostazione"));
            sito.setCaratteristiche_IT(rs.getString("caratteristiche_it"));
            sito.setCaratteristiche_ENG(rs.getString("caratteristiche_eng"));
            s.setSito(sito);
            s.setIdUtente(rs.getInt("idutentecreatore"));
            al.add(s);
        }
        rs.close();
        st.close();
        conn.close();
        return al;

    }

    public static StazioneMetereologica prendiStazioneMetereologica(int idStazioneMetereologica) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select *,st_x(coordinate::geometry) as x ,st_y(coordinate::geometry) as y,e.nome as enome  from stazione_metereologica staz "
                + "left join sito_stazione ss on (staz.idsitostazione=ss.idsitostazione)"
                + "left join ente e  on (staz.idente=e.idente)"
                + "left join ubicazione u on (staz.idubicazione=u.idubicazione)"
                + "left join comune c on (c.idcomune=u.idcomune)"
                + "left join provincia p on (c.idProvincia=p.idProvincia)"
                + "left join regione r on ( r.idregione=p.idregione)"
                + "left join nazione n on (r.idnazione=n.idnazione)"
                + "left join sottobacino s on (s.idsottobacino=u.idsottobacino) "
                + "left join bacino b on (b.idbacino=s.idbacino) where idstazionemetereologica=" + idStazioneMetereologica + "");
        StazioneMetereologica s = new StazioneMetereologica();
        Ubicazione u = new Ubicazione();
        Coordinate coord = new Coordinate();
        LocazioneAmministrativa locAmm = new LocazioneAmministrativa();
        LocazioneIdrologica locIdro = new LocazioneIdrologica();

        while (rs.next()) {
            s.setIdStazioneMetereologica(idStazioneMetereologica);
            s.setAggregazioneGiornaliera(rs.getString("aggregazioneGiornaliera"));
            s.setNote(rs.getString("note"));
            s.setTipoAggregazione(rs.getString("tipoaggregazione"));

            if (rs.getString("datainizio") != null) {
                s.setDataInizio(rs.getString("datainizio"));
            }

            if (rs.getString("datafine") != null) {
                s.setDataFine(rs.getString("datafine"));
            }
            s.setNome(rs.getString("nome"));
            coord.setX(rs.getDouble("x"));
            coord.setY(rs.getDouble("y"));
            locAmm.setIdComune(rs.getInt("idcomune"));
            locAmm.setComune(rs.getString("nomecomune"));
            locAmm.setProvincia(rs.getString("nomeprovincia"));
            locAmm.setRegione(rs.getString("nomeregione"));
            locAmm.setNazione(rs.getString("nomenazione"));
            locIdro.setIdSottoBacino(rs.getInt("idsottobacino"));
            locIdro.setBacino(rs.getString("nomebacino"));
            locIdro.setSottobacino(rs.getString("nomesottobacino"));
            u.setCoordinate(coord);
            u.setLocAmm(locAmm);
            u.setLocIdro(locIdro);
            u.setEsposizione(rs.getString("esposizione"));
            System.out.println("quota " + rs.getDouble("quota") + " espoizione" + rs.getString("esposizione"));
            u.setQuota(rs.getDouble("quota"));
            s.setUbicazione(u);
            Ente e = new Ente();

            System.out.println("prende ente " + rs.getInt("idente"));

            e.setIdEnte(rs.getInt("idente"));
            e.setEnte(rs.getString("enome"));
            s.setEnte(e);
            SitoStazioneMetereologica sito = new SitoStazioneMetereologica();
            sito.setIdSitoStazioneMetereologica(rs.getInt("idsitostazione"));
            sito.setCaratteristiche_IT(rs.getString("caratteristiche_it"));
            sito.setCaratteristiche_ENG(rs.getString("caratteristiche_eng"));
            s.setSito(sito);
            s.setIdUtente(rs.getInt("idutentecreatore"));
            System.out.println("prende ente " + rs.getInt("idente"));
        }
        ArrayList<Sensori> sensori = prendiSensori(idStazioneMetereologica);
        s.setSensori(sensori);

        rs.close();
        st.close();
        conn.close();
        return s;
    }

    public static Ente prendiEnte(int idente) throws SQLException {
        Ente e = new Ente();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM ente WHERE IDente=" + idente + "");
        while (rs.next()) {
            e.setEnte(rs.getString("nome"));
            e.setIdEnte(rs.getInt("idente"));
        }
        rs.close();
        st.close();
        conn.close();
        return e;
    }

    public static SitoStazioneMetereologica prendiSitoStazioneMetereologica(int id) throws SQLException {
        SitoStazioneMetereologica s = new SitoStazioneMetereologica();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM sito_Stazione where idsitostazione=" + id + " ");
        while (rs.next()) {
            s.setCaratteristiche_ENG(rs.getString("caratteristiche_eng"));
            s.setCaratteristiche_IT(rs.getString("caratteristiche_it"));
            s.setIdSitoStazioneMetereologica(rs.getInt("idsitostazione"));
        }
        rs.close();
        st.close();
        conn.close();
        return s;
    }

    public static void modificaStazioneMetereologica(StazioneMetereologica s, String enteVecchio, int idStazione) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();

        String sql = "update stazione_metereologica set nome=?,aggregazionegiornaliera=?,note=?,datainizio=?,datafine=?,idsitostazione=?,idente=?,idutentecreatore=?,tipoaggregazione=? "
                + "where idStazioneMetereologica= ?";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, s.getNome());
        ps.setString(2, s.getAggregazioneGiornaliera());
        if (!(enteVecchio.equals(s.getEnte().getEnte())) && !(enteVecchio.equals("null"))) {
            ps.setString(3, " stazione passata da " + enteVecchio + "a " + s.ente.getEnte() + ". " + s.getNote() + " ");
        } else {
            ps.setString(3, "" + s.getNote() + "");
        }
        ps.setString(4, s.getDataInizio());
        ps.setString(5, s.getDataFine());
        if (s.sito.getIdSitoStazioneMetereologica() != 0) {
            ps.setInt(6, s.sito.getIdSitoStazioneMetereologica());
        } else {
            ps.setNull(6, Types.INTEGER);
        }
        if (s.ente.getIdEnte() != 0) {
            ps.setInt(7, s.ente.getIdEnte());
        } else {
            ps.setNull(7, Types.INTEGER);
        }
        ps.setInt(8, s.getIdUtente());
        ps.setString(9, s.getTipoAggregazione());

        System.out.println("id stazione" + s.getIdStazioneMetereologica());
        ps.setInt(10, s.getIdStazioneMetereologica());
        System.out.println("query: " + ps.toString());
        ps.executeUpdate();

        /*
         * modifica sensori
         */
        st.executeUpdate("delete from sensore_stazione where idStazionemetereologica=" + idStazione + "");
        for (Sensori i : s.getSensori()) {
            if (i.getIdsensori() != 0) {
                try {
                    st.executeUpdate("INSERT INTO sensore_stazione (idstazionemetereologica,idsensore) VALUES (" + idStazione + "," + i.getIdsensori() + ")");
                } catch (SQLException e) {
                    System.out.println("sensore esistente");
                }
            }
        }
        st.close();
        conn.close();
        /*
         * modifica dell'ubicazione
         */
        if (s.getUbicazione() != null) {
            modificaUbicazione(s.getUbicazione());
        }

    }

    public static void salvaSensoriStazione(StazioneMetereologica s) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO sensore_stazione(idsensore,idstazionemetereologica) values");
        for (int i = 0; i < s.getSensori().size(); i++) {
            if (i != s.getSensori().size() - 1) {
                sb.append("( " + s.getSensori().get(i).getIdsensori() + "," + s.getIdStazioneMetereologica() + "),");
            } else {
                sb.append("( " + s.getSensori().get(i).getIdsensori() + "," + s.getIdStazioneMetereologica() + ") ");
            }
        }
        System.out.println(sb.toString());
        st.executeUpdate(sb.toString());
        st.close();
        conn.close();
    }

    public static ArrayList<Sensori> prendiSensore(String[] sensori) throws SQLException {
        ArrayList<Sensori> s = new ArrayList<Sensori>();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        StringBuilder sb = new StringBuilder();

        sb.append("select *from sensore where idsensore=" + sensori[0]);
        for (int i = 1; i < sensori.length; i++) {
            sb.append("or idsensore=" + sensori[i]);
        }
        ResultSet rs = st.executeQuery(sb.toString());
        System.out.println(sb.toString());
        while (rs.next()) {
            Sensori se = new Sensori();
            se.setIdsensori(rs.getInt("idsensore"));
            se.setSensori_ENG(rs.getString("tipo_eng"));
            se.setSensori_IT(rs.getString("tipo_it"));
            s.add(se);
        }
        rs.close();
        st.close();
        conn.close();
        return s;
    }

    public static ArrayList<StazioneMetereologica> ricercaStazioneMetereologica(StazioneMetereologica s, Ubicazione u) throws SQLException {
        ArrayList<StazioneMetereologica> al = new ArrayList<StazioneMetereologica>();
        StringBuilder sb = new StringBuilder();
        StringBuilder su = new StringBuilder();
        StringBuilder query = new StringBuilder();

        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();

        if (!(s.getNome() == null || s.getNome().equals(""))) {
            if ((sb.toString().equals(""))) {
                sb.append(" where staz.nome similar to '%" + s.getNome() + "%'");
            }
        }
        if (!(s.getAggregazioneGiornaliera() == null || s.getAggregazioneGiornaliera().equals(""))) {

            if (!(sb.toString().equals(""))) {
                sb.append(" and staz.aggregazionegiornaliera similar to '%" + s.getAggregazioneGiornaliera() + "%'");
            } else if ((sb.toString().equals(""))) {
                sb.append(" where staz.aggregazionegiornaliera similar to '%" + s.getAggregazioneGiornaliera() + "%'");
            }
        }

        /*if(!(s.getDataInizio()==null)){
         if(sb.toString().equals("") || sb == null)
         sb.append("where datainizio= '"+s.getDataInizio()+"' ");
         else sb.append("and datainzio='"+s.getDataInizio()+"'");
         }
         if(!(s.getDataFine()==null)){
         if(sb.toString().equals("") || sb == null)
         sb.append("where datafine= '"+s.getDataFine()+"' ");
         else sb.append("and datafine='"+s.getDataFine()+"'");
         }
		
         */
        System.out.println("sito stazione cotnroller" + s.sito.getIdSitoStazioneMetereologica());

        if (!(s.sito.getIdSitoStazioneMetereologica() == 0)) {
            if (!(sb.toString().equals(""))) {
                sb.append(" and staz.idsitostazione=" + s.sito.getIdSitoStazioneMetereologica() + "");
            } else if ((sb.toString().equals(""))) {
                sb.append(" where staz.idsitostazione=" + s.sito.getIdSitoStazioneMetereologica() + "");
            }

        }
        if (!(s.ente.getIdEnte() == 0)) {
            if (!(sb.toString().equals(""))) {
                sb.append("and idente=" + s.ente.getIdEnte() + "");
            } else if ((sb.toString().equals(""))) {
                sb.append(" where idente=" + s.ente.getIdEnte() + "");
            }
        }
        StringBuilder se = new StringBuilder();
        if (!(s.getSensori().size() == 0)) {
            se.append(" and idstazionemetereologica in(");
            for (int i = 0; i < s.getSensori().size(); i++) {
                if (i == 0) {
                    se.append("select distinct(idstazionemetereologica) from sensore_stazione where idsensore=" + s.getSensori().get(i).getIdsensori() + "");
                } else {
                    se.append(" and staz.idstazionemetereologica in(select distinct(idstazionemetereologica) from sensore_stazione where idsensore=" + s.getSensori().get(i).getIdsensori() + "");
                }
            }
            int i = 0;
            while (i != s.getSensori().size()) {
                se.append(")");
                i++;
            }

        }
        sb.append(se);

        if (!(u.getLocAmm().isEmpty())) {
            if (!(u.getLocAmm().getComune() == null || u.getLocAmm().getComune().equals(""))) {

                if (!(sb.toString().equals(""))) {
                    su.append(" and c.nomecomune ='" + u.getLocAmm().getComune() + "'");
                } else if ((su.toString().equals(""))) {
                    su.append(" where c.nomecomune ='" + u.getLocAmm().getComune() + "'");
                } else {
                    su.append(" and c.nomecomune ='" + u.getLocAmm().getComune() + "'");
                }

            }
            if (!(u.getLocAmm().getProvincia() == null || u.getLocAmm().getProvincia().equals(""))) {

                if (!(sb.toString().equals(""))) {
                    su.append(" and p.nomeprovincia = '" + u.getLocAmm().getProvincia() + "'");
                } else if ((su.toString().equals(""))) {
                    su.append(" where p.nomeprovincia = '" + u.getLocAmm().getProvincia() + "'");
                } else {
                    su.append(" and p.nomeprovincia = '" + u.getLocAmm().getProvincia() + "'");
                }
            }
            if (!(u.getLocAmm().getRegione() == null || u.getLocAmm().getRegione().equals(""))) {

                if (!(sb.toString().equals(""))) {
                    su.append(" and r.nomeregione = '" + u.getLocAmm().getRegione() + "'");
                } else if ((su.toString().equals(""))) {
                    su.append(" where r.nomeregione = '" + u.getLocAmm().getRegione() + "'");
                } else {
                    su.append(" and r.nomeregione = '" + u.getLocAmm().getRegione() + "'");
                }

            }
            if (!(u.getLocAmm().getNazione() == null || u.getLocAmm().getNazione().equals(""))) {

                if (!(sb.toString().equals(""))) {
                    su.append(" and n.nomenazione ='" + u.getLocAmm().getNazione() + "'");
                } else if ((su.toString().equals(""))) {
                    su.append(" where n.nomenazione ='" + u.getLocAmm().getNazione() + "'");
                } else {
                    su.append(" and n.nomenazione ='" + u.getLocAmm().getNazione() + "'");
                }
            }
        }

        if (!(u.getLocIdro().isEmpty())) {
            if (!(u.getLocIdro().getBacino() == null || u.getLocIdro().getBacino().equals(""))) {

                if (!(sb.toString().equals(""))) {
                    su.append(" and b.nomebacino ='" + u.getLocIdro().getBacino() + "'");
                } else if ((su.toString().equals(""))) {
                    su.append(" where b.nomebacino ='" + u.getLocIdro().getBacino() + "'");
                } else {
                    su.append(" and b.nomebacino ='" + u.getLocIdro().getBacino() + "'");
                }
            }
            if (!(u.getLocIdro().getSottobacino() == null || u.getLocIdro().getSottobacino().equals(""))) {

                if (!(sb.toString().equals(""))) {
                    su.append(" and s.nomesottobacino ='" + u.getLocIdro().getSottobacino() + "'");
                } else if ((su.toString().equals(""))) {
                    su.append(" where s.nomesottobacino ='" + u.getLocIdro().getSottobacino() + "'");
                } else {
                    su.append(" and s.nomesottobacino ='" + u.getLocIdro().getSottobacino() + "'");
                }
            }
        }
        if (!(u.getQuota() == null)) {
            if (!(su.toString().equals(""))) {
                su.append(" and u.quota=" + u.getQuota());
            } else if ((su.toString().equals(""))) {
                su.append("where u.quota=" + u.getQuota());
            }
        }

        ResultSet rs = null;

        if (u.isEmpty() == true) {

            rs = st.executeQuery("select *,st_x(coordinate::geometry) as x ,st_y(coordinate::geometry) as y,e.nome as enome  from stazione_metereologica staz "
                    + "left join sito_stazione ss on (staz.idsitostazione=ss.idsitostazione)"
                    + "left join ente e  on (staz.idente=e.idente)"
                    + "left join ubicazione u on (staz.idubicazione=u.idubicazione)"
                    + "left join comune c on (c.idcomune=u.idcomune)"
                    + "left join provincia p on (c.idProvincia=p.idProvincia)"
                    + "left join regione r on ( r.idregione=p.idregione)"
                    + "left join nazione n on (r.idnazione=n.idnazione)"
                    + "left join sottobacino s on (s.idsottobacino=u.idsottobacino) "
                    + "left join bacino b on (b.idbacino=s.idbacino)"
                    + "" + sb.toString() + " ");
        } else {

            rs = st.executeQuery("select *,st_x(coordinate::geometry) as x ,st_y(coordinate::geometry) as y,e.nome as enome  from stazione_metereologica staz "
                    + "left join sito_stazione ss on (staz.idsitostazione=ss.idsitostazione)"
                    + "left join ente e  on (staz.idente=e.idente)"
                    + "left join ubicazione u on (staz.idubicazione=u.idubicazione)"
                    + "left join comune c on (c.idcomune=u.idcomune)"
                    + "left join provincia p on (c.idProvincia=p.idProvincia)"
                    + "left join regione r on ( r.idregione=p.idregione)"
                    + "left join nazione n on (r.idnazione=n.idnazione)"
                    + "left join sottobacino s on (s.idsottobacino=u.idsottobacino) "
                    + "left join bacino b on (b.idbacino=s.idbacino)"
                    + "" + sb.toString() + " " + su.toString() + " ");
        }

        while (rs.next()) {
            StazioneMetereologica sm = new StazioneMetereologica();
            Ubicazione ubi = new Ubicazione();
            Coordinate coord = new Coordinate();
            LocazioneAmministrativa locAmm = new LocazioneAmministrativa();
            LocazioneIdrologica locIdro = new LocazioneIdrologica();
            sm.setIdStazioneMetereologica(rs.getInt("idStazioneMetereologica"));
            sm.setAggregazioneGiornaliera(rs.getString("aggregazioneGiornaliera"));
            sm.setNote(rs.getString("note"));
            sm.setDataInizio(rs.getString("datainizio"));
            sm.setDataFine(rs.getString("datafine"));
            sm.setNome(rs.getString("nome"));
            coord.setX(rs.getDouble("x"));
            coord.setY(rs.getDouble("y"));
            locAmm.setIdComune(rs.getInt("idcomune"));
            locAmm.setComune(rs.getString("nomecomune"));
            locAmm.setProvincia(rs.getString("nomeprovincia"));
            locAmm.setRegione(rs.getString("nomeregione"));
            locAmm.setNazione(rs.getString("nomenazione"));
            locIdro.setIdSottoBacino(rs.getInt("idsottobacino"));
            locIdro.setBacino(rs.getString("nomebacino"));
            locIdro.setSottobacino(rs.getString("nomesottobacino"));
            ubi.setCoordinate(coord);
            ubi.setLocAmm(locAmm);
            ubi.setLocIdro(locIdro);
            ubi.setEsposizione(rs.getString("esposizione"));
            ubi.setQuota(rs.getDouble("quota"));
            sm.setUbicazione(ubi);
            Ente e = new Ente();
            e.setIdEnte(rs.getInt("idente"));
            e.setEnte(rs.getString("enome"));
            sm.setEnte(e);
            SitoStazioneMetereologica sito = new SitoStazioneMetereologica();
            sito.setIdSitoStazioneMetereologica(rs.getInt("idsitostazione"));
            sito.setCaratteristiche_IT(rs.getString("caratteristiche_it"));
            sito.setCaratteristiche_ENG(rs.getString("caratteristiche_eng"));
            sm.setSito(sito);
            sm.setIdUtente(rs.getInt("idutentecreatore"));
            al.add(sm);
        }

        rs.close();
        st.close();
        conn.close();
        return al;
    }

    public static ArrayList<Sensori> prendiSensori(int idStazione) throws SQLException {
        ArrayList<Sensori> sensori = new ArrayList<Sensori>();

        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        System.out.println("select * from sensore where idsensore in(select idsensore from sensore_stazione where idstazionemetereologica=" + idStazione + ")");
        ResultSet rs = st.executeQuery("select * from sensore where idsensore in(select idsensore from sensore_stazione where idstazionemetereologica=" + idStazione + ")");
        while (rs.next()) {
            Sensori s = new Sensori();
            s.setSensori_IT((rs.getString("tipo_it")));
            s.setSensori_ENG((rs.getString("tipo_eng")));

            s.setIdsensori(rs.getInt("idsensore"));
            sensori.add(s);
        }
        rs.close();
        st.close();
        conn.close();
        return sensori;

    }

    public static String prendiNome(int id) throws SQLException {
        String nome = "";
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select nome from stazione_metereologica where idstazionemetereologica=" + id + "");
        while (rs.next()) {
            nome = rs.getString("nome");
        }
        rs.close();
        st.close();
        conn.close();
        return nome;
    }

    public static ArrayList<SitoStazioneMetereologica> prendiTuttiSitoStazioneMetereologica() throws SQLException {
        ArrayList<SitoStazioneMetereologica> sito = new ArrayList<SitoStazioneMetereologica>();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM sito_Stazione ");
        while (rs.next()) {
            SitoStazioneMetereologica s = new SitoStazioneMetereologica();
            s.setCaratteristiche_ENG(rs.getString("caratteristiche_eng"));
            s.setCaratteristiche_IT(rs.getString("caratteristiche_it"));
            s.setIdSitoStazioneMetereologica(rs.getInt("idsitostazione"));
            sito.add(s);
        }
        rs.close();
        st.close();
        conn.close();
        return sito;
    }

    public static ArrayList<Ente> prendiTuttiEnte() throws SQLException {
        ArrayList<Ente> ente = new ArrayList<Ente>();

        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM ente ");
        while (rs.next()) {
            Ente e = new Ente();
            e.setEnte(rs.getString("nome"));
            e.setIdEnte(rs.getInt("idente"));
            ente.add(e);
        }
        rs.close();
        st.close();
        conn.close();
        return ente;
    }

    public static ArrayList<Sensori> prendiTuttiSensori() throws SQLException {
        ArrayList<Sensori> sensori = new ArrayList<Sensori>();

        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM sensore ");
        while (rs.next()) {
            Sensori s = new Sensori();
            s.setSensori_ENG((rs.getString("tipo_eng")));
            s.setSensori_IT((rs.getString("tipo_it")));
            s.setIdsensori(rs.getInt("idsensore"));
            sensori.add(s);
        }
        rs.close();
        st.close();
        conn.close();
        return sensori;
    }

    public static ArrayList<StazioneMetereologica> prendiStazionidaRaggio(double x, double y, Processo p, int r) throws SQLException {
        ArrayList<StazioneMetereologica> s = new ArrayList<StazioneMetereologica>();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT *,st_distance((select coordinate from ubicazione where idubicazione=" + p.getUbicazione().getIdUbicazione() + "),ubicazione.coordinate) as distance FROM ubicazione,stazione_metereologica WHERE ST_DWithin((select coordinate from ubicazione where idubicazione=" + p.getUbicazione().getIdUbicazione() + "), ubicazione.coordinate," + r + ")=true and ubicazione.idubicazione=stazione_metereologica.idubicazione");
        while (rs.next()) {
            StazioneMetereologica stazione = new StazioneMetereologica();
            stazione.setIdStazioneMetereologica(rs.getInt("idstazionemetereologica"));
            stazione.setDistanzaProcesso(rs.getDouble("distance"));
            stazione.setNome(rs.getString("nome"));
            //completare
            Ubicazione ub = prendiUbicazione(rs.getInt("idUbicazione"));
            stazione.setUbicazione(ub);
            s.add(stazione);
        }
        rs.close();
        st.close();
        conn.close();
        return s;
    }

    /*
     * ubicazione
     */
    public static Ubicazione salvaUbicazione(Ubicazione u) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        StringBuilder sb = new StringBuilder();
        /*if(u.getLocIdro().getIdSottobacino()!=0)
         sb.append(""+u.getLocIdro().getIdSottobacino()+"");
         else 
         sb.append("null");
         if(u.getLocAmm().getIdComune()!=0)
         sb.append(","+u.getLocAmm().getIdComune()+"");
         else 
         sb.append(",null");
         sb.append(","+u.getQuota());
         sb.append(",'"+u.getEsposizione()+"'");*/
        sb.append("ST_GeographyFromText(" + u.getCoordinate().toDB() + ")");

		//st.executeUpdate("INSERT INTO ubicazione(idSottobacino,idComune,quota,esposizione,coordinate) values("+sb.toString()+") ");
        //		ResultSet rs = st.executeQuery("SELECT * FROM ubicazione WHERE coordinate= "+u.getCoordinate().toDB()+" ");

        /*
         if(p.getUbicazione().getIdUbicazione()!=0)
         ps.setInt(1,  p.getUbicazione().getIdUbicazione());
         else
         ps.setNull(1, Types.INTEGER);
         if(p.getSitoProcesso().getIdSito()!=0)
         ps.setInt(2, p.getSitoProcesso().getIdSito());
         */
        System.out.println(" sottobaci in db" + u.getLocIdro().getIdSottobacino());
        String sql = "INSERT INTO ubicazione(idSottobacino,idComune,quota,esposizione,affidabilita)"
                + " values(?,?,?,?,?) ";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        if (u.getLocIdro().getIdSottobacino() != 0) {
            ps.setInt(1, u.getLocIdro().getIdSottobacino());
        } else {
            ps.setNull(1, Types.INTEGER);
        }
        if (u.getLocAmm().getIdComune() != 0) {
            ps.setInt(2, u.getLocAmm().getIdComune());
        } else {
            ps.setNull(2, Types.INTEGER);
        }
        ps.setDouble(3, u.getQuota());
        ps.setString(4, u.getEsposizione());
        ps.setString(5, u.getAttendibilita());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();

        System.out.println(" " + ps.toString());
        while (rs.next()) {
            u.setIdUbicazione(rs.getInt("idUbicazione"));
            System.out.println("id= " + u.getIdUbicazione());
        }

        st.executeUpdate("UPDATE ubicazione SET coordinate=" + sb.toString() + " where idubicazione=" + u.getIdUbicazione() + " ");

        rs.close();
        st.close();
        conn.close();
        return u;
    }

    public static void modificaUbicazione(Ubicazione u) throws SQLException {
        ArrayList<StazioneMetereologica> al = new ArrayList<StazioneMetereologica>();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();

        String ubicazionesql = "update ubicazione set esposizione=?,affidabilita=?,quota=?,idcomune=?,idsottobacino=? where idubicazione=? ";
        PreparedStatement ps = conn.prepareStatement(ubicazionesql);
        ps = conn.prepareStatement(ubicazionesql);
        ps.setString(1, u.getEsposizione());
        ps.setString(2, u.getAttendibilita());
        ps.setDouble(3, u.getQuota());

        if (u.getLocAmm().getIdComune() == 0) {
            ps.setNull(4, Types.INTEGER);
        } else {
            ps.setInt(4, u.getLocAmm().getIdComune());
        }
        if (u.getLocIdro().getIdSottobacino() == 0) {
            ps.setNull(5, Types.INTEGER);
        } else {
            ps.setInt(5, u.getLocIdro().getIdSottobacino());
        }
        ps.setInt(6, u.getIdUbicazione());
        System.out.println("query update ubicazione; " + ps.toString());
        ps.executeUpdate();
        System.out.println("update ubicazione set coordinate=" + u.getCoordinate().toDB() + " WHERE idUbicazione=" + u.getIdUbicazione() + " ");
        st.executeUpdate("update ubicazione set coordinate=" + u.getCoordinate().toDB() + " WHERE idUbicazione=" + u.getIdUbicazione() + " ");
        st.close();
        conn.close();
    }

    public static Ubicazione prendiUbicazione(int idUbicazione) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        Ubicazione u = new Ubicazione();
        ResultSet rs = st.executeQuery("SELECT * FROM ubicazione WHERE idUbicazione=" + idUbicazione + " ");
        while (rs.next()) {
            u.setIdUbicazione(rs.getInt("idUbicazione"));
            u.setCoordinate(prendiCoordinate(idUbicazione));
            u.setLocAmm(prendiLocAmministrativa(rs.getInt("idComune")));
            u.setLocIdro(prendiLocIdrologica(rs.getInt("idsottobacino")));
            u.setEsposizione(rs.getString("esposizione"));
            u.setQuota(rs.getDouble("quota"));
        }
        rs.close();
        st.close();
        conn.close();
        return u;
    }

    public static LocazioneAmministrativa prendiLocAmministrativa(int idComune) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        LocazioneAmministrativa locAmm = new LocazioneAmministrativa();
        ResultSet rs = st.executeQuery("select * from comune,provincia,regione,nazione where (comune.idProvincia=provincia.idProvincia) and ( regione.idregione=provincia.idregione) and(regione.idnazione=nazione.idnazione) and idcomune=" + idComune + "");
        while (rs.next()) {
            locAmm.setIdComune(rs.getInt("idcomune"));
            locAmm.setComune(rs.getString("nomecomune"));
            locAmm.setProvincia(rs.getString("nomeprovincia"));
            locAmm.setRegione(rs.getString("nomeregione"));
            locAmm.setNazione(rs.getString("nomenazione"));
        }
        rs.close();
        st.close();
        conn.close();
        return locAmm;
    }

    public static ArrayList<LocazioneAmministrativa> prendiLocAmministrativaAll() throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ArrayList<LocazioneAmministrativa> localizAmm = new ArrayList<LocazioneAmministrativa>();
        ResultSet rs = st.executeQuery("select * from comune,provincia,regione,nazione where (comune.idProvincia=provincia.idProvincia) and ( regione.idregione=provincia.idregione) and(regione.idnazione=nazione.idnazione)");
        while (rs.next()) {
            LocazioneAmministrativa locAmm = new LocazioneAmministrativa();
            locAmm.setIdComune(rs.getInt("idcomune"));
            locAmm.setComune(rs.getString("nomecomune"));
            locAmm.setProvincia(rs.getString("nomeprovincia"));
            locAmm.setRegione(rs.getString("nomeregione"));
            locAmm.setNazione(rs.getString("nomenazione"));
            localizAmm.add(locAmm);
        }
        rs.close();
        st.close();
        conn.close();
        return localizAmm;
    }

    public static LocazioneIdrologica prendiLocIdrologica(int idSottobacino) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        LocazioneIdrologica locIdro = new LocazioneIdrologica();
        ResultSet rs = st.executeQuery("select * from bacino,sottobacino where bacino.idbacino=sottobacino.idbacino and idsottobacino=" + idSottobacino + "");

        while (rs.next()) {
            locIdro.setIdSottoBacino(rs.getInt("idsottobacino"));
            locIdro.setBacino(rs.getString("nomebacino"));
            locIdro.setSottobacino(rs.getString("nomesottobacino"));
        }
        rs.close();
        st.close();
        conn.close();
        return locIdro;
    }

    public static ArrayList<LocazioneIdrologica> prendiLocIdrologicaAll() throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ArrayList<LocazioneIdrologica> locIdrologica = new ArrayList<LocazioneIdrologica>();
        ResultSet rs = st.executeQuery("select * from bacino,sottobacino where sottobacino.idbacino=bacino.idbacino");
        while (rs.next()) {
            LocazioneIdrologica locIdro = new LocazioneIdrologica();
            locIdro.setIdSottoBacino(rs.getInt("idsottobacino"));
            locIdro.setBacino(rs.getString("nomebacino"));
            locIdro.setSottobacino(rs.getString("nomesottobacino"));
            locIdrologica.add(locIdro);
        }
        rs.close();
        st.close();
        conn.close();
        return locIdrologica;
    }

    public static Coordinate prendiCoordinate(int idUbicazione) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        Coordinate coord = new Coordinate();
        ResultSet rs = st.executeQuery("select st_x(coordinate::geometry) as x ,st_y(coordinate::geometry) as y from ubicazione where idubicazione = " + idUbicazione + "");
        while (rs.next()) {
            coord.setX(rs.getDouble("x"));
            coord.setY(rs.getDouble("y"));
        }
        rs.close();
        st.close();
        conn.close();
        return coord;
    }

    public static LocazioneAmministrativa cercaLocazioneAmministrativa(String nomecomune) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        LocazioneAmministrativa locAmm = new LocazioneAmministrativa();
        ResultSet rs = st.executeQuery("select * from comune c,provincia p,regione r,nazione n where (c.idProvincia=p.idProvincia) and ( r.idregione=p.idregione)"
                + "and(r.idnazione=n.idnazione) and c.nomecomune= '" + nomecomune + "'");
        while (rs.next()) {
            locAmm.setComune(rs.getString("nomecomune"));
            locAmm.setProvincia(rs.getString("nomeprovincia"));
            locAmm.setRegione(rs.getString("nomeregione"));
            locAmm.setNazione(rs.getString("nomenazione"));
            locAmm.setIdComune(rs.getInt("idcomune"));
        }
        rs.close();
        st.close();
        conn.close();
        return locAmm;

    }

    public static LocazioneIdrologica cercaLocazioneIdrologica(String nomesottobacino) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        LocazioneIdrologica locIdro = new LocazioneIdrologica();
        ResultSet rs = st.executeQuery("select * from bacino,sottobacino where bacino.idbacino=sottobacino.idbacino and nomesottobacino='" + nomesottobacino + "'");
        while (rs.next()) {
            locIdro.setBacino(rs.getString("nomebacino"));
            locIdro.setIdSottoBacino(rs.getInt("idsottobacino"));
            locIdro.setSottobacino(rs.getString("nomesottobacino"));
        }
        rs.close();
        st.close();
        conn.close();
        return locIdro;
    }

    public static int getIdUbicazione(int idProcesso) throws SQLException {
        int id = 0;
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select u.idubicazione from ubicazione u, processo p where u.idubicazione=p.idubicazione and p.idprocesso=" + idProcesso);
        while (rs.next()) {
            id = (rs.getInt("idUbicazione"));
        }
        rs.close();
        st.close();
        conn.close();
        return id;
    }

    public static int getIdUbicazioneStazione(int idStazione) throws SQLException {
        int id = 0;
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select u.idubicazione from ubicazione u, stazione_metereologica s where u.idubicazione=s.idubicazione and s.idstazionemetereologica=" + idStazione);
        while (rs.next()) {
            id = (rs.getInt("idUbicazione"));
        }
        rs.close();
        st.close();
        conn.close();
        return id;
    }

    /*
     * utente
     */
    public static Utente salvaUtente(Utente user, StrongPasswordEncryptor passwordEncryptor) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        String sql = "insert into utente(nome,cognome,username,password,ruolo,email,datacreazione,dataultimoaccesso,attivo) values(?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, user.getNome());
        ps.setString(2, user.getCognome());
        ps.setString(3, user.getUsername());
        String encryptedPassword = passwordEncryptor.encryptPassword(user.getPassword());
        ps.setString(4, encryptedPassword);
        ps.setString(5, user.getRuolo().toString());
        ps.setString(6, user.getEmail());
        ps.setTimestamp(7, user.getDataCreazione());
        ps.setTimestamp(8, user.getDataUltimoAccesso());
        ps.setBoolean(9, true);

        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        while (rs.next()) {
            user.setIdUtente((rs.getInt("idutente")));
        }
        rs.close();
        ps.close();
        conn.close();
        return user;
    }

    public static Utente prendiUtente(String username) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        Utente user = new Utente();

        ResultSet rs = st.executeQuery("select * from utente where username='" + username + "'");
        while (rs.next()) {

            user.setIdUtente(rs.getInt("idutente"));
            user.setCognome(rs.getString("cognome"));
            user.setDataCreazione(rs.getTimestamp("datacreazione"));
            user.setDataUltimoAccesso(rs.getTimestamp("dataultimoaccesso"));
            user.setEmail(rs.getString("email"));
            user.setNome(rs.getString("nome"));
            user.setAttivo(rs.getBoolean("attivo"));
            user.setPassword(rs.getString("password"));
            switch (rs.getString("ruolo")) {

                case "AMMINISTRATORE": {

                    user.setRuolo(Role.AMMINISTRATORE);
                    System.out.println("ruolo utente: " + rs.getString("ruolo") + "" + user.getRuolo().toString());
                    break;
                }
                case "AVANZATO": {

                    user.setRuolo(Role.AVANZATO);
                    System.out.println("ruolo utente: " + rs.getString("ruolo") + "" + user.getRuolo().toString());
                    break;
                }
                default: {

                    user.setRuolo(Role.BASE);
                    System.out.println("ruolo utente: " + rs.getString("ruolo") + "" + user.getRuolo().toString());
                }
            }

            user.setUsername(rs.getString("username"));

        }
        rs.close();
        st.close();
        conn.close();
        return user;
    }

    public static boolean login(String username, String password, StrongPasswordEncryptor passwordEncryptor) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery("select * from utente where username='" + username + "'");
        if (!(rs.next())) {
            rs.close();
            st.close();
            conn.close();
            return false;
        } else {
            if (passwordEncryptor.checkPassword(password, rs.getString("password"))) {
                st.executeUpdate("update utente set dataultimoaccesso='" + dataCorrente() + "' where idutente = " + rs.getInt("idutente") + "");
                rs.close();
                st.close();
                conn.close();
                return true;
            } else {
                rs.close();
                st.close();
                conn.close();
                return false;
            }
        }

    }

    public static ArrayList<Utente> PrendiTuttiUtenti() throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ArrayList<Utente> part = new ArrayList<Utente>();
        ResultSet rs = st.executeQuery("select * from utente");
        while (rs.next()) {
            Utente user = new Utente();
            user.setCognome(rs.getString("cognome"));
            user.setDataCreazione(rs.getTimestamp("datacreazione"));
            user.setDataUltimoAccesso(rs.getTimestamp("dataultimoaccesso"));
            user.setEmail(rs.getString("email"));
            user.setIdUtente(rs.getInt("idutente"));
            user.setNome(rs.getString("nome"));
             user.setAttivo(rs.getBoolean("attivo"));

            user.setPassword(rs.getString("password"));
            switch (rs.getString("ruolo")) {
                case "AMMINISTRATORE": {
                    user.setRuolo(Role.AMMINISTRATORE);
                    break;
                }
                case "AVANZATO": {
                    user.setRuolo(Role.AVANZATO);
                    break;
                }
                default: {
                    user.setRuolo(Role.BASE);
                }
            }
            user.setUsername(rs.getString("username"));
            part.add(user);
        }
        rs.close();
        st.close();
        conn.close();
        return part;
    }
    
    public static ArrayList<OperazioneUtente> prendiOperazioniUtente(int idutente) throws SQLException{
        ArrayList<OperazioneUtente> operazioni=new ArrayList<OperazioneUtente>();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select *,t.data as tdata, t.datainzio as tdatainzio, t.datafine as tdatafine, p.nome as processonome,s.nome as stazionenome from tracciautente as t left join"+ 
                                        " processo p on (p.idprocesso=t.idprocesso)"+
                                        " left join stazione_metereologica s on (s.idstazionemetereologica=t.idstazione)"+
                                            "where t.idutente="+idutente+"" );
        while (rs.next()) {
            OperazioneUtente o=new OperazioneUtente();
            o.setData(rs.getTimestamp("tdata"));
            o.setDataFine(rs.getTimestamp("tdatafine"));
            o.setDataInizio(rs.getTimestamp("tdatainzio"));
            o.setIdProcesso(rs.getInt("idprocesso"));
            o.setIdStazione(rs.getInt("idstazione"));
            o.setOperazione(rs.getString("operazione"));
            o.setTabella(rs.getString("tabella"));
            o.setIdUtente(rs.getInt("idutente"));
            o.setIdTraccia(rs.getInt("idtraccia"));
            o.setNomeProcesso(rs.getString("processonome"));
            o.setNomeStazione(rs.getString("stazionenome"));
            
            operazioni.add(o);
            
            

        }
        rs.close();
        st.close();
        conn.close();
        return operazioni;
    }
    
    public static int aggiornaTracciaUtente(OperazioneUtente op) throws SQLException{
        int traccia=0;
         Connection conn = DriverManager.getConnection(url, usr, pwd);
        String sql = "insert into tracciautente(idutente,data,tabella,operazione,idstazione,datainzio,datafine,idprocesso) values(?,?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, op.getIdUtente());
        ps.setTimestamp(2, op.getData());
        ps.setString(3, op.getTabella());
        ps.setString(4, op.getOperazione());
        ps.setInt(5, op.getIdStazione());
        ps.setTimestamp(6, op.getDataInizio());
        ps.setTimestamp(7, op.getDataFine());
        ps.setInt(8, op.getIdProcesso());
  
         ResultSet rs = ps.getGeneratedKeys();
        while (rs.next()) {
            traccia=(rs.getInt("idtraccia"));
        }
        rs.close();
        
         ps.executeUpdate();
        conn.close();
        return traccia;
    }
    
    public static void abilita(Utente u) throws SQLException{
         Connection conn = DriverManager.getConnection(url, usr, pwd);
         
         boolean b=u.getAttivo();
         System.out.println(" prima b"+b);
        if(b==true) b=false;
        else if(b==false) b=true;
        System.out.println("update utente set attivo="+b+" where idutente="+u.getIdUtente()+"");
        String sql = "update utente set attivo=? where idutente="+u.getIdUtente()+"";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setBoolean(1, b);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }
    
    

    public static String dataCorrente() {
        Calendar cal = new GregorianCalendar();
        int giorno = cal.get(Calendar.DAY_OF_MONTH);
        int mese = cal.get(Calendar.MONTH) + 1;
        int anno = cal.get(Calendar.YEAR);
        int ora = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        int sec = cal.get(Calendar.SECOND);
        return (anno + "-" + mese + "-" + giorno + " " + ora + ":" + min + ":" + sec);
    }

    /*
     * dati climatici
     */
    public static void salvaTemperatureAvg(int idStazione, ArrayList<Double> dati, Calendar dataInizio, Calendar dataFine) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        for (Double d : dati) {
            while (dataInizio.before(dataFine)) {
                st.executeUpdate("INSERT INTO temperatura_avg(idstazionemetereologica,temperaturaavg,data) values(" + idStazione + "," + d + ",'" + dateFormat(dataInizio) + "')");
                dataInizio.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        st.close();
        conn.close();
    }

    public static int lettoreCSVT(File f, String tabella, String attributo, int idstazione, Timestamp dataInizio) throws ParseException, IOException, SQLException {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        Connection conn = DriverManager.getConnection(url, usr, pwd);

        double t = 0;
        final int batchSize = 1000;
        int count = 0;
        Calendar data = new GregorianCalendar();
        data.setTime(dataInizio);
        try {
            br = new BufferedReader(new FileReader(f));
            try {
                String sql = "insert into " + tabella + "(idstazionemetereologica," + attributo + ",data,oraria) values(?,?,?,?)";
                PreparedStatement insert = conn.prepareStatement(sql);
                while ((line = br.readLine()) != null) {
                    String[] med = line.split(cvsSplitBy);
                    if (!med[0].equals("NaN")) {
                        t = (Double.parseDouble(med[0]));
                    } else {
                        t = -9999;
                    }
                    insert.setInt(1, idstazione);
                    insert.setDouble(2, t);
                    insert.setTimestamp(3, Timestamp.valueOf(dateFormat(data)));
                    insert.setBoolean(4, false);// DA CAMBIARE CON L UTILIZZO DI DATI ORARI!!!!!!!!!
                    insert.addBatch();

                    if (++count % batchSize == 0) {
                        insert.executeBatch();
                    }
                    data.add(Calendar.DAY_OF_MONTH, 1);
                }
                insert.executeBatch();

                insert.close();
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getNextException());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return count;
    }

    public static String dateFormat(Calendar cal) {
        String data = "" + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH) + " 00:00:00.00";
        return data;
    }

    /*
     * query climatiche
     */
    public static ArrayList<Double> prendiSommaPrecipitazioniMese(String id, String anno, boolean a) throws SQLException {//ok
        ArrayList<Double> dati = new ArrayList<Double>();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs;
        if (a == true) {
            rs = st.executeQuery("select sum(quantita),month from (select sum(quantita) as quantita,EXTRACT(month FROM data) as month from precipitazione"
                    + "				 where idstazionemetereologica=" + id + " and quantita<>-9999 and EXTRACT(year FROM data)=" + anno + " "
                    + "				 group by EXTRACT(month FROM data),EXTRACT(year FROM data) having (count(quantita)>20) "
                    + "				 order by EXTRACT(month FROM data), EXTRACT(year FROM data) ) as media group by month");
        } else {
            rs = st.executeQuery("select sum(quantita),month from (select sum(quantita) as quantita,EXTRACT(month FROM data) as month from precipitazione"
                    + "				 where idstazionemetereologica=" + id + " and quantita<>-9999 "
                    + "				 group by EXTRACT(month FROM data),EXTRACT(year FROM data) having (count(quantita)>20) "
                    + "				 order by EXTRACT(month FROM data), EXTRACT(year FROM data) ) as media group by month");
        }

        while (rs.next()) {
            double d = rs.getInt("sum");
            dati.add(d);
        }
        rs.close();
        st.close();
        conn.close();
        return dati;
    }

    public static Grafici prendiSommaPrecipitazioniAnnoMensile(String id) throws SQLException {
        Grafici g = new Grafici();
        ArrayList<Double> precipitazioni = new ArrayList<Double>();
        ArrayList<String> anni = new ArrayList<String>();

        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs;
        rs = st.executeQuery("select sum(quantita),EXTRACT(Year FROM data) as anni from precipitazione where idstazionemetereologica=" + id + "  and quantita<>-9999 group by EXTRACT(Year FROM data) order by EXTRACT(year FROM data)");

        while (rs.next()) {
            precipitazioni.add(rs.getDouble("count"));
            anni.add(String.valueOf(rs.getDouble("anni")));
        }
        rs.close();
        st.close();
        conn.close();
        g.setCategorie(anni);
        g.setY(precipitazioni);
        return g;
    }

    public static ArrayList<Double> prendiPrecipitazioniMeseMensile(String id, String anno, String mese) throws SQLException {
        ArrayList<Double> precipitazioni = new ArrayList<Double>();

        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs;
        rs = st.executeQuery("select quantita,data from precipitazione where idstazionemetereologica=" + id + " and EXTRACT(Year FROM data)=" + anno + " and EXTRACT(month FROM data)=" + mese + " order by data");
        while (rs.next()) {
            precipitazioni.add(rs.getDouble("quantita"));
        }
        rs.close();
        st.close();
        conn.close();

        return precipitazioni;
    }

    public static ArrayList<Double> prendiPrecipitazioniTrimestreMensile(String id, String anno, String mese) throws SQLException {
        ArrayList<Double> precipitazioni = new ArrayList<Double>();
        int mesefinale = Integer.parseInt(mese) + 2;
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs;
        rs = st.executeQuery("select distinct EXTRACT(MONTH FROM data),sum(quantita) from precipitazione"
                + " where quantita<>-9999 and idstazionemetereologica=" + id + " and (EXTRACT(MONTH FROM data) )::int BETWEEN " + mese + " AND " + mesefinale + " and extract(year from data)=" + anno + ""
                + "  group by(EXTRACT(MONTH FROM data)) order by  EXTRACT(MONTH FROM data)");

        while (rs.next()) {
            precipitazioni.add(rs.getDouble("sum"));
        }
        rs.close();
        st.close();
        conn.close();

        return precipitazioni;
    }

    public static ArrayList<Double> prendiTemperatureAnno(String id, String anno, boolean a, String tipo) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ArrayList<Double> tem = new ArrayList<Double>();

        ResultSet rs;

        if (a) {
            rs = st.executeQuery("SELECT avg(temperatura" + tipo + ") as " + tipo + ",EXTRACT(MONTH FROM data) FROM temperatura_" + tipo + " WHERE extract(year FROM data)=" + anno + " and idstazionemetereologica=" + id + " and temperatura" + tipo + "<>-9999 group by EXTRACT(MONTH FROM data) order by EXTRACT(MONTH FROM data)");
        } else {
            rs = st.executeQuery("SELECT avg(temperatura" + tipo + ") as " + tipo + ",EXTRACT(MONTH FROM data) FROM temperatura_" + tipo + " WHERE  idstazionemetereologica=" + id + " and temperatura" + tipo + "<>-9999 group by EXTRACT(MONTH FROM data) order by EXTRACT(MONTH FROM data)");
        }
        while (rs.next()) {
            tem.add(rs.getDouble("" + tipo + ""));

        }

        rs.close();
        st.close();
        return tem;

    }

    public static ArrayList<Double> temperatureAnno(String id, String anno, boolean a, String tipo) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ArrayList<Double> tem = new ArrayList<Double>();

        ResultSet rs;

        rs = st.executeQuery("SELECT data,temperatura" + tipo + " as " + tipo + " FROM temperatura_" + tipo + " WHERE extract(year FROM data)=" + anno + " and  idstazionemetereologica=" + id + " and temperatura" + tipo + "<>-9999 order by data");
        while (rs.next()) {
            tem.add(rs.getDouble("" + tipo + ""));

        }

        rs.close();
        st.close();
        return tem;

    }

    public static ArrayList<Double> prendiMM(String id, String anno, boolean a, String tipo) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ArrayList<Double> tem = new ArrayList<Double>();

        ResultSet rs;

        if (a) {
            rs = st.executeQuery("SELECT " + tipo + "(temperatura" + tipo + ") as " + tipo + ",EXTRACT(MONTH FROM data) FROM temperatura_" + tipo + " WHERE extract(year FROM data)=" + anno + " and idstazionemetereologica=" + id + " and temperatura" + tipo + "<>-9999 group by EXTRACT(MONTH FROM data) order by  EXTRACT(MONTH FROM data) ");

        } else {
            rs = st.executeQuery("SELECT " + tipo + "(temperatura" + tipo + ") as " + tipo + ",EXTRACT(MONTH FROM data) FROM temperatura_" + tipo + " WHERE  idstazionemetereologica=" + id + " and temperatura" + tipo + "<>-9999 group by EXTRACT(MONTH FROM data) order by  EXTRACT(MONTH FROM data) ");
        }

        while (rs.next()) {
            tem.add(rs.getDouble("" + tipo + ""));

        }

        rs.close();
        st.close();
        return tem;
    }

    public static ArrayList<Double> prendiPrecipitazioniTrimestreGiornaliero(String id, String anno, String mese) throws SQLException {
        ArrayList<Double> precipitazioni = new ArrayList<Double>();
        int mesefinale = Integer.parseInt(mese) + 2;
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs;
        rs = st.executeQuery("select quantita,data from precipitazione where idstazionemetereologica=" + id + " and (EXTRACT(MONTH FROM data))::int BETWEEN " + mese + " AND " + mesefinale + " and extract(year from data)=" + anno + "");

        while (rs.next()) {
            if (rs.getDouble("quantita") == -9999) {
                precipitazioni.add(0.0);
            } else {
                precipitazioni.add(rs.getDouble("quantita"));
            }
        }
        rs.close();
        st.close();
        conn.close();

        return precipitazioni;
    }

    public static ArrayList<Dati> prendiSommaPrecipitazioniMeseGiornaliero(String id, String anno) throws SQLException {//ok
        ArrayList<Dati> dati = new ArrayList<Dati>();
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs;
        rs = st.executeQuery("select quantita,data,(EXTRACT(month FROM data)) as mese from precipitazione where idstazionemetereologica=" + id + " and  extract(year from data)=" + anno + " order by data");
        int mese = 0;
        int m = 0;
        Dati d = null;
        while (rs.next()) {
            mese = Integer.parseInt(rs.getString("mese"));
            if (dati.isEmpty()) {
                d = new Dati();
                d.getDati().add(rs.getDouble("quantita"));
                m = mese;
                //dati.set(mese, d);
                dati.add(d);
            } else {
                if (mese != m) {
                    d = new Dati();
                    m = mese;
                    d.getDati().add(rs.getDouble("quantita"));
                    dati.add(d);
                } else {
                    d.getDati().add(rs.getDouble("quantita"));
                }
            }
        }
        rs.close();
        st.close();
        conn.close();
        return dati;
    }

    public static ArrayList<Double> temperatureTrimestre(String id, String anno, String mese, boolean a, String tipo) throws SQLException {
        ArrayList<Double> temperature = new ArrayList<Double>();
        int mesefinale = Integer.parseInt(mese) + 2;
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs;
        rs = st.executeQuery("select temperatura" + tipo + ",data from temperatura_" + tipo + " where  idstazionemetereologica=" + id + " and (EXTRACT(MONTH FROM data))::int BETWEEN " + mese + " AND " + mesefinale + " and extract(year from data)=" + anno + " order by data");
        while (rs.next()) {
            temperature.add(rs.getDouble("temperatura" + tipo + ""));
        }
        rs.close();
        st.close();
        conn.close();

        return temperature;
    }

    public static ArrayList<Processo> prendiTuttiProcessiStagioni(String stagione) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs;
        ArrayList<Processo> p = new ArrayList<Processo>();
        if (stagione.equals("inverno")) {
            rs = st.executeQuery("select * from processo left join ubicazione u on (processo.idubicazione=u.idubicazione) left join comune c on (c.idcomune=u.idcomune) where (EXTRACT(MONTH FROM processo.data))=12 or (EXTRACT(MONTH FROM processo.data))=1 or (EXTRACT(MONTH FROM processo.data))=2 ");
        } else if (stagione.equals("primavera")) {
            rs = st.executeQuery("select * from processo left join ubicazione u on (processo.idubicazione=u.idubicazione) left join comune c on (c.idcomune=u.idcomune) where (EXTRACT(MONTH FROM processo.data))=3 or (EXTRACT(MONTH FROM processo.data))=4 or (EXTRACT(MONTH FROM processo.data))=5 ");
        } else if (stagione.equals("estate")) {
            rs = st.executeQuery("select * from processo left join ubicazione u on (processo.idubicazione=u.idubicazione) left join comune c on (c.idcomune=u.idcomune) where (EXTRACT(MONTH FROM processo.data))=6 or (EXTRACT(MONTH FROM processo.data))=7 or (EXTRACT(MONTH FROM processo.data))=8 ");
        } else {
            rs = st.executeQuery("select * from processo left join ubicazione u on (processo.idubicazione=u.idubicazione) left join comune c on (c.idcomune=u.idcomune) where (EXTRACT(MONTH FROM processo.data))=9 or (EXTRACT(MONTH FROM processo.data))=10 or (EXTRACT(MONTH FROM processo.data))=11 ");
        }
        while (rs.next()) {
            String f = String.valueOf(rs.getInt("formatodata"));
            if (f.length() > 1) {

                if (HTMLProcesso.campoData(f, 1) == true) {
                    Processo pro = new ProcessoCompleto();
                    Ubicazione u = new Ubicazione();
                    pro.setIdProcesso(rs.getInt("idProcesso"));
                    pro.setNome(rs.getString("nome"));
                    pro.setData(rs.getTimestamp("data"));
                    AttributiProcesso ap = new AttributiProcesso();
                    ap.setAltezza(rs.getDouble("altezza"));
                    ap.setLarghezza(rs.getDouble("larghezza"));
                    ap.setSuperficie(rs.getDouble("superficie"));
                    pro.setFormatoData(rs.getInt("formatodata"));
                    ap.setVolume_specifico(rs.getDouble("volumespecifico"));
                    LocazioneAmministrativa locAmm = new LocazioneAmministrativa();
                    locAmm.setIdComune(rs.getInt("idcomune"));
                    locAmm.setComune(rs.getString("nomecomune"));

                    u.setLocAmm(locAmm);
                    pro.setUbicazione(u);
                    p.add(pro);
                }
            }
        }
        rs.close();
        st.close();
        conn.close();
        return p;
    }

    public static ArrayList<Processo> prendiTuttiProcessiMese(String mese) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        Statement st = conn.createStatement();
        ResultSet rs;
        ArrayList<Processo> p = new ArrayList<Processo>();
        System.out.println("select * from processo left join ubicazione u on (processo.idubicazione=u.idubicazione) left join comune c on (c.idcomune=u.idcomune) where (EXTRACT(MONTH FROM processo.data))=" + mese + "");
        rs = st.executeQuery("select * from processo left join ubicazione u on (processo.idubicazione=u.idubicazione) left join comune c on (c.idcomune=u.idcomune) where (EXTRACT(MONTH FROM processo.data))=" + mese + "  ");

        while (rs.next()) {
            String f = String.valueOf(rs.getInt("formatodata"));
            if (f.length() > 1) {

                if (HTMLProcesso.campoData(f, 1) == true) {
                    Processo pro = new ProcessoCompleto();
                    Ubicazione u = new Ubicazione();
                    pro.setIdProcesso(rs.getInt("idProcesso"));
                    pro.setNome(rs.getString("nome"));
                    pro.setData(rs.getTimestamp("data"));
                    AttributiProcesso ap = new AttributiProcesso();
                    ap.setAltezza(rs.getDouble("altezza"));
                    ap.setLarghezza(rs.getDouble("larghezza"));
                    ap.setSuperficie(rs.getDouble("superficie"));
                    pro.setFormatoData(rs.getInt("formatodata"));
                    ap.setVolume_specifico(rs.getDouble("volumespecifico"));
                    LocazioneAmministrativa locAmm = new LocazioneAmministrativa();
                    locAmm.setIdComune(rs.getInt("idcomune"));
                    locAmm.setComune(rs.getString("nomecomune"));

                    u.setLocAmm(locAmm);
                    pro.setUbicazione(u);
                    p.add(pro);
                }
            }
        }
        rs.close();
        st.close();
        conn.close();
        return p;
    }

// allegati
    public static int salvaAllegato(int idUtente, String autore, String anno, String titolo, String in, String fonte, String urlWeb, String note, String tipo, String absoluteFile) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        int idAllegato = 0;
        String query = "insert into allegati(autore,anno,titolo,nella,fonte,urlweb,note,tipoallegato,linkfile,idutente) values(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, autore);
        ps.setString(2, anno);
        ps.setString(3, titolo);
        ps.setString(4, in);
        ps.setString(5, fonte);
        ps.setString(6, urlWeb);
        ps.setString(7, note);
        ps.setString(8, tipo);
        ps.setString(9, absoluteFile);
        ps.setInt(10, idUtente);
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();

        while (rs.next()) {
            idAllegato = rs.getInt("idallegati");
        }

        rs.close();
        ps.close();
        conn.close();

        return idAllegato;
    }

    public static void salvaAllegatoProcesso(int idProcesso, int idUtente, String autore, String anno, String titolo, String in, String fonte, String urlWeb, String note, String tipo, String absolutePath) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        int idAllegato = salvaAllegato(idUtente, tipo, tipo, tipo, tipo, tipo, tipo, tipo, tipo, absolutePath);
        String query = "insert into allegati_processo(idprocesso,idallegati) values(?,?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, idProcesso);
        ps.setInt(2, idAllegato);
        ps.executeUpdate();
        conn.close();
        ps.close();
    }

    public static void salvaAllegatoStazione(int idstazione, int idUtente, String autore, String anno, String titolo, String in, String fonte, String urlWeb, String note, String tipo, String absolutePath) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        int idAllegato = salvaAllegato(idUtente, autore, anno, titolo, in, fonte, urlWeb, note, tipo, absolutePath);
        String query = "insert into allegati_stazione(idstazione,idallegati) values(?,?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, idstazione);
        ps.setInt(2, idAllegato);
        ps.executeUpdate();
        conn.close();
        ps.close();
    }

    public static ArrayList<Allegato> cercaAllegatoStazione(int idStazione) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        ArrayList<Allegato> a = new ArrayList<Allegato>();
        String query = "select * from allegati where idallegati in(select idallegati from allegati_stazione where idstazione=?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, idStazione);
        Allegato allegato = new Allegato();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            allegato.setAnno(rs.getString("anno"));
            allegato.setAutore(rs.getString("autore"));
            allegato.setData(rs.getTimestamp("data"));
            allegato.setFonte(rs.getString("fonte"));
            allegato.setId(rs.getInt("idallegati"));
            allegato.setIdUtente(rs.getInt("idUtente"));
            allegato.setLinkFile(rs.getString("linkfile"));
            allegato.setNella(rs.getString("nella"));
            allegato.setNote(rs.getString("note"));
            allegato.setTipoAllegato(rs.getString("tipoallegato"));
            allegato.setTitolo(rs.getString("titolo"));
            allegato.setUrlWeb(rs.getString("urlweb"));
            a.add(allegato);
        }
        rs.close();

        conn.close();
        return a;
    }

    public static ArrayList<Allegato> cercaAllegatoProcesso(int idProcesso) throws SQLException {
        Connection conn = DriverManager.getConnection(url, usr, pwd);
        ArrayList<Allegato> a = new ArrayList<Allegato>();
        String query = "select * from allegati where idallegati in(select idallegati from allegati_processo where idprocesso=?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, idProcesso);

        Allegato allegato = new Allegato();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            allegato.setAnno(rs.getString("anno"));
            allegato.setAutore(rs.getString("autore"));
            allegato.setData(rs.getTimestamp("data"));
            allegato.setFonte(rs.getString("fonte"));
            allegato.setId(rs.getInt("idallegati"));
            allegato.setIdUtente(rs.getInt("idUtente"));
            allegato.setLinkFile(rs.getString("linkfile"));
            allegato.setNella(rs.getString("nella"));
            allegato.setNote(rs.getString("note"));
            allegato.setTipoAllegato(rs.getString("tipoallegato"));
            allegato.setTitolo(rs.getString("titolo"));
            allegato.setUrlWeb(rs.getString("urlweb"));
            a.add(allegato);
        }
        rs.close();
        conn.close();
        return a;
    }
}
