package com.inspector.persistencia.dao;

import com.inspector.model.Inscricao;
import com.inspector.model.Palestra;
import com.inspector.model.Participante;

public interface InscricaoDAO extends GenericDAO<Inscricao, Integer> {
    Inscricao findByPalestraAndParticipante(Palestra palestra, Participante participante);
}
