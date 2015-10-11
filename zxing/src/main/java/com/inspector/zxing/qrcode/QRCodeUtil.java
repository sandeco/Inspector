package com.inspector.zxing.qrcode;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton responsável por iniciar leituras de QRCodes.<br>
 * Ele permite que qualquer classe que tenha acesso ao {@code Context} da aplicação
 * possa ler um QRCode, bastando se registrar como listener usando {@code registerListener()}
 */
public class QRCodeUtil {

    private static QRCodeUtil singleton;
    private List<Listener> mListeners;

    private QRCodeUtil() {}

    public static QRCodeUtil getInstance() {
        if (singleton == null) {
            singleton = new QRCodeUtil();
            singleton.mListeners = new ArrayList<>();
        }

        return singleton;
    }

    /* Listeners */
    public interface Listener {
        void onResult(String qrcodeResult);

    }

    public void registerListener(Listener l) {
        mListeners.add(l);
    }

    public void unregisterListener(Listener l) {
        mListeners.remove(l);
    }

    public void read(Context context) {

        //iniciar uma activity que vai iniciar o leitor de QRCode

        Intent intent = new Intent(context, QRCodeReaderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        //ela irá retornar o resultado, chamando o método response
    }

    public void response(String qrcodeText) {
        for (Listener l : mListeners)
            l.onResult(qrcodeText != null ? qrcodeText : "");
    }
}
