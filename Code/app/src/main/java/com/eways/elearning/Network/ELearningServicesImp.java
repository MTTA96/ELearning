package com.eways.elearning.Network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zzzzz on 3/13/2018.
 */

public interface ELearningServicesImp {
    @POST()
    @FormUrlEncoded
    Call<POST> login(@Field("userId") String userId);
}
