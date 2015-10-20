package com.inspector.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.inspector.activity.VerificarPresencaActivity;
import com.inspector.activity.adapter.AtividadeAdapter;
import com.inspector.model.Ministracao;
import com.inspector.persistencia.dao.MinistracaoDAO;
import com.inspector.persistencia.sqlite.MinistracaoSqliteDAO;

import java.sql.Timestamp;
import java.util.List;

public class ListaAtividadesFragment extends ListFragment {

    public static final String EXTRA_MINISTRACAO = "ministracao_object";

    private MinistracaoDAO dao;
    private List<Ministracao> ministracoes;

    public ListaAtividadesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        dao = new MinistracaoSqliteDAO();
        ministracoes = dao.listByDate(Timestamp.valueOf("2015-08-01 08:00:00"),
                Timestamp.valueOf("2015-12-31 00:00:00"));

        AtividadeAdapter adapter = new AtividadeAdapter(getActivity(), ministracoes);
        setListAdapter(adapter);

        return rootView;
    }

    @Override
    public void onDestroy() {
        dao.close();
        super.onDestroy();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(getActivity(), VerificarPresencaActivity.class);
        intent.putExtra(EXTRA_MINISTRACAO, ministracoes.get(position));
        startActivity(intent);
    }
}
