package com.icogroup.dallassuites;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by andres.torres on 1/5/15.
 */
public class NewPasswordPopup extends Activity {

    Typeface brandonreg, brandonlight;
    TextView copy;
    EditText etPassword, etRepeatPassword, etTip;
    Button bAdd;

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
                finish();
            }
        });

    }

    private void init() {

        brandonreg = Typeface.createFromAsset(getAssets(),"brandon_reg.otf");
        brandonlight = Typeface.createFromAsset(getAssets(),"brandon_light.otf");

        copy = (TextView)findViewById(R.id.newpassword_copy);
        copy.setTypeface(brandonreg);

        etPassword = (EditText)findViewById(R.id.perfil_edittext_password);
        etPassword.setTypeface(brandonreg);
        etRepeatPassword = (EditText)findViewById(R.id.perfil_edittext_repeatpassword);
        etRepeatPassword.setTypeface(brandonreg);
        etTip = (EditText)findViewById(R.id.perfil_edittext_tip);
        etTip.setTypeface(brandonreg);
        bAdd = (Button)findViewById(R.id.perfil_popup_button_agregar);
        bAdd.setTypeface(brandonlight);





    }
}
