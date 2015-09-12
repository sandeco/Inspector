package com.inspector.newimport;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ImportDataService extends Service implements ImportDataTask.Listener {

    private static final String TAG = "ImportDataService";

    private ImportDataTask mImportDataTask;
    private ScheduledThreadPoolExecutor mThreadPool;

    @Override
    public void onCreate() {
        super.onCreate();

        mImportDataTask = new ImportDataTask(this);
        mThreadPool = new ScheduledThreadPoolExecutor(1);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "Service started!");

        final int seconds = 20;

        //configurando para a tarefa de importação ser executada a cada 20 segundos
        mThreadPool.scheduleAtFixedRate(mImportDataTask, 0, seconds, TimeUnit.SECONDS);

        return START_STICKY;
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
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "Sendo destruído (onDestroy)");
    }
}
