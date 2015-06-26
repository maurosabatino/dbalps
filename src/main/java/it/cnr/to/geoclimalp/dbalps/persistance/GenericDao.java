package it.cnr.to.geoclimalp.dbalps.persistance;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Mauro on 26/06/2015.
 */
public interface GenericDao<K extends Serializable, T> {
        public T find(K id) throws SQLException;
        public List<T> find() throws SQLException;
        public K save(T value) throws SQLException;
        public void update(T value) throws SQLException;
        public void delete(T value) throws SQLException;
}

