package com.inspector.newimport;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.inspector.httpClient.InternetCheck;
import com.inspector.model.Evento;
import com.inspector.modelcom.EventoCom;
import com.inspector.newimport.request.EventoRequest;
import com.inspector.persistencia.dao.EventoDAO;
import com.inspector.persistencia.sqlite.EventoSqliteDAO;
import com.inspector.util.App;

import java.util.List;

public class ImportDataTask implements Runnable {

    public interface Listener {
        void update(boolean success);
    }

    private static final String TAG = "ImportDataTask";

    private RequestQueue mQueue;
    private Listener mListener;
    private EventoDAO mEventoDAO;

    public ImportDataTask(Listener listener) {
        mListener = listener;
        mQueue = Volley.newRequestQueue(App.getContext());

        mEventoDAO = new EventoSqliteDAO();
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

                            for (Evento evento : response)
                                mEventoDAO.create(evento);

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
