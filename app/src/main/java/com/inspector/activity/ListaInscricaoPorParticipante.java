package com.inspector.activity;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;

import com.inspector.R;
import com.inspector.activity.adapter.InscricaoParticipanteAdapter;
import com.inspector.model.Inscricao;
import com.inspector.model.Participante;
import com.inspector.persistencia.dao.InscricaoDAO;
import com.inspector.persistencia.sqlite.InscricaoSqliteDAO;

import java.util.List;

public class ListaInscricaoPorParticipante extends AppCompatActivity {

	public static final String EXTRA_PARTICIPANTE = "com.inspector.EXTRA_PARTICIPANTE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_inscricao_por_participante);

		Participante participante = (Participante)
				getIntent().getSerializableExtra(EXTRA_PARTICIPANTE);

		if (participante != null) {

			InscricaoDAO dao = new InscricaoSqliteDAO();

			List<Inscricao> inscricoes = dao.listByParticipante(participante);
			InscricaoParticipanteAdapter adapter = new InscricaoParticipanteAdapter(this, inscricoes);

			ListFragment fragment = new ListFragment();
			fragment.setListAdapter(adapter);

			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, fragment)
					.commit();
		}
	}
}
