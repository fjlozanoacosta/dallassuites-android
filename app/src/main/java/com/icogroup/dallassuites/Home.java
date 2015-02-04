package com.icogroup.dallassuites;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;


public class Home extends Activity implements View.OnClickListener{

    RelativeLayout splashBackground, home_buttons;
    ImageView logo;
    Button servicios, suites, registro, login;
    float yFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.home);

        init();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        float width = size.x;
        final float height = size.y;

        home_buttons.setY(height );


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                    ObjectAnimator alphaAnimationBackground = ObjectAnimator.ofFloat(splashBackground, "alpha", 1, 0);
                    ObjectAnimator translateAnimationLogo = ObjectAnimator.ofFloat(logo, "translationY", 0, -height*0.17f);
                    ObjectAnimator translateAnimationButtons = ObjectAnimator.ofFloat(home_buttons, "translationY", 0, -height*0.23f);
                    alphaAnimationBackground.setDuration(1000);
                    translateAnimationLogo.setDuration(1000);
                    translateAnimationButtons.setDuration(1000);
                    alphaAnimationBackground.start();
                    translateAnimationLogo.start();
                    translateAnimationButtons.start();


            }
        }, 2000);

    }

    public void init(){

        splashBackground = (RelativeLayout)findViewById(R.id.splash_background);
        logo = (ImageView)findViewById(R.id.home_logo);
        home_buttons = (RelativeLayout)findViewById(R.id.home_buttons);
        servicios = (Button)findViewById(R.id.home_button_servicios);
        suites = (Button)findViewById(R.id.home_button_suites);
        registro = (Button)findViewById(R.id.home_button_registro);
        login = (Button)findViewById(R.id.home_login);

        servicios.setOnClickListener(this);
        suites.setOnClickListener(this);
        registro.setOnClickListener(this);
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

        switch (view.getId()){

            case R.id.home_button_servicios:
                startActivity(new Intent(Home.this, Services.class));
                break;

            case R.id.home_button_suites:
                startActivity(new Intent(Home.this, Rooms.class));
                break;

            case R.id.home_button_registro:
                startActivity(new Intent(Home.this, Register.class));
                break;

            case R.id.home_login:
                startActivity(new Intent(Home.this, Login.class));
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_in_left);
                break;

        }

    }
}
