package com.inspector.persistencia.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.annotation.NonNull;

import com.inspector.model.Inscricao;
import com.inspector.model.M;
import com.inspector.model.Ministracao;
import com.inspector.model.Palestra;
import com.inspector.persistencia.dao.InscricaoDAO;
import com.inspector.persistencia.dao.MinistracaoDAO;
import com.inspector.persistencia.dao.PalestraDAO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PalestraSqliteDAO extends GenericSqliteDAO<Palestra, Integer> implements PalestraDAO {
    @Override
    public List<Palestra> listAll() {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(M.Palestra.ENTITY_NAME);

        Cursor cursor = builder.query(getDbReadable(), null, null, null, null, null, null);

        List<Palestra> palestras = new ArrayList<>();

        while (cursor.moveToNext()) {
            Palestra p = createPalestraFromCursor(cursor);
            palestras.add(p);
        }

        cursor.close();
        return palestras;
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

        Palestra p = createPalestraFromCursor(cursor);

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

        MinistracaoDAO ministracaoDAO = new MinistracaoSqliteDAO();

        if (entity.getMinistracoes() != null)
            for (Ministracao m : entity.getMinistracoes())
                ministracaoDAO.create(m);

        InscricaoDAO inscricaoDAO = new InscricaoSqliteDAO();

        if (entity.getInscricoes() != null)
            for (Inscricao i : entity.getInscricoes())
                inscricaoDAO.create(i);

        inscricaoDAO.close();
        ministracaoDAO.close();

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

    @NonNull
    private Palestra createPalestraFromCursor(Cursor cursor) {
        Palestra p = new Palestra();
        p.setDataAlteracao(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(M.Palestra.DATA_ALTERACAO))));
        p.setId(cursor.getInt(cursor.getColumnIndex(M.Palestra.ID)));
        p.setNome(cursor.getString(cursor.getColumnIndex(M.Palestra.NOME)));

        return p;
    }
}
