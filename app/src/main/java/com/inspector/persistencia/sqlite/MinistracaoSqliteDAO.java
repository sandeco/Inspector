package com.inspector.persistencia.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.annotation.NonNull;

import com.inspector.model.M;
import com.inspector.model.Ministracao;
import com.inspector.model.Atividade;
import com.inspector.persistencia.dao.MinistracaoDAO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MinistracaoSqliteDAO extends GenericSqliteDAO<Ministracao, Integer> implements MinistracaoDAO {

    @Override
    public List<Ministracao> listAll() {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        //selecionando tabelas palestra e ministracao
        builder.setTables(M.Ministracao.ENTITY_NAME + ", " + M.Palestra.ENTITY_NAME);

        Cursor cursor = builder.query(getDbReadable(), null,
                M.Palestra.ENTITY_NAME + "." + M.Palestra.ID + " = " + M.Ministracao.PALESTRA_ID,
                null, null, null, M.Palestra.NOME+" ASC");

        List<Ministracao> ministracoes = new ArrayList<>();

        while (cursor.moveToNext()) {
            Ministracao m = createMinistracaoFromCursor(cursor);
            ministracoes.add(m);
        }

        return ministracoes;
    }

    @NonNull
    private Ministracao createMinistracaoFromCursor(Cursor cursor) {
        Atividade p = new Atividade();
        p.setId(cursor.getInt(cursor.getColumnIndex(M.Ministracao.PALESTRA_ID)));
        p.setNome(cursor.getString(cursor.getColumnIndex(M.Palestra.NOME)));

        Ministracao m = new Ministracao();
        m.setId(cursor.getInt(cursor.getColumnIndex(M.Ministracao.ID)));
        m.setDiaHora(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(M.Ministracao.DIA_HORA))));
        m.setLocal(cursor.getString(cursor.getColumnIndex(M.Ministracao.LOCAL)));
        m.setAtividade(p);
        return m;
    }

    @Override
    public Ministracao findById(int id) {

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        builder.setTables(M.Ministracao.ENTITY_NAME + ", " + M.Palestra.ENTITY_NAME);

        String selection = M.Palestra.ENTITY_NAME + "." + M.Palestra.ID + " = " + M.Ministracao.PALESTRA_ID
                + " AND " + M.Ministracao.ENTITY_NAME + "." + M.Ministracao.ID + " = ?";

        String[] selectionArgs = new String[]{String.valueOf(id)};

        Cursor cursor = builder.query(getDbReadable(), null,
                selection, selectionArgs, null, null, null);

        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        Ministracao m = createMinistracaoFromCursor(cursor);

        cursor.close();
        return m;
    }

    @Override
    public Ministracao create(Ministracao entity) {

        ContentValues values = new ContentValues();

        values.put(M.Ministracao.ID, entity.getId());
        values.put(M.Ministracao.PALESTRA_ID, entity.getAtividade().getId());
        values.put(M.Ministracao.DATA_ALTERACAO, entity.getDataAlteracao().toString());
        values.put(M.Ministracao.DIA_HORA, entity.getDiaHora().toString());
        values.put(M.Ministracao.LOCAL, entity.getLocal());

        long retorno = getDbWriteble().insert(M.Ministracao.ENTITY_NAME, null, values);

        if (retorno != -1)
            return entity;
        else
            return null;
    }

    @Override
    public Ministracao update(Ministracao entity) {
        return null;
    }

    @Override
    public void delete(Ministracao entity) {

    }

    /**
     * Retorna uma lista de Ministra&ccedil;&otilde;es que est&atilde;o situadas dentro do
     * intervalo de tempo formado pelos {@code Timestamp}s passados.
     */
    @Override
    public List<Ministracao> listByDate(Timestamp dateMin, Timestamp dateMax) {

        //Utilizando SQLiteQueryBuilder para construir consultas complexas programaticamente

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        //selecionando tabelas palestra e ministracao
        builder.setTables(M.Ministracao.ENTITY_NAME + ", " + M.Palestra.ENTITY_NAME);

        //WHERE palestra._id = ministracao.palestra_id
        builder.appendWhere(M.Palestra.ENTITY_NAME + "." + M.Palestra.ID + " = " + M.Ministracao.PALESTRA_ID);

        Cursor cursor = builder.query(getDbReadable(), null,
                "datetime("+M.Ministracao.DIA_HORA+")"+" BETWEEN datetime(?) AND datetime(?)",
                new String[]{dateMin.toString(), dateMax.toString()},
                null, null, M.Palestra.NOME+" ASC");

        List<Ministracao> ministracoes = new ArrayList<>();

        while (cursor.moveToNext()) {
            Ministracao m = createMinistracaoFromCursor(cursor);

            ministracoes.add(m);
        }

        cursor.close();

        return ministracoes;
    }

    @Override
    public List<Ministracao> listByPalestra(Atividade atividade) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        //selecionando tabelas atividade e ministracao
        builder.setTables(M.Ministracao.ENTITY_NAME+", "+M.Palestra.ENTITY_NAME);

        String selection = M.Palestra.ENTITY_NAME + "." + M.Palestra.ID + " = " + M.Ministracao.PALESTRA_ID
                + " AND " + M.Ministracao.PALESTRA_ID + " = ?";

        String [] selectionArgs = new String[]{
                String.valueOf(atividade.getId())
        };

        Cursor cursor = builder.query(getDbReadable(), null,
                selection, selectionArgs, null, null, M.Palestra.NOME+" ASC");

        List<Ministracao> ministracoes = new ArrayList<>();

        while (cursor.moveToNext()) {
            Ministracao m = createMinistracaoFromCursor(cursor);
            ministracoes.add(m);
        }

        cursor.close();

        return ministracoes;
    }
}
