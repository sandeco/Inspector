package com.inspector.persistencia.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import com.inspector.model.M;
import com.inspector.model.Participante;
import com.inspector.persistencia.dao.ParticipanteDAO;

import java.sql.Timestamp;
import java.util.List;

public class ParticipanteSqliteDAO extends GenericSqliteDAO<Participante, Integer> implements ParticipanteDAO {
    @Override
    public List<Participante> listAll() {
        return null;
    }

    @Override
    public Participante findById(int id) {
        String selection = M.Participante.ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};

        Cursor cursor = getDbReadable().query(M.Participante.ENTITY_NAME, M.Participante.FIELDS,
                selection, selectionArgs, null, null, null);

        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        Participante p = new Participante();
        p.setId(cursor.getInt(cursor.getColumnIndex(M.Participante.ID)));
        p.setDataAlteracao(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(M.Participante.DATA_ALTERACAO))));
        p.setCpf(cursor.getString(cursor.getColumnIndex(M.Participante.CPF)));
        p.setNome(cursor.getString(cursor.getColumnIndex(M.Participante.NOME)));

        cursor.close();
        return p;
    }

    @Override
    public Participante create(Participante entity) {

        ContentValues values = new ContentValues();

        values.put(M.Participante.ID, entity.getId());
        values.put(M.Participante.CPF, entity.getCpf());
        values.put(M.Participante.NOME, entity.getNome());
        values.put(M.Participante.DATA_ALTERACAO, entity.getDataAlteracao().toString());

        long retorno = getDbWriteble().insert(M.Participante.ENTITY_NAME, null, values);

        return (retorno != -1) ? entity : null;
    }

    @Override
    public Participante update(Participante entity) {
        return null;
    }

    @Override
    public void delete(Participante entity) {

    }
}
