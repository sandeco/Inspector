package com.inspector.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.inspector.R;
import com.inspector.activity.fragment.ListaAtividadesFragment;

public class ListaAtividadesActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_atividades);

		//adicionando fragment que contém a lista
		if (savedInstanceState == null)
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new ListaAtividadesFragment())
					.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.lista_palestras, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();

		if (id == R.id.action_settings)
			startActivity(new Intent(this, SettingsActivity.class));

		return super.onOptionsItemSelected(item);
	}


}
