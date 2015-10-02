package com.inspector.persistencia.dao;

import com.inspector.communication.modelcom.ParticipacaoCom;
import com.inspector.model.Participacao;

import java.util.List;

public interface ParticipacaoDAO extends GenericDAO<Participacao, Integer> {
    List<ParticipacaoCom> listToExport();
}
