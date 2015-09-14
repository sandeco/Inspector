package com.inspector.persistencia.sqlite;

import android.content.ContentValues;

import com.inspector.model.M;
import com.inspector.model.Palestra;
import com.inspector.persistencia.dao.PalestraDAO;

import java.util.List;

public class PalestraSqliteDAO extends GenericSqliteDAO<Palestra, Integer> implements PalestraDAO {
    @Override
    public List<Palestra> listAll() {
        return null;
    }

    @Override
    public Palestra findById(int id) {
        return null;
    }

    @Override
    public Palestra create(Palestra entity) {
        ContentValues values = new ContentValues();

        values.put(M.Palestra.ID, entity.getId());
        values.put(M.Palestra.NOME, entity.getNome());
        values.put(M.Palestra.EVENTO_ID, entity.getEvento().getId());
        values.put(M.Palestra.DATA_ALTERACAO, entity.getDataAlteracao().toString());

        long retorno = getDbWriteble().insert(M.Palestra.ENTITY_NAME, null, values);

        if (retorno != -1)
            return entity;
        else
            return null;
    }

    @Override
    public Palestra update(Palestra entity) {
        return null;
    }

    @Override
    public void delete(Palestra entity) {

    }
}
