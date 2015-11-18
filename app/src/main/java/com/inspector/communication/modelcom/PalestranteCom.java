package com.inspector.communication.modelcom;

import com.inspector.model.Atividade;
import com.inspector.model.Palestrante;

public class PalestranteCom extends Palestrante {

    private int idPalestra;

    public int getIdPalestra() {
        return idPalestra;
    }

    public void setIdPalestra(int idPalestra) {
        this.idPalestra = idPalestra;
        Atividade p = new Atividade();
        p.setId(idPalestra);
        this.setAtividade(p);
    }
}
