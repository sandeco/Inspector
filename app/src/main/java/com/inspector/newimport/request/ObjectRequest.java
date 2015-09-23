package com.inspector.newimport.request;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ObjectRequest<T extends Serializable> {

    private Class<T> clazz;
    private int method;
    private String url;
    private Map<String, String> headers;
    private List<T> objects;

    public List<T> getObjects() {
        return objects;
    }

    public void setObjects(List<T> objects) {
        this.objects = objects;
    }

    public ObjectRequest() {}

    public ObjectRequest(Class<T> clazz, int method, String url, Map<String, String> headers) {
        this.clazz = clazz;
        this.method = method;
        this.url = url;
        this.headers = headers;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public String toString() {
        return "ObjectRequest{" +
                "clazz=" + clazz +
                ", method=" + method +
                ", url='" + url + '\'' +
                ", headers=" + headers +
                ", objects=" + objects +
                '}';
    }
}
