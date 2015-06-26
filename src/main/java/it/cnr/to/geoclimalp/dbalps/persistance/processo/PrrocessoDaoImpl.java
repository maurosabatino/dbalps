package it.cnr.to.geoclimalp.dbalps.persistance.processo;

import it.cnr.to.geoclimalp.dbalps.persistance.GenericDao;
import it.cnr.to.geoclimalp.dbalps.entity.processo.AttributiProcesso;
import it.cnr.to.geoclimalp.dbalps.entity.processo.Processo;
import it.cnr.to.geoclimalp.dbalps.entity.processo.Segnalazione;
import it.cnr.to.geoclimalp.dbalps.entity.processo.attributiProcesso.*;
import it.cnr.to.geoclimalp.dbalps.entity.ubicazione.Coordinate;
import it.cnr.to.geoclimalp.dbalps.entity.ubicazione.LocazioneAmministrativa;
import it.cnr.to.geoclimalp.dbalps.entity.ubicazione.LocazioneIdrologica;
import it.cnr.to.geoclimalp.dbalps.entity.ubicazione.Ubicazione;

import java.io.Serializable;
import java.sql.*;
import java.util.List;

/**
 * Created by Mauro on 25/06/2015.
 */
public class PrrocessoDaoImpl implements GenericDao {

    @Override
    public Object find(Serializable id) throws SQLException {
        return prendiProcesso((Integer) id);
    }

    @Override
    public List find() {
        return null;
    }

    @Override
    public Serializable save(Object value) {
        return null;
    }

    @Override
    public void update(Object value) {

    }

    @Override
    public void delete(Object value) {

    }
    private Processo prendiProcesso(int idProcesso) throws SQLException {
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
            System.out.println("nome processo" + p.getNome());
            p.setData(rs.getTimestamp("data"));
            AttributiProcesso ap = new AttributiProcesso();
            ap.setDescrizione(rs.getString("descrizione"));
            System.out.println("DEASCRIZIONE " + ap.getDescrizione());
            ap.setNote(rs.getString("note"));
            System.out.println("grado danno: " + rs.getString("gradodanno"));
            ap.setGradoDanno(rs.getString("gradodanno"));
            ap.setAltezza(rs.getDouble("altezza"));
            ap.setLarghezza(rs.getDouble("larghezza"));
            ap.setSuperficie(rs.getDouble("superficie"));
            p.setFormatoData(rs.getInt("formatodata"));
            ap.setVolume_specifico(rs.getDouble("volumespecifico"));

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
            System.out.println("quota: " + rs.getDouble("q") + " idubicazione=" + rs.getInt("idubicazione"));
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

            System.out.println("sito da db: " + rs.getString("caratteristica_it"));
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
}
