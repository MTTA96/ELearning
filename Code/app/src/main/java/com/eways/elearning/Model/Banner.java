package com.eways.elearning.Model;

import android.util.Log;

import com.eways.elearning.Interfaces.DataCallback.BannerCallBack;
import com.eways.elearning.Network.ApiUtils;
import com.eways.elearning.Network.Responses.BannerResponse;
import com.eways.elearning.Network.Services.ELearningServicesImp;
import com.eways.elearning.Utils.SupportKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zzzzz on 5/31/2018.
 */

public class Banner {

    /** METHODS */
    public static void getBanners(final BannerCallBack dataCallBack) {
        ELearningServicesImp eLearningServicesImp = ApiUtils.eLearningServices();
        eLearningServicesImp.getBanners().enqueue(new Callback<BannerResponse>() {
            @Override
            public void onResponse(Call<BannerResponse> call, Response<BannerResponse> response) {
                // handle error
                if (!response.isSuccessful()) {
                    Log.d("CheckPhoneNumberModel:", "connect failed");
                    dataCallBack.bannersCallBack(SupportKey.FAILED_CODE, null);
                    return;
                }

                // Prepare data
//                dataCallBack.bannersCallBack(getBanners(), bundle);
            }

            @Override
            public void onFailure(Call<BannerResponse> call, Throwable t) {
                Log.d("CheckPhoneNumberModel:", t.getLocalizedMessage());
                dataCallBack.bannersCallBack(SupportKey.FAILED_CODE, null);
            }
        });

    }
}
