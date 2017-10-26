package com.example.david.thumbsplit;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by David on 9/25/2017.
 */

public class MySingleton {
    private static MySingleton ourInstance;
    private RequestQueue requestQueue;
    private Context mCtx;
   public int page_size=3;



    public RequestQueue getRequestQueue(){

        if(requestQueue==null){

            requestQueue= Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;

    }

    public static synchronized MySingleton getInstance(Context context) {
        if(ourInstance==null){
            ourInstance=new MySingleton(context);
        }
        return ourInstance;
    }

    private MySingleton(Context context) {
        mCtx=context;
        requestQueue=getRequestQueue();
    }
    public <T>void addToRequestque(Request<T> request){
        requestQueue.add(request);
    }
}
