package com.eways.elearning.Network;

import com.eways.elearning.Model.TaiKhoan.BaseUserResponse;
import com.eways.elearning.Model.TaiKhoan.User;
import com.eways.elearning.Util.ServerUrl;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zzzzz on 3/13/2018.
 */

public interface UserServicesImp {
    @GET(ServerUrl.LOGIN_URL)
    Call<BaseUserResponse> login(@Query("Uid") String userId);
}
