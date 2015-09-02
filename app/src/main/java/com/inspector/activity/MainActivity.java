package com.inspector.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.inspector.R;

public class MainActivity extends ActionBarActivity {
	
	private Intent intent;
	
	private TextView tvVersion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		setContentView(R.layout.inspec_main);

		//pegando objeto com informações do manifesto
		PackageInfo pInfo;
		
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			
			tvVersion = (TextView) findViewById(R.id.tvVersion);
			tvVersion.setText("V"+pInfo.versionName);
			
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public void abrirListaPalestras(View v) {
		intent = new Intent(this, ListaPalestrasActivity.class);
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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			//abrindo activity de preferencias
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
