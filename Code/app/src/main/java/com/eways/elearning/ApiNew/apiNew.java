package com.eways.elearning.ApiNew;

import android.os.Build;
import android.support.annotation.RequiresApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by ADMIN on 7/27/2018.
 */

public interface apiNew {

    @FormUrlEncoded
    @POST("API/RequisitionCourse/AddNewRequisitionCourse.php")
    public void sendRequest(@Field("request") String request);

}
