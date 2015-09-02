package com.inspector.persistencia.dao;

import com.inspector.model.Comunicacao;

/**
 * Created by leandro on 02/09/15.
 */
public interface ComunicacaoDAO {

    void set(Comunicacao comunicacao);
    Comunicacao get();
}
