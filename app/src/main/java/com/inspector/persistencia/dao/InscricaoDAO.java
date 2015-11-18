package com.inspector.persistencia.dao;

import com.inspector.model.Inscricao;
import com.inspector.model.Atividade;
import com.inspector.model.Participante;

import java.util.List;

public interface InscricaoDAO extends GenericDAO<Inscricao, Integer> {
    Inscricao findByPalestraAndParticipante(Atividade atividade, Participante participante);

    List<Inscricao> listByPalestra(Atividade atividade);

    List<Inscricao> listByParticipante(Participante participante);
}
