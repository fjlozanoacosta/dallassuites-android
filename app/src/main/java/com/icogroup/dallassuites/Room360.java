package com.icogroup.dallassuites;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by andres.torres on 10/31/14.
 */
public class Room360 extends Activity{

    Typeface brandonregular, brandonlight;
    WebView wvRoom360;
    TextView title;
    ImageButton back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room360);

        init();

        wvRoom360.getSettings().setJavaScriptEnabled(true);
        wvRoom360.loadUrl(getIntent().getExtras().getString("room_360_url"));

        title.setText(getIntent().getExtras().getString("room_name").toUpperCase());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void init() {

        brandonregular = Typeface.createFromAsset(getAssets(), "brandon_reg.otf");
        brandonlight = Typeface.createFromAsset(getAssets(), "brandon_light.otf");

        wvRoom360 = (WebView)findViewById(R.id.room360_webview_room360);
        title = (TextView)findViewById(R.id.room360_title);

        back = (ImageButton)findViewById(R.id.rooms360_imagebutton_back);

        title.setTypeface(brandonlight);

    }
}
