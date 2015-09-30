package com.inspector.communication.importData;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ImportDataReceiver extends BroadcastReceiver {
    private static final String TAG = "ImportDataReceiver";

    public ImportDataReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(TAG, "Recebendo uma transmissão: "+intent.toString());

        //iniciando o service de importação de dados
        Intent i = new Intent(context, ImportDataService.class);
        context.startService(i);
    }
}
