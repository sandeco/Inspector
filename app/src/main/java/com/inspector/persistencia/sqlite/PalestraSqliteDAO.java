package com.inspector.persistencia.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.annotation.NonNull;

import com.inspector.model.Atividade;
import com.inspector.model.Inscricao;
import com.inspector.model.M;
import com.inspector.model.Ministracao;
import com.inspector.persistencia.dao.InscricaoDAO;
import com.inspector.persistencia.dao.MinistracaoDAO;
import com.inspector.persistencia.dao.PalestraDAO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PalestraSqliteDAO extends GenericSqliteDAO<Atividade, Integer> implements PalestraDAO {
    @Override
    public List<Atividade> listAll() {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(M.Palestra.ENTITY_NAME);

        Cursor cursor = builder.query(getDbReadable(), null, null, null, null, null, null);

        List<Atividade> atividades = new ArrayList<>();

        while (cursor.moveToNext()) {
            Atividade p = createPalestraFromCursor(cursor);
            atividades.add(p);
        }

        cursor.close();
        return atividades;
    }

    @Override
    public Atividade findById(int id) {
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

        Atividade p = createPalestraFromCursor(cursor);

        cursor.close();
        return p;
    }

    @Override
    public Atividade create(Atividade entity) {
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
    public Atividade update(Atividade entity) {
        return null;
    }

    @Override
    public void delete(Atividade entity) {

    }

    @NonNull
    private Atividade createPalestraFromCursor(Cursor cursor) {
        Atividade p = new Atividade();
        p.setDataAlteracao(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(M.Palestra.DATA_ALTERACAO))));
        p.setId(cursor.getInt(cursor.getColumnIndex(M.Palestra.ID)));
        p.setNome(cursor.getString(cursor.getColumnIndex(M.Palestra.NOME)));
        return p;
    }
}
