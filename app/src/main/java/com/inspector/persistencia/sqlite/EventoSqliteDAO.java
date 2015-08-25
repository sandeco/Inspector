package com.inspector.persistencia.sqlite;

import android.database.Cursor;

import com.inspector.model.Evento;
import com.inspector.model.M;
import com.inspector.persistencia.dao.EventoDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanderson on 21/08/2015.
 */
public class EventoSqliteDAO extends GenericSqliteDAO<Evento, Integer> implements EventoDAO {


    @Override
    public List<Evento> listAll() {
        String sql = "SELECT * from " + M.Evento.ENTITY_NAME;
        Cursor cursor = getDbReadable().rawQuery(sql,null);

        List<Evento> eventos = new ArrayList<Evento>();


		while (cursor.moveToNext()) {
            Evento e = new Evento(



            );
        }

        return eventos;
    }


    @Override
    public Evento findById(int id) {
        return null;
    }

    @Override
    public Evento create(Evento entity) {
        return null;
    }

    @Override
    public Evento update(Evento entity) {
        return null;
    }

    @Override
    public void delete(Evento entity) {

    }
}
