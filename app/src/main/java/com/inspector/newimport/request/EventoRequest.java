package com.inspector.newimport.request;

import com.android.volley.Response;
import com.inspector.modelcom.EventoCom;

import java.util.List;

/**
 * Created by leandro on 11/09/15.
 */
public class EventoRequest extends GenericRequest<EventoCom> {

    public EventoRequest(Response.Listener<List<EventoCom>> listener, Response.ErrorListener errorListener) {
        super("evento", EventoCom.class, listener, errorListener);
    }
}
