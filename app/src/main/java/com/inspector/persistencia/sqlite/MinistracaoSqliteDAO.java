package com.inspector.persistencia.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;

import com.inspector.model.M;
import com.inspector.model.Ministracao;
import com.inspector.model.Palestra;
import com.inspector.persistencia.dao.MinistracaoDAO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leandro on 28/08/15.
 */
public class MinistracaoSqliteDAO extends GenericSqliteDAO<Ministracao, Integer> implements MinistracaoDAO {

    @Override
    public List<Ministracao> listAll() {
        return null;
    }

    @Override
    public Ministracao findById(int id) {
        return null;
    }

    @Override
    public Ministracao create(Ministracao entity) {
        return null;
    }

    @Override
    public Ministracao update(Ministracao entity) {
        return null;
    }

    @Override
    public void delete(Ministracao entity) {

    }

    @Override
    public List<Ministracao> listByDate(Timestamp date) {

        //Utilizando SQLiteQueryBuilder para construir consultas complexas programaticamente

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        //selecionando tabelas palestra e ministracao
        builder.setTables(M.Ministracao.ENTITY_NAME + ", " + M.Palestra.ENTITY_NAME);

        //WHERE palestra._id = ministracao.palestra_id
        builder.appendWhere(M.Palestra.ENTITY_NAME + "." + M.Palestra.ID + " = " + M.Ministracao.PALESTRA_ID);

        Cursor cursor = builder.query(getDbReadable(), null, M.Ministracao.DIA_HORA + " >= ?", new String[]{date.toString()},
                null, null, M.Palestra.NOME+" ASC");

        //SELECT * FROM ministracao, palestra WHERE (palestra._id = palestra_id) AND (dia_hora >= '2015-08-28 08:00:00') ORDER BY nome ASC

        List<Ministracao> ministracoes = new ArrayList<>();

        while (cursor.moveToNext()) {
            Palestra p = new Palestra();
            p.setId(cursor.getInt(cursor.getColumnIndex(M.Ministracao.PALESTRA_ID)));
            p.setNome(cursor.getString(cursor.getColumnIndex(M.Palestra.NOME)));

            Ministracao m = new Ministracao();
            m.setId(cursor.getInt(cursor.getColumnIndex(M.Ministracao.ID)));
            m.setDiaHora(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(M.Ministracao.DIA_HORA))));
            m.setLocal(cursor.getString(cursor.getColumnIndex(M.Ministracao.LOCAL)));
            m.setPalestra(p);

            ministracoes.add(m);
        }

        cursor.close();

        return ministracoes;
    }
}
