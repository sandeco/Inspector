package com.inspector.communication.modelcom;

import com.inspector.model.Palestra;
import com.inspector.model.Palestrante;

public class PalestranteCom extends Palestrante {

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
