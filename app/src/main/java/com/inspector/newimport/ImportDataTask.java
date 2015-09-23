package com.inspector.newimport;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.inspector.httpClient.InternetCheck;
import com.inspector.model.Evento;
import com.inspector.model.Ministracao;
import com.inspector.model.Palestra;
import com.inspector.modelcom.EventoCom;
import com.inspector.modelcom.MinistracaoCom;
import com.inspector.modelcom.PalestraCom;
import com.inspector.newimport.request.MinistracaoRequest;
import com.inspector.newimport.request.PalestraRequest;
import com.inspector.persistencia.dao.EventoDAO;
import com.inspector.persistencia.dao.MinistracaoDAO;
import com.inspector.persistencia.dao.PalestraDAO;
import com.inspector.persistencia.sqlite.EventoSqliteDAO;
import com.inspector.persistencia.sqlite.MinistracaoSqliteDAO;
import com.inspector.persistencia.sqlite.PalestraSqliteDAO;
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
    private PalestraDAO mPalestraDAO;
    private MinistracaoDAO mMinistracaoDAO;

    public ImportDataTask(Listener listener) {
        mListener = listener;
        mQueue = Volley.newRequestQueue(App.getContext());

        mEventoDAO = new EventoSqliteDAO();
        mPalestraDAO = new PalestraSqliteDAO();
        mMinistracaoDAO = new MinistracaoSqliteDAO();
    }

    @Override
    public void run() {
        downloadNewData();
    }

    private Response.ErrorListener errorCallback = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            mListener.update(false);

        }
    };

    private EventoRequest eventoRequest = new EventoRequest(
            new Response.Listener<List<EventoCom>>() {
                @Override
                public void onResponse(List<EventoCom> response) {

                    for (Evento evento : response)
                        mEventoDAO.create(evento);

                    mQueue.add(palestraRequest);
                }
            },
            errorCallback);

    private PalestraRequest palestraRequest = new PalestraRequest(
            new Response.Listener<List<PalestraCom>>() {
                @Override
                public void onResponse(List<PalestraCom> response) {
                    for (Palestra palestra : response)
                        mPalestraDAO.create(palestra);

                    //TODO: adicionar a MinistracaoRequest a fila quando ajustar o json do servidor
                    mListener.update(true);
                }
            },
            errorCallback);

    private MinistracaoRequest ministracaoRequest = new MinistracaoRequest(
            new Response.Listener<List<MinistracaoCom>>() {
                @Override
                public void onResponse(List<MinistracaoCom> response) {
                    for (Ministracao m : response)
                        mMinistracaoDAO.create(m);
                }
            },
            errorCallback);

    /**
     * Sincroniza os dados com servidor e ao final notifica o mListener registrado
     */
    private void downloadNewData() {

        if (!InternetCheck.isConnected(App.getContext())) {
            mListener.update(false); //não tem internet
        }
        else {

            mQueue.add(eventoRequest);
        }
    }
}
