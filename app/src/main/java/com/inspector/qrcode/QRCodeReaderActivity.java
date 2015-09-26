package com.inspector.qrcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.inspector.R;

import jim.h.common.android.zxinglib.integrator.IntentIntegrator;
import jim.h.common.android.zxinglib.integrator.IntentResult;

public class QRCodeReaderActivity extends Activity {

    public final static int REQUEST_CODE = 122;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIntent = getIntent();
        initQrScan();
    }

    public void initQrScan() {
        IntentIntegrator.initiateScan(this,
                R.layout.qrcode_reader_layout,
                R.id.viewfinder_view,
                R.id.preview_view,
                true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IntentIntegrator.REQUEST_CODE) {

            //decodificando o resultado
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

            if (result != null) {

                String qrcodeText = result.getContents();

                QRCodeUtil.getInstance().response(qrcodeText);

                //respondendo em caso de activity ser chamada por startActivityForResult
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        }
    }
}
