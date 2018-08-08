package com.eways.elearning.ApiNew;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by ADMIN on 7/27/2018.
 */

public interface apiNew {

    @FormUrlEncoded
    @POST("RequisitionCourse/AddNewRequisitionCourse.php")
    public void sendRequest(@Field("request") String request);

    @GET("specialization/getspecializationsuser.php")
    public Call<String> getSubjectId(@Field("user_id") int userId);
}
