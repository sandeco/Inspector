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

/**
 * Created by leandro on 10/09/15.
 */
public class ImportDataTask {
    private static final String TAG = "ImportDataTask";

    public interface Listener {
        void update(boolean success);
    }

    private RequestQueue mQueue;
    private Listener listener;

    public ImportDataTask(Listener listener) {
        mQueue = Volley.newRequestQueue(App.getContext());
        this.listener = listener;
    }

    /**
     * Sincroniza os dados com servidor e ao final notifica o listener registrado
     */
    public void sync() {

        if (!InternetCheck.isConnected(App.getContext()))
            listener.update(false);
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
                            listener.update(true);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.v(TAG, error.toString());
                            listener.update(false);
                        }
                    });

            mQueue.add(request);
            mQueue.start();
        }
    }
}
