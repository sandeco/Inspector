package com.inspector.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.inspector.R;

public class MainActivity extends AppCompatActivity {
	
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);

			TextView tvVersion = (TextView) findViewById(R.id.tvVersion);
			tvVersion.setText(pInfo.versionName);
			
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public void abrirListaPalestras(View v) {
		intent = new Intent(this, ListaAtividadesActivity.class);
		startActivity(intent);
	}
	
	public void abrirListaMinistracaoPorParticipante(View v) {
		intent = new Intent(this, SelecionarParticipanteActivity.class);
		startActivity(intent);
	}
	
	public void abrirImportarDados(View v) {
		intent = new Intent(this, ImportarDadosActivity.class);
		startActivity(intent);
	}
	
	public void abrirExportarDados(View v) {
		intent = new Intent(this, ExportarDadosActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.action_settings) {
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
