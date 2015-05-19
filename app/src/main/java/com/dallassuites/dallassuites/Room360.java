package com.dallassuites.dallassuites;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import com.dallassuites.util.GAUtils;

/**
 * Created by andres.torres on 1/8/15.
 */
public class Room360 extends Activity {

    WebView webview;
    TextView title;
    Typeface brandonlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room360);

        init();

        Log.d("URL", getIntent().getExtras().getString("Photo360"));

        webview.getSettings().setJavaScriptEnabled(true);

        webview.loadUrl(getIntent().getExtras().getString("Photo360"));

        title.setText(getIntent().getExtras().getString("RoomName"));
    }

    private void init() {

        brandonlight = Typeface.createFromAsset(getAssets(), "brandon_light.otf");

        webview = (WebView)findViewById(R.id.webview);

        title = (TextView)findViewById(R.id.room360_title);
        title.setTypeface(brandonlight);

    }


    @Override
    protected void onStart() {
        super.onStart();

        GAUtils.sendScreen(this, "Room360");
    }

}
