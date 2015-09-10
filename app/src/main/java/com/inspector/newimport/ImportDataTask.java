package com.inspector.newimport;

import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.inspector.R;
import com.inspector.httpClient.InternetCheck;
import com.inspector.util.App;

import org.json.JSONArray;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ImportDataTask implements Runnable {
    private static final String TAG = "ImportDataTask";

    private RequestQueue mQueue;
    private Listener mListener;
    private ScheduledThreadPoolExecutor mThreadPool;
    private boolean mRunning;

    public ImportDataTask(Listener listener) {
        mQueue = Volley.newRequestQueue(App.getContext());
        mThreadPool = new ScheduledThreadPoolExecutor(1);
        this.mListener = listener;
        this.mRunning = false;
    }

    @Override
    public void run() {
        if (mRunning)
            downloadNewData();
    }

    /**
     * Sincroniza os dados com servidor e ao final notifica o mListener registrado
     */
    private void downloadNewData() {

        if (!InternetCheck.isConnected(App.getContext()))
            mListener.update(false);
        else {

            String url = PreferenceManager.getDefaultSharedPreferences(App.getContext())
                    .getString(App.getContext().getString(R.string.pref_url_key),
                            App.getContext().getString(R.string.pref_url_default)) +
                    "evento/";

            JsonArrayRequest request = new JsonArrayRequest
                    (Request.Method.GET, url, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.v(TAG, response.toString());
                            mListener.update(true);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.v(TAG, error.toString());
                            mListener.update(false);
                        }
                    });

            mQueue.add(request);
            mQueue.start();
        }
    }

    /**
     * Executa periodicamente a sincronização de dados no intervalo de segundos passado
     * @param seconds Segundo entre cada tentativa de sincronização
     * @throws UnsupportedOperationException Disparada quando as tentativas de sincronismo já foram ativadas.
     */
    public void syncEachSeconds(int seconds) {

        if (mRunning) {
            throw new UnsupportedOperationException("Already running various sync attempts, stop them with stopSync()");
        } else {
            mThreadPool.scheduleAtFixedRate(this, 0, seconds, TimeUnit.SECONDS);
            mRunning = true;
        }
    }

    /**
     * Parar o ciclo de tentativas de sincronismo
     */
    public void stopSync() {
        mRunning = false;
    }

    public interface Listener {
        void update(boolean success);
    }
}
