package com.inspector.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;
import com.inspector.serverModel.dao.MinistracaoDAOImpl;
import com.inspector.serverModel.dao.ParticipacaoDAOImpl;
import com.inspector.serverModel.Presenca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaPresencasExportadasActivity extends ListActivity {

	private MinistracaoDAOImpl mDAO;
	private ParticipacaoDAOImpl pDAO;
	private String json;
	private Gson gson;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mDAO = new MinistracaoDAOImpl(this);
		pDAO = new ParticipacaoDAOImpl(this);
		gson = new Gson();

		json = getIntent().getStringExtra(ExportarDadosActivity.JSON_EXPORTADO_EXTRA);

		if (json != null) {
			
			//Extraindo os dados do Json e do Banco de Dados

			List<Map<String, String>> data = new ArrayList<Map<String, String>>();

			Presenca[] presencas = gson.fromJson(json, Presenca[].class);

			for (Presenca p : presencas) {

				String nomePalestra = (mDAO.buscarPalestraPorId(p.getCod_atividade())).getNome();
				String nomeParticipante="";

				//String nomeParticipante = (pDAO.buscarParticipante(new Participante(p.getCod_participante(), null))).getNome();

				Map<String, String> item = new HashMap<String, String>();
				item.put("palestra nome", nomePalestra);
				item.put("participante nome", nomeParticipante);
				
				data.add(item);
			}

			//Setando o adapter para a lista
			
			getListView().setAdapter(new SimpleAdapter(this,
						data, 
						android.R.layout.simple_list_item_2, 
						new String[]{"palestra nome", "participante nome"}, 
						new int[]{android.R.id.text2, android.R.id.text1}));
		}
	}

	@Override
	protected void onDestroy() {
		mDAO.close();
		pDAO.close();
		super.onDestroy();
	}
}
