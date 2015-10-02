package com.inspector.communication.modelcom;

import com.inspector.model.Ministracao;
import com.inspector.model.Participacao;
import com.inspector.model.Participante;

public class ParticipacaoCom extends Participacao {

    private int idMinistracao;
    private int idParticipante;

    public int getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(int idParticipante) {
        this.idParticipante = idParticipante;
        Participante p = new Participante();
        p.setId(idParticipante);
        super.setParticipante(p);
    }

    public int getIdMinistracao() {
        return idMinistracao;
    }

    public void setIdMinistracao(int idMinistracao) {
        this.idMinistracao = idMinistracao;
        Ministracao m = new Ministracao();
        m.setId(idMinistracao);
        super.setMinistracao(m);
    }

    @Override
    public void setMinistracao(Ministracao ministracao) {
        super.setMinistracao(ministracao);
        this.setIdMinistracao(ministracao.getId());
    }

    @Override
    public void setParticipante(Participante participante) {
        super.setParticipante(participante);
        this.setIdParticipante(participante.getId());
    }
}
