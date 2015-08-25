package com.inspector.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


public class Participante implements Serializable {

	private int id;

	private String cpf;

	private String nome;

	private Timestamp dataAlteracao;


	//bi-directional many-to-one association to Inscricao
	private List<Inscricao> inscricoes;

	//bi-directional many-to-one association to Participacao
	private List<Participacao> participacoes;

	public Participante() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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

	public List<Inscricao> getInscricoes() {
		return this.inscricoes;
	}

	public void setInscricoes(List<Inscricao> inscricoes) {
		this.inscricoes = inscricoes;
	}

	public Inscricao addInscricao(Inscricao inscricao) {
		getInscricoes().add(inscricao);
		inscricao.setParticipante(this);

		return inscricao;
	}

	public Inscricao removeInscricao(Inscricao inscricao) {
		getInscricoes().remove(inscricao);
		inscricao.setParticipante(null);

		return inscricao;
	}

	public List<Participacao> getParticipacoes() {
		return this.participacoes;
	}

	public void setParticipacoes(List<Participacao> participacoes) {
		this.participacoes = participacoes;
	}

	public Participacao addParticipacao(Participacao participacao) {
		getParticipacoes().add(participacao);
		participacao.setParticipante(this);

		return participacao;
	}

	public Participacao removeParticipacao(Participacao participacao) {
		getParticipacoes().remove(participacao);
		participacao.setParticipante(null);

		return participacao;
	}

}
