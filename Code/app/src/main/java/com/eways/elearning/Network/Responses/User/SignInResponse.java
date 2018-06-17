package com.eways.elearning.Network.Responses.User;

import com.eways.elearning.Network.Responses.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zzzzz on 6/17/2018.
 */

public class SignInResponse extends BaseResponse {

    @SerializedName("Uid")
    @Expose
    private String uID;

    public String getUId() {
        return uID;
    }

    public void setUId(String uID) {
        this.uID = uID;
    }

}
