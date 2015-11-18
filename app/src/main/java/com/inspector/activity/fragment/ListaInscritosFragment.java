package com.inspector.activity.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.inspector.activity.ListaInscritosActivity;
import com.inspector.activity.adapter.InscricaoAdapter;
import com.inspector.model.Atividade;
import com.inspector.model.Inscricao;
import com.inspector.persistencia.dao.InscricaoDAO;
import com.inspector.persistencia.sqlite.InscricaoSqliteDAO;

import java.util.List;

public class ListaInscritosFragment extends ListFragment {

    private Atividade mAtividade;
    private InscricaoDAO mInscricaoDAO;
    private List<Inscricao> mInscricoes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        mAtividade = (Atividade) getActivity().getIntent()
            .getSerializableExtra(ListaInscritosActivity.EXTRA_PALESTRA);

        if (mAtividade != null) {

            mInscricaoDAO = new InscricaoSqliteDAO();

            mInscricoes = mInscricaoDAO.listByPalestra(mAtividade);

            InscricaoAdapter adapter = new InscricaoAdapter(getActivity(), mInscricoes);
            setListAdapter(adapter);
        }

        return rootView;
    }

    @Override
    public void onDestroy() {
        mInscricaoDAO.close();
        super.onDestroy();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent resultIntent = new Intent();
        resultIntent.putExtra(ListaInscritosActivity.EXTRA_NUMERO_INSCRITO,
                String.valueOf(mInscricoes.get(position).getParticipante().getId()));
        getActivity().setResult(Activity.RESULT_OK, resultIntent);
        getActivity().finish();
    }
}
