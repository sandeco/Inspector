package com.inspector.activity.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.inspector.activity.adapter.PalestraAdapter;
import com.inspector.model.Palestra;
import com.inspector.persistencia.dao.PalestraDAO;
import com.inspector.persistencia.sqlite.PalestraSqliteDAO;

import java.util.List;

public class ListaPalestrasFragment extends ListFragment {

    private PalestraAdapter mAdapter;
    private PalestraDAO mPalestraDAO;
    private List<Palestra> mPalestras;

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

        Snackbar.make(v, "Testando clique", Snackbar.LENGTH_LONG).show();
    }
}
