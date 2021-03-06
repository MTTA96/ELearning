package com.eways.elearning.Model.Search;

import android.os.Bundle;
import android.util.Log;

import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Model.Course.Course;
import com.eways.elearning.Model.Account.User;
import com.eways.elearning.Network.ApiUtils;
import com.eways.elearning.Network.Responses.SearchBaseResponse;
import com.eways.elearning.Network.Responses.SearchSuggestionsBaseResponse;
import com.eways.elearning.Network.Services.ELearningServicesImp;
import com.eways.elearning.Utils.SupportKeys;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zzzzz on 5/22/2018.
 */

public class SearchResults {

    @SerializedName("Users")
    @Expose
    private ArrayList<User> listUser = null;
    @SerializedName("Courses")
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
        ELearningServicesImp eLearningServicesImp = ApiUtils.eLearningServices();
        eLearningServicesImp.search(keyWord).enqueue(new Callback<SearchBaseResponse>() {
            @Override
            public void onResponse(Call<SearchBaseResponse> call, Response<SearchBaseResponse> response) {
                Log.d("search:", call.request().toString());
                // handle errors
                if (!response.isSuccessful()) {
                    Log.d("search:", " Connect Failed");
                    dataCallBack.dataCallBack(SupportKeys.FAILED_CODE, null);
                    return;
                }

                // Get data success
                // Prepare data
                Bundle bundle = new Bundle();

                if (response.body().getResults() != null)
                    bundle.putSerializable("param1", response.body().getResults());
                //bundle.putSerializable("param2", response.body().getResults().getListCourse());
                dataCallBack.dataCallBack(SupportKeys.SUCCESS_CODE, bundle);
            }

            @Override
            public void onFailure(Call<SearchBaseResponse> call, Throwable t) {
                Log.d("search:", call.request().toString());
                Log.d("search:", "Connect Failed - " + t.getLocalizedMessage());
                dataCallBack.dataCallBack(SupportKeys.FAILED_CODE, null);
            }
        });
    }

    /** Search subject suggestions */
    public static void searchSubjectSuggestions(String keyWord, final DataCallBack dataCallBack) {
        ELearningServicesImp eLearningServicesImp = ApiUtils.eLearningServices();
        eLearningServicesImp.searchSuggestions(keyWord).enqueue(new Callback<SearchSuggestionsBaseResponse>() {
            @Override
            public void onResponse(Call<SearchSuggestionsBaseResponse> call, Response<SearchSuggestionsBaseResponse> response) {
                Log.d("SearchSubSuggestions:", call.request().toString());
                // handle errors
                if (!response.isSuccessful()) {
                    Log.d("SearchSubSuggestions:", " Connect Failed");
                    dataCallBack.dataCallBack(SupportKeys.FAILED_CODE, null);
                    return;
                }

                // Get data success
                // Prepare data
                Bundle bundle = new Bundle();
                bundle.putSerializable(null, response.body().getSuggestionList());
                dataCallBack.dataCallBack(SupportKeys.SUCCESS_CODE, bundle);
                Log.d("SearchSubSuggestions:", "Get success!");
            }

            @Override
            public void onFailure(Call<SearchSuggestionsBaseResponse> call, Throwable t) {
                Log.d("SearchSubSuggestions:", t.getLocalizedMessage());
                dataCallBack.dataCallBack(SupportKeys.FAILED_CODE, null);
            }
        });
    }

    /** Search user suggestions */
    public static void searchUserSuggestions(String keyWord, final DataCallBack dataCallBack) {
        ELearningServicesImp eLearningServicesImp = ApiUtils.eLearningServices();
        eLearningServicesImp.searchSuggestions(keyWord).enqueue(new Callback<SearchSuggestionsBaseResponse>() {
            @Override
            public void onResponse(Call<SearchSuggestionsBaseResponse> call, Response<SearchSuggestionsBaseResponse> response) {
                Log.d("SearchResults:", call.request().toString());
                // handle errors
                if (!response.isSuccessful()) {
                    Log.d("SearchResults:", " Connect Failed");
                    dataCallBack.dataCallBack(SupportKeys.FAILED_CODE, null);
                    return;
                }

                // Get data success
                // Prepare data
                Bundle bundle = new Bundle();
                bundle.putSerializable(null, response.body().getSuggestionList());

                // Response to presenter
                dataCallBack.dataCallBack(SupportKeys.SUCCESS_CODE, bundle);
            }

            @Override
            public void onFailure(Call<SearchSuggestionsBaseResponse> call, Throwable t) {
                Log.d("SearchResults:", "Connect Failed");
                dataCallBack.dataCallBack(SupportKeys.FAILED_CODE, null);
            }
        });
    }
}
