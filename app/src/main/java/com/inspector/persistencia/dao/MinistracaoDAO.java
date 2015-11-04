package com.inspector.persistencia.dao;

import com.inspector.model.Ministracao;
import com.inspector.model.Palestra;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by leandro on 28/08/15.
 */
public interface MinistracaoDAO extends GenericDAO<Ministracao, Integer> {

    List<Ministracao> listByDate(Timestamp dateMin, Timestamp dateMax);

    List<Ministracao> listByPalestra(Palestra palestra);
}
