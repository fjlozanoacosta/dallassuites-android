package com.icogroup.dallassuites;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by andres.torres on 10/29/14.
 */
public class Restaurant extends Activity implements View.OnClickListener {

    ImageButton back;
    TextView title, desayuno, ensaladas, depicar, sandwiches, pizzas, postres, restodeldia, bebidas;
    Button bDesayuno, bEnsaladas, bDePicar, bSandwiches, bPizzas, bPostres, bRestoDelDia, bBebidas;
    Typeface brandonregular, brandonlight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);

        init();
    }

    private void init() {

        brandonregular = Typeface.createFromAsset(getAssets(), "brandon_reg.otf");
        brandonlight = Typeface.createFromAsset(getAssets(), "brandon_light.otf");

        title = (TextView) findViewById(R.id.restaurant_title);

        back = (ImageButton)findViewById(R.id.restaurant_back);

        desayuno = (TextView) findViewById(R.id.restaurant_text_desayuno);
        ensaladas = (TextView) findViewById(R.id.restaurant_text_ensaladas);
        depicar = (TextView) findViewById(R.id.restaurant_text_depicar);
        sandwiches = (TextView) findViewById(R.id.restaurant_text_sandwiches);
        pizzas = (TextView) findViewById(R.id.restaurant_text_pizzas);
        postres = (TextView) findViewById(R.id.restaurant_text_postres);
        restodeldia = (TextView) findViewById(R.id.restaurant_text_restodeldia);
        bebidas = (TextView) findViewById(R.id.restaurant_text_bebidas);

        bDesayuno = (Button) findViewById(R.id.restaurant_button_desayuno);
        bEnsaladas = (Button) findViewById(R.id.restaurant_button_ensaladas);
        bDePicar = (Button) findViewById(R.id.restaurant_button_depicar);
        bSandwiches = (Button) findViewById(R.id.restaurant_button_sandwiches);
        bPizzas = (Button) findViewById(R.id.restaurant_button_pizzas);
        bPostres = (Button) findViewById(R.id.restaurant_button_postres);
        bRestoDelDia = (Button) findViewById(R.id.restaurant_button_restodeldia);
        bBebidas = (Button) findViewById(R.id.restaurant_button_bebidas);

        back.setOnClickListener(this);

        bDesayuno.setOnClickListener(this);
        bEnsaladas.setOnClickListener(this);
        bDePicar.setOnClickListener(this);
        bSandwiches.setOnClickListener(this);
        bPizzas.setOnClickListener(this);
        bPostres.setOnClickListener(this);
        bRestoDelDia.setOnClickListener(this);
        bBebidas.setOnClickListener(this);


        title.setTypeface(brandonlight);

        desayuno.setTypeface(brandonregular);
        ensaladas.setTypeface(brandonregular);
        depicar.setTypeface(brandonregular);
        sandwiches.setTypeface(brandonregular);
        pizzas.setTypeface(brandonregular);
        postres.setTypeface(brandonregular);
        restodeldia.setTypeface(brandonregular);
        bebidas.setTypeface(brandonregular);

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.restaurant_back:
                finish();
                break;

            default:
                startActivity(new Intent(Restaurant.this, RestaurantDetail.class));
                break;

        }

    }
}
