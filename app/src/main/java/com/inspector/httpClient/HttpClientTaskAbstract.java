package com.inspector.httpClient;

import android.os.AsyncTask;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.util.ArrayList;
import java.util.List;

public abstract class HttpClientTaskAbstract extends AsyncTask<String, String, String> {

	protected List<HttpClientListener> listeners = new ArrayList<HttpClientListener>();
	protected static final int HTTP_TIMEOUT = 30*1000;
	private static HttpClient httpClient;
	
	@Override
	protected String doInBackground(String... params) {
			
		String output = "";
		try {
			output = executaHttp(params[0]);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return output;
	}
	
	public abstract String executaHttp(String string) throws Exception;
	
	
	protected static HttpClient getHttpClient(){
		if(httpClient==null){  
			httpClient = new DefaultHttpClient();
			final HttpParams httpParams = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, HTTP_TIMEOUT);
			HttpConnectionParams.setSoTimeout(httpParams, HTTP_TIMEOUT);
			ConnManagerParams.setTimeout(httpParams, HTTP_TIMEOUT);
		}
		return httpClient;
	}

	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		for(HttpClientListener l : listeners){
			l.updateHttpClientListener(result);
		}
	}
	
	
	public void addHttpClientListener(HttpClientListener l){
		listeners.add(l);
	}

}
