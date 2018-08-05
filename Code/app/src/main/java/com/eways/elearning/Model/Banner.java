package com.eways.elearning.Model;

import android.util.Log;

import com.eways.elearning.Interfaces.DataCallback.BannerCallBack;
import com.eways.elearning.Network.ApiUtils;
import com.eways.elearning.Network.Services.ELearningServicesImp;
import com.eways.elearning.Utils.SupportKeys;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zzzzz on 5/31/2018.
 */

public class Banner {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("link")
    @Expose
    private String link;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    /** METHODS */

    public static void getBanners(final BannerCallBack dataCallBack) {
        ELearningServicesImp eLearningServicesImp = ApiUtils.eLearningServices();
        eLearningServicesImp.getBanners().enqueue(new Callback<ArrayList<Banner>>() {
            @Override
            public void onResponse(Call<ArrayList<Banner>> call, Response<ArrayList<Banner>> response) {

                Log.d("GetBannerModel:", call.request().toString());

                // handle error
                if (!response.isSuccessful()) {
                    Log.d("GetBannerModel:", "connect failed");
                    dataCallBack.bannersCallBack(SupportKeys.FAILED_CODE, null);
                    return;
                }

                // Prepare data
                dataCallBack.bannersCallBack(SupportKeys.SUCCESS_CODE, response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Banner>> call, Throwable t) {
                Log.d("GetBannerModel:", call.request().toString());
                Log.d("GetBannerModel:", t.getLocalizedMessage());
                dataCallBack.bannersCallBack(SupportKeys.FAILED_CODE, null);
            }
        });

    }
}
