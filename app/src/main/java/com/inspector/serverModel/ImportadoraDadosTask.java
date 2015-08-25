package com.inspector.serverModel;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;

import com.google.gson.Gson;
import com.inspector.activity.ImportarDadosActivity;
import com.inspector.dao.DatabaseHelper;
import com.inspector.dao.ParticipacaoDAOImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImportadoraDadosTask extends AsyncTask<String, String, Boolean>{

	private DatabaseHelper helper;
	private SQLiteDatabase db;
	private SimpleDateFormat dateFormatJson;
	private Gson gson;

	private ImportarDadosActivity ac;
	private ParticipacaoDAOImpl dao;
	private ExportadoraDados exportadora;


	@SuppressLint("SimpleDateFormat")
	public ImportadoraDadosTask(ImportarDadosActivity ac) {
		this.ac = ac;
		helper = new DatabaseHelper(ac);
		dateFormatJson = new SimpleDateFormat("dd-MM-yyyy"); //formato de data do Json
		gson = new Gson();
		exportadora = new ExportadoraDados(ac);
	}



	@Override
	protected Boolean doInBackground(String... params) {
		return gravarDadosComTransacao(params[0]);
	}


	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);

		dao.close();
		db.close();

//		ac.retornoGravacaoDados(result);
	}


	private boolean gravarDados(String json) {
		db = helper.getWritableDatabase();
		dao = new ParticipacaoDAOImpl(ac);

		//salvando arquivo
		try {
			// data atual
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());

			//grava arquivo com json na pasta Downloas do dispositivo
			String nomeArquivo = "PresencasEvento "+ date + ".txt";
			String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + Environment.DIRECTORY_DOWNLOADS;

			File arquivo = new File( dir, nomeArquivo);
			arquivo.createNewFile();

			//exporta o JSON para o arquivo
			FileWriter writer = new FileWriter(arquivo);
			writer.write(exportadora.getJsonEvento());
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		json = json.replace("\\/", "-");
		Evento evento = gson.fromJson(json, Evento.class);

		if (evento == null) {
			Log.e("ImportadoraDadosTask", "ERRO: Leitura do Json - O objeto evento é null");
			return false;
		}

		//1a fase: guardar as participações
		//		List<Participacao> participacoesAntigas = dao.listarParticipacoesComPresenca();

		//2a fase: excluir tudo
		db.execSQL("DELETE FROM palestra;");
		db.execSQL("DELETE FROM ministracao;");
		db.execSQL("DELETE FROM participacao WHERE presenca = 0;");
		db.execSQL("DELETE FROM participante;");


		try {
			//3a fase: incluir os dados do json
			int ministracaoIdCount = 0; //contador para os ids das ministracoes

			for (Atividade atividade : evento.getLISTA_ATIVIDADES()) {

				if (atividade == null)
					break;

				ContentValues values = new ContentValues(); //criando o contentvalues que será usado
				String data;

				//fazendo processamento da data
				try {
					data = DateFormat.format("yyyy-MM-dd",
							dateFormatJson.parse(atividade.getDTHORA_INICIO()))
							.toString();

				} catch (ParseException e) {
					Log.e("ERRO: ImportadoraDados",
							"Erro no parser da data a partir do Json");
					return false;
				}
				Log.i("ImportadoraDadosTask", "Data a ser passado pro banco: "+data);

				Cursor cursor = db.rawQuery("SELECT palestra._id FROM palestra WHERE _id = "+
						atividade.getCODATIVIDADE(), null);

				//verificar se a palestra NAO EXISTE
				if (cursor.getCount() == 0) {

					//se a palestra nao existe, então vamos adiciona-la ao banco

					Log.i("ImportadoraDadosTask", "Criando Palestra cod:"+atividade.getCODATIVIDADE());

					//criando registro Palestra no banco
					values.put("_id", Integer.parseInt(atividade.getCODATIVIDADE()));
					values.put("nome", atividade.getATIVIDADE());
					db.insert("palestra", null, values);
					values.clear();

					//criando registro Ministracao no banco
					values.put("_id", ministracaoIdCount);
					values.put("data", data);
					values.put("palestra_id",
							Integer.parseInt(atividade.getCODATIVIDADE()));
					db.insert("ministracao", null, values);
					values.clear();

					for (ParticipanteServer participante : atividade
							.getLISTA_PARTICIPANTES()) {

						if (participante == null)
							break;

						Cursor cursor2 = db.rawQuery("SELECT participante.inscricao FROM participante WHERE inscricao = "+
								participante.getCODPARTICIPANTE(), null);

						if (cursor2.getCount() == 0) {

							Log.i("ImportadoraDadosTask", "Criando Participante cod:"+participante.getCODPARTICIPANTE());

							//criando registro Participante no banco
							values.put("inscricao",
									Integer.parseInt(participante.getCODPARTICIPANTE()));
							values.put("nome", participante.getNOME());
							db.insert("participante", null, values);
							values.clear();

						}

						//limpando cursor da memória, esses resultados não são mais necessário
						cursor2.close();

						//Verificando se a participacao já existe no banco
						Cursor cursor3 = db.rawQuery("SELECT ministracao_id, participante_inscricao " +
								"FROM participacao WHERE ministracao_id = "+ministracaoIdCount+" " +
								"AND participante_inscricao = "+participante.getCODPARTICIPANTE(), null);

						if (cursor3.getCount() == 0) {

							Log.i("ImportadoraDadosTask", "Criando nova Participação (ministracao_id="+ministracaoIdCount+
									" participante_inscricao="+participante.getCODPARTICIPANTE()+")");

							//criando registro Participacao no banco
							values.put("ministracao_id", ministracaoIdCount);
							values.put("participante_inscricao",
									Integer.parseInt(participante.getCODPARTICIPANTE()));
							values.put("presenca", false);
							values.put("updated", false);
							db.insert("participacao", null, values);
							values.clear();
						}

						cursor3.close();
					}
				} else {
					//caso contrário, a palestra já existe no banco
					//então vamos criar uma ministracao, com a nova data

					Log.i("ImportadoraDadosTask", "Palestra cod:"+atividade.getCODATIVIDADE()+" ja existe no banco!");

					//criando registro Ministracao no banco
					values.put("_id", ministracaoIdCount);
					values.put("data", data);
					values.put("palestra_id",
							Integer.parseInt(atividade.getCODATIVIDADE()));
					db.insert("ministracao", null, values);
					values.clear();

					for (ParticipanteServer participante : atividade
							.getLISTA_PARTICIPANTES()) {

						if (participante == null)
							break;

						Cursor cursor4 = db.rawQuery("SELECT ministracao_id, participante_inscricao " +
								"FROM participacao WHERE ministracao_id = "+ministracaoIdCount+" " +
								"AND participante_inscricao = "+participante.getCODPARTICIPANTE(), null);

						//Verificando se a participacao já existe no banco
						if (cursor4.getCount() == 0) {

							Log.i("ImportadoraDadosTask", "Criando nova Participação (ministracao_id="+ministracaoIdCount+
									" participante_inscricao="+participante.getCODPARTICIPANTE()+")");

							//criando registro Participacao no banco
							values.put("ministracao_id", ministracaoIdCount);
							values.put("participante_inscricao",
									Integer.parseInt(participante.getCODPARTICIPANTE()));
							values.put("presenca", false);
							values.put("updated", false);
							db.insert("participacao", null, values);
							values.clear();
						}

						cursor4.close();
					}
				}

				ministracaoIdCount++;

				//limpando cursor da memória, esses resultados não são mais necessário
				cursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();

			Log.e("ImportadoraDadosTask", "ERRO: Ocorreu um erro na 3ª etapa - inserção dos dados no banco");

		} finally {

			//4a fase: reinserir participações antigas

			//			for (Participacao participacao : participacoesAntigas) {
			//				Log.i("ImportadoraDadosTask", "Backup: Atualizando Participacao");
			//				Log.i("ImportadoraDadosTask", "ministracao_id = "+participacao.getMinistracao().getId()+
			//						" participante nome:"+participacao.getParticipante().getNome()+
			//						" inscricao:"+participacao.getParticipante().getInscricao());
			//
			//				dao.updateParticipacao(participacao);
			//			}

			Log.i("ImportadoraDadosTask", "Importação Terminada.");
		}

		return true;
	}

	private boolean gravarDadosComTransacao(String json) {
		boolean retorno = true;
		
		db = helper.getWritableDatabase();
		dao = new ParticipacaoDAOImpl(ac);

		//salvando arquivo
		try {
			// data atual
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());

			//grava arquivo com json na pasta Downloas do dispositivo
			String nomeArquivo = "PresencasEvento "+ date + ".txt";
			String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + Environment.DIRECTORY_DOWNLOADS;

			File arquivo = new File( dir, nomeArquivo);
			arquivo.createNewFile();

			//exporta o JSON para o arquivo
			FileWriter writer = new FileWriter(arquivo);
			writer.write(exportadora.getJsonEvento());
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		json = json.replace("\\/", "-");
		Evento evento = gson.fromJson(json, Evento.class);

		if (evento == null) {
			Log.e("ImportadoraDadosTask", "ERRO: Leitura do Json - O objeto evento é null");
			return false;
		}

		//GRAVACAO DE DADOS NO BANCO USANDO TRANSACAO

		db.beginTransaction(); //iniciando a transacao
		try {
			//aqui vão as alterações desta transacao

			db.execSQL("DELETE FROM palestra;");
			db.execSQL("DELETE FROM ministracao;");
			db.execSQL("DELETE FROM participacao WHERE presenca = 0;");
			db.execSQL("DELETE FROM participante;");

			int ministracaoIdCount = 0; //contador para os ids das ministracoes

			for (Atividade atividade : evento.getLISTA_ATIVIDADES()) {

				if (atividade == null)
					break;

				ContentValues values = new ContentValues(); //criando o contentvalues que será usado
				String data;

				//fazendo processamento da data
				data = DateFormat.format("yyyy-MM-dd",
						dateFormatJson.parse(atividade.getDTHORA_INICIO()))
						.toString();

				Log.i("ImportadoraDadosTask", "Data a ser passada pro banco: "+data);

				Cursor cursor = db.rawQuery("SELECT palestra._id FROM palestra WHERE _id = "+
						atividade.getCODATIVIDADE(), null);

				//verificar se a palestra NAO EXISTE
				if (cursor.getCount() == 0) {

					//se a palestra nao existe, então vamos adiciona-la ao banco

					Log.i("ImportadoraDadosTask", "Criando Palestra cod:"+atividade.getCODATIVIDADE());

					//criando registro Palestra no banco
					values.put("_id", Integer.parseInt(atividade.getCODATIVIDADE()));
					values.put("nome", atividade.getATIVIDADE());
					db.insertOrThrow("palestra", null, values);
					values.clear();

					//criando registro Ministracao no banco
					values.put("_id", ministracaoIdCount);
					values.put("data", data);
					values.put("palestra_id",
							Integer.parseInt(atividade.getCODATIVIDADE()));
					db.insertOrThrow("ministracao", null, values);
					values.clear();

					for (ParticipanteServer participante : atividade
							.getLISTA_PARTICIPANTES()) {

						if (participante == null)
							break;

						Cursor cursor2 = db.rawQuery("SELECT participante.inscricao FROM participante WHERE inscricao = "+
								participante.getCODPARTICIPANTE(), null);

						if (cursor2.getCount() == 0) {

							Log.i("ImportadoraDadosTask", "Criando Participante cod:"+participante.getCODPARTICIPANTE());

							//criando registro Participante no banco
							values.put("inscricao",
									Integer.parseInt(participante.getCODPARTICIPANTE()));
							values.put("nome", participante.getNOME());
							db.insertOrThrow("participante", null, values);
							values.clear();

						}

						//limpando cursor da memória, esses resultados não são mais necessário
						cursor2.close();

						//Verificando se a participacao já existe no banco
						Cursor cursor3 = db.rawQuery("SELECT ministracao_id, participante_inscricao " +
								"FROM participacao WHERE ministracao_id = "+ministracaoIdCount+" " +
								"AND participante_inscricao = "+participante.getCODPARTICIPANTE(), null);

						if (cursor3.getCount() == 0) {

							Log.i("ImportadoraDadosTask", "Criando nova Participação (ministracao_id="+ministracaoIdCount+
									" participante_inscricao="+participante.getCODPARTICIPANTE()+")");

							//criando registro Participacao no banco
							values.put("ministracao_id", ministracaoIdCount);
							values.put("participante_inscricao",
									Integer.parseInt(participante.getCODPARTICIPANTE()));
							values.put("presenca", false);
							values.put("updated", false);
							db.insertOrThrow("participacao", null, values);
							values.clear();
						}

						cursor3.close();
					}
				} else {
					//caso contrário, a palestra já existe no banco
					//então vamos criar uma ministracao, com a nova data

					Log.i("ImportadoraDadosTask", "Palestra cod:"+atividade.getCODATIVIDADE()+" ja existe no banco!");

					//criando registro Ministracao no banco
					values.put("_id", ministracaoIdCount);
					values.put("data", data);
					values.put("palestra_id",
							Integer.parseInt(atividade.getCODATIVIDADE()));
					db.insertOrThrow("ministracao", null, values);
					values.clear();

					for (ParticipanteServer participante : atividade
							.getLISTA_PARTICIPANTES()) {

						if (participante == null)
							break;

						Cursor cursor4 = db.rawQuery("SELECT ministracao_id, participante_inscricao " +
								"FROM participacao WHERE ministracao_id = "+ministracaoIdCount+" " +
								"AND participante_inscricao = "+participante.getCODPARTICIPANTE(), null);

						//Verificando se a participacao já existe no banco
						if (cursor4.getCount() == 0) {

							Log.i("ImportadoraDadosTask", "Criando nova Participação (ministracao_id="+ministracaoIdCount+
									" participante_inscricao="+participante.getCODPARTICIPANTE()+")");

							//criando registro Participacao no banco
							values.put("ministracao_id", ministracaoIdCount);
							values.put("participante_inscricao",
									Integer.parseInt(participante.getCODPARTICIPANTE()));
							values.put("presenca", false);
							values.put("updated", false);
							db.insertOrThrow("participacao", null, values);
							values.clear();
						}

						cursor4.close();
					}
				}

				ministracaoIdCount++;

				//limpando cursor da memória, esses resultados não são mais necessário
				cursor.close();
			}

			//se o fluxo chegar aqui, quer dizer que nenhuma operacao lançou uma excecao
			//então a transacao sera commitada
			db.setTransactionSuccessful(); //commit da transação

		} catch (ParseException e) {
			Log.e("ImportadoraDadosTask",
					"Erro no parser da data a partir do Json");
			retorno = false;
		} catch (Exception e) {
			Log.e("ImportadoraDadosTask",
					"Erro na gravacao dos dados, transacao nao terminada.");
			retorno = false;
		} finally {
			//a transação deve ser terminada, mesmo em caso de erro e não commit dela
			db.endTransaction();
		}

		Log.i("ImportadoraDadosTask", "Importação Terminada.");
		return retorno;
	}
}
