package com.inspector.activity;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.SimpleAdapter;

import com.inspector.R;
import com.inspector.communication.modelcom.ParticipacaoCom;
import com.inspector.model.Participacao;
import com.inspector.persistencia.dao.ParticipacaoDAO;
import com.inspector.persistencia.sqlite.ParticipacaoSqliteDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaPresencasExportadasActivity extends AppCompatActivity {

	private List<ParticipacaoCom> mParticipacoes;
	private ParticipacaoDAO mParticipacaoDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_layout);

		ListFragment fragment = new ListFragment();

		getSupportFragmentManager().beginTransaction()
				.add(R.id.container, fragment)
				.commit();

		mParticipacaoDAO = new ParticipacaoSqliteDAO();

		mParticipacoes = mParticipacaoDAO.listToExport();

		List<Map<String, String>> data = new ArrayList<Map<String, String>>();

		for (Participacao participacao : mParticipacoes) {

				Map<String, String> item = new HashMap<String, String>();
				item.put("palestra nome", participacao.getMinistracao().getAtividade().getNome());
				item.put("participante nome", participacao.getParticipante().getNome());
				
				data.add(item);
		}

		fragment.setListAdapter(new SimpleAdapter(this,
					data,
					android.R.layout.simple_list_item_2,
					new String[]{"palestra nome", "participante nome"},
					new int[]{android.R.id.text2, android.R.id.text1}));
	}

	@Override
	protected void onDestroy() {
		mParticipacaoDAO.close();
		super.onDestroy();
	}
}
