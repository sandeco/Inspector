package com.inspector.persistencia.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import com.inspector.communication.modelcom.ParticipacaoCom;
import com.inspector.model.M;
import com.inspector.model.Participacao;
import com.inspector.persistencia.dao.MinistracaoDAO;
import com.inspector.persistencia.dao.ParticipacaoDAO;
import com.inspector.persistencia.dao.ParticipanteDAO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ParticipacaoSqliteDAO extends GenericSqliteDAO<Participacao, Integer> implements ParticipacaoDAO {
    @Override
    public List<Participacao> listAll() {
        Cursor cursor =
                getDbReadable().query(M.Participacao.ENTITY_NAME, M.Participacao.FIELDS,
                        null, null, null, null, null);

        List<Participacao> participacoes = new ArrayList<>();

        MinistracaoDAO ministracaoDAO = new MinistracaoSqliteDAO();
        ParticipanteDAO participanteDAO = new ParticipanteSqliteDAO();

        while (cursor.moveToNext()) {
            Participacao p = new Participacao();

            p.setId(cursor.getInt(cursor.getColumnIndex(M.Participacao.ID)));
            p.setDataAlteracao(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(M.Participacao.DATA_ALTERACAO))));
            p.setMinistracao(ministracaoDAO.findById(cursor.getInt(cursor.getColumnIndex(M.Participacao.MINISTRACAO_ID))));
            p.setParticipante(participanteDAO.findById(cursor.getInt(cursor.getColumnIndex(M.Participacao.PARTICIPANTE_ID))));

            participacoes.add(p);
        }

        ministracaoDAO.close();
        participanteDAO.close();
        return participacoes;
    }

    @Override
    public Participacao findById(int id) {
        return null;
    }

    @Override
    public Participacao create(Participacao entity) {
        ContentValues values = new ContentValues();

        values.put(M.Participacao.ID, entity.getId());
        values.put(M.Participacao.PARTICIPANTE_ID, entity.getParticipante().getId());
        values.put(M.Participacao.MINISTRACAO_ID, entity.getMinistracao().getId());
        values.put(M.Participacao.DATA_ALTERACAO, entity.getDataAlteracao().toString());

        long retorno = getDbWriteble().insert(M.Participacao.ENTITY_NAME, null, values);

        return (retorno != -1) ? entity : null;
    }

    @Override
    public Participacao update(Participacao entity) {
        return null;
    }

    @Override
    public void delete(Participacao entity) {

    }

    @Override
    public List<ParticipacaoCom> listToExport() {

        List<ParticipacaoCom> participacaoComs = new ArrayList<>();

        for (Participacao p : listAll()) {

            ParticipacaoCom pCom = new ParticipacaoCom();
            pCom.setId(p.getId());
            pCom.setDataAlteracao(p.getDataAlteracao());
            pCom.setIdMinistracao(p.getMinistracao().getId());
            pCom.setIdParticipante(p.getParticipante().getId());

            participacaoComs.add(pCom);
        }

        return participacaoComs;
    }
}
