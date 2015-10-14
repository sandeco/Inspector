package com.inspector.communication.exportData;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspector.communication.importData.ObjectRequest;
import com.inspector.util.App;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe responsável por exportar os dados para o servidor, no caso,
 * as participações.
 * Created by leandro on 30/09/15.
 */
public class ExportData {

    private static final String REQUEST_TAG = "REQUEST_TAG";

    private enum STATE {
        FINISH,
        RUNNING,
        FINISH_WITH_ERROR
    }

    private RequestQueue mRequestQueue;
    private VolleyError mException;
    private STATE mState;

    private ObjectMapper mapper;

    public ExportData() {
        mRequestQueue = Volley.newRequestQueue(App.getContext());
        mapper = new ObjectMapper();
    }

    public void export(final ObjectRequest request) throws IOException, VolleyError, JsonProcessingException {

        final String json = mapper.writeValueAsString(request.getObjects());

        StringRequest stringRequest = new StringRequest(request.getMethod(), request.getUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mState = STATE.FINISH;
                        Log.i("ExportData", "Response from server: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mState = STATE.FINISH_WITH_ERROR;
                        mException = error;
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("participacoes", json);
                return params;
            }
        };

        stringRequest.setTag(REQUEST_TAG);
        mRequestQueue.add(stringRequest);

        do {
            if (mException != null) {
                mRequestQueue.cancelAll(REQUEST_TAG);
                throw mException;
            }
        } while (mState == STATE.RUNNING);

    }
}
