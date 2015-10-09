package com.inspector.persistencia.dao;

import com.inspector.model.Inscricao;
import com.inspector.model.Palestra;
import com.inspector.model.Participante;

import java.util.List;

public interface InscricaoDAO extends GenericDAO<Inscricao, Integer> {
    Inscricao findByPalestraAndParticipante(Palestra palestra, Participante participante);

    List<Inscricao> listByPalestra(Palestra palestra);
}
