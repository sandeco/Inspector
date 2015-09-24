package com.inspector.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.inspector.R;
import com.inspector.activity.fragment.ListaAtividadesFragment;

public class ListaPalestrasActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_palestras_activity);

		if (savedInstanceState == null)
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new ListaAtividadesFragment())
					.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_palestras, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
//		switch (item.getItemId()) {
//		case R.id.action_part_list:
//			//por teste abrindo lista de participacoes
//			startActivity(new Intent(this, ParticipacaoListActivity.class));
//			break;
//		}

		return super.onOptionsItemSelected(item);
	}


}
