package com.inspector.persistencia.sqlite;

import android.content.ContentValues;

import com.inspector.model.Inscricao;
import com.inspector.model.M;
import com.inspector.persistencia.dao.InscricaoDAO;

import java.util.List;

public class InscricaoSqliteDAO extends GenericSqliteDAO<Inscricao, Integer> implements InscricaoDAO {
    @Override
    public List<Inscricao> listAll() {
        return null;
    }

    @Override
    public Inscricao findById(int id) {
        return null;
    }

    @Override
    public Inscricao create(Inscricao entity) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(M.Inscricao.ID, entity.getId());
        contentValues.put(M.Inscricao.DATA_ALTERACAO, entity.getDataAlteracao().toString());
        contentValues.put(M.Inscricao.PALESTRA_ID, entity.getPalestra().getId());
        contentValues.put(M.Inscricao.PARTICIPANTE_ID, entity.getParticipante().getId());

        long retorno = getDbWriteble().insert(M.Inscricao.ENTITY_NAME, null, contentValues);

        return (retorno != -1) ? entity : null;
    }

    @Override
    public Inscricao update(Inscricao entity) {
        return null;
    }

    @Override
    public void delete(Inscricao entity) {

    }
}
