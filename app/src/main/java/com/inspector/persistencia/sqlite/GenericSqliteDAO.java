package com.inspector.persistencia.sqlite;

import android.database.sqlite.SQLiteDatabase;

import com.inspector.persistencia.dao.GenericDAO;
import com.inspector.util.App;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * Created by sanderson on 21/08/2015.
 */
public abstract class GenericSqliteDAO<T, ID extends Serializable>  implements GenericDAO<T, ID> {

    private DatabaseHelper helper;
    private SQLiteDatabase db;

    Class<T> classePersistente;

    public GenericSqliteDAO() {
        this.classePersistente = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.helper = new DatabaseHelper(App.getContext());
    }

    public DatabaseHelper getHelper() {
        return helper;
    }

    public void setHelper(DatabaseHelper helper) {
        this.helper = helper;
    }

    public SQLiteDatabase getDbWriteble() {
        if (db == null)
            db = helper.getWritableDatabase();

        return db;
    }

    public SQLiteDatabase getDbReadable(){
        if (db == null)
            db = helper.getReadableDatabase();

        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    public void close() {
        helper.close();
        helper = null;
    }
}
