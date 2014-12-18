package com.icogroup.dallassuites;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.ImageButton;
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
import org.json.JSONArray;
import org.json.JSONException;
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
    SharedPreferences prefs;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        prefs = getSharedPreferences(Keystring.DALLAS_SUITES_PREFERENCES,MODE_PRIVATE);

        init();

    }

    private void init() {

        brandonmedium = Typeface.createFromAsset(getAssets(), "brandon_med.otf");
        brandonlight = Typeface.createFromAsset(getAssets(), "brandon_light.otf");
        brandonreg = Typeface.createFromAsset(getAssets(), "brandon_reg.otf");

        title = (TextView)findViewById(R.id.login_title);

        etUsername = (EditText) findViewById(R.id.login_edittext_username);
        etPassword = (EditText) findViewById(R.id.login_edittext_password);

        etUsername.setText(prefs.getString(Keystring.USER_USERNAME, ""));

        ibRetrievePassword = (ImageButton) findViewById(R.id.login_imagebutton_retrievepassword);

        login = (Button)findViewById(R.id.login_button_login);

        login.setOnClickListener(this);

        title.setTypeface(brandonreg);
        etUsername.setTypeface(brandonreg);
        etPassword.setTypeface(brandonreg);

        ibRetrievePassword.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.login_imagebutton_retrievepassword:
                Toast.makeText(Login.this, "Servicio de recuperacion de contraseña", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_button_login:
                if(etUsername.getText().toString().equals(""))
                    Toast.makeText(Login.this, "Debe introducir su nombre de usuario", Toast.LENGTH_SHORT).show();
                else if(etPassword.getText().toString().equals(""))
                    Toast.makeText(Login.this, "Debe introducir su contraseña", Toast.LENGTH_SHORT).show();
                else
                    new LoginAsync().execute();
                break;

        }

    }


    class LoginAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            String url = "http://ec2-54-218-96-225.us-west-2.compute.amazonaws.com/api/?o=userLogin";
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
                nameValuePairs.add(new BasicNameValuePair("user_login", etUsername.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("user_password", etPassword.getText().toString()));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));


                HttpResponse response = httpclient.execute(httppost);
                jsonResult = Utilities.convertStreamToString(
                        response.getEntity().getContent()).toString();

                Log.d("JSON", jsonResult);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonResult;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            JSONArray array = null;

            try {
                array = new JSONArray(result);
                Log.d("ARRAY", array.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }



            if (!result.equals("")) {
                SharedPreferences.Editor editor = getSharedPreferences(Keystring.DALLAS_SUITES_PREFERENCES, MODE_PRIVATE).edit();
                try {

                    editor.putString(Keystring.USER_ID, array.getJSONObject(0).getString("user_id"));
                    editor.putString(Keystring.USER_NAME, array.getJSONObject(0).getString("user_name"));
                    editor.putString(Keystring.USER_LASTNAME, array.getJSONObject(0).getString("user_lastname"));
                    editor.putString(Keystring.USER_USERNAME, array.getJSONObject(0).getString("user_username"));
                    editor.putString(Keystring.USER_PASSWORD, etPassword.getText().toString());
                    editor.putString(Keystring.USER_EMAIL, array.getJSONObject(0).getString("user_email"));
                    editor.putString(Keystring.USER_DOB, array.getJSONObject(0).getString("user_dob"));
                    editor.putString(Keystring.USER_CI, array.getJSONObject(0).getString("user_ci"));

                    editor.commit();

                    startActivity(new Intent(Login.this, Profile.class));
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


        }else{

            Toast.makeText(Login.this, "No estas registrado", Toast.LENGTH_SHORT).show();

        }


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
