package com.icogroup.dallassuites;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.icogroup.util.Utilities;

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
 * Created by andres.torres on 12/19/14.
 */
public class RetrievePassword extends Activity implements View.OnClickListener {

    EditText etMail;
    TextView title;
    Typeface brandonreg;
    Button retrievePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrievepassword);

        init();

    }

    private void init() {

        brandonreg = Typeface.createFromAsset(getAssets(), "brandon_reg.otf");

        title = (TextView) findViewById(R.id.retrievepassword_title);
        title.setTypeface(brandonreg);

        etMail = (EditText) findViewById(R.id.retrievepassword_edittext_mail);
        etMail.setTypeface(brandonreg);

        retrievePassword = (Button) findViewById(R.id.retrievepassword_button_retrievepassword);
        retrievePassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        new RetrievePasswordAsync().execute();

    }


    class RetrievePasswordAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            String url = "http://ec2-54-218-96-225.us-west-2.compute.amazonaws.com/api/?o=resetUserPassword";
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
                        1);
                nameValuePairs.add(new BasicNameValuePair("user_email", "fl1@ig.com"));
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

            if(result.contains("msg")){
                Toast.makeText(RetrievePassword.this, "Email enviado al correo electrónico correspondiente", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RetrievePassword.this, Login.class));
                finish();
            }else{
                Toast.makeText(RetrievePassword.this, "No se ha podido recuperar la contraseña", Toast.LENGTH_SHORT).show();
            }



        }
    }


}
