package com.inspector.persistencia.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sanderson on 21/08/2015.
 */
public interface GenericDAO<T, ID extends Serializable> {

    List<T> listAll();
    T findById(int id);
    T create(T entity);
    T update(T entity);
    void delete(T entity);

    void close();
}
