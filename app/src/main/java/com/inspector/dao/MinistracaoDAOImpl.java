package com.inspector.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.inspector.model.M;
import com.inspector.model.Ministracao;
import com.inspector.model.Palestra;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MinistracaoDAOImpl implements MinistracaoDAO {

	private SimpleDateFormat dateFormat;
	private DatabaseHelper helper;
	private SQLiteDatabase db;

	public MinistracaoDAOImpl(Context context) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		helper = new DatabaseHelper(context);
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
	public List<Ministracao> listarMinistracoesDeHoje() {

		//precisamos da data de hoje para fazer a comparacao na busca sql
		//formatando a data de hoje para yyyy-MM-dd
		String dataHoje = dateFormat.format(new Date());

		Log.d("MinistracaoDAOImpl", "Data de hoje para busca no banco: "+dataHoje);

//		Cursor cursor = getDb().rawQuery("SELECT * FROM "+M.Ministracao.ENTITY_NAME+", "+M.Palestra.ENTITY_NAME+" WHERE " +
//				M.Palestra.ID+" = "+M.Ministracao.PALESTRA_ID+" AND "+M.Ministracao.DIA_HORA+" = ? ORDER BY "+M.Palestra.NOME+" ASC",
//				new String[]{dataHoje});

//		Cursor cursor = getDb().rawQuery("SELECT ministracao._id, ministracao.palestra_id, palestra.nome FROM ministracao, palestra " +
//				"WHERE palestra._id = ministracao.palestra_id AND ministracao.data = ? ORDER BY palestra.nome ASC", new String[]{dataHoje});

		//Utilizando SQLiteQueryBuilder para construir consultas complexas programaticamente

		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

		builder.setTables(M.Ministracao.ENTITY_NAME + ", " + M.Palestra.ENTITY_NAME);
		builder.appendWhere(M.Palestra.ENTITY_NAME + "." + M.Palestra.ID + " = " + M.Ministracao.PALESTRA_ID);

		Cursor cursor = builder.query(getDb(), null, M.Ministracao.DIA_HORA + " >= ?", new String[]{dataHoje},
				null, null, M.Palestra.NOME+" ASC");

		//SELECT * FROM ministracao, palestra WHERE (palestra._id = palestra_id) AND (dia_hora LIKE '2015-08-28') ORDER BY nome ASC

		List<Ministracao> ministracoes = new ArrayList<Ministracao>();

		while (cursor.moveToNext()) {
			Palestra p = new Palestra();
			p.setId(cursor.getInt(cursor.getColumnIndex(M.Palestra.ID)));
			p.setNome(cursor.getString(cursor.getColumnIndex(M.Palestra.NOME)));
			
			Ministracao m = new Ministracao();
			m.setId(cursor.getInt(cursor.getColumnIndex(M.Ministracao.ID)));
			m.setDiaHora(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(M.Ministracao.DIA_HORA))));
			m.setPalestra(p);
			ministracoes.add(m);
		}

		cursor.close();
		return ministracoes;
	}
	
	@Override
	public List<Ministracao> listarMinistracoes() {

		Cursor cursor = getDb().rawQuery("SELECT ministracao._id, ministracao.data, ministracao.palestra_id, palestra.nome " +
				"FROM ministracao, palestra " + 
				"WHERE palestra._id = ministracao.palestra_id", null);

		List<Ministracao> ministracoes = new ArrayList<Ministracao>();

		/*
		while (cursor.moveToNext()) {

			try {

				Palestra palestra = new Palestra(
						cursor.getString(cursor.getColumnIndex("nome")),
						cursor.getInt(cursor.getColumnIndex("palestra_id"))
						);

				Ministracao m = new Ministracao(
						cursor.getInt(cursor.getColumnIndex("_id")),
						palestra,		
						dateFormat.parse(cursor.getString(cursor.getColumnIndex("data")))
						);

				ministracoes.add(m);
			} catch (ParseException e) {

				e.printStackTrace();
			}


		}*/

		cursor.close();
		return ministracoes;
	}


	@Override
	public boolean inserirMinistracao(Ministracao m) {

		ContentValues values = new ContentValues();

		values.put("_id", m.getId());
		values.put("palestra_id", m.getPalestra().getId());


		long retorno = getDb().insert("ministracao", null, values);

		return (retorno != 1);

	}

	@Override
	public boolean removerMinistracao(Ministracao m) {

		String id = String.valueOf(m.getId());

		int retorno = getDb().delete("ministracao", "WHERE _id = ?", 
				new String[]{id});

		return retorno != 0;
	}

	@Override
	public Ministracao buscarMinistracaoPorId(int id) {

		Ministracao ministracao= null;

		Cursor cursor = getDb().rawQuery("SELECT ministracao._id, ministracao.palestra_id, palestra.nome, ministracao.data "
				+ "FROM ministracao, palestra "
				+ "WHERE palestra._id = ministracao.palestra_id "
				+ "AND ministracao._id = "+id, null);

		cursor.moveToFirst();
		/*
		try {

			ministracao = new Ministracao(cursor.getInt(cursor.getColumnIndex("_id")), 
					new Palestra(cursor.getString(cursor.getColumnIndex("nome")), 
							cursor.getInt(cursor.getColumnIndex("palestra_id"))), 
							dateFormat.parse(cursor.getString(cursor.getColumnIndex("data"))));

		} catch (ParseException e) {
			Log.e("MinistracaoDAOImpl", "ERRO: O parser encontrou problemas na data recebida");
			//ocorreu um erro no parser da data do banco
			return null;
		}
		*/
		
		cursor.close();
		return ministracao;
	}

	public Palestra buscarPalestraPorId(int id) {
		Palestra p = null;
		
		Cursor cursor = getDb().rawQuery("SELECT * FROM palestra WHERE palestra._id = "+id, null);
		
		while (cursor.moveToNext()) {
			p = new Palestra();
			p.setId(id);
			p.setNome(cursor.getString(cursor.getColumnIndex("nome")));
			break;
		}
		
		return p;
	}

}
