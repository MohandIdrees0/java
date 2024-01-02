package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class adapter extends BaseAdapter{
    Context context;
    ArrayList<News> News;
    LayoutInflater layoutInflater;
    public adapter(Context context, ArrayList<News> News){
        layoutInflater=LayoutInflater.from(context);
        this.News=News;
    }
    @Override
    public int getCount() {
        return News.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.d("adapterResponse",News.get(i).url+"");
        View view1=layoutInflater.inflate(R.layout.mediastack,null);
        TextView title=(TextView) view1.findViewById(R.id.newTitle);
        TextView description=(TextView) view1.findViewById(R.id.description);
        ImageView imageView=view1.findViewById(R.id.imageView);

        if(News.get(i).url.compareToIgnoreCase("null")==0){
            Glide.with(view1).load("https://ichef.bbci.co.uk/images/ic/624x351/p07kfjyp.jpg").into(imageView);
        }
        else {
            Glide.with(view1).load(News.get(i).url).into(imageView);
        }
        title.setText(News.get(i).title);
        description.setText(News.get(i).description);


        return  view1;
    }
}