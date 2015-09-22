package com.inspector.newimport;

import android.util.Log;

import com.inspector.model.Palestra;
import com.inspector.newimport.request.ObjectRequest;

import java.util.List;

public class ProxyRest {

    public interface Listener {
        void onError(Exception e);
        void onSuccess();
    }

    private Listener mListener;

    public ProxyRest(Listener listener) {
        this.mListener = listener;
    }

    public void sync(List<ObjectRequest> requisicoes) {

        //1 passo: baixar os dados do servidor

        try {

            DownloadRequests downloadRequests = new DownloadRequests();
            requisicoes = downloadRequests.download(requisicoes);

        } catch (Exception e) {
            mListener.onError(e);
        }


        for (ObjectRequest r : requisicoes) {

            if (r.getObjects().get(0) instanceof Palestra) {
                Log.v("ProxyRest", r.getObjects().get(0).toString());
            }

            Log.v("ProxyRest", r.getUrl());
        }

        mListener.onSuccess();
    }
}
