package com.inspector.dataimport;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.inspector.dao.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

public class ImportadoraDados implements Response.ErrorListener, Response.Listener<JSONObject> {

    private RequestQueue requestQueue;
    private static final String REQUEST_TAG = "IMPORTACAO_TAG";
    private static final String LOG_TAG = "ImportadoraDados";

    private DatabaseHelper helper;
    private SQLiteDatabase db;

    private ImportadoraListener listener;

    public ImportadoraDados(Activity activity, ImportadoraListener listener) {
        helper = new DatabaseHelper(activity);
        requestQueue = Volley.newRequestQueue(activity);
        this.listener = listener;
    }

    public void iniciar() {
        final String palestraURL =
                "https://raw.githubusercontent.com/leandrovianna/congresso-presenca/master/etc/json/palestra.json";
        final String ministracaoURL =
                "https://raw.githubusercontent.com/leandrovianna/congresso-presenca/master/etc/json/ministracao.json";
        final String participanteURL =
                "https://raw.githubusercontent.com/leandrovianna/congresso-presenca/master/etc/json/participante.json";
        final String participacaoURL =
                "https://raw.githubusercontent.com/leandrovianna/congresso-presenca/master/etc/json/participacao.json";


        /*
        JsonObjectRequest palestraRequest = new JsonObjectRequest(Request.Method.GET, palestraURL, null, this, this);






        JsonObjectRequest ministracaoRequest = new JsonObjectRequest(Request.Method.GET, ministracaoURL, null, this, this);

        JsonObjectRequest participanteRequest = new JsonObjectRequest(Request.Method.GET, participanteURL, null, this, this);

        JsonObjectRequest participacaoRequest = new JsonObjectRequest(Request.Method.GET, participacaoURL, null, this, this);

        palestraRequest.setTag(REQUEST_TAG);
        ministracaoRequest.setTag(REQUEST_TAG);
        participanteRequest.setTag(REQUEST_TAG);
        participacaoRequest.setTag(REQUEST_TAG);

        Log.i(LOG_TAG, "Requests criadas");

        requestQueue.add(palestraRequest);
        requestQueue.add(ministracaoRequest);
        requestQueue.add(participanteRequest);
        requestQueue.add(participacaoRequest);

        Log.i(LOG_TAG, "Fila de Requisições criada");

        */
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        requestQueue.cancelAll(REQUEST_TAG);
        listener.updateImportacao(false);
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.i(LOG_TAG, "Terminando de baixar JSON");
        gravarDados(response); //mandando gravar os dados deste Json no banco
    }

    /** Grava os dados provinientes do JSON no banco **/
    private void gravarDados(JSONObject json) {
        Log.i(LOG_TAG, "Iniciando gravação dos dados no banco");

        try {
            db = helper.getWritableDatabase();
            db.beginTransaction(); //inicia a transação

            if (json.has("palestra"))
                gravarPalestra(db, json.getJSONArray("palestra"));
            else if (json.has("ministracao"))
                gravarMinistracao(db, json.getJSONArray("ministracao"));
            else if (json.has("participante"))
                gravarParticipante(db, json.getJSONArray("participante"));
            else if (json.has("participacao"))
                gravarParticipacao(db, json.getJSONArray("participacao"));

            db.setTransactionSuccessful(); //commit da transação
            Log.i(LOG_TAG, "Transacao bem sucedida.");
            listener.updateImportacao(true);

        } catch (Exception e) {
            //Um exceção foi disparada por alguma operação do banco
            Log.e(LOG_TAG, "ERRO: Transacao mal sucedida");
            listener.updateImportacao(false);
        } finally {
            //independente se a transação teve commit ou não, é necessário terminá-la.
            db.endTransaction();
            db.close();
        }
    }

    private void gravarPalestra(SQLiteDatabase db, JSONArray palestra) throws JSONException, SQLException {
        ContentValues values = new ContentValues();

        db.execSQL("DELETE FROM palestra;");

        for (int i = 0; i < palestra.length(); i++) {
            values.put("_id", palestra.getJSONObject(i).getInt("_id"));
            values.put("nome", palestra.getJSONObject(i).getString("nome"));

            db.insertOrThrow("palestra", null, values);
            values.clear();
        }
    }

    private void gravarMinistracao(SQLiteDatabase db, JSONArray ministracao) throws JSONException, SQLException {
        ContentValues values = new ContentValues();

        db.execSQL("DELETE FROM ministracao");

        for (int i = 0; i < ministracao.length(); i++) {
            values.put("_id", ministracao.getJSONObject(i).getInt("_id"));
            values.put("palestra_id", ministracao.getJSONObject(i).getInt("palestra_id"));
            values.put("data", ministracao.getJSONObject(i).getString("data"));

            db.insertOrThrow("ministracao", null, values);
            values.clear();
        }
    }

    private void gravarParticipante(SQLiteDatabase db, JSONArray participante) throws JSONException, SQLException {
        ContentValues values = new ContentValues();

        db.execSQL("DELETE FROM participante");

        for (int i = 0; i < participante.length(); i++) {
            values.put("inscricao", participante.getJSONObject(i).getInt("inscricao"));
            values.put("nome", participante.getJSONObject(i).getString("nome"));

            db.insertOrThrow("participante", null, values);
            values.clear();
        }

    }

    private void gravarParticipacao(SQLiteDatabase db, JSONArray participacao) throws JSONException, SQLException {
        ContentValues values = new ContentValues();

        db.execSQL("DELETE FROM participacao WHERE presenca = 0");

        for (int i = 0; i < participacao.length(); i++) {
            values.put("ministracao_id", participacao.getJSONObject(i).getInt("ministracao_id"));
            values.put("participante_inscricao", participacao.getJSONObject(i).getInt("participante_inscricao"));
            values.put("presenca", false);
            values.put("updated", false);

            db.insertOrThrow("participacao", null, values);
            values.clear();
        }

    }
}
