package com.eways.elearning.Model.Database;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eways.elearning.View.Activity.Activity.MainActivity;

/**
 * Created by zzzzz on 8/28/2017.
 */
//Tuông tác dữ liệu với firebase
public class FireBaseHandler {
    Context context;
    FirebaseHandlerCallback firebaseHandlerCallback;

    public FireBaseHandler(Context context, FirebaseHandlerCallback firebaseHandlerCallback) {
        this.context = context;
        this.firebaseHandlerCallback = firebaseHandlerCallback;
    }
    //Lấy data từ firebase trả về
    public void GET(String url){
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                firebaseHandlerCallback.TransData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                firebaseHandlerCallback.TransData(error.toString());
            }
        });
        requestQueue.add(stringRequest);
    }

}
