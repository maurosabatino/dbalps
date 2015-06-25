package it.cnr.to.geoclimalp.dbalps.dao.processo;

import it.cnr.to.geoclimalp.dbalps.entity.Utente.Utente;
import it.cnr.to.geoclimalp.dbalps.entity.processo.Processo;
import it.cnr.to.geoclimalp.dbalps.entity.ubicazione.Ubicazione;

import java.util.ArrayList;

/**
 * Created by Mauro on 25/06/2015.
 */
public class PrrocessoDaoImpl implements ProcessoDao {
    @Override
    public Processo insertProcesso(Processo p, Utente user) {
        return null;
    }

    @Override
    public Processo getProcesso(int id) {
        return null;
    }

    @Override
    public ArrayList<Processo> getAllProcessi() {
        return null;
    }

    @Override
    public ArrayList<Processo> findProcessi(Processo p, Ubicazione u) {
        return null;
    }

    @Override
    public Processo upddateProcesso(Processo p) {
        return null;
    }

    @Override
    public void deleteProcesso(Processo p) {

    }
}
