package com.dallassuites.dallassuites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dallassuites.util.GAUtils;
import com.dallassuites.util.Utilities;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by andres.torres on 10/24/14.
 */
public class Rooms extends Activity {

    Typeface brandonlight, brandonregular;
    ListView listRooms;
    String[] rooms = {"Suite Le Grande", "Suite Container", "Suite Spa", "Suite Boutique Art", "Suite Hotel", "Suite Establo Vapor", "Suite Establo", "Suite Sexy", "Suite New Concept", "Suite Presidencial", "Suite Deluxe", "Suite Duplex", "Suite Plus C/ Jacuzzi", "Suite Plus"};
    ArrayList<String> rooms_descrip;
    int[] cover = {R.drawable.rooms_img_legrande, R.drawable.rooms_img_container, R.drawable.rooms_img_spa, R.drawable.rooms_img_boutiqueart, R.drawable.rooms_img_hotel, R.drawable.rooms_img_establovapor, R.drawable.rooms_img_establo, R.drawable.rooms_img_sexy, R.drawable.rooms_img_newconcept, R.drawable.rooms_img_suitepresidencial, R.drawable.rooms_img_suitedeluxe, R.drawable.rooms_img_suiteduplex, R.drawable.rooms_img_suiteplusconjacuzzi, R.drawable.rooms_img_suiteplus };
    HashMap<String, String> photos360, photos;
    ImageView iRoom;
    TextView tRoomName, title, tRoomDescrip;
    RoomsAdapter roomsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rooms);

        new getRoomInfoAsync().execute();

        init();

        listRooms.setAdapter(roomsAdapter);


    }

    private void init() {

        brandonregular = Typeface.createFromAsset(getAssets(), "brandon_reg.otf");
        brandonlight = Typeface.createFromAsset(getAssets(), "brandon_light.otf");

        listRooms = (ListView) findViewById(R.id.rooms_listview_rooms);
        photos360 = new HashMap<String, String>();
        photos = new HashMap<String, String>();
        title = (TextView) findViewById(R.id.rooms_title);

        rooms_descrip = new ArrayList<String>();

        title.setTypeface(brandonlight);

        roomsAdapter = new RoomsAdapter(getApplicationContext());

    }


    class RoomsAdapter extends BaseAdapter {

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

            iRoom = (ImageView) view.findViewById(R.id.rooms_rooms_img);
            tRoomName = (TextView) view.findViewById(R.id.rooms_text_roomname);
            tRoomDescrip = (TextView) view.findViewById(R.id.rooms_text_roomdescription);

            tRoomName.setTypeface(brandonlight);
            tRoomDescrip.setTypeface(brandonregular);

            if(!rooms_descrip.isEmpty())
                tRoomDescrip.setText(rooms_descrip.get(position));

            if(rooms.length > 0)
                tRoomName.setText("" + rooms[position].toUpperCase());

            iRoom.setImageResource(cover[position]);

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

            if (result != null) {

                for (int i = 0; i < result.length(); i++) {

                    try {
                        photos360.put(result.getJSONObject(i).getString("room_category"), result.getJSONObject(i).getString("room_360"));
                        photos.put(result.getJSONObject(i).getString("room_category"), result.getJSONObject(i).getString("room_pictures"));
                        rooms_descrip.add(result.getJSONObject(i).getString("room_description"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                roomsAdapter.notifyDataSetChanged();
                listRooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                        startActivity(new Intent(Rooms.this, RoomDetail.class).putExtra("Photo360", photos360.get(rooms[position])).putExtra("Photos", photos.get(rooms[position])).putExtra("RoomName", rooms[position]));
                        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_in_left);

                    }
                });

            }else{

                Toast.makeText(Rooms.this, "No hay conexión a Internet", Toast.LENGTH_SHORT).show();

            }


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_in_right);

    }


    @Override
    protected void onStart() {
        super.onStart();

        GAUtils.sendScreen(this, "Rooms");
    }

}
