package com.icogroup.dallassuites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.icogroup.util.Keystring;
import com.icogroup.util.Utilities;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by andres.torres on 10/24/14.
 */
public class Profile extends Activity {

    TextView tTitle, tName, tHistory, tPoints, tNoInfoTitle, tNoInfoText, timelineDate, timelineAction, timelineRoom;
    Typeface brandonregular, brandonlight, brandonmedium, brandonmediumitalic;
    ImageView iNoInfoImg, timelineImage;
    ImageButton back, edit;
    ListView timeline;
    ArrayList earned, used, rooms, dates;
    TimelineAdapter timelineAdapter;
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_profile);

        init();

        prefs = getSharedPreferences(Keystring.DALLAS_SUITES_PREFERENCES, MODE_PRIVATE);

        new getUserInfoAsync().execute();

        timelineAdapter = new TimelineAdapter(this);

        timeline.setAdapter(timelineAdapter);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, Edit.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearSharedPreferences();
                finish();
            }
        });

    }

    private void init() {

        brandonmedium = Typeface.createFromAsset(getAssets(), "brandon_med.otf");
        brandonlight = Typeface.createFromAsset(getAssets(), "brandon_light.otf");
        brandonregular = Typeface.createFromAsset(getAssets(), "brandon_reg.otf");
        brandonmediumitalic = Typeface.createFromAsset(getAssets(), "brandon_med_it.otf");

        back = (ImageButton) findViewById(R.id.profile_back);

        edit = (ImageButton) findViewById(R.id.profile_edit);

        tTitle = (TextView) findViewById(R.id.profile_title);
        tName = (TextView) findViewById(R.id.profile_textview_name);
        tHistory = (TextView) findViewById(R.id.profile_textview_history);
        tPoints = (TextView) findViewById(R.id.profile_textview_points);
        tNoInfoTitle = (TextView) findViewById(R.id.profile_timeline_noinfo_title);
        tNoInfoText = (TextView) findViewById(R.id.profile_timeline_noinfo_text);

        timeline = (ListView) findViewById(R.id.profile_listview_timeline);

        iNoInfoImg = (ImageView) findViewById(R.id.profile_timeline_noinfo_img);


        tTitle.setTypeface(brandonlight);
        tName.setTypeface(brandonregular);
        tHistory.setTypeface(brandonlight);
        tPoints.setTypeface(brandonlight);
        tNoInfoTitle.setTypeface(brandonregular);
        tNoInfoText.setTypeface(brandonregular);

        used = new ArrayList();
        earned = new ArrayList();
        rooms = new ArrayList();
        dates = new ArrayList();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clearSharedPreferences();

    }

    @Override
    protected void onResume() {
        super.onResume();
        new getUserInfoAsync().execute();
    }

    class TimelineAdapter extends BaseAdapter {

        public LayoutInflater inflater = null;

        public TimelineAdapter(Context context) {

            inflater = LayoutInflater.from(context);
            new getHistoryAsync().execute();

        }

        @Override
        public int getCount() {
            return rooms.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            view = inflater.inflate(R.layout.timeline_element, null, false);

            timelineImage = (ImageView) view.findViewById(R.id.profile_timeline_img);
            timelineDate = (TextView) view.findViewById(R.id.profile_timeline_text_date);
            timelineAction = (TextView) view.findViewById(R.id.profile_timeline_text_action);
            timelineRoom = (TextView) view.findViewById(R.id.profile_timeline_text_habitacion);

            timelineRoom.setTypeface(brandonregular);
            timelineAction.setTypeface(brandonmedium);
            timelineDate.setTypeface(brandonmediumitalic);

            if (!rooms.isEmpty() && !earned.isEmpty() && !used.isEmpty()) {
                if (earned.get(position).equals("")) {

                    timelineRoom.setText(rooms.get(position).toString());
                    timelineAction.setText("Se canjearon " + used.get(position).toString() + " pts.");
                    timelineDate.setText(dates.get(position).toString());

                    if (position == getCount() - 1) {

                        timelineImage.setImageResource(R.drawable.perfil_menos_simple);

                    } else {

                        timelineImage.setImageResource(R.drawable.perfil_menos_doble);

                    }

                } else if (used.get(position).equals("")) {

                    timelineRoom.setText(rooms.get(position).toString());
                    timelineAction.setText("Se sumaron " + earned.get(position).toString() + " pts.");
                    timelineDate.setText(dates.get(position).toString());

                    if (position == getCount() - 1) {

                        timelineImage.setImageResource(R.drawable.perfil_mas_simple);

                    } else {

                        timelineImage.setImageResource(R.drawable.perfil_mas_doble);

                    }

                }

            }

            return view;
        }
    }

    class getHistoryAsync extends AsyncTask<Void, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(Void... params) {

            String url = "http://ec2-54-218-96-225.us-west-2.compute.amazonaws.com/api/?o=getUserHistory&user_id=" + prefs.getString(Keystring.USER_ID, "0") + "&user_password=" + prefs.getString(Keystring.USER_PASSWORD, "0");
            JSONArray object = null;
            String jsonResult = null;
            String result = null;

            Log.d("URL1", url);

            try {
                DefaultHttpClient httpclient = new DefaultHttpClient();
                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
                HttpConnectionParams.setSoTimeout(httpParams, 30000);

                HttpGet httpget = new HttpGet(url);

                HttpResponse response = httpclient.execute(httpget);
                jsonResult = Utilities.convertStreamToString(
                        response.getEntity().getContent()).toString();


                object = new JSONArray(jsonResult);


            } catch (Exception e) {
                e.printStackTrace();

            }


            return object;
        }


        @Override
        protected void onPostExecute(JSONArray result) {
            super.onPostExecute(result);

            for (int i = 0; i < result.length(); i++) {

                try {
                    JSONObject object = result.getJSONObject(i);
                    earned.add(object.getString("earned_points"));
                    used.add(object.getString("used_points"));
                    rooms.add(object.getString("room_category"));
                    dates.add(reformatDate(object.getString("visit_timestamp")));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            iNoInfoImg.setVisibility(View.GONE);
            tNoInfoText.setVisibility(View.GONE);
            tNoInfoTitle.setVisibility(View.GONE);

            timeline.setVisibility(View.VISIBLE);


            timelineAdapter.notifyDataSetChanged();

        }

    }

    class getUserInfoAsync extends AsyncTask<Void, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(Void... params) {

            String url = "http://ec2-54-218-96-225.us-west-2.compute.amazonaws.com/api/?o=getUserWithPoints&user_id=" + prefs.getString(Keystring.USER_ID, "0") + "&user_password=" + prefs.getString(Keystring.USER_PASSWORD, "0");
            JSONArray object = null;
            String jsonResult = null;
            String result = null;

            Log.d("URL2", url);

            try {
                DefaultHttpClient httpclient = new DefaultHttpClient();
                final HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
                HttpConnectionParams.setSoTimeout(httpParams, 30000);

                HttpGet httpget = new HttpGet(url);

                HttpResponse response = httpclient.execute(httpget);
                jsonResult = Utilities.convertStreamToString(
                        response.getEntity().getContent()).toString();


                object = new JSONArray(jsonResult);


            } catch (Exception e) {
                e.printStackTrace();

            }


            return object;
        }


        @Override
        protected void onPostExecute(JSONArray result) {
            super.onPostExecute(result);

            try {
                tPoints.setText(result.getJSONObject(0).getString("points_available") + " PTS." );
                tName.setText(result.getJSONObject(0).getString("user_name") + " " + result.getJSONObject(0).getString("user_lastname"));

            }catch(Exception e){

                e.printStackTrace();
            }
        }


    }



    private String reformatDate(String unformattedDate) {

        String[] splitDate = unformattedDate.split(" ")[0].split("-");

        String formattedDate = splitDate[2] + " - " + splitDate[1] + " - " + splitDate[0];

        return formattedDate;
    }

    private void clearSharedPreferences(){


        SharedPreferences.Editor editor  = prefs.edit();
        editor.remove(Keystring.USER_NAME);
        editor.remove(Keystring.USER_LASTNAME);
        editor.remove(Keystring.USER_PASSWORD);
        editor.remove(Keystring.USER_ID);
        editor.remove(Keystring.USER_DOB);
        editor.remove(Keystring.USER_CI);
        editor.remove(Keystring.USER_EMAIL);

        editor.apply();

    }

}



