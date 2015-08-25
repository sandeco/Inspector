package com.inspector.httpClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

public class GetHttpClientTask extends HttpClientTaskAbstract {
	
	public String executaHttp(String url) throws Exception {
		BufferedReader bufferreader = null;
		
		try{
			HttpClient client = getHttpClient();
			HttpGet httpGet = new HttpGet();
			
			httpGet.setURI(new URI(url));
			HttpResponse httpResponse = client.execute(httpGet);
			
			
			bufferreader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
			
			StringBuilder response= new StringBuilder();
			int c;
			while ((c = bufferreader.read()) != -1) {
			    //Since c is an integer, cast it to a char. If it isn't -1, it will be in the correct range of char.
			    response.append( (char)c ) ;  
			}
			String resultado = response.toString();
			
			
			/*bufferreader = new BufferedReader(new InputStreamReader());
			StringBuffer stringBuffer = new StringBuffer("");
			String line = "";
			String LS = System.getProperty("line.separator");
			while((line = bufferreader.readLine()) != null){
				stringBuffer.append(line + LS);
			}
			
			bufferreader.close();
			 */
						
			return resultado;

		}finally{
			if(bufferreader != null){
				try{
					bufferreader.close();
				}catch(IOException e){
					e.printStackTrace();
				}               
			}           
		}
	}

}
