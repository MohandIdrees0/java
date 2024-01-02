package com.example.myapplication;

import static com.android.volley.toolbox.Volley.newRequestQueue;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

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

public class filldata {
    ListView items;
    String url="";
    ArrayList<News> news=new ArrayList<>();
    Context context;

    private RequestQueue queue;
    filldata(String url, ListView items,Context context){
        this.items=items;
        this.url=url;
        this.context=context;
        queue=newRequestQueue(context);
        btn_OnClick(items,context);
    }
    public void btn_OnClick(ListView listView,Context context) {

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            Log.d("countResponse",response.toString());
                            JSONArray data=response.getJSONArray(url.contains("mediastack")?"data":"results");
                            for(int i=0;i<(data.length());i++){
                                JSONObject object = data.getJSONObject(i);
                                news.add(new News(object.getString("title"),object.getString("description"),object.getString(url.contains("mediastack")?"image":"image_url"),object.getString("language")));
                            }
                            fillData("");
                        }
                        catch(Error | JSONException error){
                            Log.d("countResponse",error+"1212");}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        if (error instanceof ParseError) {
                            // Log or handle parse error
                        } else {
                            // Handle other types of errors
                        }
                    }
                }
        );

        queue.add(request);


    }
    void fillData(String lang){
        ArrayList list=new ArrayList();
        for(int i=0;i<news.size();i++){
            Log.d("wtf","wtf");
            News object=news.get(i);
            if(object.language.contains(lang)){
                list.add(object);
            }

        }
        adapter newsAdapter1=new adapter(context,list);
        Log.d("wtf",items+"anamohand");
        items.setAdapter(newsAdapter1);
    }
}
