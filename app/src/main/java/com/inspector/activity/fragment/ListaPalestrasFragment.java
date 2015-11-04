package com.inspector.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.inspector.activity.ListaMinistracaoActivity;
import com.inspector.activity.adapter.PalestraAdapter;
import com.inspector.communication.importData.ProxyPalestra;
import com.inspector.model.Palestra;
import com.inspector.persistencia.dao.PalestraDAO;
import com.inspector.persistencia.sqlite.PalestraSqliteDAO;

import java.util.List;

public class ListaPalestrasFragment extends ListFragment implements ProxyPalestra.ProxyPalestraListener {

    private PalestraAdapter mAdapter;
    private PalestraDAO mPalestraDAO;
    private List<Palestra> mPalestras;
    private Palestra mClickedPalestra;

    public ListaPalestrasFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        mPalestraDAO = new PalestraSqliteDAO();

        mPalestras = mPalestraDAO.listAll();

        mAdapter = new PalestraAdapter(getActivity(), mPalestras);
        setListAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onDestroy() {
        mPalestraDAO.close();
        super.onDestroy();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        mClickedPalestra = mPalestras.get(position);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                ProxyPalestra proxyPalestra = new ProxyPalestra();
                proxyPalestra.registerListener(ListaPalestrasFragment.this);
                proxyPalestra.sync(mClickedPalestra);
            }
        });

        t.start();
    }

    @Override
    public void onProxyPalestraError(Exception e) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getActivity(), "Oops. Ocorreu um erro na busca pelos dados desta atividade",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onProxyPalestraSuccess() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getActivity(), ListaMinistracaoActivity.class);
                intent.putExtra(ListaMinistracaoActivity.PALESTRA_EXTRA, mClickedPalestra);
                startActivity(intent);
            }
        });
    }
}
