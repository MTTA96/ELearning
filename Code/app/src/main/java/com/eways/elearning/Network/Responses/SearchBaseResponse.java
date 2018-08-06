package com.eways.elearning.Network.Responses;

import com.eways.elearning.Model.Account.User;
import com.eways.elearning.Model.Search.SearchResults;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by zzzzz on 5/27/2018.
 */

public class SearchBaseResponse {
    @SerializedName("errorCode")
    @Expose
    private Integer errorCode;
    @SerializedName("response")
    @Expose
    private ArrayList<User> results;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public ArrayList<User> getResults() {
        return results;
    }

    public void setResults( ArrayList<User>  results) {
        this.results = results;
    }
}
