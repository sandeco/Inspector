package com.inspector.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.inspector.R;
import com.inspector.activity.fragment.ListaInscritosFragment;

public class ListaInscritosActivity extends AppCompatActivity {

	public static final int REQUEST_CODE = 999;
	public static final String EXTRA_NUMERO_INSCRITO = "com.inspector.extra_numero_inscrito";
	public static final String EXTRA_PALESTRA = "com.inspector.extra_palestra";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_inscritos);

		getSupportFragmentManager().beginTransaction()
				.add(R.id.container, new ListaInscritosFragment())
				.commit();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
