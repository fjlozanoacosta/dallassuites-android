package com.icogroup.dallassuites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.icogroup.util.Utilities;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

/**
 * Created by andres.torres on 10/24/14.
 */
public class Rooms extends Activity {

    Typeface brandonlight;
   ListView listRooms;
   String[] rooms = {"Suite Plus", "Suite Plus C/ Jacuzzi", "Suite Duplex", "Suite Deluxe", "Suite Presidencial"};
   HashMap<String, String> photos360;
    ImageView iRoom;
    TextView tRoomName, title;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rooms);

        new getRoomInfoAsync().execute();

        init();

        listRooms.setAdapter(new RoomsAdapter(getApplicationContext()));
        listRooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

               startActivity(new Intent(Rooms.this, Room360.class).putExtra("room_360_url", photos360.get(rooms[position])).putExtra("room_name", rooms[position]));


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void init() {

        brandonlight = Typeface.createFromAsset(getAssets(), "brandon_light.otf");
        listRooms = (ListView)findViewById(R.id.rooms_listview_rooms);
        photos360 = new HashMap<String, String>();
        back = (ImageButton)findViewById(R.id.rooms_back_button);
        title = (TextView)findViewById(R.id.rooms_title);

        title.setTypeface(brandonlight);


    }


    class RoomsAdapter extends BaseAdapter{

        public LayoutInflater inflater = null;

        public RoomsAdapter(Context context) {

            inflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return rooms.length;
        }

        @Override
        public Object getItem(int position) {
            return rooms[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            view = inflater.inflate(R.layout.rooms_element, null, false);

            iRoom = (ImageView)view.findViewById(R.id.rooms_rooms_img);
            tRoomName = (TextView)view.findViewById(R.id.rooms_text_roomname);

            tRoomName.setText("" + rooms[position]);

            if(rooms[position].equals("Suite Plus")){

                iRoom.setImageResource(R.drawable.rooms_img_suiteplus);

            }else if(rooms[position].equals("Suite Plus C/ Jacuzzi")){

                iRoom.setImageResource(R.drawable.rooms_img_suiteplusconjacuzzi);

            }else if(rooms[position].equals("Suite Duplex")){

                iRoom.setImageResource(R.drawable.rooms_img_suiteduplex);

            }else if(rooms[position].equals("Suite Deluxe")){

                iRoom.setImageResource(R.drawable.rooms_img_suitedeluxe);

            }else if(rooms[position].equals("Suite Presidencial")){

                iRoom.setImageResource(R.drawable.rooms_img_suitepresidencial);

            }


            return view;
        }
    }


    class getRoomInfoAsync extends AsyncTask<Void, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(Void... params) {

            String url = "http://ec2-54-218-96-225.us-west-2.compute.amazonaws.com/api/?o=getRoomsAndPoints";
            JSONArray object = null;
            String jsonResult = null;
            String result = null;

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

          for(int i = 0; i < result.length(); i++){

              try {
                  photos360.put(result.getJSONObject(i).getString("room_category"), result.getJSONObject(i).getString("room_360"));
              } catch (JSONException e) {
                  e.printStackTrace();
              }

          }

            Log.d("Photos 360", photos360.toString());


        }


    }

}
