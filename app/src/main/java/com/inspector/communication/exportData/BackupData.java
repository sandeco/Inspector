package com.inspector.communication.exportData;

import android.os.Environment;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspector.communication.importData.ObjectRequest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe que irá fazer o backup dos dados a serem exportados em um arquivo texto.
 */
public class BackupData {

    private ObjectMapper mMapper;

    public void backupData(@NonNull ObjectRequest request) throws Exception, IOException {

        if (request.getObjects() == null) {
            throw new NullPointerException("Objects in ObjectRequest are null");
        }

        File folder = new File(Environment.getExternalStorageDirectory() + "/Inspector");

        boolean success = true;

        if (!folder.exists()) {
            success = folder.mkdir();
        }

        if (success) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());

            String nomeArquivo = "InspectorParticipacoes_"+ date + ".txt";
            File file = new File(folder, nomeArquivo);
            file.createNewFile();

            mMapper = new ObjectMapper();

            String content = mMapper.writeValueAsString(request.getObjects());

            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();

        } else {
            throw new Exception("Backup file isn't created");
        }
    }
}
