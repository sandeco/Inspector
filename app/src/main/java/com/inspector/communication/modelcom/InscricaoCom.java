package com.inspector.communication.modelcom;

import com.inspector.model.Inscricao;
import com.inspector.model.Palestra;
import com.inspector.model.Participante;

public class InscricaoCom extends Inscricao {

    public int getIdPalestra() {
        return idPalestra;
    }

    public void setIdPalestra(int idPalestra) {
        Palestra p = new Palestra();
        p.setId(idPalestra);

        this.setPalestra(p);
        this.idPalestra = idPalestra;
    }

    public int getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(int idParticipante) {
        Participante p = new Participante();
        p.setId(idParticipante);

        this.setParticipante(p);
        this.idParticipante = idParticipante;
    }

    private int idPalestra;
    private int idParticipante;
}
