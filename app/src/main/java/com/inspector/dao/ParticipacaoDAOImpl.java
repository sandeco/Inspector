package com.inspector.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.inspector.model.Ministracao;
import com.inspector.model.Participacao;
import com.inspector.model.Participante;
import com.inspector.serverModel.Presenca;

import java.util.ArrayList;
import java.util.List;

public class ParticipacaoDAOImpl implements ParticipacaoDAO {

	private DatabaseHelper helper;
	private SQLiteDatabase db;

	private MinistracaoDAOImpl ministDAO;

	public ParticipacaoDAOImpl(Context context) {
		helper = new DatabaseHelper(context);
		ministDAO = new MinistracaoDAOImpl(context);
	}

	private SQLiteDatabase getDb() {
		if (db == null)
			db = helper.getWritableDatabase();

		return db;
	}
	
	public void close() {
		helper.close();
	}

	@Override
	public List<Participacao> listarParticipacao() {

		Cursor cursor = getDb().rawQuery("SELECT * FROM participacao, participante " +
				"WHERE participacao.participante_inscricao = participante.inscricao", null);

		List<Participacao> participacoes = new ArrayList<Participacao>();

		while (cursor.moveToNext()) {
			Participacao p = criarParticipacao(cursor);
			participacoes.add(p);
		}

		cursor.close();
		return participacoes;
	}

	public List<Participacao> listarParticipacoesComPresenca() {

		Cursor cursor = getDb().rawQuery("SELECT * FROM participacao, participante " +
				"WHERE participacao.participante_inscricao = participante.inscricao " +
				"AND participacao.presenca = 1", null);

		List<Participacao> participacoes = new ArrayList<Participacao>();

		while (cursor.moveToNext()) {
			Participacao p = criarParticipacao(cursor);
			participacoes.add(p);
		}

		cursor.close();
		return participacoes;
	}

	public List<Presenca> listarParticipacoesJson() {

		Cursor cursor = getDb().rawQuery("SELECT "
				+ "participacao.participante_inscricao as cod_participante, "
				+ "palestra._id as cod_atividade "
				+ "FROM "
				+ "palestra INNER JOIN ministracao "
				+ "ON palestra._id = ministracao.palestra_id "
				+ "INNER JOIN participacao "
				+ "ON ministracao._id = participacao.ministracao_id "
				+ "WHERE participacao.presenca = 1 "
				+ "GROUP BY participacao.participante_inscricao "
				+ "HAVING count(participacao.participante_inscricao) >= 0.75*"
				+ "(SELECT count(ministracao.palestra_id) as ministracoes "
				+ "FROM ministracao WHERE ministracao.palestra_id = palestra._id)" , null);

		List<Presenca> participacoes = new ArrayList<Presenca>();

		while (cursor.moveToNext()) {
			Presenca p = new Presenca();
			
			p.setCod_participante(cursor.getInt(cursor.getColumnIndex("cod_participante")));
			p.setCod_atividade(cursor.getInt(cursor.getColumnIndex("cod_atividade")));
			
			participacoes.add(p);
		}

		cursor.close();
		return participacoes;
	}

	private Participacao criarParticipacao(Cursor cursor) {
		Participacao p = new Participacao();


		/*
		p.setParticipante(new Participante(
				cursor.getInt(cursor.getColumnIndex("participante_inscricao")),
				cursor.getString(cursor.getColumnIndex("nome"))
				));
		p.setPresenca(cursor.getInt(cursor.getColumnIndex("presenca")));
		p.setUpdated(cursor.getInt(cursor.getColumnIndex("updated")));
		*/
		p.setMinistracao(ministDAO.buscarMinistracaoPorId(cursor.getInt(cursor.getColumnIndex("ministracao_id"))));
		return p;
	}

	@Override
	public boolean inserirParticipacao(Participacao participacao) {
		ContentValues values = new ContentValues();

		values.put("ministracao_id", participacao.getMinistracao().getId());


		long retorno = getDb().insert("participacao", null, values);

		return retorno != -1;
	}

	@Override
	public boolean removerParticipacao(Participacao participacao) {
		/*
		int retorno = getDb().delete("participacao", "ministracao_id = "+participacao.getMinistracao().getId()+
				" AND participante_inscricao = "+participacao.getParticipante().getInscricao(), null);

		return retorno != 0;

		*/
		return false;
	}


	@Override
	public Participacao buscarParticipacaoPorInscricaoMinistracao(
			int inscricao, Ministracao ministracao) {

		Cursor cursor = getDb().rawQuery("SELECT * FROM participacao, participante " +
				"WHERE participante.inscricao = participacao.participante_inscricao AND " +
				"participacao.ministracao_id = "+ministracao.getId()+" AND " +
				"participacao.participante_inscricao = "+inscricao
				, null);

		while (cursor.moveToNext()) {
			Participacao p = criarParticipacao(cursor);
			return p;
		}

		return null;
	}

	@Override
	public boolean updateParticipacao(Participacao participacao) {
		ContentValues values = new ContentValues();

		values.put("ministracao_id", participacao.getMinistracao().getId());

		//fazer update no registro que tiver ministracao_id e participante_inscricao iguais ao desse objeto
		/*
		int retorno = getDb().update("participacao", values, 
				"ministracao_id = "+participacao.getMinistracao().getId()+
				" AND participante_inscricao = "+participacao.getParticipante().getInscricao(), null);

		return retorno != 0;

		*/

		return false;
	}

	public List<Participacao> listarParticipacoesPorMinistracao(Ministracao m) {
		Cursor cursor = getDb().rawQuery("SELECT * FROM participacao, participante " +
				"WHERE participacao.participante_inscricao = participante.inscricao " +
				"AND participacao.ministracao_id = "+m.getId(), null);

		List<Participacao> participacoes = new ArrayList<Participacao>();

		while (cursor.moveToNext()) {
			Participacao p = criarParticipacao(cursor);
			participacoes.add(p);
		}

		cursor.close();
		return participacoes;
	}
	
	public List<Participacao> listarParticipacoesPorParticipante(Participante participante) {

		List<Participacao> participacoes = new ArrayList<Participacao>();
		/*
		Cursor cursor = getDb().rawQuery("SELECT * FROM participacao, participante " +
				"WHERE participacao.participante_inscricao = participante.inscricao " +
				"AND participante.inscricao = "+participante.getInscricao(), null);



		while (cursor.moveToNext()) {
			Participacao p = criarParticipacao(cursor);
			participacoes.add(p);
		}

		cursor.close();
		*/
		return participacoes;
	}
	
	public Participante buscarParticipante(Participante participante) {
		Participante p = null;

		/*
		Cursor cursor = getDb().rawQuery("SELECT * FROM participante " +
				"WHERE inscricao = "+participante.getInscricao(), null);
		

		
		while (cursor.moveToNext()) {
			p = new Participante();
			p.setInscricao(cursor.getInt(cursor.getColumnIndex("inscricao")));
			p.setNome(cursor.getString(cursor.getColumnIndex("nome")));
			
			break;
		}
		*/

		return p;
	}

}
