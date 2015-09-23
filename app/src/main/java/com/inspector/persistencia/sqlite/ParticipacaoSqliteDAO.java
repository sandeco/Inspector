package com.inspector.persistencia.sqlite;

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
        return null;
    }

    @Override
    public Participacao update(Participacao entity) {
        return null;
    }

    @Override
    public void delete(Participacao entity) {

    }
}
