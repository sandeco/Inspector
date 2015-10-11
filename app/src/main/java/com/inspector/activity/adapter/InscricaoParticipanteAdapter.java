package com.inspector.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.inspector.R;
import com.inspector.model.Inscricao;

import java.util.List;

public class InscricaoParticipanteAdapter extends ArrayAdapter<Inscricao> {

    private LayoutInflater mInflater;

    public InscricaoParticipanteAdapter(Context context, List<Inscricao> objects) {
        super(context, 0, objects);

        mInflater = LayoutInflater.from(getContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewGroup listItemView;

        if (convertView == null) {
            listItemView = (ViewGroup) mInflater.inflate(R.layout.simple_two_text_list_item, parent, false);
        } else {
            listItemView = (ViewGroup) convertView;
        }

        TextView nomePalestra = (TextView) listItemView.findViewById(R.id.text_large);
        TextView data = (TextView) listItemView.findViewById(R.id.text_small);

        nomePalestra.setText(getItem(position).getPalestra().getNome());
        data.setText(""); //TODO

        return listItemView;
    }
}
