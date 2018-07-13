package com.malharia.b3.b3malharizbar.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;

import com.Wsdl2Code.WebServices.AppService.RolosOrdem;
import com.malharia.b3.b3malharizbar.R;
import com.malharia.b3.b3malharizbar.model.Rolo;
import com.malharia.b3.b3malharizbar.model.RoloOperations;

import java.util.List;

public class ScanRolosActivity extends FragmentActivity {
    private FragmentTabHost mTabHost;
    private static final int ZBAR_CAMERA_PERMISSION = 1;
    private Class<?> mClss;
    private RoloOperations roloOps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_scan_rolos);

        String isSubs = "Lista de Rolos";

        roloOps = new RoloOperations(ScanRolosActivity.this);
        roloOps.open();
        List<Rolo> allRolos = roloOps.getAllRolos();
        for (Rolo item : allRolos) {
            if (item.getPermiteSubstituir().contains("S")) {
                isSubs = "Lista de Rolos pode substituir";
            }
        }

        roloOps.close();
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("Tab1").setIndicator(isSubs),
                HelperFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Tab2").setIndicator("Scanner"),
                FullScannerFragment.class, null);
    }

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
        launchActivity(MainActivity.class);
    }

    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZBAR_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, clss);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

}
