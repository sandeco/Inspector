package com.inspector.communication;

import android.content.Context;
import android.net.ConnectivityManager;

import com.inspector.util.App;

public class InternetCheck {

	public static boolean isConnected() {
	    boolean conectado;  	   
	    ConnectivityManager conectivtyManager = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		conectado = conectivtyManager.getActiveNetworkInfo() != null
				&& conectivtyManager.getActiveNetworkInfo().isAvailable()
				&& conectivtyManager.getActiveNetworkInfo().isConnected();
	    return conectado;  
	}  
}
