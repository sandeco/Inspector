package com.inspector.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.inspector.R;
import com.inspector.httpClient.HttpClientListener;
import com.inspector.httpClient.InternetCheck;
import com.inspector.httpClient.PostHttpClientTask;
import com.inspector.serverModel.ExportadoraDados;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExportarDadosActivity extends ActionBarActivity implements HttpClientListener, OnClickListener {

	public static final String JSON_EXPORTADO_EXTRA = "com.inspector.json_exportado_extra";
	
	private ProgressBar progressBar;
	private Button btExportar;
	private TextView tvOutput;
	private Button btListaPresencas;
	
	private final String link = "http://intranet.ifg.edu.br/eventos/admin/post.php";

	private ExportadoraDados exportadora;
	private String json;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exportar_dados);

		exportadora = new ExportadoraDados(this);

		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		btExportar = (Button) findViewById(R.id.bt_exportar);
		tvOutput = (TextView) findViewById(R.id.textOutput);
		btListaPresencas = (Button) findViewById(R.id.bt_lista_presencas);

		btExportar.setOnClickListener(this);


		ativarPrimeiraTela();

	}

	public void exportarDados(View v) throws JSONException, IOException{

		//gera o JSON
		json = exportadora.getJsonEvento();

		// data atual
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
		
		
		
		
		//grava arquivo com json na pasta Downloas do dispositivo
		String nomeArquivo = "PresencasEvento "+ date + ".txt";
		String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + Environment.DIRECTORY_DOWNLOADS;
		
		File arquivo = new File( dir, nomeArquivo);
		arquivo.createNewFile();
		
		//exporta o JSON para o arquivo
		FileWriter writer = new FileWriter(arquivo);
		writer.write(json);
		writer.close();
		
		Toast.makeText(this, getString(R.string.backup_exportado) + dir, Toast.LENGTH_SHORT).show();

		if(InternetCheck.isConnected(this)){
			ativarTelaCarregamento();

			PostHttpClientTask task = new PostHttpClientTask();
			task.addHttpClientListener(this);

			//adiciona o JSON a task e a executa
			NameValuePair nameValuePair;

			nameValuePair = new BasicNameValuePair("presenca", json);
			task.addNameValuePair(nameValuePair);
			task.execute(link);

		}else{
			Toast.makeText(this, getString(R.string.internet_erro), Toast.LENGTH_LONG).show();
		}


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

	@Override
	public void updateHttpClientListener(String result) {
		ativarSegundaTela();
		
//		tvOutput.setText(getString(R.string.json_exportado) + result);
		tvOutput.setText(getString(R.string.dados_exportados));
		
		Toast.makeText(this, getString(R.string.dados_exportados), Toast.LENGTH_LONG).show();
	}
	
	public void abrirListaPresencasExportadas(View v) {
		Intent intent = new Intent(this, ListaPresencasExportadasActivity.class);
		intent.putExtra(JSON_EXPORTADO_EXTRA, json);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exportar_dados, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		try {
			exportarDados(v);
		} catch (JSONException e) {
			Toast.makeText(this, getString(R.string.erro_jsonExportacao), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

