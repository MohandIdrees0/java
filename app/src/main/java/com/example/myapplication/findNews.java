package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Debug;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

public class findNews extends AppCompatActivity {

    EditText editText;

    filldata filldata;
    Button search;
    CheckBox checkBox;
    String url = "https://newsdata.io/api/1/news?apikey=pub_35233ba9b2dd0830f2026b8f5507c890e080d";
    ListView items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_news);
        editText=findViewById(R.id.findNewstextFeild);
        items=findViewById(R.id.itemsfind);
        search=findViewById(R.id.search);
        checkBox=findViewById(R.id.checkBox2);
        filldata=new filldata(url,items,getApplicationContext());
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox.setChecked(false);
                Log.d("didnotexist",url+"&q="+editText.getText());
                filldata=new filldata(url+"&q="+editText.getText(),items,getApplicationContext());
            }
        });
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox.setActivated(!checkBox.isActivated());
                if(checkBox.isActivated()){
                    filldata.fillData("en");
                }
                else{
                    filldata.fillData("");
                }
            }
        });

    }
}