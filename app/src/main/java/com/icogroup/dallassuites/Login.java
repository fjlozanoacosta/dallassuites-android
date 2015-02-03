package com.icogroup.dallassuites;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
    ImageButton ibRetrievePassword;
    Button login;
    SharedPreferences prefs;
    TextView title;
    RelativeLayout infoPopup;
    ProgressBar progressBar;


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

        infoPopup = (RelativeLayout)findViewById(R.id.info_popup);

        login.setOnClickListener(this);

        title.setTypeface(brandonreg);
        etUsername.setTypeface(brandonreg);
        etPassword.setTypeface(brandonreg);

        ibRetrievePassword.setOnClickListener(this);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.login_imagebutton_retrievepassword:
                startActivity(new Intent(Login.this, RetrievePassword.class));
                break;
            case R.id.login_button_login:
                if(etUsername.getText().toString().equals(""))
                    Toast.makeText(Login.this, "Debe introducir su nombre de usuario", Toast.LENGTH_SHORT).show();
                else if(etPassword.getText().toString().equals(""))
                    Toast.makeText(Login.this, "Debe introducir su contraseña", Toast.LENGTH_SHORT).show();
                else {
                    new LoginAsync().execute();
                    progressBar.setVisibility(View.VISIBLE);
                }

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
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (!result.contains("[]")) {
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

                    editor.apply();

                    startActivity(new Intent(Login.this, Profile.class));
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


        }else{
          infoPopup.setVisibility(View.VISIBLE);

                login.setOnClickListener(null);

                ObjectAnimator animation1 = ObjectAnimator.ofFloat(infoPopup,
                        "translationY", 0 - infoPopup.getHeight(), 0);
                animation1.setDuration(1000);
                animation1.start();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ObjectAnimator animation1 = ObjectAnimator.ofFloat(infoPopup,
                                "translationY", 0 , 0 - infoPopup.getHeight());
                        animation1.setDuration(1000);
                        animation1.start();
                        login.setOnClickListener(Login.this);
                    }
                }, 2000);



        }
            progressBar.setVisibility(View.GONE);


       }
    }


}
