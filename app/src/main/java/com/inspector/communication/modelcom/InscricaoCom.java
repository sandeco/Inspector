package com.inspector.communication.modelcom;

import com.inspector.model.Atividade;
import com.inspector.model.Inscricao;
import com.inspector.model.Participante;

public class InscricaoCom extends Inscricao {

    public void setIdPalestra(int idPalestra) {

        Atividade atividade = super.getAtividade();

        if (atividade == null) {
            atividade = new Atividade();
            atividade.setId(idPalestra);
            super.setAtividade(atividade);
        }
    }

    public void setIdParticipante(int idParticipante) {
        Participante participante = super.getParticipante();

        if (participante == null) {
            participante = new Participante();
            participante.setId(idParticipante);
            super.setParticipante(participante);
        }
    }
}
