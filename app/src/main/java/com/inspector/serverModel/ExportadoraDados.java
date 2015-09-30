package com.inspector.serverModel;

import android.content.Context;

import com.google.gson.Gson;
import com.inspector.serverModel.dao.ParticipacaoDAOImpl;

import java.util.ArrayList;
import java.util.List;

public class ExportadoraDados {

	private ParticipacaoDAOImpl pDAO;
	private Gson gson;

	public ExportadoraDados(Context context) {
		pDAO = new ParticipacaoDAOImpl(context);
		gson = new Gson();
	}

	public String getJsonEvento(){

		List<Presenca> presencas = new ArrayList<Presenca>();

		presencas = pDAO.listarParticipacoesJson();
		
		String json = gson.toJson(presencas);

		return json;
	}
}
