package com.inspector.persistencia.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.inspector.model.M;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String BANCO_DADOS = "inspector";
	private static final int VERSAO = 1;

	public DatabaseHelper(Context context) {
		super(context, BANCO_DADOS, null, VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(M.Evento.CREATE);
		db.execSQL(M.Inscricao.CREATE);
		db.execSQL(M.Ministracao.CREATE);
		db.execSQL(M.Palestra.CREATE);
		db.execSQL(M.Palestrante.CREATE);
		db.execSQL(M.Participacao.CREATE);
		db.execSQL(M.Participante.CREATE);
		db.execSQL(M.Comunicacao.CREATE);

	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	

}
