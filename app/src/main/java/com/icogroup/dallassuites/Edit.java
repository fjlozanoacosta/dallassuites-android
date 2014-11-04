package com.icogroup.dallassuites;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by andres.torres on 10/31/14.
 */
public class Edit extends Activity implements View.OnFocusChangeListener{

    Typeface brandonregular, brandonlight;
    EditText etName, etLastname, etEmail, etDateOfBirth, etID, etUsername, etPassword, etRepeatPassword;
    LinearLayout editLayout;
    TextView title;
    ImageButton back;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);

        init();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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

        back = (ImageButton)findViewById(R.id.edit_back);

        etName = (EditText) findViewById(R.id.edit_edittext_name);
        etLastname = (EditText) findViewById(R.id.edit_edittext_lastname);
        etEmail = (EditText) findViewById(R.id.edit_edittext_email);
        etDateOfBirth = (EditText) findViewById(R.id.edit_edittext_dateofbirth);
        etID = (EditText) findViewById(R.id.edit_edittext_id);
        etUsername = (EditText) findViewById(R.id.edit_edittext_username);
        etPassword = (EditText) findViewById(R.id.edit_edittext_password);
        etRepeatPassword = (EditText) findViewById(R.id.edit_edittext_addpassword);

        save = (Button) findViewById(R.id.edit_button_save);

        editLayout = (LinearLayout) findViewById(R.id.editlayout);

        title.setTypeface(brandonlight);

        etName.setTypeface(brandonregular);
        etLastname.setTypeface(brandonregular);
        etEmail.setTypeface(brandonregular);
        etDateOfBirth.setTypeface(brandonregular);
        etID.setTypeface(brandonregular);
        etUsername.setTypeface(brandonregular);
        etPassword.setTypeface(brandonregular);
        etRepeatPassword.setTypeface(brandonregular);

        etPassword.setOnFocusChangeListener(this);

        save.setTypeface(brandonlight);


    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }
}
