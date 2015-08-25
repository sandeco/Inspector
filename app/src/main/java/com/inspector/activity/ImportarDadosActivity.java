package com.inspector.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.inspector.R;
import com.inspector.dataimport.ImportadoraDados;
import com.inspector.dataimport.ImportadoraListener;
import com.inspector.httpClient.InternetCheck;

public class ImportarDadosActivity extends ActionBarActivity implements ImportadoraListener {

    private ProgressBar progressBar;
    private TextView tvAguarde;
    private Button btImportar;

    private ImportadoraDados importadoraDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importar_dados);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvAguarde = (TextView) findViewById(R.id.tv_aguarde);
        btImportar = (Button) findViewById(R.id.bt_importar);

        importadoraDados = new ImportadoraDados(this, this);

        ativarTelaNormal();
    }

    public void loadFromServer(View v) {

        if (InternetCheck.isConnected(this)) {
            ativarTelaCarregamento();
            importadoraDados.iniciar();
            tvAguarde.setText("Os dados estão sendo importados para o seu dispositivo");
        } else {
            Toast.makeText(this, getString(R.string.internet_erro), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void updateImportacao(boolean result) {
        if (result)
            tvAguarde.setText(getString(R.string.dados_importadosEgravados));
        else
            tvAguarde.setText(getString(R.string.dados_naoGravados));

        ativarTelaNormal();
        tvAguarde.setVisibility(View.VISIBLE);
    }

    private void ativarTelaCarregamento() {
        btImportar.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        tvAguarde.setVisibility(View.VISIBLE);
    }

    private void ativarTelaNormal() {
        btImportar.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        tvAguarde.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.importar_dados, menu);
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
