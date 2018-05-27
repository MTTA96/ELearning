package com.eways.elearning.Network.Responses;

import com.eways.elearning.Model.SearchResults;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zzzzz on 5/27/2018.
 */

public class SearchBaseResponse {
    @SerializedName("errorCode")
    @Expose
    private Integer errorCode;
    @SerializedName("results")
    @Expose
    private SearchResults results;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public SearchResults getResults() {
        return results;
    }

    public void setResults(SearchResults results) {
        this.results = results;
    }
}
