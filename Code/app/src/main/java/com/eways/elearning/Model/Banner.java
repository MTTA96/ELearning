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
    @SerializedName("IdBanner")
    @Expose
    private String idBanner;
    @SerializedName("Img")
    @Expose
    private String img;
    @SerializedName("Link")
    @Expose
    private String link;

    public String getIdBanner() {
        return idBanner;
    }

    public void setIdBanner(String idBanner) {
        this.idBanner = idBanner;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
                Log.d("GetBannerModel:", t.getLocalizedMessage());
                dataCallBack.bannersCallBack(SupportKeys.FAILED_CODE, null);
            }
        });

    }
}
