package com.eways.elearning.Network;

/**
 * Created by zzzzz on 3/25/2018.
 */

import com.eways.elearning.Model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseUserResponse {

    @SerializedName("errorCode")
    @Expose
    private Integer errorCode;
    @SerializedName("user")
    @Expose
    private User user;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
