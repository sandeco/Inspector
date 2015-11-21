package com.inspector.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.inspector.R;
import com.inspector.communication.importData.ProxyRest;
import com.inspector.communication.importData.ProxySummary;
import com.inspector.communication.InternetCheck;

public class ImportarDadosActivity extends AppCompatActivity implements ProxyRest.Listener, ProxySummary.ProxySummaryListener {

    private ProgressBar progressBar;
    private TextView tvAguarde;
    private Button btImportar;
    private Button btImportSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importar_dados);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvAguarde = (TextView) findViewById(R.id.tv_aguarde);
        btImportar = (Button) findViewById(R.id.bt_importar);
        btImportSummary = (Button) findViewById(R.id.bt_import_summary);

        ativarTelaNormal();
    }

    public void loadFromServer(View v) {

        if (InternetCheck.isConnected()) {
            ativarTelaCarregamento();

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    ProxyRest proxyRest = new ProxyRest();
                    proxyRest.registerListener(ImportarDadosActivity.this);
                    proxyRest.sync();
                }
            });

            t.start();

            tvAguarde.setText("Os dados estão sendo importados para o seu dispositivo");
        } else {
            Toast.makeText(this, getString(R.string.internet_erro), Toast.LENGTH_LONG).show();
        }
    }

    public void importSummary(View v) {
        if (InternetCheck.isConnected()) {
            ativarTelaCarregamento();

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    ProxySummary proxySummary = new ProxySummary();
                    proxySummary.registerListener(ImportarDadosActivity.this);
                    proxySummary.sync();
                }
            });

            t.start();

            tvAguarde.setText("Os dados estão sendo importados para o seu dispositivo");
        } else {
            Toast.makeText(this, getString(R.string.internet_erro), Toast.LENGTH_LONG).show();
        }
    }

    private void ativarTelaCarregamento() {
        btImportar.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        tvAguarde.setVisibility(View.VISIBLE);
        btImportSummary.setVisibility(View.INVISIBLE);
    }

    private void ativarTelaNormal() {
        btImportar.setVisibility(View.VISIBLE);
        btImportSummary.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        tvAguarde.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onError(Exception e) {
        avisarErro();
    }

    @Override
    public void onSuccess() {
        avisarSucesso();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.importar_dados, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void avisarErro() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ativarTelaNormal();
                tvAguarde.setText(getString(R.string.dados_naoGravados));
                tvAguarde.setVisibility(View.VISIBLE);
            }
        });
    }

    private void avisarSucesso() {
        runOnUiThread(new Runnable() {
            public void run() {
                ativarTelaNormal();
                tvAguarde.setText(getString(R.string.dados_importadosEgravados));
                tvAguarde.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onProxySummaryError(Exception e) {
        avisarErro();
    }

    @Override
    public void onProxySummarySuccess() {
        runOnUiThread(new Runnable() {
            public void run() {
                avisarSucesso();

                Intent intent = new Intent(ImportarDadosActivity.this, ListaPalestrasActivity.class);
                startActivity(intent);
            }
        });
    }
}
