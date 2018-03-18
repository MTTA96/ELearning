package com.eways.elearning.Network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zzzzz on 3/13/2018.
 */

public interface ELearningServicesImp {
    @POST("/market/ETH/AUD/tick?since=now")
    @FormUrlEncoded
    Call<POST> login(@Field("title") String title,
                        @Field("body") String body,
                        @Field("userId") long userId);
}
