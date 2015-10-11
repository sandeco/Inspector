package com.inspector.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.inspector.R;
import com.inspector.model.Participante;
import com.inspector.persistencia.dao.InscricaoDAO;
import com.inspector.persistencia.dao.ParticipanteDAO;
import com.inspector.persistencia.sqlite.ParticipanteSqliteDAO;
import com.inspector.zxing.qrcode.QRCodeUtil;

public class SelecionarParticipanteActivity extends AppCompatActivity implements QRCodeUtil.Listener {

	private EditText etInscricao;
	private InscricaoDAO mInscricaoDAO;
	private ParticipanteDAO mParticipanteDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selecionar_participante);

		etInscricao = (EditText) findViewById(R.id.et_inscricao);
		mParticipanteDAO = new ParticipanteSqliteDAO();
	}

	@Override
	protected void onDestroy() {
		mParticipanteDAO.close();
		super.onDestroy();
	}

	//=======================
	//METODOS ONCLICK BUTTONS

	public void readQRCode(View v) {
		QRCodeUtil.getInstance().registerListener(this);
		QRCodeUtil.getInstance().read(this);
	}

	public void buscarInscrito(View v) {
		carregarInscritoNaTela();
	}

	//=======================

	@Override
	public void onResult(@NonNull String qrcodeResult) {
		QRCodeUtil.getInstance().unregisterListener(this);
		etInscricao.setText(qrcodeResult);
		carregarInscritoNaTela();
	}

	private void carregarInscritoNaTela() {

		String value = etInscricao.getText().toString();

		if (!value.isEmpty() && value.matches("[0-9]+")) {

			int numeroParticipante = Integer.parseInt(etInscricao.getText().toString());

			Participante participante = mParticipanteDAO.findById(numeroParticipante);

			if (participante != null) {
				Intent intent = new Intent(this, ListaInscricaoPorParticipante.class);
				intent.putExtra(ListaInscricaoPorParticipante.EXTRA_PARTICIPANTE, participante);
				startActivity(intent);
			} else {
				Toast.makeText(this, getString(R.string.inscricao_nao_encontrada), Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, getString(R.string.inscricao_invalida), Toast.LENGTH_LONG).show();
			etInscricao.setText("");
		}
	}
}
