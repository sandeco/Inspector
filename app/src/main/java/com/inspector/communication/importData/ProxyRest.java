package com.inspector.communication.importData;

import com.android.volley.Request;
import com.inspector.R;
import com.inspector.communication.modelcom.EventoCom;
import com.inspector.communication.modelcom.InscricaoCom;
import com.inspector.communication.modelcom.MinistracaoCom;
import com.inspector.communication.modelcom.PalestraCom;
import com.inspector.communication.modelcom.PalestranteCom;
import com.inspector.communication.modelcom.ParticipanteCom;
import com.inspector.model.Comunicacao;
import com.inspector.persistencia.ComunicacaoSPDao;
import com.inspector.persistencia.dao.ComunicacaoDAO;
import com.inspector.util.App;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProxyRest {

    private String mBaseUrl;

    public interface Listener {
        void onError(Exception e);
        void onSuccess();
    }

    private List<ObjectRequest> mRequisicoes;
    private Comunicacao mComunicacao;
    private ComunicacaoDAO mComunicacaoDAO;
    private Listener mListener;

    public ProxyRest() {}

    public void registerListener(Listener listener) {
        this.mListener = listener;
    }

    public void sync() {
        createRequests();
        downloadAndPersistRequests();
    }

    private void downloadAndPersistRequests() {
        try {

            DownloadRequests downloadRequests = new DownloadRequests();
            mRequisicoes = downloadRequests.download(mRequisicoes);

            //TODO: pegar data atual do servidor

            PersistData persistData = new PersistData();
            persistData.persist(mRequisicoes);

            mListener.onSuccess();

        } catch (Exception e) {
            mListener.onError(e);
            e.printStackTrace();
        }
    }

    /**
     * Cria e adiciona as requisições a lista de requisições
     */
    private void createRequests() {
        mRequisicoes = new ArrayList<>();

        //pegando a url das preferencias do usuario
        mBaseUrl = App.getPreferences().getString(
                App.getContext().getString(R.string.pref_url_key), //key
                App.getContext().getString(R.string.pref_url_default) //default value
        );

        mComunicacaoDAO = new ComunicacaoSPDao();
        mComunicacao = mComunicacaoDAO.get();

        mRequisicoes.add(createObjectRequest("evento", EventoCom.class));
        mRequisicoes.add(createObjectRequest("palestra", PalestraCom.class));
        mRequisicoes.add(createObjectRequest("ministracao", MinistracaoCom.class));
        mRequisicoes.add(createObjectRequest("inscricao", InscricaoCom.class));
        mRequisicoes.add(createObjectRequest("palestrante", PalestranteCom.class));
        mRequisicoes.add(createObjectRequest("participante", ParticipanteCom.class));
    }

    private ObjectRequest createObjectRequest(String entityName, Class clazz) {
        return new ObjectRequest<>(
                clazz, Request.Method.GET, getUrl(mBaseUrl, entityName, mComunicacao.getLast_update()), null);
    }

    private String getUrl(String baseurl, String entityName, Timestamp timestamp) {
        if (!baseurl.endsWith("/"))
            baseurl = baseurl.concat("/");

        String url = baseurl+entityName+"/dataAlteracao/"+timestamp;
        url = url.replaceAll(" ", "%20");
        return url;
    }
}
