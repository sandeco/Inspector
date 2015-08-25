package com.inspector.serverModel;

import java.io.Serializable;

public class ParticipanteServer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String CODPARTICIPANTE;
	private String NOME;
	private String CPF;

	public ParticipanteServer() {
		
	}

	public String getCODPARTICIPANTE() {
		return CODPARTICIPANTE;
	}

	public void setCODPARTICIPANTE(String cODPARTICIPANTE) {
		CODPARTICIPANTE = cODPARTICIPANTE;
	}

	public String getNOME() {
		return NOME;
	}

	public void setNOME(String nOME) {
		NOME = nOME;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}
}
