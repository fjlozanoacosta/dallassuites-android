package com.icogroup.dallassuites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.icogroup.util.Keystring;


public class Home extends Activity implements View.OnClickListener {

    Typeface brandonLight;
    Button rooms, services, register_profile;
    ImageButton login;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        init();

    }

    public void init(){

        brandonLight = Typeface.createFromAsset(getAssets(), "brandon_light.otf");

        prefs = getSharedPreferences(Keystring.DALLAS_SUITES_PREFERENCES, Context.MODE_PRIVATE);

        rooms = (Button)findViewById(R.id.home_button_rooms);
        services = (Button)findViewById(R.id.home_button_services);
        register_profile = (Button)findViewById(R.id.home_button_register_profile);

        login = (ImageButton)findViewById(R.id.home_imagebutton_login);

        rooms.setTypeface(brandonLight);
        services.setTypeface(brandonLight);
        register_profile.setTypeface(brandonLight);

        if(prefs.getBoolean("registered",false))
            register_profile.setText(R.string.home_profile);
        else
            register_profile.setText(R.string.home_register);

        rooms.setOnClickListener(this);
        services.setOnClickListener(this);
        register_profile.setOnClickListener(this);

        login.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.home_button_rooms:
                startActivity(new Intent(Home.this, Rooms.class));
                break;

            case R.id.home_button_services:
                startActivity(new Intent(Home.this, Services.class));
                break;

            case R.id.home_button_register_profile:
                if(register_profile.getText().toString().contains("REGISTRO"))
                    startActivity(new Intent(Home.this, Register.class));
                else
                    startActivity(new Intent(Home.this, Login.class));
                    break;

            case R.id.home_imagebutton_login:
                startActivity(new Intent(Home.this, Login.class));
                break;



        }

    }
}
