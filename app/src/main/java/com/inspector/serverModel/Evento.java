
package com.inspector.serverModel;

import java.io.Serializable;
import java.util.List;

public class Evento implements Serializable {
   	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String CODEVENTO;
   	private String EVENTO;
   	private List<Atividade> LISTA_ATIVIDADES;

   	public Evento() {}

	public String getCODEVENTO() {
		return CODEVENTO;
	}

	public void setCODEVENTO(String cODEVENTO) {
		CODEVENTO = cODEVENTO;
	}

	public String getEVENTO() {
		return EVENTO;
	}

	public void setEVENTO(String eVENTO) {
		EVENTO = eVENTO;
	}

	public List<Atividade> getLISTA_ATIVIDADES() {
		return LISTA_ATIVIDADES;
	}

	public void setLISTA_ATIVIDADES(List<Atividade> lISTA_ATIVIDADES) {
		LISTA_ATIVIDADES = lISTA_ATIVIDADES;
	}

}
