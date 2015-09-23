package com.inspector.com.inspector.modelCom;


import com.inspector.model.Palestra;

import java.io.Serializable;

/**
 * Created by sanderson on 09/09/2015.
 */
public class PalestraCom extends Palestra implements Serializable {

    private int idEvento;

    public PalestraCom() {

    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }



}
