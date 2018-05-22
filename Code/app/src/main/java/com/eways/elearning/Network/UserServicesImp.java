package com.eways.elearning.Network;

import com.eways.elearning.Utils.ServerUrl;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by zzzzz on 3/13/2018.
 */

public interface UserServicesImp {
    /** Sign in */
    @POST(ServerUrl.LOGIN_URL)
    @FormUrlEncoded
    Call<BaseResponse> signIn(@Field("Phone") String userId,
                                  @Field("Password") String password);

    /** Sign up */
    @POST(ServerUrl.SIGN_UP_URL)
    @FormUrlEncoded
    Call<BaseResponse> signUp(@Field("mydata") String data);

    /** Check phone number */
    @POST(ServerUrl.CHECK_PHONE_NUMBER_URL)
    @FormUrlEncoded
    Call<BaseResponse> checkPhoneNumber(@Field("Phone") String phone);

    /** Add majors */

}
