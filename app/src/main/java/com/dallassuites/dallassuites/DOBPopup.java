package com.dallassuites.dallassuites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.dallassuites.custompicker.DayListener;
import com.dallassuites.custompicker.MonthListener;
import com.dallassuites.custompicker.PickerAdapter;
import com.dallassuites.custompicker.YearListener;

import java.util.ArrayList;

/**
 * Created by andres.torres on 11/3/14.
 */
public class DOBPopup extends Activity implements View.OnClickListener{

    PickerAdapter dayAdapter, monthAdapter, yearAdapter;
    ListView dayPicker, monthPicker, yearPicker;
    Button bOK;
    ArrayList<String> months, days, years;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dobpopup);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        init();
        initPickerData();


    }

    private void init() {

        dayPicker = (ListView)findViewById(R.id.dobpopup_dia);
        monthPicker = (ListView)findViewById(R.id.dobpopup_mes);
        yearPicker = (ListView)findViewById(R.id.dobpopup_ano);

        bOK = (Button)findViewById(R.id.dodpopup_button_ok);

        bOK.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        setResult(RESULT_OK, new Intent(DOBPopup.this, Register.class).putExtra("Date", dayAdapter.getMid() + " - " + monthAdapter.getMid() + " - " + yearAdapter.getMid()));
        finish();

    }

    private void initPickerData() {

        months = new ArrayList<String>();
        months.add("--");
        months.add("Ene");
        months.add("Feb");
        months.add("Mar");
        months.add("Abr");
        months.add("May");
        months.add("Jun");
        months.add("Jul");
        months.add("Ago");
        months.add("Sep");
        months.add("Oct");
        months.add("Nov");
        months.add("Dic");
        months.add("--");

        //Se inicia en Enero
        days = new ArrayList<String>();
        days.add("--");
        for (int i = 1; i <= 31; i++) {
            if(i<=9)
                days.add("0" + i);
            else
                days.add("" + i);
        }
        days.add("--");

        years = new ArrayList<String>();
        years.add("--");
        for(int i= 1955; i<2016; i++){
            years.add("" + i);
        }
        years.add("--");

        dayAdapter = new PickerAdapter(DOBPopup.this, days);
        monthAdapter = new PickerAdapter(DOBPopup.this, months);
        yearAdapter = new PickerAdapter(DOBPopup.this, years);

        dayPicker.setAdapter(dayAdapter);
        monthPicker.setAdapter(monthAdapter);
        yearPicker.setAdapter(yearAdapter);

        dayPicker.setOnScrollListener(new DayListener(dayPicker, dayAdapter));
        monthPicker.setOnScrollListener(new MonthListener(monthPicker, monthAdapter, dayAdapter, yearAdapter));
        yearPicker.setOnScrollListener(new YearListener(yearPicker, monthAdapter, dayAdapter, yearAdapter));
    }

}
