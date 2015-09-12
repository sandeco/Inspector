package com.inspector.newimport.request;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.inspector.R;
import com.inspector.util.App;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class GenericRequest<T> extends JsonRequest<List<T>> {

    private Gson mGson;
    private static final String BASE_URL = App.getPreferences().getString(
            App.getContext().getString(R.string.pref_url_key),
            App.getContext().getString(R.string.pref_url_default));

    public GenericRequest(String resourceName, Response.Listener<List<T>> listener, Response.ErrorListener errorListener) {
        super(Method.GET, BASE_URL+resourceName , null, listener, errorListener);

        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });

        mGson = builder.create();
    }

    @Override
    protected Response<List<T>> parseNetworkResponse(NetworkResponse response) {
        try {
            final String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            Type listType = new TypeToken<List<T>>(){}.getType();
            List<T> list = mGson.fromJson(jsonString, listType);

            return Response.success(list, HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        } catch (JsonParseException e) {
            return Response.error(new ParseError(e));
        }
    }
}