package com.inspector.persistencia.sqlite;

import android.content.ContentValues;

import com.inspector.model.M;
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

        ContentValues values = new ContentValues();

        values.put(M.Palestrante.ID, entity.getId());
        values.put(M.Palestrante.DATA_ALTERACAO, entity.getDataAlteracao().toString());
        values.put(M.Palestrante.NOME, entity.getNome());
        values.put(M.Palestrante.PALESTRA_ID, entity.getAtividade().getId());

        long retorno = getDbWriteble().insert(M.Palestrante.ENTITY_NAME, null, values);

        return (retorno != -1) ? entity : null;
    }

    @Override
    public Palestrante update(Palestrante entity) {
        return null;
    }

    @Override
    public void delete(Palestrante entity) {

    }
}
