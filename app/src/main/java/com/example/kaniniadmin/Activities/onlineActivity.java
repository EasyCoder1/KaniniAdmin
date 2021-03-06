package com.example.kaniniadmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kaniniadmin.R;
import com.example.kaniniadmin.adapters.MyDataAdapter;
import com.example.kaniniadmin.adapters.MyData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class onlineActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.Adapter adapter;
    private String branchSelected;
    private DividerItemDecoration dividerItemDecoration;
    private List <MyData> mldata;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        Intent intent=getIntent();
        branchSelected=intent.getStringExtra("branchSelected");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        mldata= new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        loadData();

    }

    private void loadData(){
        //call on server method online activity. pass the branchSelected string in the url
        final ProgressDialog progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Loading... please wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String url = "http://avaniatech.co.ke/api/branchOnline/"+branchSelected;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONArray array=new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jo = array.getJSONObject(i);
                        MyData myData = new MyData();
                        myData.setName(jo.getString("username"));
                        myData.setPhone(jo.getString("phoneNo"));
                        mldata.add(myData);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("OnlineActivity", "error in onlineActivity, Server Response");
                progressDialog.dismiss();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

}
/*
* This java class will get a json  from the server by passing it through a url like http://domain.name/online.json
* it will print
* */