package com.inspector.newimport.teste;

import com.android.volley.Request;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sanderson on 16/09/2015.
 */
public class Requisicao<T extends Serializable> {

    private String url;
    private List<T> entities;
    private int method;
    private Class<T> entity;

    public Requisicao() {
    }

    public Requisicao(String url, int method) {
        this.url = url;
        this.entities = entities;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<T> getEntities() {
        return entities;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    public Class<T> getEntity() {
        return entity;
    }

    public void setEntity(Class<T> entity) {
        this.entity = entity;
    }


    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }
}
