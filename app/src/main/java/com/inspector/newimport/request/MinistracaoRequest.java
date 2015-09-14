package com.inspector.newimport.request;

import com.android.volley.Response;
import com.inspector.modelcom.MinistracaoCom;

import java.util.List;

/**
 * Created by leandro on 13/09/15.
 */
public class MinistracaoRequest extends GenericRequest<MinistracaoCom> {

    public MinistracaoRequest(Response.Listener<List<MinistracaoCom>> listener, Response.ErrorListener errorListener) {
        super("ministracao", MinistracaoCom.class, listener, errorListener);
    }
}
