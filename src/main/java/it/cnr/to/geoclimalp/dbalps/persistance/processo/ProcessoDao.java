package it.cnr.to.geoclimalp.dbalps.persistance.processo;

import it.cnr.to.geoclimalp.dbalps.entity.Utente.Utente;
import it.cnr.to.geoclimalp.dbalps.entity.processo.Processo;
import it.cnr.to.geoclimalp.dbalps.entity.ubicazione.Ubicazione;

import java.util.ArrayList;

/**
 * Created by Mauro on 25/06/2015.
 */
public interface ProcessoDao {
    public Processo insertProcesso(Processo p, Utente user);
    public Processo getProcesso(int id);
    public ArrayList<Processo> getAllProcessi();
    public ArrayList<Processo> findProcessi(Processo p, Ubicazione u);
    public Processo upddateProcesso(Processo p);
    public void deleteProcesso(Processo p);
}
