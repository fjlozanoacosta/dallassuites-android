package com.icogroup.dallassuites;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

/**
 * Created by andres.torres on 1/8/15.
 */
public class Room360 extends Activity {

    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room360);

        init();

        Log.d("URL",getIntent().getExtras().getString("Photo360"));

        webview.getSettings().setJavaScriptEnabled(true);

        webview.loadUrl(getIntent().getExtras().getString("Photo360"));
    }

    private void init() {

        webview = (WebView)findViewById(R.id.webview);

    }


}
