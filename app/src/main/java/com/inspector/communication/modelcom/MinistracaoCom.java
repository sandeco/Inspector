package com.inspector.communication.modelcom;

import com.inspector.model.Ministracao;
import com.inspector.model.Palestra;

public class MinistracaoCom extends Ministracao {

    private int idPalestra;

    public int getIdPalestra() {
        return idPalestra;
    }

    public void setIdPalestra(int idPalestra) {
        this.idPalestra = idPalestra;
        Palestra p = new Palestra();
        p.setId(idPalestra);
        this.setPalestra(p);
    }
}
