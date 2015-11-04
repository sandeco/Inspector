package com.inspector.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.inspector.activity.ListaMinistracaoActivity;
import com.inspector.activity.adapter.MinistracaoAdapter;
import com.inspector.model.Ministracao;
import com.inspector.model.Palestra;
import com.inspector.persistencia.dao.MinistracaoDAO;
import com.inspector.persistencia.sqlite.MinistracaoSqliteDAO;

import java.util.List;

public class ListaMinistracaoFragment extends ListFragment {

    private MinistracaoAdapter mAdapter;
    private MinistracaoDAO mDAO;
    private List<Ministracao> mMinistracoes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        Palestra palestra = (Palestra) getActivity().getIntent()
                .getSerializableExtra(ListaMinistracaoActivity.PALESTRA_EXTRA);

        if (palestra != null) {

            mDAO = new MinistracaoSqliteDAO();

            mMinistracoes = mDAO.listByPalestra(palestra);

            mAdapter = new MinistracaoAdapter(getActivity(), mMinistracoes);
            setListAdapter(mAdapter);
        }

        return rootView;
    }

    @Override
    public void onDestroyView() {
        mDAO.close();
        super.onDestroyView();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }
}
