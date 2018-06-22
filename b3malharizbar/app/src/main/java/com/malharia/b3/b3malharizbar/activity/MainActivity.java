package com.malharia.b3.b3malharizbar.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.Wsdl2Code.WebServices.AppService.RolosOrdem;
import com.malharia.b3.b3malharizbar.BuildConfig;
import com.malharia.b3.b3malharizbar.R;
import com.malharia.b3.b3malharizbar.model.MarcadoOperations;
import com.malharia.b3.b3malharizbar.model.RoloOperations;
import com.malharia.b3.b3malharizbar.model.Sessao;
import com.malharia.b3.b3malharizbar.model.SessaoOperations;

public class MainActivity extends AppCompatActivity {
    private static final int ZBAR_CAMERA_PERMISSION = 1;
    private Class<?> mClss;
    private SessaoOperations sessaoOps;
    private RoloOperations roloOps;
    private MarcadoOperations marcadoOps;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        setupToolbar();

        TextView mTv = (TextView) findViewById(R.id.numero_versao);
        mTv.setText(BuildConfig.VERSION_NAME);
        mTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,30);
        //mTv.setTextColor(ContextCompat.getColor(this, R.color.));

        Sessao data = null;
        try{
            sessaoOps = new SessaoOperations(MainActivity.this);
            sessaoOps.open();
            sessaoOps.removeSessao();
            sessaoOps.close();
        }
        catch(Exception e){
            int tesrr = 1+1;
        }

        try{
            roloOps = new RoloOperations(MainActivity.this);
            roloOps.open();
            roloOps.removeRolo();
            roloOps.close();
        }
        catch(Exception e){
            int tesrr = 1+1;
        }

        try{
            marcadoOps = new MarcadoOperations(MainActivity.this);
            marcadoOps.open();
            marcadoOps.removeMarcado();
            marcadoOps.close();
        }
        catch(Exception e){
            int tesrr = 1+1;
        }
    }

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void launchSimpleActivity(View v) {
        launchActivity(SimpleScannerActivity.class);
    }

    public void launchSimpleFragmentActivity(View v) {
        launchActivity(SimpleScannerFragmentActivity.class);
    }

    public void launchFullActivity(View v) {
        launchActivity(FullScannerMainActivity.class);
    }

    public void launchFullFragmentActivity(View v) {
        launchActivity(FullScannerFragmentActivity.class);
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
}