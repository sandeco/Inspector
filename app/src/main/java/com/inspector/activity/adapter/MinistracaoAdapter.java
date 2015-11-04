package com.inspector.activity.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.inspector.R;
import com.inspector.model.Ministracao;

import java.util.List;

/**
 * Created by leandro on 03/11/15.
 */
public class MinistracaoAdapter extends ArrayAdapter<Ministracao> {

    private LayoutInflater mInflater;

    public MinistracaoAdapter(Context context, List<Ministracao> objects) {
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

        TextView data = (TextView) listItemView.findViewById(R.id.text_large);
        data.setText(DateFormat.format("dd/MM/yyyy HH:mm", getItem(position).getDiaHora().getTime()).toString());

        return listItemView;
    }
}
