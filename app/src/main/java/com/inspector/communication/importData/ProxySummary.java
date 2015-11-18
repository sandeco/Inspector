package com.inspector.communication.importData;

import com.android.volley.Request;
import com.inspector.R;
import com.inspector.communication.modelcom.AtividadeCom;
import com.inspector.model.Comunicacao;
import com.inspector.persistencia.ComunicacaoSPDao;
import com.inspector.persistencia.dao.ComunicacaoDAO;
import com.inspector.util.App;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProxySummary {
    private String mBaseUrl;

    public interface ProxySummaryListener {
        void onProxySummaryError(Exception e);
        void onProxySummarySuccess();
    }

    private List<ObjectRequest> mRequisicoes;
    private Comunicacao mComunicacao;
    private ComunicacaoDAO mComunicacaoDAO;
    private ProxySummaryListener mListener;

    public ProxySummary() {}

    public void registerListener(ProxySummaryListener listener) {
        this.mListener = listener;
    }

    public void sync() {
        createRequests();

        try {
            DownloadRequests downloadRequests = new DownloadRequests();
            downloadRequests.download(mRequisicoes);

            PersistData persistData = new PersistData();
            persistData.persist(mRequisicoes);

            mListener.onProxySummarySuccess();

        } catch (Exception e) {
            e.printStackTrace();
            mListener.onProxySummaryError(e);
        }
    }

    private void createRequests() {
        mRequisicoes = new ArrayList<>();

        //pegando a url das preferencias do usuario
        mBaseUrl = App.getPreferences().getString(
                App.getContext().getString(R.string.pref_url_key), //key
                App.getContext().getString(R.string.pref_url_default) //default value
        );

        mComunicacaoDAO = new ComunicacaoSPDao();
        mComunicacao = mComunicacaoDAO.get();

        mRequisicoes.add(createObjectRequest("palestra", AtividadeCom.class));
    }

    private ObjectRequest createObjectRequest(String entity, Class clazz) {
        return
            new ObjectRequest(clazz, Request.Method.GET, getUrl(mBaseUrl, entity, mComunicacao.getLast_update()));
    }

    private String getUrl(String baseurl, String entityName, Timestamp timestamp) {
        if (!baseurl.endsWith("/"))
            baseurl = baseurl.concat("/");

        String url = baseurl+entityName+"/dataAlteracao/"+timestamp;
        url = url.replaceAll(" ", "%20");
        return url;
    }
}
