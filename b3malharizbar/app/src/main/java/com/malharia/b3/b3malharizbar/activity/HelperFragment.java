package com.malharia.b3.b3malharizbar.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.malharia.b3.b3malharizbar.R;
import com.malharia.b3.b3malharizbar.model.Marcado;
import com.malharia.b3.b3malharizbar.model.MarcadoOperations;
import com.malharia.b3.b3malharizbar.model.Rolo;
import com.malharia.b3.b3malharizbar.model.RoloOperations;
import com.malharia.b3.b3malharizbar.model.Sessao;
import com.malharia.b3.b3malharizbar.model.SessaoOperations;

import java.util.ArrayList;
import java.util.List;

public class HelperFragment extends ListFragment implements MessageDialogFragment.MessageDialogListener
{

    private static final int ZBAR_CAMERA_PERMISSION = 1;
    private Class<?> mClss;
    FragmentActivity activity;

    CustomAdapter adapter;
    private List<RowItem> rowItems;


    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
    }

    public void launchActivity(Class<?> clss) {

        Intent intent = new Intent(getActivity(), clss);
        startActivity(intent);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        rowItems = GetDadosOrdem();

        adapter = new CustomAdapter(activity, rowItems);
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();

        rowItems = GetDadosOrdem();

        adapter = new CustomAdapter(activity, rowItems);
        setListAdapter(adapter);
    }

    public void showMessageDialog(String message) {
        DialogFragment fragment = MessageDialogFragment.newInstance("Resultado", message, this);
        fragment.show(getFragmentManager(), "scan_results");
    }

    public void closeMessageDialog() {
        closeDialog("scan_results");
    }

    public void closeFormatsDialog() {
        closeDialog("format_selector");
    }

    public void closeDialog(String dialogName) {
        FragmentManager fragmentManager = getFragmentManager();
        DialogFragment fragment = (DialogFragment) fragmentManager.findFragmentByTag(dialogName);
        if(fragment != null) {
            fragment.dismiss();
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        int groselia = 1;
        Intent intent = new Intent(getContext(), FullScannerAlterarPecaActivity.class);
        startActivity(intent);
    }


    public List<RowItem> GetDadosOrdem()
    {

        roloOps = new RoloOperations(this.getContext());
        roloOps.open();

        List<Rolo> allRolos = roloOps.getAllRolos();

        List<String> rolosRetorno =  new ArrayList<>();

        //rolosRetorno.add(" LOCAL" + " _ " + "ENDEREÇO" + " _ " + "LOTE" + " _ " + "PEÇA");
        if (allRolos.size() > 0)
        {
            for (Rolo item : allRolos)
            {
                if(item.Local != null)
                {
                    item.Local = item.Local.trim();
                }
                if(item.Endereco != null)
                {
                    item.Endereco = item.Endereco.trim();
                }
                if(item.NumLote != null)
                {
                    item.NumLote = item.NumLote.trim();
                }
                if(item.NumPeca != null)
                {
                    item.NumPeca = item.NumPeca.trim();
                }
                rolosRetorno.add(" " +item.Local + " _ " + item.Endereco + " _ " + item.NumLote + " _ " + item.NumPeca);
            }
        }

        rowItems = new ArrayList<RowItem>();
        for (int i = 0; i < rolosRetorno.size(); i++) {

            RowItem items = new RowItem(rolosRetorno.get(i));
            rowItems.add(items);
        }
        roloOps.close();
        return rowItems;
    }

    private SessaoOperations sessaoOps;
    private MarcadoOperations marcadoOps;
    private RoloOperations roloOps;
}
