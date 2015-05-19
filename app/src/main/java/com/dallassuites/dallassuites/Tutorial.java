package com.dallassuites.dallassuites;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dallassuites.util.GAUtils;

/**
 * Created by andres.torres on 2/9/15.
 */
public class Tutorial extends Activity {

    Button bContinue;
    Typeface brandonlight, brandonregular;
    TextView copy, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);

        init();

        bContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Tutorial.this, Profile.class));
                finish();
            }
        });

    }

    private void init() {

        brandonlight = Typeface.createFromAsset(getAssets(), "brandon_light.otf");
        brandonregular = Typeface.createFromAsset(getAssets(), "brandon_reg.otf");

        bContinue = (Button)findViewById(R.id.bcontinue);
        bContinue.setTypeface(brandonlight);

        copy = (TextView)findViewById(R.id.tvCopyTutorial);
        copy.setTypeface(brandonregular);

        title = (TextView)findViewById(R.id.tvTitleTutorial);
        title.setTypeface(brandonregular);


    }


    @Override
    protected void onStart() {
        super.onStart();

        GAUtils.sendScreen(this, "Tutorial");
    }
}
