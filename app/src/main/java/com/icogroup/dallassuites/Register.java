package com.icogroup.dallassuites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
 * Created by andres.torres on 10/24/14.
 */
public class Register extends Activity implements View.OnClickListener, View.OnFocusChangeListener {

    Typeface brandonregular, brandonlight;
    EditText etName, etLastname, etEmail, etDateOfBirth, etID, etUsername, etPassword, etRepeatPassword, etKeyword;
    TextView title;
    Button register, bDob;
    String name, lastname, email, dob, id, username, password, repeatPassword, keyword;
    SharedPreferences prefs;
    LinearLayout registerLayout;
    static int REQUEST_CODE = 123456789;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        init();

        bDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Register.this, DOBPopup.class), REQUEST_CODE);
            }
        });

        registerLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }

            private void hideKeyboard(View v) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });

    }

    private void init() {

        prefs = getSharedPreferences(Keystring.DALLAS_SUITES_PREFERENCES, MODE_PRIVATE);

        brandonregular = Typeface.createFromAsset(getAssets(), "brandon_reg.otf");
        brandonlight = Typeface.createFromAsset(getAssets(), "brandon_light.otf");

        title = (TextView) findViewById(R.id.register_title);

        etName = (EditText) findViewById(R.id.register_edittext_name);
        etLastname = (EditText) findViewById(R.id.register_edittext_lastname);
        etEmail = (EditText) findViewById(R.id.register_edittext_email);
        etDateOfBirth = (EditText) findViewById(R.id.register_edittext_dateofbirth);
        etID = (EditText) findViewById(R.id.register_edittext_id);
        etUsername = (EditText) findViewById(R.id.register_edittext_username);
        etPassword = (EditText) findViewById(R.id.register_edittext_password);
        etRepeatPassword = (EditText) findViewById(R.id.register_edittext_repeatpassword);
        etKeyword = (EditText) findViewById(R.id.register_edittext_keyword);

        bDob = (Button)findViewById(R.id.button_register_dob);

        register = (Button) findViewById(R.id.register_button_register);

        registerLayout = (LinearLayout) findViewById(R.id.registerlayout);

        register.setOnClickListener(this);

        title.setTypeface(brandonlight);

        etName.setTypeface(brandonregular);
        etLastname.setTypeface(brandonregular);
        etEmail.setTypeface(brandonregular);
        etDateOfBirth.setTypeface(brandonregular);
        etID.setTypeface(brandonregular);
        etUsername.setTypeface(brandonregular);
        etPassword.setTypeface(brandonregular);
        etRepeatPassword.setTypeface(brandonregular);
        etKeyword.setTypeface(brandonregular);

        etPassword.setOnFocusChangeListener(this);



        register.setTypeface(brandonlight);


    }

    @Override
    public void onClick(View view) {

        name = etName.getText().toString();
        lastname = etLastname.getText().toString();
        email = etEmail.getText().toString();
        dob = etDateOfBirth.getText().toString();
        id = etID.getText().toString();
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();
        repeatPassword = etRepeatPassword.getText().toString();
        keyword = etKeyword.getText().toString();


        if (!username.equals("") && !email.equals("") && !password.equals("")
                && !repeatPassword.equals("") &&
                !dob.equals("") ) {

            if (password.equals(repeatPassword)) {

                switch (view.getId()) {

                    case R.id.register_button_register:

                        new RegisterAsync().execute();
                        new AddPasswordAsync().execute();

                        break;
                }
            } else {

                Toast.makeText(Register.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();

            }

        } else {

            if (username.equals(""))
                Toast.makeText(Register.this, "Debe ingresar algún nombre de usuario", Toast.LENGTH_SHORT).show();
            else if (password.equals(""))
                Toast.makeText(Register.this, "Debe ingresar alguna contraseña", Toast.LENGTH_SHORT).show();
            else if (repeatPassword.equals(""))
                Toast.makeText(Register.this, "Debe repetir la contraseña", Toast.LENGTH_SHORT).show();
            else if (email.equals(""))
                Toast.makeText(Register.this, "Debe ingresar su correo electronico", Toast.LENGTH_SHORT).show();
            else if (dob.equals(""))
                Toast.makeText(Register.this, "Debe ingresar su fecha de nacimiento", Toast.LENGTH_SHORT).show();
            else if (keyword.equals(""))
                Toast.makeText(Register.this, "Debe ingresar su palabra clave", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onFocusChange(View view, boolean b) {

      /*  if (view.getId() == R.id.register_edittext_password && etPassword.hasFocus())*/
            startActivity(new Intent(Register.this, Dallas_Popup.class).putExtra("Categoria", "Contraseña"));
     /*   else if(view.getId() == R.id.register_edittext_dateofbirth && etDateOfBirth.hasFocus())
            startActivityForResult(new Intent(Register.this, DOBPopup.class), REQUEST_CODE);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(REQUEST_CODE == requestCode && resultCode == RESULT_OK){

            etDateOfBirth.setText(data.getExtras().getString("Date"));


        }

    }

    class RegisterAsync extends AsyncTask<Void, Void, String> {

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

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
                        7);
                nameValuePairs.add(new BasicNameValuePair("user_name", name));
                nameValuePairs.add(new BasicNameValuePair("user_lastname", lastname));
                nameValuePairs.add(new BasicNameValuePair("user_username", username));
                nameValuePairs.add(new BasicNameValuePair("user_email", email));
                nameValuePairs.add(new BasicNameValuePair("user_dob", dob));
                nameValuePairs.add(new BasicNameValuePair("user_ci", id));
                nameValuePairs.add(new BasicNameValuePair("user_keyword", keyword));
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

            if (result.contains("id")) {

                String[] arrayresult = result.split(" ");

                SharedPreferences.Editor editor = prefs.edit();
                /*Cambiar a true*/
                editor.putBoolean("registered", true);
                editor.putString(Keystring.USER_ID, arrayresult[arrayresult.length - 1]);
                editor.putString(Keystring.USER_PASSWORD, password);
                editor.commit();

            } else {
                Toast.makeText(Register.this, result, Toast.LENGTH_SHORT).show();

            }

        }
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
                        2);

                nameValuePairs.add(new BasicNameValuePair("user_id", prefs.getString(Keystring.USER_ID, "")));
                nameValuePairs.add(new BasicNameValuePair("user_password", password));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpclient.execute(httppost);
                jsonResult = Utilities.convertStreamToString(
                        response.getEntity().getContent()).toString();

                object = new JSONObject(jsonResult);

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

            if (result.contains("Registrada")) {

                Intent intent = new Intent(Register.this, Profile.class);
                startActivity(intent);
                finish();

            } else {

                Toast.makeText(Register.this, result, Toast.LENGTH_SHORT).show();

            }


        }
    }


}
