package com.inspector.communication.importData;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.inspector.util.Notifier;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ImportDataService extends Service implements ProxyRest.Listener {

    private static final String TAG = "ImportDataService";

    private ProxyRest proxyRest;
    private ScheduledExecutorService schedule;

    @Override
    public void onCreate() {
        super.onCreate();

        proxyRest = new ProxyRest();
        proxyRest.registerListener(this);
        schedule = new ScheduledThreadPoolExecutor(1);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //executando a tarefa de sync a cada 2 minutos
        schedule.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                proxyRest.sync();
            }
        }, 20, 120, TimeUnit.SECONDS);

        return START_STICKY;
    }

    @Override
    public void onError(Exception e) {
        Log.i(TAG, "ERROR");
        Notifier.show("Sync failed", e.getMessage());
    }

    @Override
    public void onSuccess() {
        Log.i(TAG, "SUCCESS");
        Notifier.show("Inspector is update", "Sync with the server is completed.");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "Sendo destruído (onDestroy)");
        schedule.shutdownNow();

        super.onDestroy();
    }
}
