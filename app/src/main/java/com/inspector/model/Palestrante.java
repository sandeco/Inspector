package com.inspector.model;

import java.sql.Timestamp;

/**
 * Created by sanderson on 20/08/2015.
 */


public class Palestrante {

    private int id;

    private Timestamp dataAlteracao;

    private String nome;

    //bi-directional many-to-one association to Palestra
    private Palestra palestra;


    public Palestrante() {
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

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Palestra getPalestra() {
        return this.palestra;
    }

    public void setPalestra(Palestra palestra) {
        this.palestra = palestra;
    }



}
