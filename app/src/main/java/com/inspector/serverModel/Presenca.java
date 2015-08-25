package com.inspector.serverModel;

import java.io.Serializable;

public class Presenca implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int cod_participante;
	private int cod_atividade;

	public Presenca() {}

	public int getCod_participante() {
		return cod_participante;
	}

	public void setCod_participante(int cod_participante) {
		this.cod_participante = cod_participante;
	}

	public int getCod_atividade() {
		return cod_atividade;
	}

	public void setCod_atividade(int cod_atividade) {
		this.cod_atividade = cod_atividade;
	}
}
