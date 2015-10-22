package com.inspector.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.inspector.R;
import com.inspector.activity.fragment.ListaPalestrasFragment;

public class ListaPalestrasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_palestras);

        //adicionando fragment que contém a lista
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ListaPalestrasFragment())
                    .commit();
    }
}
