package com.inspector.httpClient;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.inspector.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class TesteHttpClientPost extends Activity implements HttpClientListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teste_http_client);
		
		/***
		 * Código para envio POST ao servidor http
		 */
		
		//cria a tarefa
		PostHttpClientTask task = new PostHttpClientTask();
		
		//adiciona essa atividade como listener da tarefa
		task.addHttpClientListener(this);
		
		// cria par de valores para envio
		NameValuePair nameValuePair = new BasicNameValuePair("presenca", "sanderson jas");
		
		// adiciona a tarefa
		task.addNameValuePair(nameValuePair);
		
		// executa tarefa httpClient post
		task.execute("http://intranet.ifg.edu.br/eventos/admin/post.php");
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.teste_http_client, menu);
		return true;
	}

	@Override
	public void updateHttpClientListener(String result) {
		System.out.println(result);
		
	}

}
