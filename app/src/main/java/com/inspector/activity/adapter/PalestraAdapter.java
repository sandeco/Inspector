package com.inspector.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.inspector.R;
import com.inspector.model.Atividade;

import java.util.List;

public class PalestraAdapter extends ArrayAdapter<Atividade> {

    private LayoutInflater mInflater;

    public PalestraAdapter(Context context, List<Atividade> objects) {
        super(context, 0, objects);

        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewGroup listItemView;

        if (convertView == null) {
            listItemView = (ViewGroup) mInflater.inflate(R.layout.simple_one_text_list_item, parent, false);
        } else {
            listItemView = (ViewGroup) convertView;
        }

        TextView nomePalestra = (TextView) listItemView.findViewById(R.id.text_large);
        nomePalestra.setText(getItem(position).getNome());

        return listItemView;
    }
}
