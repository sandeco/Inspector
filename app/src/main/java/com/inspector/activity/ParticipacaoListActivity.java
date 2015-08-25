package com.inspector.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import com.inspector.R;
import com.inspector.dao.ParticipacaoDAOImpl;
import com.inspector.model.Participacao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParticipacaoListActivity extends ListActivity {
	
	private ParticipacaoDAOImpl dao;
	private List<Participacao> participacoes;
	private List<Map<String, Object>> listAdapter;
	
	private String[] mapKeys = new String[]{"id", "ministracao id", "palestra nome",
										"palestra id", "participante nome", "participante id",
										"presenca", "ministracao data"};
	
	private int[] interfaceKeys = new int[]{R.id.p_id, R.id.p_ministracao_id, R.id.p_palestra_nome,
											R.id.p_palestra_id, R.id.p_participante_id, R.id.p_partipante_nome,
											R.id.p_presenca, R.id.p_ministracao_data};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		dao = new ParticipacaoDAOImpl(this);
		participacoes = dao.listarParticipacao();
		
		listAdapter = new ArrayList<Map<String,Object>>();
		
		for (Participacao p : participacoes) {
			Map<String,Object> item = new HashMap<String,Object>();

			item.put("ministracao id", p.getMinistracao().getId());
			item.put("palestra nome", p.getMinistracao().getPalestra().getNome());
			item.put("palestra id", p.getMinistracao().getPalestra().getId());
			item.put("participante nome", p.getParticipante().getNome());
			item.put("participante id", p.getParticipante().getId());
			//item.put("presenca", p.isPresenca());
			item.put("ministracao data", p.getMinistracao().getDiaHora().toString());
			
			listAdapter.add(item);
		}
		
		setListAdapter(new SimpleAdapter(this, listAdapter, 
				R.layout.activity_participacao_list, mapKeys, interfaceKeys));
		
	}

	@Override
	protected void onDestroy() {
		dao.close();
		super.onDestroy();
	}
	
	
}
