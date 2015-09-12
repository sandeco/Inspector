package com.inspector.newimport;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.inspector.httpClient.InternetCheck;
import com.inspector.modelcom.EventoCom;
import com.inspector.newimport.request.EventoRequest;
import com.inspector.persistencia.dao.EventoDAO;
import com.inspector.util.App;

import java.util.List;

public class ImportDataTask implements Runnable {

    public interface Listener {
        void update(boolean success);
    }

    private static final String TAG = "ImportDataTask";

    private RequestQueue mQueue;
    private Listener mListener;

    public ImportDataTask(Listener listener) {
        mListener = listener;
        mQueue = Volley.newRequestQueue(App.getContext());
    }

    @Override
    public void run() {
        downloadNewData();
    }

    /**
     * Sincroniza os dados com servidor e ao final notifica o mListener registrado
     */
    private void downloadNewData() {

        if (!InternetCheck.isConnected(App.getContext()))
            mListener.update(false); //não tem internet
        else {

            EventoRequest eventoRequest = new EventoRequest(
                    new Response.Listener<List<EventoCom>>() {
                        @Override
                        public void onResponse(List<EventoCom> response) {
                            mListener.update(true);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            mListener.update(false);
                        }
                    });

            mQueue.add(eventoRequest);
        }
    }
}
