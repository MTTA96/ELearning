package com.eways.elearning.Model;

import android.os.Bundle;
import android.util.Log;

import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Network.ApiUtils;
import com.eways.elearning.Network.Responses.ListResponse;
import com.eways.elearning.Network.Services.ETutorServicesImp;
import com.eways.elearning.Utils.SupportKey;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zzzzz on 5/22/2018.
 */

public class SearchResults {

    /** METHODS */

    /** Sign in */
    public static void search(String keyWord, final DataCallBack dataCallBack) {
        ETutorServicesImp eTutorServicesImp = ApiUtils.eTutorServices();
        eTutorServicesImp.search(String.valueOf(0), keyWord).enqueue(new Callback<ListResponse>() {
            @Override
            public void onResponse(Call<ListResponse> call, Response<ListResponse> response) {
                Log.d("search:", call.request().toString());
                // handle errors
                if (!response.isSuccessful()) {
                    Log.d("search:", " Connect Failed");
                    dataCallBack.dataCallBack(SupportKey.FAILED_CODE, null);
                    return;
                }

                // Get data success
                // Prepare data
                Bundle bundle = new Bundle();
                bundle.putSerializable(null, (Serializable) response.body().getResults());
                dataCallBack.dataCallBack(SupportKey.SUCCESS_CODE, bundle);
            }

            @Override
            public void onFailure(Call<ListResponse> call, Throwable t) {
                Log.d("search:", "Connect Failed");
                dataCallBack.dataCallBack(SupportKey.FAILED_CODE, null);
            }
        });
    }

    /** Search subject suggestions */
    public static void searchSubjectSuggestions(String keyWord, final DataCallBack dataCallBack) {
        ETutorServicesImp eTutorServicesImp = ApiUtils.eTutorServices();
        eTutorServicesImp.searchSuggestions(String.valueOf(0), keyWord).enqueue(new Callback<ListResponse>() {
            @Override
            public void onResponse(Call<ListResponse> call, Response<ListResponse> response) {
                Log.d("SearchSubSuggestions:", call.request().toString());
                // handle errors
                if (!response.isSuccessful()) {
                    Log.d("SearchSubSuggestions:", " Connect Failed");
                    dataCallBack.dataCallBack(SupportKey.FAILED_CODE, null);
                    return;
                }

                // Get data success
                // Prepare data
                Bundle bundle = new Bundle();
                bundle.putSerializable(null, (Serializable) response.body().getResults());
                dataCallBack.dataCallBack(SupportKey.SUCCESS_CODE, bundle);
            }

            @Override
            public void onFailure(Call<ListResponse> call, Throwable t) {
                Log.d("SearchSubSuggestions:", "Connect Failed");
                dataCallBack.dataCallBack(SupportKey.FAILED_CODE, null);
            }
        });
    }

    /** Search user suggestions */
    public static void searchUserSuggestions(String keyWord, final DataCallBack dataCallBack) {
        ETutorServicesImp eTutorServicesImp = ApiUtils.eTutorServices();
        eTutorServicesImp.searchSuggestions(String.valueOf(0), keyWord).enqueue(new Callback<ListResponse>() {
            @Override
            public void onResponse(Call<ListResponse> call, Response<ListResponse> response) {
                Log.d("SearchResults:", call.request().toString());
                // handle errors
                if (!response.isSuccessful()) {
                    Log.d("SearchResults:", " Connect Failed");
                    dataCallBack.dataCallBack(SupportKey.FAILED_CODE, null);
                    return;
                }

                // Get data success
                // Prepare data
                Bundle bundle = new Bundle();
                bundle.putSerializable(null, response.body().getResults());

                // Response to presenter
                dataCallBack.dataCallBack(SupportKey.SUCCESS_CODE, bundle);
            }

            @Override
            public void onFailure(Call<ListResponse> call, Throwable t) {
                Log.d("SearchResults:", "Connect Failed");
                dataCallBack.dataCallBack(SupportKey.FAILED_CODE, null);
            }
        });
    }
}
