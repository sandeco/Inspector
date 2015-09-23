package com.inspector.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by sanderson on 20/08/2015.
 */

public class Inscricao implements Serializable {

    private int id;
    private Timestamp dataAlteracao;

    //bi-directional many-to-one association to Palestra
    private Palestra palestra;

    //bi-directional many-to-one association to Participante
    private Participante participante;


    public Inscricao() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDataAlteracao() {
        return this.dataAlteracao;
    }

    public void setDataAlteracao(Timestamp dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Palestra getPalestra() {
        return this.palestra;
    }

    public void setPalestra(Palestra palestra) {
        this.palestra = palestra;
    }

    public Participante getParticipante() {
        return this.participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }



}
