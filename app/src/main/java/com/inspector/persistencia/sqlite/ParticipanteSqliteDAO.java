package com.inspector.persistencia.sqlite;

import com.inspector.model.Participante;
import com.inspector.persistencia.dao.ParticipanteDAO;

import java.util.List;

public class ParticipanteSqliteDAO extends GenericSqliteDAO<Participante, Integer> implements ParticipanteDAO {
    @Override
    public List<Participante> listAll() {
        return null;
    }

    @Override
    public Participante findById(int id) {
        return null;
    }

    @Override
    public Participante create(Participante entity) {
        //TODO
        return null;
    }

    @Override
    public Participante update(Participante entity) {
        return null;
    }

    @Override
    public void delete(Participante entity) {

    }
}
