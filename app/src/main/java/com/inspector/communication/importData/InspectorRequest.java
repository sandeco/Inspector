package com.inspector.communication.importData;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Requisi&ccedil;&atilde;o pr&oacute;pria do Inspector. Estende a requisi&ccedil;&atilde;o padr&atilde;o do Volley.<br>
 * Criar uma requisi&ccedil;&atilde;o Volley a partir de um objeto ObjectRequest. Recebe a
 * refer&ecirc;ncia para duas callbacks, {@code Response.Listener<List<T>>} para sucesso
 * e {@code Response.ErrorListener} em caso de erro.
 */
public class InspectorRequest<T extends Serializable> extends Request<List<T>> {

    private Response.Listener<List<T>> mListener;
    private Map<String, String> mHeaders;
    private Class<T> mClazz;
    private ObjectMapper mMapper;
    private ObjectRequest<T> mObjectRequest;

    public InspectorRequest(ObjectRequest<T> objectRequest, Response.Listener<List<T>> listener, Response.ErrorListener errorListener) {
        super(objectRequest.getMethod(), objectRequest.getUrl(), errorListener);

        this.mListener = listener;
        this.mHeaders = objectRequest.getHeaders();
        this.mClazz = objectRequest.getClazz();
        this.mMapper = new ObjectMapper();
        this.mObjectRequest = objectRequest;

    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders != null ? mHeaders : super.getHeaders();
    }

    @Override
    protected Response<List<T>> parseNetworkResponse(NetworkResponse response) {
        try {
            final String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            List<T> list = getList(jsonString);

            return Response.success(list, HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        } catch (JsonParseException e) {
            return Response.error(new ParseError(e));
        } catch (IOException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(List<T> response) {
        mListener.onResponse(response);
    }

    @Override
    public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
        return super.setRetryPolicy(retryPolicy);
    }

    /**
     * Faz o parse do json para um ArrayList usando o Jackson
     * @param json String com o json array
     * @return Lista de T a partir do json passado
     * @throws IOException
     */
    private List<T> getList(String json) throws IOException {

        List<T> list = mMapper.readValue(json,
                mMapper.getTypeFactory().constructParametrizedType(ArrayList.class, List.class, mClazz));

        return list;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            return mMapper.writeValueAsString(mObjectRequest.getObjects()).getBytes();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return super.getBody();
        }
    }
}
