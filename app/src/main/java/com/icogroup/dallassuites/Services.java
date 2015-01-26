package com.icogroup.dallassuites;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by andres.torres on 10/24/14.
 */
public class Services extends Activity implements View.OnClickListener{

    Button restaurant, roomService, privacyAndSecurity, doubleReception, comfortAndAmenities;
    Typeface brandonlight,brandonregular;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services);

        init();
    }

    private void init() {

        brandonlight = Typeface.createFromAsset(getAssets(), "brandon_light.otf");
        brandonregular = Typeface.createFromAsset(getAssets(), "brandon_reg.otf");

        title = (TextView)findViewById(R.id.services_title);

        restaurant = (Button)findViewById(R.id.services_button_restaurant);
        roomService = (Button)findViewById(R.id.services_button_roomservice);
        privacyAndSecurity = (Button)findViewById(R.id.services_button_privacyandsecurity);
        doubleReception= (Button)findViewById(R.id.services_button_doublereception);
        comfortAndAmenities = (Button)findViewById(R.id.services_button_comfortandamenities);

        title.setTypeface(brandonlight);

        restaurant.setTypeface(brandonlight);
        roomService.setTypeface(brandonlight);
        privacyAndSecurity.setTypeface(brandonlight);
        doubleReception.setTypeface(brandonlight);
        comfortAndAmenities.setTypeface(brandonlight);

        restaurant.setOnClickListener(this);
        roomService.setOnClickListener(this);
        privacyAndSecurity.setOnClickListener(this);
        doubleReception.setOnClickListener(this);
        comfortAndAmenities.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.services_button_restaurant:
                startActivity(new Intent(Services.this, Restaurant.class));
                break;

            case R.id.services_button_roomservice:
                startActivity(new Intent(Services.this, Dallas_Popup.class).putExtra("Categoria","Room Service"));
                break;

            case R.id.services_button_privacyandsecurity:
                startActivity(new Intent(Services.this, Dallas_Popup.class).putExtra("Categoria","Privacidad y Seguridad"));
                break;

            case R.id.services_button_doublereception:
                startActivity(new Intent(Services.this, Dallas_Popup.class).putExtra("Categoria","Doble Recepcion"));
                break;

            case R.id.services_button_comfortandamenities:
                startActivity(new Intent(Services.this, Dallas_Popup.class).putExtra("Categoria","Comfort y Amenidades"));
                break;

        }

    }
}
