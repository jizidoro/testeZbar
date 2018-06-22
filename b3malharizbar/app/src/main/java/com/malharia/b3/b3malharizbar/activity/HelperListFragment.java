//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.malharia.b3.b3malharizbar.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.malharia.b3.b3malharizbar.R;

public class HelperListFragment extends ListFragment {

    public static HelperListFragment newInstance() {
        return new HelperListFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_column , R.id.text);
        setListAdapter(adapter);
        adapter.addAll(createDataList(100));
    }

    private static List<String> createDataList(int counts) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < counts; i++) {
            list.add("i=" + i);
        }
        return list;
    }
}