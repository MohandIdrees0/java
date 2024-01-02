package com.example.myapplication;

import static com.android.volley.toolbox.Volley.newRequestQueue;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainpageActivity extends AppCompatActivity {

    String url = "http://api.mediastack.com/v1/news?access_key=81a7b7b410865134910c37784162410b";

    ListView items;
    Button signOut;
    private RequestQueue queue;
    SharedPreferences sharedPreferences;
    CheckBox english;
    ImageButton sport;
    ImageButton middleEast;
    ImageButton findNews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
        items=findViewById(R.id.items);
        english=findViewById(R.id.english);
        middleEast=findViewById(R.id.middleEast);
        sport=findViewById(R.id.SportNewsOne);
        queue=newRequestQueue(this);
        findNews=findViewById(R.id.findNews);
        signOut=findViewById(R.id.sginout);
        sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        filldata filldata=new filldata(url,items,getApplicationContext());
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                english.setActivated(!english.isActivated());
                if(english.isActivated()){
                    filldata.fillData("en");
                }
                else{
                    filldata.fillData("");
                }
            }
        });
        findNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), findNews.class);
                startActivity(intent);
            }
        });
        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), differentnews.class);
                intent.putExtra("type","sport");
                startActivity(intent);
            }
        });
        middleEast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), differentnews.class);
                intent.putExtra("type","middleEast");
                startActivity(intent);
            }
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean("signdIn",false);
                editor.apply();
               finish();
            }
        });
    }
}