package com.inspector.newimport;

import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.inspector.R;
import com.inspector.httpClient.InternetCheck;
import com.inspector.modelcom.EventoCom;
import com.inspector.util.App;

import java.util.List;

public class ImportDataTask implements Runnable {

    public interface Listener {
        void update(boolean success);
    }

    private static final String TAG = "ImportDataTask";

    private RequestQueue mQueue;
    private Listener mListener;
    private String mBaseUrl;

    public ImportDataTask(Listener listener) {
        mListener = listener;
        mQueue = Volley.newRequestQueue(App.getContext());

        mBaseUrl = App.getPreferences().getString(
                App.getContext().getString(R.string.pref_url_key),
                App.getContext().getString(R.string.pref_url_default));
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

            EventoRequest eventoRequest = new EventoRequest(mBaseUrl + "evento/",
                    new Response.Listener<List<EventoCom>>() {
                        @Override
                        public void onResponse(List<EventoCom> response) {
                            Toast.makeText(App.getContext(), response.size(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(App.getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    });

            mQueue.add(eventoRequest);
        }
    }
}
