package com.icogroup.dallassuites;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by andres.torres on 10/29/14.
 */
public class Dallas_Popup extends Activity implements View.OnClickListener{

    ImageButton buttonClose;
    Button buttonOK;
    TextView title, text;
    Typeface brandonregular, brandonlight;
    ImageView image;
    String pantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dallas_popup);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        init();

    }

    private void init() {

        brandonregular = Typeface.createFromAsset(getAssets(), "brandon_reg.otf");
        brandonlight = Typeface.createFromAsset(getAssets(), "brandon_light.otf");

        buttonClose = (ImageButton)findViewById(R.id.register_popup_imagebutton_close);
        buttonOK = (Button)findViewById(R.id.register_popup_button_ok);
        title = (TextView)findViewById(R.id.register_popup_title);
        text = (TextView)findViewById(R.id.register_popup_text);
        image = (ImageView)findViewById(R.id.register_popup_img);

        title.setTypeface(brandonregular);
        text.setTypeface(brandonregular);
        buttonOK.setTypeface(brandonlight);



        buttonOK.setOnClickListener(this);
        buttonClose.setOnClickListener(this);

        setResources();

    }

    private void setResources() {

        pantalla = getIntent().getExtras().getString("Categoria");

        if(pantalla.contains("Contraseña")){
            image.setImageResource(R.drawable.home_popup_pass);
            title.setText(getResources().getString(R.string.register_password));
            text.setText(getResources().getString(R.string.lorem_ipsum));
        }else if(pantalla.contains("Room Service")){
            image.setImageResource(R.drawable.servicios_room);
            title.setText(getResources().getString(R.string.services_roomservice));
            text.setText(getResources().getString(R.string.lorem_ipsum));
        }else if(pantalla.contains("Privacidad y Seguridad")){
            image.setImageResource(R.drawable.home_popup_pass);
            title.setText(getResources().getString(R.string.services_privacyandsecurity));
            text.setText(getResources().getString(R.string.lorem_ipsum));
        }else if(pantalla.contains("Doble Recepcion")){
            image.setImageResource(R.drawable.servicios_doble);
            title.setText(getResources().getString(R.string.services_doublereception));
            text.setText(getResources().getString(R.string.lorem_ipsum));
        }else if(pantalla.contains("Comfort y Amenidades")){
            image.setImageResource(R.drawable.servicios_confort);
            title.setText(getResources().getString(R.string.services_comfortandamenities));
            text.setText(getResources().getString(R.string.lorem_ipsum));
        }


    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
