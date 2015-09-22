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

        //1o passo: baixar os dados do servidor
        try {

            DownloadRequests downloadRequests = new DownloadRequests();
            requisicoes = downloadRequests.download(requisicoes);

            for (ObjectRequest r : requisicoes) {

                //testando como distinguir as listas para colocar no banco
                if ((r.getObjects() != null) && (r.getObjects().size() > 0)) {
                    if ((r.getObjects().get(0) instanceof Palestra)) {
                        Log.v("ProxyRest", r.getObjects().get(0).toString());
                    }
                }

                Log.v("ProxyRest", r.getUrl());
            }

            mListener.onSuccess();

        } catch (Exception e) {
            mListener.onError(e);
            e.printStackTrace();
        }
    }
}
