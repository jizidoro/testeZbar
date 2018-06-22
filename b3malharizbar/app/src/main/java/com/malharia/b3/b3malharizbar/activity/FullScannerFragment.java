package com.malharia.b3.b3malharizbar.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Wsdl2Code.WebServices.AppService.AppService;
import com.Wsdl2Code.WebServices.AppService.RolosOrdem;
import com.Wsdl2Code.WebServices.AppService.VectorRolosOrdem;
import com.malharia.b3.b3malharizbar.R;
import com.malharia.b3.b3malharizbar.customzbar.CustomZBarScannerView;
import com.malharia.b3.b3malharizbar.model.Marcado;
import com.malharia.b3.b3malharizbar.model.MarcadoOperations;
import com.malharia.b3.b3malharizbar.model.Rolo;
import com.malharia.b3.b3malharizbar.model.RoloOperations;
import com.malharia.b3.b3malharizbar.model.Sessao;
import com.malharia.b3.b3malharizbar.model.SessaoOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;

public class FullScannerFragment extends Fragment implements MessageDialogFragment.MessageDialogListener,
        CustomZBarScannerView.ResultHandler, FormatSelectorDialogFragment.FormatSelectorDialogListener,
        CameraSelectorDialogFragment.CameraSelectorDialogListener {
    private static final String FLASH_STATE = "FLASH_STATE";
    private static final String AUTO_FOCUS_STATE = "AUTO_FOCUS_STATE";
    private static final String SELECTED_FORMATS = "SELECTED_FORMATS";
    private static final String CAMERA_ID = "CAMERA_ID";
    private static final int ZBAR_CAMERA_PERMISSION = 1;
    private Class<?> mClss;
    private CustomZBarScannerView mScannerView;
    private boolean mFlash;
    private boolean mAutoFocus;
    private ArrayList<Integer> mSelectedIndices;
    private int mCameraId = -1;
    private String ResultadoScaneado;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        mScannerView = new CustomZBarScannerView(getActivity());
        if(state != null) {
            mFlash = state.getBoolean(FLASH_STATE, false);
            mAutoFocus = state.getBoolean(AUTO_FOCUS_STATE, true);
            mSelectedIndices = state.getIntegerArrayList(SELECTED_FORMATS);
            mCameraId = state.getInt(CAMERA_ID, -1);
        } else {
            mFlash = false;
            mAutoFocus = true;
            mSelectedIndices = null;
            mCameraId = -1;
        }
        setupFormats();
        return mScannerView;


    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setHasOptionsMenu(true);
        //new LongOperation(getContext()).execute("1;7459;74307-01-040;1");
    }

    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem menuItem;

        if(mFlash) {
            menuItem = menu.add(Menu.NONE, R.id.menu_flash, 0, R.string.flash_on);
        } else {
            menuItem = menu.add(Menu.NONE, R.id.menu_flash, 0, R.string.flash_off);
        }
        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);


        if(mAutoFocus) {
            menuItem = menu.add(Menu.NONE, R.id.menu_auto_focus, 0, R.string.auto_focus_on);
        } else {
            menuItem = menu.add(Menu.NONE, R.id.menu_auto_focus, 0, R.string.auto_focus_off);
        }
        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);

        menuItem = menu.add(Menu.NONE, R.id.menu_formats, 0, R.string.formats);
        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);

        menuItem = menu.add(Menu.NONE, R.id.menu_camera_selector, 0, R.string.select_camera);
        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menu_flash:
                mFlash = !mFlash;
                if(mFlash) {
                    item.setTitle(R.string.flash_on);
                } else {
                    item.setTitle(R.string.flash_off);
                }
                mScannerView.setFlash(mFlash);
                return true;
            case R.id.menu_auto_focus:
                mAutoFocus = !mAutoFocus;
                if(mAutoFocus) {
                    item.setTitle(R.string.auto_focus_on);
                } else {
                    item.setTitle(R.string.auto_focus_off);
                }
                mScannerView.setAutoFocus(mAutoFocus);
                return true;
            case R.id.menu_formats:
                DialogFragment fragment = FormatSelectorDialogFragment.newInstance(this, mSelectedIndices);
                fragment.show(getActivity().getSupportFragmentManager(), "format_selector");
                return true;
            case R.id.menu_camera_selector:
                mScannerView.stopCamera();
                DialogFragment cFragment = CameraSelectorDialogFragment.newInstance(this, mCameraId);
                cFragment.show(getActivity().getSupportFragmentManager(), "camera_selector");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera(mCameraId);
        mScannerView.setFlash(mFlash);
        mScannerView.setAutoFocus(mAutoFocus);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FLASH_STATE, mFlash);
        outState.putBoolean(AUTO_FOCUS_STATE, mAutoFocus);
        outState.putIntegerArrayList(SELECTED_FORMATS, mSelectedIndices);
        outState.putInt(CAMERA_ID, mCameraId);
    }

    @Override
    public void handleResult(Result rawResult) {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getActivity().getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {}
        ResultadoScaneado = rawResult.getContents();
        if(ResultadoScaneado != null)
        {
            new LongOperation(getContext()).execute(ResultadoScaneado);
        }
        showMessageDialog("Dados = " + rawResult.getContents());
    }

    public void showMessageDialog(String message) {
        DialogFragment fragment = MessageDialogFragment.newInstance("Resultado", message, this);
        fragment.show(getActivity().getSupportFragmentManager(), "scan_results");
    }

    public void closeMessageDialog() {
        closeDialog("scan_results");
    }

    public void closeFormatsDialog() {
        closeDialog("format_selector");
    }

    public void closeDialog(String dialogName) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        DialogFragment fragment = (DialogFragment) fragmentManager.findFragmentByTag(dialogName);
        if(fragment != null) {
            fragment.dismiss();
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // Resume the camera
        mScannerView.resumeCameraPreview(this);


    }

    @Override
    public void onFormatsSaved(ArrayList<Integer> selectedIndices) {
        mSelectedIndices = selectedIndices;
        setupFormats();
    }

    @Override
    public void onCameraSelected(int cameraId) {
        mCameraId = cameraId;
        mScannerView.startCamera(mCameraId);
        mScannerView.setFlash(mFlash);
        mScannerView.setAutoFocus(mAutoFocus);
    }

    public void setupFormats() {
        List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        if(mSelectedIndices == null || mSelectedIndices.isEmpty()) {
            mSelectedIndices = new ArrayList<Integer>();
            for(int i = 0; i < BarcodeFormat.ALL_FORMATS.size(); i++) {
                mSelectedIndices.add(i);
            }
        }

        formats.add(BarcodeFormat.QRCODE);
        /*
        for(int index : mSelectedIndices) {
            formats.add(BarcodeFormat.ALL_FORMATS.get(index));
        }
        */
        if(mScannerView != null) {
            mScannerView.setFormats(formats);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
        closeMessageDialog();
        closeFormatsDialog();
    }

    public void launchActivity(Class<?> clss) {

            Intent intent = new Intent(getActivity(), clss);
            startActivity(intent);
    }

    private SessaoOperations sessaoOps;
    private RoloOperations roloOps;
    private MarcadoOperations marcadoOps;
    private class LongOperation extends AsyncTask<String, Void, String>
    {
        private Context mContext;

        public LongOperation (Context context){
            mContext = context;
        }

        @Override
        protected String doInBackground(String... params) {

            roloOps = new RoloOperations(mContext);
            roloOps.open();

            String[] leitura = params[0].split(";");
            String CodItem = leitura[0];
            String NumLote = leitura[1];
            String NumPeca = leitura[2];
            List<Rolo> allRolos = roloOps.getAllRolos();
            Rolo roloAtual = roloOps.getMarcarRolo(CodItem ,NumLote ,NumPeca);

            marcadoOps = new MarcadoOperations(mContext);
            marcadoOps.open();

            Marcado jaMarcado = marcadoOps.getMarcadoByDados(NumLote,NumPeca);
            if(roloAtual != null && jaMarcado == null) {
                Marcado data = new Marcado();
                data.setCodItem(CodItem);
                data.setNumLote(NumLote);
                data.setNumPeca(NumPeca);
                data.setOrdem(0);
                data.setPosicao(0);
                roloAtual.setPosicao(1);
                roloOps.updateRolo(roloAtual);
                marcadoOps.addMarcado(data);
            }
            else
            {
                Rolo roloSubstituido = roloOps.getFirstNaoMarcaroRolo(CodItem ,NumLote ,NumPeca);
                if(roloSubstituido.PermiteSubstituir.equals("S") && roloSubstituido.CodItem.trim().equals(CodItem) && roloSubstituido.NumLote.trim().equals(NumLote)) {
                    boolean roloSubstituir = roloOps.getSubstituirMarcarRolo(CodItem, NumLote, NumPeca);

                    AppService srv1 = new AppService();
                    boolean naoPodeUsarPeca = false;
                    RolosOrdem itemRolo = new RolosOrdem();
                    itemRolo.codItem = CodItem;
                    //itemRolo.endereco = roloSubstituido.getEndereco();
                    //itemRolo.local = roloSubstituido.getLocal();
                    itemRolo.numLote = NumLote;
                    itemRolo.numPeca = NumPeca;
                    //itemRolo.permiteSubstituir = roloSubstituido.getPermiteSubstituir();
                    naoPodeUsarPeca = srv1.VerificaPecaSubstituicao(itemRolo);//retorna true se nao puder usar


                    if (roloSubstituir && jaMarcado == null && roloSubstituido.PermiteSubstituir.equals("S") && !naoPodeUsarPeca) {
                        Marcado data = new Marcado();
                        data.setCodItem(CodItem);
                        data.setNumLote(NumLote);
                        data.setNumPeca(NumPeca);
                        data.setOrdem(0);
                        data.setPosicao(1);

                        roloSubstituido.setCodItem(CodItem);
                        roloSubstituido.setNumLote(NumLote);
                        roloSubstituido.setNumPeca(NumPeca);
                        roloSubstituido.setPosicao(1);
                        roloOps.updateRolo(roloSubstituido);
                        marcadoOps.addMarcado(data);
                    }
                }
            }
            List<Marcado> allMarcado = marcadoOps.getAllMarcado();


            if(allMarcado.size() >= allRolos.size())
            {
                Intent intent = new Intent(mContext, EncerramentoActivity.class);
                startActivity(intent);
            }

            roloOps.close();
            marcadoOps.close();
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
