package com.eways.elearning.Network.Responses;

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
    @SerializedName("results")
    @Expose
    private ArrayList results = null;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public ArrayList getResults() {
        return results;
    }

    public void setResults(ArrayList results) {
        this.results = results;
    }

}
