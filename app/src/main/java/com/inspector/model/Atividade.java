package com.inspector.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


public class Atividade implements Serializable {

	private int id;

	private String nome;

    private Evento evento;

    private Timestamp dataAlteracao;

    //bi-directional many-to-one association to Inscricao
    private List<Inscricao> inscricoes;

    //bi-directional many-to-one association to Ministracao
    private List<Ministracao> ministracoes;

    //bi-directional many-to-one association to Palestrante
    private List<Palestrante> palestrantes;


    public Atividade() { super();}

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

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Timestamp getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Timestamp dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public List<Inscricao> getInscricoes() {
        return this.inscricoes;
    }

    public void setInscricoes(List<Inscricao> inscricoes) {
        this.inscricoes = inscricoes;
    }

    public Inscricao addInscricao(Inscricao inscricao) {
        getInscricoes().add(inscricao);
        inscricao.setAtividade(this);

        return inscricao;
    }

    public Inscricao removeInscricao(Inscricao inscricao) {
        getInscricoes().remove(inscricao);
        inscricao.setAtividade(null);

        return inscricao;
    }

    public List<Ministracao> getMinistracoes() {
        return this.ministracoes;
    }

    public void setMinistracoes(List<Ministracao> ministracoes) {
        this.ministracoes = ministracoes;
    }

    public Ministracao addMinistracao(Ministracao ministracao) {
        getMinistracoes().add(ministracao);
        ministracao.setAtividade(this);

        return ministracao;
    }

    public Ministracao removeMinistracao(Ministracao ministracao) {
        getMinistracoes().remove(ministracao);
        ministracao.setAtividade(null);

        return ministracao;
    }


    public List<Palestrante> getPalestrantes() {
        return this.palestrantes;
    }

    public void setPalestrantes(List<Palestrante> palestrantes) {
        this.palestrantes = palestrantes;
    }

    public Palestrante addPalestrante(Palestrante palestrante) {
        getPalestrantes().add(palestrante);
        palestrante.setAtividade(this);

        return palestrante;
    }

    public Palestrante removePalestrante(Palestrante palestrante) {
        getPalestrantes().remove(palestrante);
        palestrante.setAtividade(null);

        return palestrante;
    }


}
