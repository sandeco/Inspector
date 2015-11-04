package com.inspector.communication.modelcom;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inspector.model.Evento;
import com.inspector.model.Inscricao;
import com.inspector.model.Ministracao;
import com.inspector.model.Palestra;

import java.util.ArrayList;
import java.util.List;

public class PalestraCom extends Palestra {

    public void setIdEvento(int idEvento) {
        if (super.getEvento() == null) {
            Evento evento = new Evento();
            evento.setId(idEvento);
            super.setEvento(evento);
        }
    }

    @JsonProperty("inscricoes")
    public void setInscricoesCom(List<InscricaoCom> inscricoesCom) {

        //pegando a lista passada pelo mapper e copiando
        //para a lista de inscricoes
        //assim fazendo a conversao do tipo Com para o normal

        List<Inscricao> inscricoes = new ArrayList<>();

        for (InscricaoCom inscricaoCom : inscricoesCom) {
            inscricoes.add(inscricaoCom);
        }

        super.setInscricoes(inscricoes);
    }

    @JsonProperty("ministracoes")
    public void setMinistracoesCom(List<MinistracaoCom> ministracoesCom) {

        List<Ministracao> ministracoes = new ArrayList<>();

        for (MinistracaoCom ministracaoCom : ministracoesCom) {
            ministracoes.add(ministracaoCom);
        }

        super.setMinistracoes(ministracoes);
    }
}
