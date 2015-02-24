package com.dallassuites.dallassuites;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.dallassuites.qrresources.Contents;
import com.dallassuites.qrresources.QRCodeEncoder;
import com.dallassuites.util.Keystring;

/**
 * Created by andres.torres on 1/6/15.
 */
public class QRCode extends Activity {

    ImageView ivQRCode;
    String qrContent;
    SharedPreferences prefs;
    String userID, userPwd;
    Typeface brandonreg;
    TextView copy1, copy2, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode);

        init();

        userID = prefs.getString(Keystring.USER_ID, "0");
        userPwd = prefs.getString(Keystring.USER_PASSWORD, "0");

        qrContent = "[{\"id\" : \" " + userID + "\",\"pwd\":\"" + userPwd + "\"}]";

        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrContent,
                null,
                Contents.Type.TEXT,
                BarcodeFormat.QR_CODE.toString(),
                1000);


        try {
            ivQRCode.setImageBitmap(qrCodeEncoder.encodeAsBitmap());
        } catch (WriterException e) {
            e.printStackTrace();
        }




    }

    private void init() {

        prefs = getSharedPreferences(Keystring.DALLAS_SUITES_PREFERENCES, MODE_PRIVATE);

        brandonreg = Typeface.createFromAsset(getAssets(), "brandon_reg.otf");

        ivQRCode = (ImageView)findViewById(R.id.ivQRCode);

        copy1 = (TextView)findViewById(R.id.qrcode_copy1);
        copy1.setTypeface(brandonreg);
        copy2 = (TextView)findViewById(R.id.qrcode_copy2);
        copy2.setTypeface(brandonreg);

        title = (TextView)findViewById(R.id.qrcode_title);
        title.setTypeface(brandonreg);

    }
}
