package com.inspector.newimport;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.Request;
import com.inspector.R;
import com.inspector.modelcom.EventoCom;
import com.inspector.modelcom.MinistracaoCom;
import com.inspector.modelcom.PalestraCom;
import com.inspector.newimport.request.ObjectRequest;
import com.inspector.util.App;
import com.inspector.util.Notifier;

import java.util.ArrayList;
import java.util.List;
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

        proxyRest = new ProxyRest(this);
        schedule = new ScheduledThreadPoolExecutor(1);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //executando a tarefa de sync a cada 2 minutos

        schedule.scheduleAtFixedRate(new SyncTask(), 0, 120, TimeUnit.SECONDS);

        return START_STICKY;
    }

    /**
     * Thread que agrupa as ações para sincronização.
     */
    private class SyncTask implements Runnable {

        @Override
        public void run() {
            List<ObjectRequest> requisicoes = new ArrayList<>();

            //pegando a url das preferencias do usuario
            String BASEURL = App.getPreferences().getString(
                    getString(R.string.pref_url_key),
                    getString(R.string.pref_url_default));

            //criando os ObjectRequests que são representações de uma requisição
            //e são usados para armazenar o resultado delas
            ObjectRequest<EventoCom> eventoRequest = new ObjectRequest<>(
                    EventoCom.class, Request.Method.GET, BASEURL+"evento", null);

            ObjectRequest<PalestraCom> palestraRequest = new ObjectRequest<>(
                    PalestraCom.class, Request.Method.GET, BASEURL+"palestra", null);

            ObjectRequest<MinistracaoCom> ministracaoRequest = new ObjectRequest<>(
                    MinistracaoCom.class, Request.Method.GET, BASEURL+"ministracao", null);

            //adicionando os ObjectRequests a uma lista para serem processadas
            requisicoes.add(eventoRequest);
            requisicoes.add(palestraRequest);
            requisicoes.add(ministracaoRequest);

            //executando a sincronização
            proxyRest.sync(requisicoes);
        }
    }

    @Override
    public void onError(Exception e) {
        Log.i(TAG, "ERROR");
        Notifier.show("Sync", "An error occurred.");
    }

    @Override
    public void onSuccess() {
        Log.i(TAG, "SUCCESS");
        Notifier.show("Sync", "Success!");
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
