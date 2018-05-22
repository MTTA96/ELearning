package com.eways.elearning.Network;

/**
 * Created by ADMIN on 5/20/2018.
 */


import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListResponse {

    @SerializedName("errorCode")
    @Expose
    private Integer errorCode;
    @SerializedName("listC")
    @Expose
    private ArrayList listC = null;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public ArrayList getListC() {
        return listC;
    }

    public void setListC(ArrayList listC) {
        this.listC = listC;
    }

}
