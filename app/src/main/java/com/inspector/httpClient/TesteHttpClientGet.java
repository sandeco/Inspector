package com.inspector.httpClient;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.inspector.R;

public class TesteHttpClientGet extends Activity implements HttpClientListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teste_http_client_get);
		
		GetHttpClientTask task = new GetHttpClientTask();
		
		task.addHttpClientListener(this);
		
		task.execute("http://intranet.ifg.edu.br/eventos/admin/dadosjson.php");
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.teste_http_client_get, menu);
		return true;
	}

	@Override
	public void updateHttpClientListener(String result) {
		System.out.println(result);
		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		
	}

}
