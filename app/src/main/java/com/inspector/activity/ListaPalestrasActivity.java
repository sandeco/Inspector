package com.inspector.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.inspector.R;
import com.inspector.model.Ministracao;
import com.inspector.persistencia.sqlite.MinistracaoSqliteDAO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaPalestrasActivity extends ListActivity {


	public static final String EXTRA_MINISTRACAO = "ministracao_object";
	private MinistracaoSqliteDAO dao;
	private List<Ministracao> ministracoesHoje;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		dao = new MinistracaoSqliteDAO();
		ministracoesHoje = dao.listByDate(Timestamp.valueOf("2015-08-28 08:00:00"));

		ArrayList<HashMap<String, String>> itens = new ArrayList<>();
		for (Ministracao m : ministracoesHoje) {
			HashMap<String, String> item = new HashMap<>();
			
			item.put("nome", m.getPalestra().getNome());
			item.put("data", DateFormat.format("dd/MM/yyyy", m.getDiaHora().getTime()).toString());

			itens.add(item);
		}

		String[] from = new String[]{"nome", "data"};
		int[] to = new int[]{R.id.nome_palestra, R.id.data_palestra};
		int resource = R.layout.activity_lista_palestras;

		SimpleAdapter adapter = new SimpleAdapter(this, itens, resource, from, to);

		setListAdapter(adapter);

	}
	
	@Override
	protected void onDestroy() {
		dao.close();
		super.onDestroy();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_palestras, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
//		switch (item.getItemId()) {
//		case R.id.action_part_list:
//			//por teste abrindo lista de participacoes
//			startActivity(new Intent(this, ParticipacaoListActivity.class));
//			break;
//		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Intent intent = new Intent(getApplicationContext(), VerificarPresencaActivity.class);
		intent.putExtra(EXTRA_MINISTRACAO, ministracoesHoje.get(position));
		startActivity(intent);
	}

}
