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

        Log.i(TAG, "Ativado");

        //solicitando que sincronize os dados com o servidor
        importDataTask.sync();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void update(boolean success) {

        //codigo abaixo para testar o fluxo entre o service e a importadora

        if (success)
            Log.i(TAG, "Sincronização completada");
        else
            Log.i(TAG, "Sincronização falhou");

        //TODO: Código para pausar a operação por 20 segundos
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20000);

                    importDataTask.sync(); //solicitando o sincronismo novamente
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
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

        Log.i(TAG, "Sendo destruído (onDestroy)");
    }
}
