package com.inspector.newimport;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.inspector.modelcom.EventoCom;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.List;

/**
 * Created by leandro on 11/09/15.
 */
public class EventoRequest extends JsonRequest<List<EventoCom>> {

    private Gson gson;

    public EventoRequest(String url, Response.Listener<List<EventoCom>> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, null, listener, errorListener);

        GsonBuilder builder = new GsonBuilder();
        //TODO: acertar o formato de data correto para o gson ler
        builder.setDateFormat(DateFormat.MEDIUM);

        gson = builder.create();
    }

    @Override
    protected Response<List<EventoCom>> parseNetworkResponse(NetworkResponse response) {
        try {
            final String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            Type listType = new TypeToken<List<EventoCom>>(){}.getType();
            List<EventoCom> eventos = gson.fromJson(jsonString, listType);

            return Response.success(eventos, HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
}
