package com.inspector.communication.modelcom;

import com.inspector.model.Atividade;
import com.inspector.model.Ministracao;

public class MinistracaoCom extends Ministracao {

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
