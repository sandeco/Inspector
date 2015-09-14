package com.inspector.modelcom;

import com.inspector.model.Evento;
import com.inspector.model.Palestra;

public class PalestraCom extends Palestra {

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;

        Evento evento = new Evento();
        evento.setId(idEvento);
        super.setEvento(evento);
    }

    private int idEvento;
}
