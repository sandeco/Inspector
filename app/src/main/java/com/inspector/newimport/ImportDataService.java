package com.inspector.newimport;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ImportDataService extends Service implements ImportDataTask.Listener {

    private static final String TAG = "ImportDataService";

    private ImportDataTask importDataTask;

    public ImportDataService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        importDataTask = new ImportDataTask(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "Started " + intent.toString());

        //executar a sincronização a cada 20 segundos
        importDataTask.syncEachSeconds(20);

        return START_REDELIVER_INTENT;
    }

    @Override
    public void update(boolean success) {

        if (success)
            Log.i(TAG, "Sincronização completada");
        else
            Log.i(TAG, "Sincronização falhou");
    }

    @Override
    public IBinder onBind(Intent intent) {
        //nao iremos permitir bindService,
        //entao retornamos null
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        importDataTask.stopSync();

        Log.i(TAG, "Sendo destruído (onDestroy)");
    }
}
