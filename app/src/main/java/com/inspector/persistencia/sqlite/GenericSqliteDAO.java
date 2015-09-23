package com.inspector.persistencia.sqlite;

import android.database.sqlite.SQLiteDatabase;

import com.inspector.model.Evento;
import com.inspector.model.Inscricao;
import com.inspector.model.Ministracao;
import com.inspector.model.Palestra;
import com.inspector.model.Palestrante;
import com.inspector.model.Participacao;
import com.inspector.model.Participante;
import com.inspector.persistencia.dao.GenericDAO;
import com.inspector.util.App;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

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

    public SQLiteDatabase getDbReadable() {
        if (db == null)
            db = helper.getReadableDatabase();

        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public void close() {
        if (db != null) {
            db.close();
        }

        helper.close();
        helper = null;
    }


    public static GenericDAO getGenericDAO(List<Serializable> list) {

        GenericDAO dao = null;
        Serializable object = list.get(0);

        if (object instanceof Palestra)
            dao = new PalestraSqliteDAO();
        else if (object instanceof Evento)
            dao = new EventoSqliteDAO();
        else if (object instanceof Ministracao)
            dao = new MinistracaoSqliteDAO();
        else if (object instanceof Inscricao)
            dao = new InscricaoSqliteDAO();
        else if (object instanceof Palestrante)
            dao = new PalestranteSqliteDAO();
        else if (object instanceof Participacao)
            dao = new ParticipacaoSqliteDAO();
        else if (object instanceof Participante)
            dao = new ParticipanteSqliteDAO();

        return dao;
    }
}
