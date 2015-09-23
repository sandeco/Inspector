package com.inspector.newimport;

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

            PersistData persistData = new PersistData();
            persistData.persist(requisicoes);

            mListener.onSuccess();

        } catch (Exception e) {
            mListener.onError(e);
            e.printStackTrace();
        }
    }
}
