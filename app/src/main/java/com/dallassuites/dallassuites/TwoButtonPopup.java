package com.dallassuites.dallassuites;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dallassuites.util.Keystring;
import com.dallassuites.util.Utilities;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres.torres on 5/19/15.
 */
public class TwoButtonPopup extends Activity {

    Typeface brandonregular, brandonlight;
    TextView title, text;
    Button btnAhoraNo, btnSumar;
    SharedPreferences prefs;
    EditText etSumaPuntos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.twobuttonpopup);
        
        init();
        
    }

    private void init() {

        prefs = getSharedPreferences(Keystring.DALLAS_SUITES_PREFERENCES, MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(Keystring.USER_QRSCANS, 0);
        editor.apply();

        brandonregular = Typeface.createFromAsset(getAssets(), "brandon_reg.otf");
        brandonlight = Typeface.createFromAsset(getAssets(), "brandon_light.otf");

        title = (TextView)findViewById(R.id.register_popup_title);
        title.setTypeface(brandonregular);
        text = (TextView)findViewById(R.id.register_popup_text);
        text.setTypeface(brandonregular);
        btnAhoraNo = (Button)findViewById(R.id.btn_ahorano);
        btnAhoraNo.setTypeface(brandonlight);
        btnSumar = (Button)findViewById(R.id.btn_sumar);
        btnSumar.setTypeface(brandonlight);

        etSumaPuntos = (EditText)findViewById(R.id.campo_sumapuntos);

        btnAhoraNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(TwoButtonPopup.this, Room360.class).putExtra("Photo360", "http://www.dallassuiteshotel.com").putExtra("RoomName", "Dallas Suites"));
                new AddPointsAsync().execute();
            }
        });


    }


    class AddPointsAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            String url = "http://ec2-54-218-96-225.us-west-2.compute.amazonaws.com/api/?o=addPoints";
            JSONObject object = null;
            String jsonResult = null;
            String result = null;

            try {
                DefaultHttpClient httpclient = new DefaultHttpClient();
                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
                HttpConnectionParams.setSoTimeout(httpParams, 30000);

                HttpPost httppost = new HttpPost(url);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
                        3);
                nameValuePairs.add(new BasicNameValuePair("user_id", prefs.getString(Keystring.USER_ID, "")));
                nameValuePairs.add(new BasicNameValuePair("user_password", prefs.getString(Keystring.USER_PASSWORD, "")));
                nameValuePairs.add(new BasicNameValuePair("ticket_id", etSumaPuntos.getText().toString()));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));


                HttpResponse response = httpclient.execute(httppost);
                jsonResult = Utilities.convertStreamToString(
                        response.getEntity().getContent()).toString();


            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonResult;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

                if(result.contains("error")){
                    Log.d("TBP", "error");
                    setResult(RESULT_CANCELED);
                    finish();
                }else{
                    Log.d("TBP", "ok");
                    setResult(RESULT_OK);
                    finish();
                }


        }
    }


}
