package com.malharia.b3.b3malharizbar.activity;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.Wsdl2Code.WebServices.AppService.AppService;
import com.malharia.b3.b3malharizbar.R;
import com.malharia.b3.b3malharizbar.model.Marcado;
import com.malharia.b3.b3malharizbar.model.MarcadoOperations;

public class CustomAdapter extends BaseAdapter {

    Context context;
    List<RowItem> rowItem;
    Marcado marcadosAtual;

    CustomAdapter(Context context, List<RowItem> rowItem) {
        this.context = context;
        this.rowItem = rowItem;

    }

    @Override
    public int getCount() {

        return rowItem.size();
    }


    public String getItemText(int position) {

        return rowItem.get(position).getTitle();
    }

    @Override
    public Object getItem(int position) {

        return rowItem.get(position);
    }

    @Override
    public long getItemId(int position) {

        return rowItem.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.activity_listview, null);
        }

        //ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.dadosRolo);

        RowItem row_pos = rowItem.get(position);
        // setting the image resource and title
        //imgIcon.setImageResource(row_pos.getIcon());
        txtTitle.setText(row_pos.getTitle());

        marcadosAtual = GetDadosMarcado(row_pos.getTitle());
        if(marcadosAtual != null)
        {
            if(marcadosAtual.Posicao == 0)
            {
                txtTitle.setBackgroundColor(0xFF00FF00);
            }
            else if(marcadosAtual.Posicao == 1)
            {
                txtTitle.setBackgroundColor(0xFFFFFF00);
            }
            else
            {
                txtTitle.setBackgroundColor(0xFFFFFFFF);
            }
        }
        else
        {
            txtTitle.setBackgroundColor(0xFFFFFFFF);
        }

        return convertView;
    }

    private MarcadoOperations marcadoOps;
    public Marcado GetDadosMarcado(String dados)
    {
        marcadoOps = new MarcadoOperations(context);
        marcadoOps.open();

        String[] leitura = dados.split("_");

        //String CodItem = leitura[0];
        String NumLote = leitura[2].trim();
        String NumPeca = leitura[3].trim();

        Marcado marcado  = marcadoOps.getMarcadoByDados(NumLote,NumPeca);
        return marcado;
    }
}