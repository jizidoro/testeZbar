package com.malharia.b3.b3malharizbar.activity;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.Wsdl2Code.WebServices.AppService.VectorRolosOrdem;
import com.malharia.b3.b3malharizbar.R;
import com.Wsdl2Code.WebServices.AppService.AppService;

public class TesteWebserviceActivity extends Activity {
    private TextView txt;
    private String celsius;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teste_webservice);

        final EditText edt = (EditText)findViewById(R.id.value_to_convert);
        Button btn = (Button)findViewById(R.id.convert);
        txt = (TextView)findViewById(R.id.answer);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (edt.length() > 0) {
                    new LongOperation().execute("");

                //}
                //else {
                    //txt.setText("se o teste tem valor obrigatorio.");
                //}
            }
        });
    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            AppService srv1 = new AppService();
            VectorRolosOrdem tes = new VectorRolosOrdem();
            tes = srv1.BuscaDadosOrdemProducao(5);
            txt.setText("Web service funcionando.");
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            TextView txt = (TextView) findViewById(R.id.answer);
            txt.setText("Executed"); // txt.setText(result);
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}