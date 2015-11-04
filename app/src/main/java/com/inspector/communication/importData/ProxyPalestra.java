package com.inspector.communication.importData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspector.R;
import com.inspector.communication.modelcom.PalestraCom;
import com.inspector.model.Palestra;
import com.inspector.persistencia.dao.PalestraDAO;
import com.inspector.persistencia.sqlite.PalestraSqliteDAO;
import com.inspector.util.App;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Baixa e persiste uma palestra em especifico
 */
public class ProxyPalestra {
    public interface ProxyPalestraListener {
        void onProxyPalestraError(Exception e);
        void onProxyPalestraSuccess();
    }
    private ProxyPalestraListener mListener;
    public void registerListener(ProxyPalestraListener listener) {
        this.mListener = listener;
    }

    private String mBaseUrl;
    private RequestQueue mQueue;
    private PalestraCom mPalestra;
    private PalestraDAO mPalestraDAO;

    public void sync(Palestra palestra) {
        try {
            downloadAndPersist(palestra.getId());
        } catch (Exception e) {
            notifyError(e);
        }
    }

    private void downloadAndPersist(int idPalestra) {
        mQueue = Volley.newRequestQueue(App.getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                getUrl(idPalestra),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        persist(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        notifyError(error);
                    }
                }
        );

        mQueue.add(jsonObjectRequest);
    }

    private void persist(JSONObject response) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            mPalestra = mapper.readValue(response.toString(), PalestraCom.class);

            mPalestraDAO = new PalestraSqliteDAO();
            mPalestraDAO.create(mPalestra);
            mPalestraDAO.close();

            mListener.onProxyPalestraSuccess();
        } catch (IOException e) {
            notifyError(e);
        }
    }

    private String getUrl(int palestraId) {
        mBaseUrl = App.getPreferences().getString(
                App.getContext().getString(R.string.pref_url_key), //key
                App.getContext().getString(R.string.pref_url_default) //default value
        );

        if (!mBaseUrl.endsWith("/"))
            mBaseUrl = mBaseUrl.concat("/");

        String url = mBaseUrl+"palestra/id/"+palestraId;
        url = url.replaceAll(" ", "%20");
        return url;
    }

    /**
     * Procedimento de notificação de erro para o listener
     * @param e Exceção lançada
     */
    private void notifyError(Exception e) {
        e.printStackTrace();
        mListener.onProxyPalestraError(e);
    }
}

