package com.inspector.persistencia.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;

import com.inspector.model.Inscricao;
import com.inspector.model.M;
import com.inspector.model.Palestra;
import com.inspector.model.Participante;
import com.inspector.persistencia.dao.InscricaoDAO;
import com.inspector.persistencia.dao.PalestraDAO;
import com.inspector.persistencia.dao.ParticipanteDAO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class InscricaoSqliteDAO extends GenericSqliteDAO<Inscricao, Integer> implements InscricaoDAO {
    @Override
    public List<Inscricao> listAll() {
        return null;
    }

    @Override
    public Inscricao findById(int id) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        builder.setTables(M.Inscricao.ENTITY_NAME);

        String selection = M.Inscricao.ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};

        Cursor cursor = builder.query(getDbReadable(), null, selection, selectionArgs, null, null, null);

        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        Inscricao inscricao = new Inscricao();

        PalestraDAO palestraDAO = new PalestraSqliteDAO();
        ParticipanteDAO participanteDAO = new ParticipanteSqliteDAO();

        inscricao.setId(cursor.getInt(cursor.getColumnIndex(M.Inscricao.ID)));
        inscricao.setDataAlteracao(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(M.Inscricao.DATA_ALTERACAO))));

        final Participante participante =
                participanteDAO.findById(cursor.getInt(cursor.getColumnIndex(M.Inscricao.PARTICIPANTE_ID)));

        final Palestra palestra =
                palestraDAO.findById(cursor.getInt(cursor.getColumnIndex(M.Inscricao.PALESTRA_ID)));

        inscricao.setParticipante(participante);
        inscricao.setPalestra(palestra);

        if (participante == null || palestra == null)
            inscricao = null;

        palestraDAO.close();
        participanteDAO.close();
        cursor.close();

        return inscricao;
    }

    @Override
    public Inscricao create(Inscricao entity) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(M.Inscricao.ID, entity.getId());
        contentValues.put(M.Inscricao.DATA_ALTERACAO, entity.getDataAlteracao().toString());
        contentValues.put(M.Inscricao.PALESTRA_ID, entity.getPalestra().getId());
        contentValues.put(M.Inscricao.PARTICIPANTE_ID, entity.getParticipante().getId());

        long retorno = getDbWriteble().insert(M.Inscricao.ENTITY_NAME, null, contentValues);

        ParticipanteDAO participanteDAO = new ParticipanteSqliteDAO();

        if (entity.getParticipante().getDataAlteracao() != null)
            participanteDAO.create(entity.getParticipante());

        participanteDAO.close();

        return (retorno != -1) ? entity : null;
    }

    @Override
    public Inscricao update(Inscricao entity) {
        return null;
    }

    @Override
    public void delete(Inscricao entity) {

    }

    @Override
    public Inscricao findByPalestraAndParticipante(Palestra palestra, Participante participante) {

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        builder.setTables(M.Inscricao.ENTITY_NAME);

        String selection = M.Inscricao.PALESTRA_ID + " = ? AND " + M.Inscricao.PARTICIPANTE_ID + " = ?";
        String[] selectionArgs = new String[] {
                String.valueOf(palestra.getId()),
                String.valueOf(participante.getId())
        };

        Cursor cursor = builder.query(getDbReadable(), null, selection, selectionArgs, null, null, null);

        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        Inscricao inscricao = new Inscricao();

        PalestraDAO palestraDAO = new PalestraSqliteDAO();
        ParticipanteDAO participanteDAO = new ParticipanteSqliteDAO();

        inscricao.setId(cursor.getInt(cursor.getColumnIndex(M.Inscricao.ID)));
        inscricao.setDataAlteracao(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(M.Inscricao.DATA_ALTERACAO))));

        participante = participanteDAO.findById(cursor.getInt(cursor.getColumnIndex(M.Inscricao.PARTICIPANTE_ID)));

        palestra = palestraDAO.findById(cursor.getInt(cursor.getColumnIndex(M.Inscricao.PALESTRA_ID)));

        inscricao.setParticipante(participante);
        inscricao.setPalestra(palestra);

        if (participante == null || palestra == null)
            inscricao = null;

        palestraDAO.close();
        participanteDAO.close();
        cursor.close();

        return inscricao;
    }

    @Override
    public List<Inscricao> listByPalestra(Palestra palestra) {

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        builder.setTables(M.Inscricao.ENTITY_NAME);

        String selection = M.Inscricao.PALESTRA_ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(palestra.getId())};

        Cursor cursor = builder.query(getDbReadable(), null, selection, selectionArgs, null, null, null);

        List<Inscricao> inscricoes = new ArrayList<>();
        PalestraDAO palestraDAO = new PalestraSqliteDAO();
        ParticipanteDAO participanteDAO = new ParticipanteSqliteDAO();

        while (cursor.moveToNext()) {
            Inscricao i = createInscricaoFromCursor(cursor, palestraDAO, participanteDAO);

            if (i == null)
                return new ArrayList<>();

            inscricoes.add(i);
        }

        palestraDAO.close();
        participanteDAO.close();
        cursor.close();

        return inscricoes;
    }

    @Override
    public List<Inscricao> listByParticipante(Participante participante) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        builder.setTables(M.Inscricao.ENTITY_NAME);

        String selection = M.Inscricao.PARTICIPANTE_ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(participante.getId())};

        Cursor cursor = builder.query(getDbReadable(), null, selection, selectionArgs, null, null, null);

        List<Inscricao> inscricoes = new ArrayList<>();
        PalestraDAO palestraDAO = new PalestraSqliteDAO();
        ParticipanteDAO participanteDAO = new ParticipanteSqliteDAO();

        while (cursor.moveToNext()) {
            Inscricao i = createInscricaoFromCursor(cursor, palestraDAO, participanteDAO);

            if (i == null)
                return new ArrayList<>();

            inscricoes.add(i);
        }

        palestraDAO.close();
        participanteDAO.close();
        cursor.close();

        return inscricoes;
    }

    /**
     * <p>Criar uma inscricao a partir de um Cursor. Deve ser passado os DAOs para criar
     * o objeto complexo dentro do objeto Inscricao. Cursor.close() não é chamado dentro.
     * deste método.</p>
     *
     * <p>O objeto Inscricao retornado pode ser null, neste caso, não foi possível criar os outros
     * objetos complexos que estão dentro dele (Palestra e Participação).</p>
     */
    private Inscricao createInscricaoFromCursor(Cursor cursor, PalestraDAO palestraDAO, ParticipanteDAO participanteDAO) {
        Inscricao inscricao = new Inscricao();

        inscricao.setId(cursor.getInt(cursor.getColumnIndex(M.Inscricao.ID)));
        inscricao.setDataAlteracao(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(M.Inscricao.DATA_ALTERACAO))));

        Participante participante =
                participanteDAO.findById(cursor.getInt(cursor.getColumnIndex(M.Inscricao.PARTICIPANTE_ID)));

        Palestra palestra =
                palestraDAO.findById(cursor.getInt(cursor.getColumnIndex(M.Inscricao.PALESTRA_ID)));

        inscricao.setParticipante(participante);
        inscricao.setPalestra(palestra);

        if (participante == null || palestra == null)
            inscricao = null;

        return inscricao;
    }
}
