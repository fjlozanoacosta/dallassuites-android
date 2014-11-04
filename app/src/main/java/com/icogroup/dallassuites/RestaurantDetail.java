package com.icogroup.dallassuites;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by andres.torres on 11/3/14.
 */
public class RestaurantDetail extends Activity {


    Typeface brandonregular, brandonlight;
    ListView listViewFood;
    int[] foodNames = {R.string.tequeños, R.string.abrebocasvenezolanos, R.string.nachos, R.string.conchadepapa, R.string.dedosdemozzarella, R.string.calamaresalajillo};
    int[] foodDescrips = {R.string.tequeños_descrip, R.string.abrebocasvenezolanos_descrip, R.string.nachos_descrip, R.string.conchadepapa_descrip, R.string.dedosdemozarella_descrip, R.string.calamaresalajillo_descrip};
    TextView foodName, foodDescrip, title;
    ImageButton back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurantdetail);

        init();

        listViewFood.setAdapter(new FoodAdapter(getApplicationContext()));

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

        listViewFood = (ListView) findViewById(R.id.restaurantdetails_listview_foods);

        title = (TextView)findViewById(R.id.restaurantdetail_title);

        title.setTypeface(brandonlight);

        back = (ImageButton)findViewById(R.id.restaurantdetail_back_button);


    }

    class FoodAdapter extends BaseAdapter {

        public LayoutInflater inflater = null;

        public FoodAdapter(Context context) {

            inflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return foodNames.length;
        }

        @Override
        public Object getItem(int position) {
            return foodNames[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = inflater.inflate(R.layout.restaurant_detail_element, null, false);

            foodName = (TextView)view.findViewById(R.id.restaurantdetail_foodname);
            foodDescrip = (TextView)view.findViewById(R.id.restaurantdetail_fooddescription);

            foodName.setTypeface(brandonregular);
            foodDescrip.setTypeface(brandonregular);

            foodName.setText(foodNames[i]);
            foodDescrip.setText(foodDescrips[i]);


            return view;
        }
    }


}
