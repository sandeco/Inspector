package com.inspector.model;


import java.io.Serializable;
import java.sql.Timestamp;

public class Participacao implements Serializable {

	private int id;

	private Timestamp dataAlteracao;

	//bi-directional many-to-one association to Ministracao
	private Ministracao ministracao;

	//bi-directional many-to-one association to Participante
	private Participante participante;

	public Participacao() {
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

	public Ministracao getMinistracao() {
		return this.ministracao;
	}

	public void setMinistracao(Ministracao ministracao) {
		this.ministracao = ministracao;
	}

	public Participante getParticipante() {
		return this.participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}


}
