package com.dallassuites.dallassuites;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.dallassuites.util.Keystring;

/**
 * Created by andres.torres on 5/19/15.
 */
public class TwoButtonPopup extends Activity {

    Typeface brandonregular, brandonlight;
    TextView title, text;
    Button btnAhoraNo, btnIrALaWeb;
    SharedPreferences prefs;

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
        btnIrALaWeb = (Button)findViewById(R.id.btn_iralaweb);
        btnIrALaWeb.setTypeface(brandonlight);

        btnAhoraNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnIrALaWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TwoButtonPopup.this, Room360.class).putExtra("Photo360", "http://www.dallassuiteshotel.com").putExtra("RoomName", "Dallas Suites"));
                finish();
            }
        });


    }
}
