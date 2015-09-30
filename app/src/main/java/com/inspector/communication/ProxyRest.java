package com.inspector.communication;

import com.android.volley.Request;
import com.inspector.R;
import com.inspector.model.Comunicacao;
import com.inspector.communication.modelcom.EventoCom;
import com.inspector.communication.modelcom.InscricaoCom;
import com.inspector.communication.modelcom.MinistracaoCom;
import com.inspector.communication.modelcom.PalestraCom;
import com.inspector.communication.modelcom.PalestranteCom;
import com.inspector.communication.modelcom.ParticipanteCom;
import com.inspector.persistencia.ComunicacaoSPDao;
import com.inspector.persistencia.dao.ComunicacaoDAO;
import com.inspector.util.App;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProxyRest {

    public interface Listener {
        void onError(Exception e);
        void onSuccess();
    }

    private Listener mListener;
    private ComunicacaoDAO comunicacaoDAO;

    public ProxyRest() {}

    public void registerListener(Listener listener) {
        this.mListener = listener;
    }

    public void sync() {

        List<ObjectRequest> requisicoes = new ArrayList<>();

        //pegando a url das preferencias do usuario
        String BASEURL = App.getPreferences().getString(
                App.getContext().getString(R.string.pref_url_key), //key
                App.getContext().getString(R.string.pref_url_default) //default value
            );

        comunicacaoDAO = new ComunicacaoSPDao();
        Comunicacao comunicacao = comunicacaoDAO.get();

        //criando os ObjectRequests que são representações de uma requisição
        //e são usados para armazenar o resultado delas
        ObjectRequest<EventoCom> eventoRequest = new ObjectRequest<>(
                EventoCom.class, Request.Method.GET, getUrl(BASEURL, "evento", comunicacao.getLast_update()), null);

        ObjectRequest<PalestraCom> palestraRequest = new ObjectRequest<>(
                PalestraCom.class, Request.Method.GET, getUrl(BASEURL, "palestra", comunicacao.getLast_update()), null);

        ObjectRequest<MinistracaoCom> ministracaoRequest = new ObjectRequest<>(
                MinistracaoCom.class, Request.Method.GET, getUrl(BASEURL, "ministracao", comunicacao.getLast_update()), null);

        ObjectRequest<InscricaoCom> inscricaoRequest = new ObjectRequest<>(
                InscricaoCom.class, Request.Method.GET, getUrl(BASEURL, "inscricao", comunicacao.getLast_update()), null);

        ObjectRequest<PalestranteCom> palestranteRequest = new ObjectRequest<>(
                PalestranteCom.class, Request.Method.GET, getUrl(BASEURL, "palestrante", comunicacao.getLast_update()), null);

        ObjectRequest<ParticipanteCom> participanteRequest = new ObjectRequest<>(
                ParticipanteCom.class, Request.Method.GET, getUrl(BASEURL, "participante", comunicacao.getLast_update()), null);

        //adicionando os ObjectRequests a uma lista para serem processadas
        requisicoes.add(eventoRequest);
        requisicoes.add(palestraRequest);
        requisicoes.add(ministracaoRequest);
        requisicoes.add(inscricaoRequest);
        requisicoes.add(palestranteRequest);
        requisicoes.add(participanteRequest);

        try {

            DownloadRequests downloadRequests = new DownloadRequests();
            requisicoes = downloadRequests.download(requisicoes);

            //TODO: pegar data atual do servidor

            PersistData persistData = new PersistData();
            persistData.persist(requisicoes);

            mListener.onSuccess();

        } catch (Exception e) {
            mListener.onError(e);
            e.printStackTrace();
        }
    }

    private String getUrl(String baseurl, String entityName, Timestamp timestamp) {
        if (!baseurl.endsWith("/"))
            baseurl = baseurl.concat("/");

        String url = baseurl+entityName+"/dataAlteracao/"+timestamp;
        url = url.replaceAll(" ", "%20");
        return url;
    }
}
