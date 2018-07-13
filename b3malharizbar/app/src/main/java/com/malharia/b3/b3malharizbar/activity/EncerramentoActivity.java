package com.malharia.b3.b3malharizbar.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.Wsdl2Code.WebServices.AppService.AppService;
import com.Wsdl2Code.WebServices.AppService.RolosOrdem;
import com.Wsdl2Code.WebServices.AppService.VectorRolosOrdem;
import com.malharia.b3.b3malharizbar.R;
import com.malharia.b3.b3malharizbar.model.Marcado;
import com.malharia.b3.b3malharizbar.model.MarcadoOperations;
import com.malharia.b3.b3malharizbar.model.Rolo;
import com.malharia.b3.b3malharizbar.model.RoloOperations;
import com.malharia.b3.b3malharizbar.model.Sessao;
import com.malharia.b3.b3malharizbar.model.SessaoOperations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class EncerramentoActivity extends AppCompatActivity implements MessageDialogFragment.MessageDialogListener {
    private static final int ZBAR_CAMERA_PERMISSION = 1;
    private Class<?> mClss;
    private SessaoOperations sessaoOps;
    private RoloOperations roloOps;
    private MarcadoOperations marcadoOps;

    private Button fimOp;
    private Button btnVoltar;

    CustomAdapter adapter;
    private List<RowItem> rowItems;

    private ListView listaPecas;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_encerramento);
        setupToolbar();

        /*
        numbers_digits = GetDadosOrdem();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                activity, android.R.layout.simple_list_item_1,
                numbers_digits);

        setListAdapter(adapter);
        */

        fimOp = (Button) findViewById(R.id.button_operacao);
        fimOp.setBackgroundColor(Color.GREEN);
        //fimOp.setEnabled(false);

        btnVoltar = (Button) findViewById(R.id.button_voltar);
        btnVoltar.setBackgroundColor(Color.GRAY);

        listaPecas = (ListView) findViewById(R.id.lvOrdens);

        rowItems = GetDadosOrdem();

        adapter = new CustomAdapter(this, rowItems);
        listaPecas.setAdapter(adapter);

    }

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
        launchActivity(MainActivity.class);
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

    public void terminaOperacao(View v) {
        fimOp.setEnabled(false);
        fimOp.setText("Processando no Logix");
        fimOp.setBackgroundColor(0xFFFFA500);
        new LongOperation(this).execute();
    }

    public void voltar(View v) {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
        launchActivity(MainActivity.class);
    }

    public void showMessageDialog(String message) {
        DialogFragment fragment = MessageDialogFragment.newInstance("", message, this);
        fragment.show(getSupportFragmentManager(), "scan_results");
    }

    public void closeMessageDialog() {
        closeDialog("scan_results");
    }

    public void closeFormatsDialog() {
        closeDialog("format_selector");
    }

    public void closeDialog(String dialogName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogFragment fragment = (DialogFragment) fragmentManager.findFragmentByTag(dialogName);
        if(fragment != null) {
            fragment.dismiss();
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        int groselia = 1;
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
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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




    public List<RowItem> GetDadosOrdem()
    {
        //String dbPath = Path.Combine(System.Environment.GetFolderPath(System.Environment.SpecialFolder.Personal), "bancoB3.db3");
        //var db2 = new SQLiteConnection(dbPath);
        //db2.Close();
        //var db = new SQLiteConnection(dbPath);
        //var dadosSessao = db.Table<Sessao>();
        //var dadosRolos = db.Table<RolosOrdem>();

        //sessao = dadosSessao.FirstOrDefault();

        sessaoOps = new SessaoOperations(this);
        sessaoOps.open();

        List<Sessao> teste = sessaoOps.getAllSessaos();
        if (teste.size() > 0) {
            Sessao primeiro = sessaoOps.getFirstSessao();
            sessaoOps.updateSessao(primeiro);
        }
        sessaoOps.close();


        roloOps = new RoloOperations(this);
        roloOps.open();

        List<Rolo> allRolos = roloOps.getAllRolos();

        //rolos = dadosRolos.Where(x => x.Ordem == sessao.Ordem).ToList();
        //db.Close();

        List<String> rolosRetorno =  new ArrayList<>();


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
        roloOps.close();

        rowItems = new ArrayList<RowItem>();
        for (int i = 0; i < rolosRetorno.size(); i++) {

            RowItem items = new RowItem(rolosRetorno.get(i));
            rowItems.add(items);
        }

        return rowItems;
    }

    public List<RowItem> GetDadosOrdemMarcados()
    {
        //String dbPath = Path.Combine(System.Environment.GetFolderPath(System.Environment.SpecialFolder.Personal), "bancoB3.db3");
        //var db2 = new SQLiteConnection(dbPath);
        //db2.Close();
        //var db = new SQLiteConnection(dbPath);
        //var dadosSessao = db.Table<Sessao>();
        //var dadosRolos = db.Table<RolosOrdem>();

        //sessao = dadosSessao.FirstOrDefault();

        sessaoOps = new SessaoOperations(this);
        sessaoOps.open();

        List<Sessao> teste = sessaoOps.getAllSessaos();
        if (teste.size() > 0) {
            Sessao primeiro = sessaoOps.getFirstSessao();
            sessaoOps.updateSessao(primeiro);
        }
        sessaoOps.close();


        marcadoOps = new MarcadoOperations(this);
        marcadoOps.open();

        List<Marcado> allRolos = marcadoOps.getAllMarcado();

        //rolos = dadosRolos.Where(x => x.Ordem == sessao.Ordem).ToList();
        //db.Close();

        List<String> rolosRetorno =  new ArrayList<>();


        if (allRolos.size() > 0)
        {
            for (Marcado item : allRolos)
            {
                if(item.NumLote != null)
                {
                    item.NumLote = item.NumLote.trim();
                }
                if(item.NumPeca != null)
                {
                    item.NumPeca = item.NumPeca.trim();
                }
                rolosRetorno.add(item.NumLote + " _ " + item.NumPeca);
            }
        }
        roloOps.close();

        rowItems = new ArrayList<RowItem>();
        for (int i = 0; i < rolosRetorno.size(); i++) {

            RowItem items = new RowItem(rolosRetorno.get(i));
            rowItems.add(items);
        }

        return rowItems;
    }


    @SuppressLint("HandlerLeak")
    Handler handlerOperacaoEncerramento = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    showMessageDialog("Operação concluida");
                    fimOp.setText("Finalizado");
                    fimOp.setBackgroundColor(Color.GRAY);
                    break;
                case 1:
                    //showMessageDialog("Erro no logix");
                    fimOp.setBackgroundColor(Color.GREEN);
                    fimOp.setText("Finalizar");
                    fimOp.setEnabled(true);
                    break;
                case 2:
                    showMessageDialog("Erro no web service");
                    fimOp.setBackgroundColor(Color.GREEN);
                    fimOp.setText("Finalizar");
                    fimOp.setEnabled(true);
                    break;
                case 3:
                    showMessageDialog("Erro: Favor Reiniciar a operação");
                    fimOp.setBackgroundColor(Color.GREEN);
                    fimOp.setText("Finalizar");
                    fimOp.setEnabled(true);
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

            try {
                roloOps = new RoloOperations(mContext);
                roloOps.open();
                String HorarioFimOrdem = new Date().toString();
                List<Rolo> allRolos = roloOps.getAllRolos();

                marcadoOps = new MarcadoOperations(mContext);
                marcadoOps.open();
                List<Marcado> allMarcado = marcadoOps.getAllMarcado();


                sessaoOps = new SessaoOperations(mContext);
                sessaoOps.open();
                List<Sessao> allsessao = sessaoOps.getAllSessaos();
                Sessao primeiro = new Sessao();
                if (allsessao.size() > 0) {
                    primeiro = sessaoOps.getFirstSessao();
                    long tsAgora = (System.currentTimeMillis()/1000/60);
                    primeiro.setFimOperacao(tsAgora);
                    sessaoOps.updateSessao(primeiro);
                }

                if (allMarcado.size() == primeiro.getRoloTroca() ) {

                    int tempoTotalOperacao = (int)(primeiro.getFimOperacao() - primeiro.getInicioOperacao());

                    VectorRolosOrdem rolov = new VectorRolosOrdem();

                    List<String> codMarcados = new Vector<String>();
                    for (Marcado item : allMarcado) {
                        codMarcados.add(item.getNumPeca());
                    }

                    for (Rolo item : allRolos) {
                        for(String s : codMarcados) {
                            if (s.trim().contains(item.getNumPeca().trim())) {
                                RolosOrdem itemRolo = new RolosOrdem();
                                itemRolo.codItem = item.getCodItem();
                                itemRolo.endereco = item.getEndereco();
                                itemRolo.local = item.getLocal();
                                itemRolo.numLote = item.getNumLote();
                                itemRolo.numPeca = item.getNumPeca();
                                itemRolo.permiteSubstituir = item.getPermiteSubstituir();
                                rolov.add(itemRolo);
                            }
                        }
                    }

                    /*
                    for (Marcado item : allMarcado) {
                        RolosOrdem itemRolo = new RolosOrdem();
                        itemRolo.codItem = item.getCodItem();
                        itemRolo.endereco = "END";
                        itemRolo.local = "END";
                        itemRolo.numLote = item.getNumLote();
                        itemRolo.numPeca = item.getNumPeca();
                        itemRolo.permiteSubstituir = "S";
                        rolov.add(itemRolo);
                    }
                    */
                    try {
                        AppService srv1 = new AppService();
                        //boolean operacaoFim = srv1.FinalizaSeparacao(primeiro.getCracha(), primeiro.getOrdem(), rolov , tempoTotalOperacao);
                        String operacaoFim = srv1.FinalizaSeparacao(primeiro.getCracha(), primeiro.getOrdem(), rolov,tempoTotalOperacao);
                        showMessageDialog(operacaoFim);
                        handlerOperacaoEncerramento.sendEmptyMessage(1);
                        /*
                        if(operacaoFim)
                        {
                            handlerOperacaoEncerramento.sendEmptyMessage(0);
                        }
                        else
                        {
                            handlerOperacaoEncerramento.sendEmptyMessage(1);

                        }
                        */

                    } catch (Exception ex) {
                        handlerOperacaoEncerramento.sendEmptyMessage(2);
                    }

                    //Intent intent = new Intent(mContext, EncerramentoActivity.class);
                    //startActivity(intent);

                }
                else
                {}
            } catch (Exception ex) {
                handlerOperacaoEncerramento.sendEmptyMessage(3);
            }

            marcadoOps.close();
            roloOps.close();
            sessaoOps.close();

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