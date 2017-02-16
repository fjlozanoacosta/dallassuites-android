package com.dallassuites.dallassuites;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import com.dallassuites.util.GAUtils;

/**
 * Created by Rosario.Vivas on 2/16/17.
 */

public class Rates extends Activity {
    WebView webview;
    TextView title;
    Typeface brandonlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rates);

        init();

        Log.d("URL", getResources().getString(R.string.rates_url));

        webview.getSettings().setJavaScriptEnabled(true);

        webview.loadUrl(getResources().getString(R.string.rates_url));

        title.setText(getResources().getString(R.string.rates_title));
    }

    private void init() {

        brandonlight = Typeface.createFromAsset(getAssets(), "brandon_light.otf");

        webview = (WebView)findViewById(R.id.webview);

        title = (TextView)findViewById(R.id.rates_title);
        title.setTypeface(brandonlight);

    }


    @Override
    protected void onStart() {
        super.onStart();

        GAUtils.sendScreen(this, "Rates");
    }
}
