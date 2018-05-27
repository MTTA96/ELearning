package com.eways.elearning.Model;

import android.os.Bundle;
import android.util.Log;

import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Network.ApiUtils;
import com.eways.elearning.Network.Responses.SearchBaseResponse;
import com.eways.elearning.Network.Responses.SearchSuggestionsBaseResponse;
import com.eways.elearning.Network.Services.ETutorServicesImp;
import com.eways.elearning.Utils.SupportKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zzzzz on 5/22/2018.
 */

public class SearchResults {

    @SerializedName("listUser")
    @Expose
    private ArrayList<User> listUser = null;
    @SerializedName("listCourse")
    @Expose
    private ArrayList<Course> listCourse = null;

    public ArrayList<User> getListUser() {
        return listUser;
    }

    public void setListUser(ArrayList<User> listUser) {
        this.listUser = listUser;
    }

    public ArrayList<Course> getListCourse() {
        return listCourse;
    }

    public void setListCourse(ArrayList<Course> listCourse) {
        this.listCourse = listCourse;
    }

    /** METHODS */

    /** Sign in */
    public static void search(String keyWord, final DataCallBack dataCallBack) {
        ETutorServicesImp eTutorServicesImp = ApiUtils.eTutorServices();
        eTutorServicesImp.search(keyWord).enqueue(new Callback<SearchBaseResponse>() {
            @Override
            public void onResponse(Call<SearchBaseResponse> call, Response<SearchBaseResponse> response) {
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
                bundle.putSerializable("param1", response.body().getResults().getListUser());
                bundle.putSerializable("param2", response.body().getResults().getListUser());
                dataCallBack.dataCallBack(SupportKey.SUCCESS_CODE, bundle);
            }

            @Override
            public void onFailure(Call<SearchBaseResponse> call, Throwable t) {
                Log.d("search:", "Connect Failed - " + t.getLocalizedMessage());
                dataCallBack.dataCallBack(SupportKey.FAILED_CODE, null);
            }
        });
    }

    /** Search subject suggestions */
    public static void searchSubjectSuggestions(String keyWord, final DataCallBack dataCallBack) {
        ETutorServicesImp eTutorServicesImp = ApiUtils.eTutorServices();
        eTutorServicesImp.searchSuggestions(keyWord).enqueue(new Callback<SearchSuggestionsBaseResponse>() {
            @Override
            public void onResponse(Call<SearchSuggestionsBaseResponse> call, Response<SearchSuggestionsBaseResponse> response) {
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
                bundle.putSerializable(null, response.body().getResults());
                dataCallBack.dataCallBack(SupportKey.SUCCESS_CODE, bundle);
            }

            @Override
            public void onFailure(Call<SearchSuggestionsBaseResponse> call, Throwable t) {
                Log.d("SearchSubSuggestions:", t.getLocalizedMessage());
                dataCallBack.dataCallBack(SupportKey.FAILED_CODE, null);
            }
        });
    }

    /** Search user suggestions */
    public static void searchUserSuggestions(String keyWord, final DataCallBack dataCallBack) {
        ETutorServicesImp eTutorServicesImp = ApiUtils.eTutorServices();
        eTutorServicesImp.searchSuggestions(keyWord).enqueue(new Callback<SearchSuggestionsBaseResponse>() {
            @Override
            public void onResponse(Call<SearchSuggestionsBaseResponse> call, Response<SearchSuggestionsBaseResponse> response) {
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
            public void onFailure(Call<SearchSuggestionsBaseResponse> call, Throwable t) {
                Log.d("SearchResults:", "Connect Failed");
                dataCallBack.dataCallBack(SupportKey.FAILED_CODE, null);
            }
        });
    }
}
