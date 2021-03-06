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

public class InscricaoAdapter extends ArrayAdapter<Inscricao> {

    private LayoutInflater mInflater;

    public InscricaoAdapter(Context context, List<Inscricao> objects) {
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

        TextView numeroInscricao = (TextView) listItemView.findViewById(R.id.text_small);
        TextView nomeParticipante = (TextView) listItemView.findViewById(R.id.text_large);

        numeroInscricao.setText(String.valueOf(getItem(position).getParticipante().getId()));
        nomeParticipante.setText(getItem(position).getParticipante().getNome());

        return listItemView;
    }


}
