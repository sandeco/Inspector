package com.inspector.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by sanderson on 20/08/2015.
 */


public class Evento implements Serializable{

    private int id;
    private String nome;
    private Timestamp dataAlteracao;
    private Timestamp dataFim;
    private Timestamp dataInicio;

    private List<Palestra> palestras;

    public Evento() {}

    public Evento(int id, String nome, Timestamp dataInicio, Timestamp dataFim, Timestamp dataAlteracao ) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.dataAlteracao = dataAlteracao;
        this.nome = nome;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Timestamp getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Timestamp dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Timestamp getDataFim() {
        return dataFim;
    }

    public void setDataFim(Timestamp dataFim) {
        this.dataFim = dataFim;
    }

    public Timestamp getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Timestamp dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setPalestras(List<Palestra> palestras) {
        this.palestras = palestras;
    }

    public List<Palestra> getPalestras() {
        return this.palestras;
    }

    public Palestra addPalestra(Palestra palestra) {
        getPalestras().add(palestra);
        palestra.setEvento(this);
        return palestra;
    }

    public Palestra removePalestra(Palestra palestra) {
        getPalestras().remove(palestra);
        palestra.setEvento(null);
        return palestra;
    }


}
