package com.inspector.serverModel;

import java.io.Serializable;
import java.util.List;

public class Atividade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ATIVIDADE;
	private String CODATIVIDADE;
	private String DTHORA_INICIO;
	private String DTHORA_FIM;
	private List<ParticipanteServer> LISTA_PARTICIPANTES;

	public Atividade() {}

	public String getATIVIDADE() {
		return ATIVIDADE;
	}

	public void setATIVIDADE(String aTIVIDADE) {
		ATIVIDADE = aTIVIDADE;
	}

	public String getCODATIVIDADE() {
		return CODATIVIDADE;
	}

	public void setCODATIVIDADE(String cODATIVIDADE) {
		CODATIVIDADE = cODATIVIDADE;
	}

	public String getDTHORA_INICIO() {
		return DTHORA_INICIO;
	}

	public void setDTHORA_INICIO(String dTHORA_INICIO) {
		DTHORA_INICIO = dTHORA_INICIO;
	}

	public String getDTHORA_FIM() {
		return DTHORA_FIM;
	}

	public void setDTHORA_FIM(String dTHORA_FIM) {
		DTHORA_FIM = dTHORA_FIM;
	}

	public List<ParticipanteServer> getLISTA_PARTICIPANTES() {
		return LISTA_PARTICIPANTES;
	}

	public void setLISTA_PARTICIPANTES(List<ParticipanteServer> lISTA_PARTICIPANTES) {
		LISTA_PARTICIPANTES = lISTA_PARTICIPANTES;
	}

	
}
