package com.example.kaniniadmin.Activities;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.os.Handler;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.kaniniadmin.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class myMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_maps);
        Bundle extras = getIntent().getExtras();
        String username=extras.getString("username");
        String phoneNo=extras.getString("phoneNo");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Update();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void getdata(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url=" ";
            JSONObject object = new JSONObject();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //http method to return a json array named location
                    try{
                        JSONArray array = response.getJSONArray("location");
                        for(int i=0;i<array.length();i++) {
                            JSONObject smanloc = array.getJSONObject(i);
                            String z = smanloc.getString("lat");
                            String x = smanloc.getString("lng");

                            lat = Double.parseDouble(z);
                            lng = Double.parseDouble(x);
                        }

                        }catch (JSONException e){
                        e.printStackTrace();
                    }



                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(jsonObjectRequest);
        }

        public  void Update(){
          final Handler handler = new Handler();
          Runnable runnableCode = new Runnable() {
            @Override
            public void run() {
                getdata();
                handler.postDelayed(this, 4000);
            }
        };
        handler.post(runnableCode);
    }
    }
   

