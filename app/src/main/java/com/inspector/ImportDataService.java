package com.inspector;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ImportDataService extends Service {
    public ImportDataService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //TODO: iniciar a verificação de dados novos no servidor aqui...

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        //nao iremos permitir bindService
        return null;
    }
}
