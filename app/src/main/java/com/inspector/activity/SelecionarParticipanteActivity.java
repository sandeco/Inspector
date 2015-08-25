package com.inspector.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.inspector.R;
import com.inspector.dao.ParticipacaoDAOImpl;
import com.inspector.model.Participante;

import jim.h.common.android.zxinglib.integrator.IntentIntegrator;
import jim.h.common.android.zxinglib.integrator.IntentResult;

public class SelecionarParticipanteActivity extends Activity {

	public static final String EXTRA_PARTICIPANTE = "com.congresso.extra_participante";

	private EditText etInscricao;
	
	private ParticipacaoDAOImpl dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selecionar_participante);

		dao = new ParticipacaoDAOImpl(this);
		etInscricao = (EditText) findViewById(R.id.et_inscricao);
	}

	@Override
	protected void onDestroy() {
		dao.close();
		super.onDestroy();
	}
	
	public void qr(View v) {
		IntentIntegrator.initiateScan(this, 
				R.layout.qrcode_reader_layout, 
				R.id.viewfinder_view, 
				R.id.preview_view, 
				true);
	}


	// RETORNO DO COMPONENTE DE LEITURA DO QR-CODE
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
		}
	}

	// MÉTODO PARA BUSCAR UMA INSCRICAO
	public void buscarInscrito (View v) {

		if (etInscricao.getText().length() != 0) {

			int inscricao = Integer.parseInt(etInscricao.getText().toString());

			Participante participante = new Participante();
			participante.setId(inscricao);
			
			participante = dao.buscarParticipante(participante);

			if (participante != null) {

				Intent intent = new Intent(this, ListaMinistracaoPorParticipanteActivity.class);
				intent.putExtra(EXTRA_PARTICIPANTE, participante);
				startActivity(intent);

			} else {

				Toast.makeText(this, getString(R.string.inscricao_naoEcontrada), Toast.LENGTH_LONG).show();

			}
		}else{
			Toast.makeText(this, getString(R.string.inscricao_invalida), Toast.LENGTH_LONG).show();
			etInscricao.setText("");

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.selecionar_participante, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
