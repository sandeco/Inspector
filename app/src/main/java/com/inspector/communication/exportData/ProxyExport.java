package com.inspector.communication.exportData;

import com.android.volley.Request;
import com.inspector.R;
import com.inspector.communication.importData.ObjectRequest;
import com.inspector.communication.modelcom.ParticipacaoCom;
import com.inspector.persistencia.dao.ParticipacaoDAO;
import com.inspector.persistencia.sqlite.ParticipacaoSqliteDAO;
import com.inspector.util.App;

import java.util.List;

public class ProxyExport {

    private ParticipacaoDAO dao;

    public interface Listener {
        void onError(Exception e);
        void onSuccess();
    }

    private Listener mListener;

    public ProxyExport(Listener listener) {
        this.mListener = listener;

        dao = new ParticipacaoSqliteDAO();
    }

    public void sync() {

        try {
            List<ParticipacaoCom> participacoes = dao.listToExport();

            ObjectRequest<ParticipacaoCom> request = new ObjectRequest<>();
            request.setObjects(participacoes);
            request.setClazz(ParticipacaoCom.class);
            request.setMethod(Request.Method.POST);
            request.setUrl(getUrl("participacao"));

            BackupData backupData = new BackupData();
            backupData.backupData(request);

            ExportData exportData = new ExportData();
            exportData.export(request);

            mListener.onSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            mListener.onError(e);
        }

    }

    private String getUrl(String entityName) {
        String baseurl = App.getPreferences().getString(
                App.getContext().getString(R.string.pref_url_key), //key
                App.getContext().getString(R.string.pref_url_default) //default value
        );

        if (!baseurl.endsWith("/"))
            baseurl = baseurl.concat("/");

        String url = baseurl+entityName;
        url = url.replaceAll(" ", "%20");
        return url;
    }
}
