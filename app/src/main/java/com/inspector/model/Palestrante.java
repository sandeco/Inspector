package com.inspector.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by sanderson on 20/08/2015.
 */


public class Palestrante implements Serializable {

    private int id;

    private Timestamp dataAlteracao;

    private String nome;

    //bi-directional many-to-one association to Atividade
    private Atividade atividade;


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

    public Atividade getAtividade() {
        return this.atividade;
    }

    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;
    }



}
