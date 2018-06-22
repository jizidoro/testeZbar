package com.malharia.b3.b3malharizbar.activity;

import android.os.Bundle;

import com.malharia.b3.b3malharizbar.R;

public class FullScannerFragmentActivity extends BaseScannerActivity {
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_full_scanner_fragment);
        setupToolbar();
    }
}