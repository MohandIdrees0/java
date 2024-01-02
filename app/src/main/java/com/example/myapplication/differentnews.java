package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import static com.android.volley.toolbox.Volley.newRequestQueue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class differentnews extends AppCompatActivity {
    String url="https://newsdata.io/api/1/news?apikey=pub_35233ba9b2dd0830f2026b8f5507c890e080d&q=";
    String type;
    CheckBox lang;
    TextView titletype;
    ListView items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.differentnews);
        Intent intent=getIntent();
        type=intent.getStringExtra("type");
        titletype=findViewById(R.id.type);
        this.items=findViewById(R.id.itemsFootball);
        lang=findViewById(R.id.lang);
        titletype.setText(type+" News");

        filldata filldata=new filldata(url+type,items,getApplicationContext());
        lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lang.setActivated(!lang.isActivated());
                if(lang.isActivated()){
                    filldata.fillData("english");
                }
                else{
                    filldata.fillData("");
                }
            }
        });
    }
}