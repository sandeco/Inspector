package com.inspector.activity;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

		getSupportFragmentManager().beginTransaction()
				.add(R.id.container, new ListaInscricaoPorParticipanteFragment())
				.commit();
	}

	public static class ListaInscricaoPorParticipanteFragment extends ListFragment {

		private Participante mParticipante;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

			mParticipante = (Participante)
					getActivity().getIntent().getSerializableExtra(EXTRA_PARTICIPANTE);

			if (mParticipante != null) {

				InscricaoDAO dao = new InscricaoSqliteDAO();

				List<Inscricao> inscricoes = dao.listByParticipante(mParticipante);
				InscricaoParticipanteAdapter adapter = new InscricaoParticipanteAdapter(getActivity(), inscricoes);

				setListAdapter(adapter);

				getActivity().setTitle(mParticipante.getNome());
			}

			return super.onCreateView(inflater, container, savedInstanceState);
		}
	}
}
