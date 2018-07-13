package com.malharia.b3.b3malharizbar.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.Wsdl2Code.WebServices.AppService.AppService;
import com.malharia.b3.b3malharizbar.BuildConfig;
import com.malharia.b3.b3malharizbar.R;
import com.malharia.b3.b3malharizbar.model.MarcadoOperations;
import com.malharia.b3.b3malharizbar.model.RoloOperations;
import com.malharia.b3.b3malharizbar.model.Configuracao;
import com.malharia.b3.b3malharizbar.model.ConfiguracaoOperations;
import com.malharia.b3.b3malharizbar.model.Configuracao;
import com.malharia.b3.b3malharizbar.model.ConfiguracaoOperations;

import java.util.List;

public class ConfiguracaoActivity extends AppCompatActivity {
    private static final int ZBAR_CAMERA_PERMISSION = 1;
    private Class<?> mClss;
    private ConfiguracaoOperations configuracaoOps;


    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        setupToolbar();

        TextView mTv = (TextView) findViewById(R.id.txt_endereco);
        //mTv.setTextColor(ContextCompat.getColor(this, R.color.));

        Configuracao data = null;
        try{
            configuracaoOps = new ConfiguracaoOperations(ConfiguracaoActivity.this);
            configuracaoOps.open();

            mTv.setText(BuildConfig.VERSION_NAME);

            configuracaoOps.close();
        }
        catch(Exception e){
            int tesrr = 1+1;
        }
    }

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void salvaConfiguracao(View v)
    {
        TextView mTv = (TextView) findViewById(R.id.txt_endereco);
        String endConfig = mTv.getText().toString();
        new LongOperation(this).execute(endConfig);
    }

    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZBAR_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, clss);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZBAR_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(mClss != null) {
                        Intent intent = new Intent(this, mClss);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(this, "Aceitar permissão de camera", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    @SuppressLint("HandlerLeak")
    Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    launchActivity(ScanOrdemActivity.class);
                    break;
                case 1:
                    //showMessageDialog("Erro ,tente novamente");
                    break;
                case 2:
                    //showMessageDialog("Falha ao conectar ao Logix");
                    break;
                default:
                    break;
            }
        }
    };

    private class LongOperation extends AsyncTask<String, Void, String>
    {
        private Context mContext;

        public LongOperation (Context context){
            mContext = context;
        }


        @Override
        protected String doInBackground(String... params) {

            AppService srv1 = new AppService();
            boolean testeLogix = srv1.ValidaCracha("erro");
            if(!testeLogix) {
                boolean crachaValido = srv1.ValidaCracha(params[0]);
                //txt.setText("Web service funcionando.");

                if (crachaValido) {
                    configuracaoOps = new ConfiguracaoOperations(mContext);
                    configuracaoOps.open();


                    List<Configuracao> teste = configuracaoOps.getAllConfiguracao();
                    if (teste.size() > 0) {
                        configuracaoOps.removeConfiguracao();
                    }

                    Configuracao data = new Configuracao();
                    data.setEnderecoServidor(params[0]);

                    configuracaoOps.addConfiguracao(data);
                    configuracaoOps.close();

                    myHandler.sendEmptyMessage(0);
                } else {
                    myHandler.sendEmptyMessage(1);
                }
            }
            else
            {
                myHandler.sendEmptyMessage(2);
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you

        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}