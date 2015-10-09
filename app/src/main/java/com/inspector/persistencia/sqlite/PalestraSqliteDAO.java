package com.inspector.persistencia.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;

import com.inspector.model.M;
import com.inspector.model.Palestra;
import com.inspector.persistencia.dao.PalestraDAO;

import java.sql.Timestamp;
import java.util.List;

public class PalestraSqliteDAO extends GenericSqliteDAO<Palestra, Integer> implements PalestraDAO {
    @Override
    public List<Palestra> listAll() {
        return null;
    }

    @Override
    public Palestra findById(int id) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        builder.setTables(M.Palestra.ENTITY_NAME);

        String selection = M.Palestra.ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = builder.query(getDbReadable(), null,
                selection, selectionArgs,
                null, null, null);

        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        Palestra p = new Palestra();
        p.setDataAlteracao(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(M.Palestra.DATA_ALTERACAO))));
        p.setId(cursor.getInt(cursor.getColumnIndex(M.Palestra.ID)));
        p.setNome(cursor.getString(cursor.getColumnIndex(M.Palestra.NOME)));

        cursor.close();
        return p;
    }

    @Override
    public Palestra create(Palestra entity) {
        ContentValues values = new ContentValues();

        values.put(M.Palestra.ID, entity.getId());
        values.put(M.Palestra.NOME, entity.getNome());
        values.put(M.Palestra.EVENTO_ID, entity.getEvento().getId());
        values.put(M.Palestra.DATA_ALTERACAO, entity.getDataAlteracao().toString());

        long retorno = getDbWriteble().insert(M.Palestra.ENTITY_NAME, null, values);

        if (retorno != -1)
            return entity;
        else
            return null;
    }

    @Override
    public Palestra update(Palestra entity) {
        return null;
    }

    @Override
    public void delete(Palestra entity) {

    }
}
