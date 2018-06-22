package com.malharia.b3.b3malharizbar.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.app.FragmentManager;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ArrayAdapter;


public class LoaderCursorSupport extends FragmentActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fm = getSupportFragmentManager();

        if (fm.findFragmentById(android.R.id.content) == null) {
            HelperListFragment list = new HelperListFragment();
            //fm.beginTransaction().add(android.R.id.content, list).commit();
        }
    }
}


