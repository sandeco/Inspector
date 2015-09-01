package com.inspector.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by sanderson on 01/09/2015.
 */
public class Comunicacao  implements Serializable {

    private Timestamp last_update;
    private String token;

    public Comunicacao() {
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
