package com.eways.elearning.Network.Responses.User;

import com.eways.elearning.Model.Subject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by zzzzz on 6/2/2018.
 */

public class TrendingSubjectResponse {
    @SerializedName("errorCode")
    @Expose
    private Integer errorCode;
    @SerializedName("trendingList")
    @Expose
    private ArrayList<Subject> trendingList = null;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public ArrayList<Subject> getTrendingList() {
        return trendingList;
    }

    public void setTrendingList(ArrayList<Subject> trendingList) {
        this.trendingList = trendingList;
    }
}
