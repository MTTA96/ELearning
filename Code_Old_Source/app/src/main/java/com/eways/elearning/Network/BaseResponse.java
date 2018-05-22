package com.eways.elearning.Network;

import com.eways.elearning.Model.TaiKhoan.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zzzzz on 5/6/2018.
 */

public class BaseResponse {
    @SerializedName("errorCode")
    @Expose
    private Integer errorCode;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
