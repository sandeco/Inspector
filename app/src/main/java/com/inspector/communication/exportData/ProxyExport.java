package com.inspector.communication.exportData;

import com.inspector.communication.importData.ObjectRequest;
import com.inspector.communication.modelcom.ParticipacaoCom;

import java.util.ArrayList;

public class ProxyExport {

    public interface Listener {
        void onError(Exception e);
        void onSuccess();
    }

    private Listener mListener;

    public ProxyExport(Listener listener) {
        this.mListener = listener;
    }

    public void sync() {

        try {
            BackupData backupData = new BackupData();

            ObjectRequest<ParticipacaoCom> request = new ObjectRequest<>();
            request.setObjects(new ArrayList<ParticipacaoCom>());
            backupData.backupData(request);

            mListener.onSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            mListener.onError(e);
        }

    }
}
