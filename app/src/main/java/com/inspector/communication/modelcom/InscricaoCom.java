package com.inspector.communication.modelcom;

import com.inspector.model.Inscricao;
import com.inspector.model.Palestra;
import com.inspector.model.Participante;

public class InscricaoCom extends Inscricao {

    public void setIdPalestra(int idPalestra) {

        Palestra palestra = super.getPalestra();

        if (palestra == null) {
            palestra = new Palestra();
            palestra.setId(idPalestra);
            super.setPalestra(palestra);
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
