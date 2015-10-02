package com.inspector.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.inspector.R;
import com.inspector.communication.importData.ProxyRest;
import com.inspector.httpClient.InternetCheck;

public class ImportarDadosActivity extends AppCompatActivity implements ProxyRest.Listener {

    private ProgressBar progressBar;
    private TextView tvAguarde;
    private Button btImportar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importar_dados);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvAguarde = (TextView) findViewById(R.id.tv_aguarde);
        btImportar = (Button) findViewById(R.id.bt_importar);

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
    public void onError(Exception e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ativarTelaNormal();
                tvAguarde.setText(getString(R.string.dados_naoGravados));
                tvAguarde.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onSuccess() {
        runOnUiThread(new Runnable() {
            public void run() {
                ativarTelaNormal();
                tvAguarde.setText(getString(R.string.dados_importadosEgravados));
                tvAguarde.setVisibility(View.VISIBLE);
            }
        });
    }
}
