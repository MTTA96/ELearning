package com.eways.elearning.Data.Model.Database;

import android.content.Context;
import android.provider.CalendarContract;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eways.elearning.Util.Interfaces.DataCallBack;

/**
 * Created by Quang Trí on 8/28/2017.
 *
 * Description: Tương tác dữ liệu firenase
 */

public class FireBaseHandler {
    Context context;
    DataCallBack dataCallBack;

    public FireBaseHandler(Context context, DataCallBack dataCallBack) {
        this.context = context;

        this.dataCallBack = dataCallBack;
    }
    //Lấy data từ firebase trả về
    public void GET(String url){
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                dataCallBack.TransData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                firebaseHandlerCallback.TransData("Loi");
            }
        });
        requestQueue.add(stringRequest);
    }

}
