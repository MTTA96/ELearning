package com.eways.elearning.Network.Responses.User;

import com.eways.elearning.Model.Subject;
import com.eways.elearning.Model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by zzzzz on 6/2/2018.
 */

public class UserFavoriteWithCourseResponse {
    @SerializedName("errorCode")
    @Expose
    private Integer errorCode;
    @SerializedName("results")
    @Expose
    private ArrayList<Subject> listSubject = null;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public ArrayList<Subject> getListSubject() {
        return listSubject;
    }

    public void setListSubject(ArrayList<Subject> listSubject) {
        this.listSubject = listSubject;
    }
}
