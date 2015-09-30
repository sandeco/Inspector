package com.inspector.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inspector.R;
import com.inspector.activity.fragment.ListaAtividadesFragment;
import com.inspector.serverModel.dao.ParticipacaoDAOImpl;
import com.inspector.model.Ministracao;
import com.inspector.model.Participacao;

import jim.h.common.android.zxinglib.integrator.IntentIntegrator;
import jim.h.common.android.zxinglib.integrator.IntentResult;

public class VerificarPresencaActivity extends Activity implements OnClickListener {

	private AlertDialog dialogConfirmacao;

	private EditText etInscricao;
	private TextView tvNome, tvPalestra;
	private ImageButton btValidar;
	private LinearLayout fundoBusca;


	private Participacao participacao;
	private Ministracao ministracao;
	private ParticipacaoDAOImpl dao;

	public static final String EXTRA_MINISTRACAO = "ministracao_atual";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verificar_presenca);

		ministracao = (Ministracao) getIntent().getSerializableExtra(ListaAtividadesFragment.EXTRA_MINISTRACAO);

		if (ministracao != null) {
			dialogConfirmacao = constroiDialogoConfirmacao();

			dao = new ParticipacaoDAOImpl(this);
			participacao = new Participacao();

			etInscricao = (EditText) findViewById(R.id.et_inscricao);
			tvNome = (TextView) findViewById(R.id.tv_nome);
			btValidar = (ImageButton) findViewById(R.id.btValidar);
			tvPalestra = (TextView) findViewById(R.id.tvPalestra);
			fundoBusca = (LinearLayout) findViewById(R.id.fundoBusca);



			tvPalestra.setText(ministracao.getPalestra().getNome());
			btValidar.setEnabled(false);
			limparBusca();
		}
	}




	@Override
	protected void onDestroy() {
		dao.close();
		super.onDestroy();
	}




	// M�TODO QUE BUSCA QR-CODE EM COMPONENTE
	public void qr(View v) {
		IntentIntegrator.initiateScan(this, 
				R.layout.qrcode_reader_layout, 
				R.id.viewfinder_view, 
				R.id.preview_view, 
				true);
	}


	// RETORNO DO COMPONENTE DE LEITURA DO QR-CODE E
	// RETORNO DA ACTIVITY LISTA PARTICIPANTES COM O PARTICIPANTE SELECIONADO
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == IntentIntegrator.REQUEST_CODE) {

			IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

			if (result != null) {

				String textoQr = result.getContents();

				try {
					int numeroInscricao = Integer.parseInt(textoQr);
					etInscricao.setText(numeroInscricao+"");
					buscarInscrito(null);
				} catch (NumberFormatException e) {
					etInscricao.setText("");
				}
			}
		} else if (requestCode == ListaParticipantesActivity.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {                
            	
            	etInscricao.setText(data.getStringExtra(ListaParticipantesActivity.EXTRA_NUMERO_INSCRITO)+"");
            	buscarInscrito(null);
            } else {
            	limparBusca();
            }
        }
	}




	// M�TODO PARA BUSCAR UMA INSCRICAO
	public void buscarInscrito (View v) {

		if (etInscricao.getText().length() != 0) {

			int inscricao = Integer.parseInt(etInscricao.getText().toString());

			participacao = dao.buscarParticipacaoPorInscricaoMinistracao(inscricao, ministracao);

			if (participacao != null) {

				tvNome.setText(participacao.getParticipante().getNome());				
				btValidar.setEnabled(true);
				fundoBusca.setBackgroundColor(Color.rgb(168, 207, 96));

			} else {

				tvNome.setText(getString(R.string.inscricao_naoEcontrada));
				btValidar.setEnabled(false);
				fundoBusca.setBackgroundColor(Color.rgb(248, 172, 146));

			}
		}else{
			Toast.makeText(this, getString(R.string.inscricao_invalida), Toast.LENGTH_LONG).show();
			limparBusca();
		}
	}



	// MÉTODO PARA VALIDAR A PRESENÇA 
	public void validarPresenca (View v) {
		//chamando dialogo de confirmação da presença
		dialogConfirmacao.show();
	}

	//MÉTODO PARA LISTAR OS PARTICIPANTES DESTA MINISTRAÇÃO
	public void verParticipacoes(View v) {
		//carregando activity com a lista e passando a ministração para ela
		Intent intent = new Intent(this, ListaParticipantesActivity.class);
		intent.putExtra(EXTRA_MINISTRACAO, ministracao);
		startActivityForResult(intent, ListaParticipantesActivity.REQUEST_CODE);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

		if (dialog == dialogConfirmacao && which == DialogInterface.BUTTON_POSITIVE) {
			//participacao.setPresenca(true);
			boolean sucesso = dao.updateParticipacao(participacao);

			if (sucesso) {
				Toast.makeText(this, getString(R.string.presenca_registrada), Toast.LENGTH_LONG).show();				
				//reiniciando os valores na interface
				limparBusca();
			}
			else
				mostrarDialogoMensagem(getString(R.string.erro_presenca));
		}

	}



	private void mostrarDialogoMensagem(String message) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(getString(R.string.dialogo_titulo));
		builder.setMessage(message);
		builder.setNeutralButton(getString(R.string.dialogo_ok), null);

		builder.show();
	}

	private AlertDialog constroiDialogoConfirmacao() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.dialogo_confirmacao));
		builder.setMessage(getString(R.string.dialogo_mensagem));

		builder.setPositiveButton(getString(R.string.dialogo_confirmar), this);

		builder.setNegativeButton(getString(R.string.dialogo_cancelar), this);

		return builder.create();		
	}


	private void limparBusca(){
		tvNome.setText("");
		btValidar.setEnabled(false);
		etInscricao.setText("");
		fundoBusca.setBackgroundColor(Color.rgb(210, 211, 213));
	}



}
