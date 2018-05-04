package com.malharia.b3.b3malharizbar.activity;

import android.os.Bundle;

import com.malharia.b3.b3malharizbar.R;

public class SimpleScannerFragmentActivity extends BaseScannerActivity {
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_simple_scanner_fragment);
        setupToolbar();
    }
}