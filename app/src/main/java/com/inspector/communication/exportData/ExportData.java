package com.inspector.communication.exportData;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.inspector.communication.importData.InspectorRequest;
import com.inspector.communication.importData.ObjectRequest;
import com.inspector.util.App;

import java.io.IOException;
import java.util.List;

/**
 * Classe responsável por exportar os dados para o servidor, no caso,
 * as participações.
 * Created by leandro on 30/09/15.
 */
public class ExportData {

    private static final String TAG = "REQUEST_TAG";

    private enum STATE {
        FINISH,
        RUNNING,
        FINISH_WITH_ERROR
    }

    private RequestQueue mRequestQueue;
    private VolleyError mException;
    private STATE mState;

    public ExportData() {
        mRequestQueue = Volley.newRequestQueue(App.getContext());
    }

    public void export(ObjectRequest request) throws IOException, VolleyError {

        InspectorRequest inspectorRequest = new InspectorRequest(request,
                new Response.Listener<List>() {
                    @Override
                    public void onResponse(List response) {
                        mState = STATE.FINISH;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mState = STATE.FINISH_WITH_ERROR;
                        mException = error;
                    }
                });

        inspectorRequest.setTag(TAG);

        mRequestQueue.add(inspectorRequest);

        do {
            if (mException != null) {
                mRequestQueue.cancelAll(TAG);
                throw mException;
            }
        } while (mState == STATE.RUNNING);

    }
}
