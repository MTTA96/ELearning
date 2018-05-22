package com.eways.elearning.Utils.Api;

import com.eways.elearning.Network.ListResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by ADMIN on 3/18/2018.
 */

public interface CourseServicesImp {
//    //Get user by id
//    @GET("/API/GetTeacherByUid.php")
//    Call<UserBaseResponse> getUserById(@Query("Uid") String uid);
//
//    @POST("/API/AddNewUser.php")
//    Call<UserBaseResponse> addRawUser(@Field("Uid") String uid, @Field("FirstName") String firstName, @Field("Phone") String phone);

    @POST("/API/Course/SearchCourses.php")
    @FormUrlEncoded
    Call<ListResponse> getCourseSearch(@Field("mydata") String condition);
}
