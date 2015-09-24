package com.inspector.persistencia.sqlite;

import android.content.ContentValues;

import com.inspector.model.M;
import com.inspector.model.Participacao;
import com.inspector.persistencia.dao.ParticipacaoDAO;

import java.util.List;

public class ParticipacaoSqliteDAO extends GenericSqliteDAO<Participacao, Integer> implements ParticipacaoDAO {
    @Override
    public List<Participacao> listAll() {
        return null;
    }

    @Override
    public Participacao findById(int id) {
        return null;
    }

    @Override
    public Participacao create(Participacao entity) {
        ContentValues values = new ContentValues();

        values.put(M.Participacao.ID, entity.getId());
        values.put(M.Participacao.PARTICIPANTE_ID, entity.getParticipante().getId());
        values.put(M.Participacao.MINISTRACAO_ID, entity.getMinistracao().getId());
        values.put(M.Participacao.DATA_ALTERACAO, entity.getDataAlteracao().toString());

        long retorno = getDbWriteble().insert(M.Participacao.ENTITY_NAME, null, values);

        return (retorno != -1) ? entity : null;
    }

    @Override
    public Participacao update(Participacao entity) {
        return null;
    }

    @Override
    public void delete(Participacao entity) {

    }
}
