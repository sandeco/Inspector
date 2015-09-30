package com.inspector.communication;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.inspector.util.App;

import java.util.List;

public class DownloadRequests {

    private static final String TAG = "DownloadRequests";

    private RequestQueue mQueue;
    private static final String REQUEST_TAG = "REQUEST_TAG";

    private int count;
    private int lenghtOfRequests;
    private boolean finished;

    private Exception mErrorException;

    public DownloadRequests() {
        mQueue = Volley.newRequestQueue(App.getContext());
    }

    public synchronized List<ObjectRequest> download(List<ObjectRequest> requests) throws Exception {

        initCountRequests(requests.size());

        for (final ObjectRequest request : requests) {
            InspectorRequest inspectorRequest = new InspectorRequest(request,
                    new Response.Listener<List>() {
                        @Override
                        public void onResponse(List response) {
                            request.setObjects(response);
                            requestTerminated(request);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            requestTerminatedBad(request, error);
                        }
                    });

            inspectorRequest.setTag(REQUEST_TAG);

            mQueue.add(inspectorRequest);
        }

        do {

            if (mErrorException != null) {
                mQueue.cancelAll(REQUEST_TAG);
                throw mErrorException;
            }

        } while (!finished);

        return requests;
    }

    /**
     * Inicia os mecanismos para contagem de requests
     * terminadas e controle da permissão de retorno
     * a classe chamadora (cliente).
     * @param size Número de requisições a serem efetuadas
     */
    private void initCountRequests(int size) {

        count = 0; //zerando o contador
        lenghtOfRequests = size;

        finished = false;
        mErrorException = null;
    }

    /**
     * Chamado dentro da callback de sucesso de uma
     * request. Aumenta o contador de requisições
     * que tiveram sucesso.
     * @param request Request que teve sucesso
     */
    private void requestTerminated(ObjectRequest request) {
        count++;

        Log.i(TAG, "Request "+request+" is finished with success");

        if (count == lenghtOfRequests) {
            Log.i(TAG, "All Requests was finished");
            finished = true; //já chegamos ao número de requisições a serem feitas
        } else if (count > lenghtOfRequests) {
            Log.e(TAG, "Something is wrong. Count of requests is greater than requests availables. Aborting.");
            mErrorException = new Exception("Something is wrong. Count of requests is greater than requests availables.");
        }
    }

    private void requestTerminatedBad(ObjectRequest request, VolleyError error) {
        Log.e(TAG, "ERROR "+error+" in request "+request);
        error.printStackTrace();

        mErrorException = new Exception("Downloading of the data has failed.");
    }
}
