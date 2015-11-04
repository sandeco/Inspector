package com.inspector.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.inspector.R;

public class ListaMinistracaoActivity extends AppCompatActivity {

    public static final String PALESTRA_EXTRA = "com.inspector.PALESTRA_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ministracao);
    }
}
