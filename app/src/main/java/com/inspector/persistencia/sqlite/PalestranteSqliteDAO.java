package com.inspector.persistencia.sqlite;

import com.inspector.model.Palestrante;
import com.inspector.persistencia.dao.PalestranteDAO;

import java.util.List;

public class PalestranteSqliteDAO extends GenericSqliteDAO<Palestrante, Integer> implements PalestranteDAO {
    @Override
    public List<Palestrante> listAll() {
        return null;
    }

    @Override
    public Palestrante findById(int id) {
        return null;
    }

    @Override
    public Palestrante create(Palestrante entity) {
        return null;
    }

    @Override
    public Palestrante update(Palestrante entity) {
        return null;
    }

    @Override
    public void delete(Palestrante entity) {

    }
}
