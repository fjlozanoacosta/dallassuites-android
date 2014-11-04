package com.icogroup.dallassuites;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.icogroup.util.Utilities;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres.torres on 11/3/14.
 */
public class Login extends Activity implements View.OnClickListener {

    Typeface brandonmedium, brandonreg, brandonlight;
    EditText etUsername, etPassword;
    ImageButton ibRetrievePassword, close;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_popup);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        init();

    }

    private void init() {

        brandonmedium = Typeface.createFromAsset(getAssets(), "brandon_med.otf");
        brandonlight = Typeface.createFromAsset(getAssets(), "brandon_light.otf");
        brandonreg = Typeface.createFromAsset(getAssets(), "brandon_reg.otf");

        close = (ImageButton) findViewById(R.id.login_imagebutton_close);

        etUsername = (EditText) findViewById(R.id.login_edittext_username);
        etPassword = (EditText) findViewById(R.id.login_edittext_password);

        ibRetrievePassword = (ImageButton) findViewById(R.id.login_imagebutton_retrievepassword);

        login = (Button) findViewById(R.id.login_button_login);

        etUsername.setTypeface(brandonreg);
        etPassword.setTypeface(brandonreg);

        login.setTypeface(brandonlight);

        ibRetrievePassword.setOnClickListener(this);
        login.setOnClickListener(this);

        close.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.login_imagebutton_close:
                finish();
                break;
            case R.id.login_imagebutton_retrievepassword:
        //        new RetrievePasswordAsync().execute();
                Toast.makeText(Login.this, "Servicio de recuperacion de contraseña", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_button_login:
                //  new LoginAsync().execute();
                startActivity(new Intent(Login.this, Profile.class));
                break;




        }

    }


    class LoginAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            String url = "http://ec2-54-218-96-225.us-west-2.compute.amazonaws.com/api/?o=addUser";
            JSONObject object = null;
            String jsonResult = null;
            String result = null;

            try {
                DefaultHttpClient httpclient = new DefaultHttpClient();
                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
                HttpConnectionParams.setSoTimeout(httpParams, 30000);

                HttpPost httppost = new HttpPost(url);

//                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
//                        2);
//                nameValuePairs.add(new BasicNameValuePair("user_name", name));
//                nameValuePairs.add(new BasicNameValuePair("user_lastname", lastname));


                HttpResponse response = httpclient.execute(httppost);
                jsonResult = Utilities.convertStreamToString(
                        response.getEntity().getContent()).toString();

                object = new JSONObject(jsonResult);

                Log.d("JSON", jsonResult);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


        }
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
                        2);
                nameValuePairs.add(new BasicNameValuePair("user_id", "16"));
                nameValuePairs.add(new BasicNameValuePair("user_email", "chopper.jose@gmail.com"));


                HttpResponse response = httpclient.execute(httppost);
                jsonResult = Utilities.convertStreamToString(
                        response.getEntity().getContent()).toString();

                object = new JSONObject(jsonResult);

                Log.d("JSON", jsonResult);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


        }
    }

}
