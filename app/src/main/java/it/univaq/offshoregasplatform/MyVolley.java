package it.univaq.offshoregasplatform;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * NonaLezione
 * Created by leonardo on 2019-11-29.
 */
public class MyVolley {

    private RequestQueue queue;

    private static MyVolley instance = null;

    public static MyVolley getInstance(Context context){
        return instance == null ? instance = new MyVolley(context) : instance;
    }

    private MyVolley(Context context) {

        queue = Volley.newRequestQueue(context);
    }

    public RequestQueue getQueue(){
        return queue;
    }
}
