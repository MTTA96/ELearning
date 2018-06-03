package com.eways.elearning.Network.Responses.User;

/**
 * Created by ADMIN on 3/18/2018.
 */

import com.eways.elearning.Model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserBaseResponse {

    @SerializedName("errorCode")
    @Expose
    private Integer errorCode;
    @SerializedName("teacher")
    @Expose
    private User teacher;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

}

