package com.icogroup.dallassuites;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.icogroup.util.Keystring;
import com.icogroup.util.Utilities;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres.torres on 1/5/15.
 */
public class NewPasswordPopup extends Activity {

    Typeface brandonreg, brandonlight;
    TextView copy;
    EditText etPassword, etRepeatPassword, etKeyword;
    Button bAdd;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.newpassword_popup);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        init();

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etPassword.getText().toString().equals(etRepeatPassword.getText().toString()))
                    new AddPasswordAsync().execute();
                else
                    Toast.makeText(NewPasswordPopup.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void init() {

        prefs = getSharedPreferences(Keystring.DALLAS_SUITES_PREFERENCES, MODE_PRIVATE);

        brandonreg = Typeface.createFromAsset(getAssets(), "brandon_reg.otf");
        brandonlight = Typeface.createFromAsset(getAssets(), "brandon_light.otf");

        copy = (TextView) findViewById(R.id.newpassword_copy);
        copy.setTypeface(brandonreg);

        etPassword = (EditText) findViewById(R.id.perfil_edittext_password);
        etPassword.setTypeface(brandonreg);
        etRepeatPassword = (EditText) findViewById(R.id.perfil_edittext_repeatpassword);
        etRepeatPassword.setTypeface(brandonreg);
        etKeyword = (EditText) findViewById(R.id.perfil_edittext_tip);
        etKeyword.setTypeface(brandonreg);
        bAdd = (Button) findViewById(R.id.perfil_popup_button_agregar);
        bAdd.setTypeface(brandonlight);

    }

    class AddPasswordAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            String url = "http://ec2-54-218-96-225.us-west-2.compute.amazonaws.com/api/?o=addPassword";
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

                nameValuePairs.add(new BasicNameValuePair("user_id", prefs.getString(Keystring.USER_ID,"")));
                nameValuePairs.add(new BasicNameValuePair("user_password", etPassword.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("user_keyword", etKeyword.getText().toString()));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpclient.execute(httppost);
                jsonResult = Utilities.convertStreamToString(
                        response.getEntity().getContent()).toString();

                object = new JSONObject(jsonResult);

                Log.d("JSON", jsonResult);

                if (object.names().toString().contains("msg")) {
                    try {
                        result = object.getString("msg");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        result = object.getString("error");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();

            }


            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(result.contains("Registrada")) {
                Toast.makeText(NewPasswordPopup.this, "Contraseña registrada con éxito", Toast.LENGTH_SHORT).show();
                finish();
            }else{

                Toast.makeText(NewPasswordPopup.this, "No se pudo registrar la contraseña", Toast.LENGTH_SHORT).show();
            }

        }

    }
}
