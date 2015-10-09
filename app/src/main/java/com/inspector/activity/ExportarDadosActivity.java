package com.inspector.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.inspector.R;
import com.inspector.communication.exportData.ProxyExport;

public class ExportarDadosActivity extends AppCompatActivity implements ProxyExport.Listener {

	public static final String JSON_EXPORTADO_EXTRA = "com.inspector.json_exportado_extra";
	
	private ProgressBar progressBar;
	private Button btExportar;
	private TextView tvOutput;
	private Button btListaPresencas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exportar_dados);

		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		btExportar = (Button) findViewById(R.id.bt_exportar);
		tvOutput = (TextView) findViewById(R.id.textOutput);
		btListaPresencas = (Button) findViewById(R.id.bt_lista_presencas);

		btExportar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ativarTelaCarregamento();

				ProxyExport proxyExport = new ProxyExport(ExportarDadosActivity.this);
				proxyExport.sync();
			}
		});

		ativarPrimeiraTela();
	}

	private void ativarTelaCarregamento() {
		btExportar.setVisibility(View.INVISIBLE);
		progressBar.setVisibility(View.VISIBLE);
		tvOutput.setVisibility(View.INVISIBLE);
		btListaPresencas.setVisibility(View.INVISIBLE);
	}

	private void ativarPrimeiraTela() {
		btExportar.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.INVISIBLE);
		tvOutput.setVisibility(View.INVISIBLE);
		btListaPresencas.setVisibility(View.INVISIBLE);
	}

	private void ativarSegundaTela() {
		btExportar.setVisibility(View.INVISIBLE);
		progressBar.setVisibility(View.INVISIBLE);
		tvOutput.setVisibility(View.VISIBLE);
		btListaPresencas.setVisibility(View.VISIBLE);
	}

	public void abrirListaPresencasExportadas(View v) {
		Intent intent = new Intent(this, ListaPresencasExportadasActivity.class);
//		intent.putExtra(JSON_EXPORTADO_EXTRA, json);
		startActivity(intent);
	}

	@Override
	public void onError(Exception e) {
		ativarSegundaTela();
		tvOutput.setText(getString(R.string.erro_jsonExportacao));
	}

	@Override
	public void onSuccess() {
		ativarSegundaTela();
		tvOutput.setText(getString(R.string.dados_exportados));
		Toast.makeText(this, getString(R.string.dados_exportados), Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.exportar_dados, menu);
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

