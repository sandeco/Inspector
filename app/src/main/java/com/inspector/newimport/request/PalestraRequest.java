package com.inspector.newimport.request;

import com.android.volley.Response;
import com.inspector.modelcom.PalestraCom;

import java.util.List;

public class PalestraRequest extends GenericRequest<PalestraCom> {
    public PalestraRequest(Response.Listener<List<PalestraCom>> listener, Response.ErrorListener errorListener) {
        super("palestra", PalestraCom.class, listener, errorListener);
    }
}
