package com.inspector.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


public class Ministracao implements Serializable {

	private int id;
	private Timestamp dataAlteracao;
	private Timestamp diaHora;

	private String local;

	//bi-directional many-to-one association to Palestra
	private Palestra palestra;

	//bi-directional many-to-one association to Participacao
	private List<Participacao> participacoes;

	public Ministracao() {
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

	public Timestamp getDiaHora() {
		return this.diaHora;
	}

	public void setDiaHora(Timestamp diaHora) {
		this.diaHora = diaHora;
	}

	public String getLocal() {
		return this.local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public Palestra getPalestra() {
		return this.palestra;
	}

	public void setPalestra(Palestra palestra) {
		this.palestra = palestra;
	}

	public List<Participacao> getParticipacoes() {
		return this.participacoes;
	}

	public void setParticipacoes(List<Participacao> participacoes) {
		this.participacoes = participacoes;
	}

	public Participacao addParticipacao(Participacao participacao) {
		getParticipacoes().add(participacao);
		participacao.setMinistracao(this);

		return participacao;
	}

	public Participacao removeParticipacao(Participacao participacao) {
		getParticipacoes().remove(participacao);
		participacao.setMinistracao(null);

		return participacao;
	}
}
