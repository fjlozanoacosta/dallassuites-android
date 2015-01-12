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

import com.icogroup.custompicker.Fecha;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres.torres on 10/31/14.
 */
public class Edit extends Activity implements View.OnClickListener {

    Typeface brandonregular, brandonlight;
    EditText etName, etLastname, etDateOfBirth, etID;
    LinearLayout editLayout;
    TextView title, etUsername, etEmail, tvName, tvUsername;
    SharedPreferences prefs;
    Button save, bDob;
    public static int REQUEST_CODE = 987456321;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);

        prefs = getSharedPreferences(Keystring.DALLAS_SUITES_PREFERENCES, MODE_PRIVATE);

        init();

        tvUsername.setText(getIntent().getExtras().getString("Username"));
        tvName.setText(getIntent().getExtras().getString("Name"));


        editLayout.setOnTouchListener(new View.OnTouchListener() {
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

        brandonregular = Typeface.createFromAsset(getAssets(), "brandon_reg.otf");
        brandonlight = Typeface.createFromAsset(getAssets(), "brandon_light.otf");

        title = (TextView) findViewById(R.id.edit_title);



       /* etName = (EditText) findViewById(R.id.edit_edittext_name);
        etLastname = (EditText) findViewById(R.id.edit_edittext_lastname);*/
        etEmail = (TextView) findViewById(R.id.edit_edittext_email);
        etDateOfBirth = (EditText) findViewById(R.id.edit_edittext_dateofbirth);
        etID = (EditText) findViewById(R.id.edit_edittext_id);
        etUsername = (TextView) findViewById(R.id.edit_edittext_username);

        tvName = (TextView) findViewById(R.id.edit_textview_nombre);
        tvUsername = (TextView) findViewById(R.id.edit_textview_username);

        bDob = (Button) findViewById(R.id.button_dob);

        save = (Button) findViewById(R.id.edit_button_save);

        editLayout = (LinearLayout) findViewById(R.id.editlayout);

       /* etName.setText(prefs.getString(Keystring.USER_NAME, ""));
        etLastname.setText(prefs.getString(Keystring.USER_LASTNAME, ""));*/
        etEmail.setText(prefs.getString(Keystring.USER_EMAIL, ""));
        etDateOfBirth.setText(reformatDate(prefs.getString(Keystring.USER_DOB, "")));
        etID.setText(prefs.getString(Keystring.USER_CI, ""));
        etUsername.setText(prefs.getString(Keystring.USER_USERNAME, ""));


        title.setTypeface(brandonlight);

      /*  etName.setTypeface(brandonregular);
        etLastname.setTypeface(brandonregular);*/
        etEmail.setTypeface(brandonregular);
        etDateOfBirth.setTypeface(brandonregular);
        etID.setTypeface(brandonregular);
        etUsername.setTypeface(brandonregular);

        save.setTypeface(brandonlight);

        save.setOnClickListener(this);


        bDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Edit.this, DOBPopup.class), REQUEST_CODE);
            }
        });

    }

    /*@Override
    public void onFocusChange(View view, boolean b) {
       if(view.getId() == R.id.edit_edittext_dateofbirth && etDateOfBirth.hasFocus()) {
          startActivityForResult(new Intent(Edit.this, DOBPopup.class), REQUEST_CODE);
       }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (REQUEST_CODE == requestCode && resultCode == RESULT_OK) {

            etDateOfBirth.setText(data.getExtras().getString("Date"));


        }
    }

    public String reformatDate(String date) {

        String[] dateArray = date.replace(" ", "").split("-");

        return dateArray[2] + " - " + dateArray[1] + " - " + dateArray[0];

    }

    public String sendReformatDate(String date) {

        String[] dateArray = date.replace(" ", "").split("-");

        return dateArray[2] + "-" + Fecha.parseToNumber(dateArray[1]) + "-" + dateArray[0];

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.edit_button_save) {

            new UpdateProfileAsync().execute();


        }

    }

    class UpdateProfileAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            String url = "http://ec2-54-218-96-225.us-west-2.compute.amazonaws.com/api/?o=updateUserInfo";
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
              /*  nameValuePairs.add(new BasicNameValuePair("user_name", etName.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("user_lastname", etLastname.getText().toString()));*/
                nameValuePairs.add(new BasicNameValuePair("user_dob", sendReformatDate(etDateOfBirth.getText().toString())));
                nameValuePairs.add(new BasicNameValuePair("user_ci", etID.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("user_username", etUsername.getText().toString()));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpclient.execute(httppost);
                jsonResult = Utilities.convertStreamToString(
                        response.getEntity().getContent()).toString();

                object = new JSONObject(jsonResult);

                Log.d("JSON", jsonResult);


            } catch (Exception e) {
                e.printStackTrace();

            }


            return jsonResult;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result.contains("msg")) {

                SharedPreferences.Editor editor = prefs.edit();

             /*   editor.putString(Keystring.USER_NAME, etName.getText().toString());
                editor.putString(Keystring.USER_LASTNAME, etLastname.getText().toString());*/
                editor.putString(Keystring.USER_CI, etID.getText().toString());
                editor.putString(Keystring.USER_DOB, reformatDate(etDateOfBirth.getText().toString()));

                editor.commit();

                new UpdatePasswordAsync().execute();

            }


        }
    }


    class UpdatePasswordAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            String url = "http://ec2-54-218-96-225.us-west-2.compute.amazonaws.com/api/?o=updateUserPassword";
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
                nameValuePairs.add(new BasicNameValuePair("old_password", prefs.getString(Keystring.USER_PASSWORD, "")));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpclient.execute(httppost);
                jsonResult = Utilities.convertStreamToString(
                        response.getEntity().getContent()).toString();

                object = new JSONObject(jsonResult);

                Log.d("JSON", jsonResult);


            } catch (Exception e) {
                e.printStackTrace();

            }


            return jsonResult;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (!result.contains("error")) {

                SharedPreferences.Editor editor = prefs.edit();


                editor.commit();

                finish();
            } else {

                Toast.makeText(Edit.this, "No se pudo actualizar los datos", Toast.LENGTH_SHORT).show();

            }

        }
    }


}
