package com.inspector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ImportDataReceiver extends BroadcastReceiver {
    public ImportDataReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        //iniciando o service de importação de dados

        Intent i = new Intent(context, ImportDataService.class);
        context.startService(i);
    }
}
