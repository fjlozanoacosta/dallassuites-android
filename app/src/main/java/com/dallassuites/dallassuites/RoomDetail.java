package com.dallassuites.dallassuites;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by andres.torres on 10/31/14.
 */
public class RoomDetail extends Activity {

    Typeface brandonregular, brandonlight;
    TextView title;
    String photos, photo360;
    ImageView pic1, pic2, pic3, pic4;
    String[] separatedPhotos;
    ImageButton ibRoom360;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roomdetail);

        init();

        photos = getIntent().getExtras().getString("Photos");
        photo360 = getIntent().getExtras().getString("Photo360");

        if (photos != null) ;
        separatedPhotos = photos.replace(" ", "").split(";");

        Picasso.with(this)
                .load("https://s3.amazonaws.com/dallassuites_app/rooms/" + separatedPhotos[0])
                .into(pic1);
        Picasso.with(this)
                .load("https://s3.amazonaws.com/dallassuites_app/rooms/" + separatedPhotos[1])
                .into(pic2);
        Picasso.with(this)
                .load("https://s3.amazonaws.com/dallassuites_app/rooms/" + separatedPhotos[2])
                .into(pic3);
        Picasso.with(this)
                .load("https://s3.amazonaws.com/dallassuites_app/rooms/" + separatedPhotos[3])
                .into(pic4);


        ibRoom360.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RoomDetail.this, Room360.class).putExtra("Photo360", photo360).putExtra("RoomName", getIntent().getExtras().getString("RoomName")));
            }
        });

    }

    private void init() {

        brandonregular = Typeface.createFromAsset(getAssets(), "brandon_reg.otf");
        brandonlight = Typeface.createFromAsset(getAssets(), "brandon_light.otf");

        pic1 = (ImageView) findViewById(R.id.pic1);
        pic2 = (ImageView) findViewById(R.id.pic2);
        pic3 = (ImageView) findViewById(R.id.pic3);
        pic4 = (ImageView) findViewById(R.id.pic4);

        title = (TextView) findViewById(R.id.roomdetail_title);
        title.setTypeface(brandonlight);

        ibRoom360 = (ImageButton) findViewById(R.id.roomdetail_imagebutton_room360);


        ImageView img = (ImageView) findViewById(R.id.hand);
            img.setBackgroundResource(R.drawable.hand);
            AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
            frameAnimation.start();




    }
}
