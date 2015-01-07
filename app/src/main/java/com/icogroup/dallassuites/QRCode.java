package com.icogroup.dallassuites;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.icogroup.qrresources.Contents;
import com.icogroup.qrresources.QRCodeEncoder;
import com.icogroup.util.Keystring;

/**
 * Created by andres.torres on 1/6/15.
 */
public class QRCode extends Activity {

    ImageView ivQRCode;
    String qrContent;
    SharedPreferences prefs;
    String userID, userPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode);

        init();

        userID = prefs.getString(Keystring.USER_ID, "0");
        userPwd = prefs.getString(Keystring.USER_PASSWORD, "0");

        qrContent = "[{\"id\" : \" " + userID + "\",\"pwd\":\"" + userPwd + "\"}]";

        Log.d("QRContent", qrContent);


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

        ivQRCode = (ImageView)findViewById(R.id.ivQRCode);

    }
}
