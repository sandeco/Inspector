package com.inspector.persistencia;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.inspector.model.Comunicacao;
import com.inspector.persistencia.dao.ComunicacaoDAO;
import com.inspector.util.App;

import java.sql.Timestamp;

/**
 * Created by leandro on 02/09/15.
 */
public class ComunicacaoSPDao implements ComunicacaoDAO {

    public static final String COMUNICACAO_LAST_UPDATE = "Comunicacao.last_update";
    public static final String COMUNICACAO_TOKEN = "Comunicacao.token";
    private SharedPreferences sharedPreferences;

    private SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null)
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getContext());

        return sharedPreferences;
    }

    @Override
    public void set(Comunicacao comunicacao) {

        if (comunicacao.getToken().isEmpty())
            throw new IllegalArgumentException("Token is empty");

        getSharedPreferences().edit()
                .putString(COMUNICACAO_LAST_UPDATE, comunicacao.getLast_update().toString());
        getSharedPreferences().edit()
                .putString(COMUNICACAO_TOKEN, comunicacao.getToken());
    }

    @Override
    public Comunicacao get() {

        Comunicacao c = new Comunicacao();
        Timestamp lastUpdate = Timestamp.valueOf(getSharedPreferences().getString(COMUNICACAO_LAST_UPDATE, "0"));

        c.setLast_update(lastUpdate);
        c.setToken(getSharedPreferences().getString(COMUNICACAO_TOKEN, ""));

        return c;
    }
}
