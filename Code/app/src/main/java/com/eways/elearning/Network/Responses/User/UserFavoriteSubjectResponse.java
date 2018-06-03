package com.eways.elearning.Network.Responses.User;

import com.eways.elearning.Model.FavoriteSubjectWithCourses;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by zzzzz on 5/31/2018.
 */

public class UserFavoriteSubjectResponse {
    @SerializedName("errorCode")
    @Expose
    private Integer errorCode;
    @SerializedName("favSubjectWithCourseList")
    @Expose
    private ArrayList<FavoriteSubjectWithCourses> favSubjectWithCourseList = null;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public ArrayList<FavoriteSubjectWithCourses> getFavSubjectWithCourseList() {
        return favSubjectWithCourseList;
    }

    public void setFavSubjectWithCourseList(ArrayList<FavoriteSubjectWithCourses> favSubjectWithCourseList) {
        this.favSubjectWithCourseList = favSubjectWithCourseList;
    }
}