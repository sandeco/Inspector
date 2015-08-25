package com.inspector.httpClient;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class PostHttpClientTask extends HttpClientTaskAbstract{

	List<NameValuePair> listOfNameValuePair = new ArrayList<NameValuePair>();


	@Override
	public String executaHttp(String string) throws Exception {

		BufferedReader bufferreader = null;
		String resultado;
		//Encoding POST data
		try {
			HttpClient httpClient = getHttpClient();
			// replace with your url
			HttpPost httpPost = new HttpPost(string);
			httpPost.setEntity(new UrlEncodedFormEntity(listOfNameValuePair));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			// write response to log


			bufferreader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
			StringBuffer stringBuffer = new StringBuffer("");
			String line = "";
			String LS = System.getProperty("line.separator");
			while((line = bufferreader.readLine()) != null){
				stringBuffer.append(line + LS);
			}
			bufferreader.close();

			resultado = stringBuffer.toString();			

			return resultado;
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			// Log exception
			e.printStackTrace();
		}finally{
			if(bufferreader != null){
				try{
					bufferreader.close();
				}catch(IOException e){
					e.printStackTrace();
				}               
			}           
		}
		return null;

		
	}


	public void addNameValuePair(NameValuePair nameValuePair){
		listOfNameValuePair.add(nameValuePair);
	}


}
