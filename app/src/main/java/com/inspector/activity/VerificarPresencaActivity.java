package com.inspector.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inspector.R;
import com.inspector.activity.fragment.ListaPalestrasFragment;
import com.inspector.model.Inscricao;
import com.inspector.model.Ministracao;
import com.inspector.model.Participacao;
import com.inspector.model.Participante;
import com.inspector.persistencia.dao.InscricaoDAO;
import com.inspector.persistencia.dao.ParticipacaoDAO;
import com.inspector.persistencia.sqlite.InscricaoSqliteDAO;
import com.inspector.persistencia.sqlite.ParticipacaoSqliteDAO;
import com.inspector.zxing.qrcode.QRCodeUtil;

import java.sql.Timestamp;
import java.util.Calendar;

public class VerificarPresencaActivity extends AppCompatActivity implements QRCodeUtil.Listener {

	private AlertDialog dialogValidarPresenca;
	private EditText etInscricao;
	private TextView tvNome;
	private ImageButton btValidar;
	private LinearLayout fundoBusca;

	private Ministracao mMinistracao;
	private Inscricao mInscricao;

	private ParticipacaoDAO mParticipacaoDAO;
	private InscricaoDAO mInscricaoDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verificar_presenca);

		mMinistracao = (Ministracao) getIntent().getSerializableExtra(ListaPalestrasFragment.EXTRA_MINISTRACAO);

		if (mMinistracao != null) {
			dialogValidarPresenca = createDialogValidarPresenca();

			mParticipacaoDAO = new ParticipacaoSqliteDAO();
			mInscricaoDAO = new InscricaoSqliteDAO();

			etInscricao = (EditText) findViewById(R.id.et_inscricao);
			tvNome = (TextView) findViewById(R.id.tv_nome);
			btValidar = (ImageButton) findViewById(R.id.btValidar);
			fundoBusca = (LinearLayout) findViewById(R.id.fundoBusca);
			TextView tvPalestra = (TextView) findViewById(R.id.tvPalestra);

			tvPalestra.setText(mMinistracao.getPalestra().getNome());
			limparCamposFormulario();
		}
	}

	@Override
	protected void onDestroy() {
		mParticipacaoDAO.close();
		mInscricaoDAO.close();
		super.onDestroy();
	}

//	==========================================================
//	METODOS ONCLICK BUTTONS

	public void buscarInscrito(View v) {
		carregarInscritoNaTela();
	}

	public void validarPresenca(View v) {
		dialogValidarPresenca.show();
	}

	public void readQRCode(View v) {
		QRCodeUtil.getInstance().registerListener(this); //antes de pedir a leitura, se registrar como interessado
		QRCodeUtil.getInstance().read(this); //inicia processo de leitura do QRCode
	}

	public void abrirListaParticipantes(View v) {
		//carregando activity com a lista e passando a ministração para ela
		Intent intent = new Intent(this, ListaInscritosActivity.class);
		intent.putExtra(ListaInscritosActivity.EXTRA_PALESTRA, mMinistracao.getPalestra());
		startActivityForResult(intent, ListaInscritosActivity.REQUEST_CODE);
	}

//	==========================================================


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == ListaInscritosActivity.REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				etInscricao.setText(data.getStringExtra(ListaInscritosActivity.EXTRA_NUMERO_INSCRITO));
				carregarInscritoNaTela();
			}
		}
	}

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

			//com o numero do participante verificar se nesta palestra
			//existe uma inscricao desse participante nesta palestra

			Participante participante = new Participante();
			participante.setId(numeroParticipante);

			mInscricao = mInscricaoDAO.findByPalestraAndParticipante(mMinistracao.getPalestra(), participante);

			if (mInscricao != null) {
				tvNome.setText(mInscricao.getParticipante().getNome());
				btValidar.setEnabled(true);
				fundoBusca.setBackgroundColor(Color.rgb(168, 207, 96));

			} else {
				tvNome.setText(getString(R.string.inscricao_nao_encontrada));
				btValidar.setEnabled(false);
				fundoBusca.setBackgroundColor(Color.rgb(248, 172, 146));
			}
		} else {
			Toast.makeText(this, getString(R.string.inscricao_invalida), Toast.LENGTH_LONG).show();
			limparCamposFormulario();
		}
	}

	private void showMessageDialog(String message) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(getString(android.R.string.dialog_alert_title));
		builder.setMessage(message);
		builder.setNeutralButton(getString(android.R.string.ok), null);

		builder.show();
	}


	private AlertDialog createDialogValidarPresenca() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(getString(R.string.dialogo_confirmacao))
				.setMessage(getString(R.string.dialogo_mensagem))
				.setPositiveButton(getString(R.string.dialogo_confirmar), new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						Participacao p = new Participacao();
						p.setDataAlteracao(new Timestamp(Calendar.getInstance().getTimeInMillis()));
						p.setMinistracao(mMinistracao);
						p.setParticipante(mInscricao.getParticipante());

						boolean result = mParticipacaoDAO.create(p) != null;

						if (result) {
							Toast.makeText(VerificarPresencaActivity.this, getString(R.string.presenca_registrada),
									Toast.LENGTH_LONG).show();
							limparCamposFormulario();
						}
						else
							showMessageDialog(getString(R.string.erro_presenca));
					}
				})
				.setNegativeButton(getString(R.string.dialogo_cancelar), null);

		return builder.create();
	}

	private void limparCamposFormulario() {
		tvNome.setText("");
		btValidar.setEnabled(false);
		etInscricao.setText("");
		fundoBusca.setBackgroundColor(Color.rgb(210, 211, 213));
	}
}
