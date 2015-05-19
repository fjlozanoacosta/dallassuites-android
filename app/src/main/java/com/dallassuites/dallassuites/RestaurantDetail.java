package com.dallassuites.dallassuites;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dallassuites.util.GAUtils;
import com.dallassuites.util.Utilities;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by andres.torres on 11/3/14.
 */
public class RestaurantDetail extends Activity {


    Typeface brandonregular, brandonlight;
    ListView listViewFood;
    TextView foodName, foodDescrip, title;
    ArrayList<String> foodTitles, foodDescriptions;
    FoodAdapter foodAdapter;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurantdetail);

        init();

        foodAdapter = new FoodAdapter(getApplicationContext());

        listViewFood.setAdapter(foodAdapter);

        new FetchMenuAsync().execute();

    }

    private void init() {

        brandonregular = Typeface.createFromAsset(getAssets(), "brandon_reg.otf");
        brandonlight = Typeface.createFromAsset(getAssets(), "brandon_light.otf");

        listViewFood = (ListView) findViewById(R.id.restaurantdetails_listview_foods);

        title = (TextView) findViewById(R.id.restaurantdetail_title);

        title.setTypeface(brandonlight);

        foodTitles = new ArrayList<String>();
        foodDescriptions = new ArrayList<String>();

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

    }

    class FoodAdapter extends BaseAdapter {

        public LayoutInflater inflater = null;

        public FoodAdapter(Context context) {

            inflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return foodTitles.size();
        }

        @Override
        public Object getItem(int position) {
            return foodTitles.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = inflater.inflate(R.layout.restaurant_detail_element, null, false);

            foodName = (TextView) view.findViewById(R.id.restaurantdetail_foodname);
            foodDescrip = (TextView) view.findViewById(R.id.restaurantdetail_fooddescription);

            foodName.setTypeface(brandonregular);
            foodDescrip.setTypeface(brandonregular);

            foodName.setText(foodTitles.get(i));
            foodDescrip.setText(foodDescriptions.get(i));


            return view;
        }
    }


    class FetchMenuAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            String url = null;
            JSONObject object = null;
            String jsonResult = null;
            String result = null;

            try {
                DefaultHttpClient httpclient = new DefaultHttpClient();
                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
                HttpConnectionParams.setSoTimeout(httpParams, 30000);


                if (getIntent().getExtras().getString("Bebida") == null) {

                    url = "http://ec2-54-218-96-225.us-west-2.compute.amazonaws.com/api/?o=getRestaurantMenu&cat=" + getIntent().getExtras().getString("Categoria");

                } else {

                    url = "http://ec2-54-218-96-225.us-west-2.compute.amazonaws.com/api/?o=getRestaurantMenu&cat=bebida&drink=" + getIntent().getExtras().getString("Bebida");
                }

                HttpGet httpget = new HttpGet(url);

                HttpResponse response = httpclient.execute(httpget);
                jsonResult = Utilities.convertStreamToString(
                        response.getEntity().getContent()).toString();


            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonResult;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONArray array = new JSONArray(result);

                for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);
                        foodTitles.add(object.get("food_product").toString());
                        foodDescriptions.add(object.get("food_description").toString());

                }


                progressBar.setVisibility(View.GONE);
                foodAdapter.notifyDataSetChanged();


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        GAUtils.sendScreen(this, "RestaurantDetail");
    }

}
