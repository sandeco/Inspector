package com.inspector.communication.exportData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.inspector.communication.importData.ObjectRequest;
import com.inspector.util.App;

import java.io.IOException;

/**
 * Classe responsável por exportar os dados para o servidor, no caso,
 * as participações.
 * Created by leandro on 30/09/15.
 */
public class ExportData {

    private RequestQueue mRequestQueue;

    public ExportData() {
        mRequestQueue = Volley.newRequestQueue(App.getContext());
    }

    public void export(ObjectRequest request) throws IOException {


//        InspectorRequest inspectorRequest = new InspectorRequest(request,
//                new Response.Listener<List>() {
//                    @Override
//                    public void onResponse(List response) {
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//
//        mRequestQueue.add(inspectorRequest);
    }
}
