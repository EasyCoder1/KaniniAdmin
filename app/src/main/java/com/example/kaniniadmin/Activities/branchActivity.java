package com.example.kaniniadmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.kaniniadmin.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class branchActivity extends AppCompatActivity {

    private Button BtnOnline,BtnOffline,BtnOnTrip,BtnOffTrip;
    private TextView noSalesmen, OnlineSalesmen, OfflineSalesmen,onTripSalesmen,offTripSalesmen;
    private String select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);
        noSalesmen = findViewById(R.id.noSalesmen);
        OnlineSalesmen = findViewById(R.id.onlineSalesmen);
        OfflineSalesmen = findViewById(R.id.offlineSalesmen);
        onTripSalesmen=findViewById(R.id.onTripSalesmen);
        offTripSalesmen=findViewById(R.id.offtripSalesmen);


        Spinner spinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Branches, R.layout.sxpinner_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select = parent.getItemAtPosition(position).toString();
                Log.d("Spinner on selection", "selected" + select);
                    final Handler handler = new Handler();
                    Runnable runnableCode = new Runnable() {
                        @Override
                        public void run() {
                            loadData(select);
                            handler.postDelayed(this, 4000);
                        }
                    };
                    handler.post(runnableCode);
                }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                final Handler handler = new Handler();
                Runnable runnableCode = new Runnable() {
                    @Override
                    public void run() {
                        loadData2();
                        handler.postDelayed(this, 4000);
                    }
                };
                handler.post(runnableCode);
            }

        });

        //BtnOnline routes the application to online activity class
        BtnOnline = findViewById(R.id.btnSalesmanOnline);
        BtnOffline= findViewById(R.id.btnsalesmenOffline);
        BtnOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), onlineActivity.class);
                intent.putExtra("branchSelected",select);
                startActivity(intent);
            }
        });
        BtnOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(branchActivity.this, "Functionality not created yet", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void loadData(String SX) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setCancelable(false);
        String url = "https://avaniatech.co.ke/api/countbranch/"+select;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();

                try {
                    JSONArray array=new JSONArray(response);
                    for (int i=0;i<array.length();i++)
                    {
                        JSONObject jsonObject=array.getJSONObject(i);
                        noSalesmen.setText(jsonObject.getString("branchSalesmen"));
                        noSalesmen.setText(jsonObject.getString("branchSalesmen"));
                        OnlineSalesmen.setText(jsonObject.getString("onlinecount"));
                        OfflineSalesmen.setText(jsonObject.getString("offlinecount"));
                    }

//                    onTripSalesmen.setText(response.getString("onTripSalesmen"));
//                    offTripSalesmen.setText(response.getString("offTripSalesmen"));

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("branchActivity ", "server error" + error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);

    }

    public void loadData2() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setCancelable(false);
        String url = "https://avaniatech.co.ke/api/countbranch/Thika";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();

                try {
                    noSalesmen.setText(response.getString("branchSalesmen"));
                    OnlineSalesmen.setText(response.getString("onlineCount"));
                    OfflineSalesmen.setText(response.getString("offlineCount"));
                    onTripSalesmen.setText(response.getString("onTripSalesmen"));
                    offTripSalesmen.setText(response.getString("offTripSalesmen"));

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("branchActivity ", "server error" + error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);

    }

}
