package com.eways.elearning.Network.Responses;

/**
 * Created by ADMIN on 5/20/2018.
 */


import java.util.ArrayList;
import java.util.List;

import com.eways.elearning.Model.SearchResults;
import com.eways.elearning.Model.SearchSuggestions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchSuggestionsBaseResponse {

    @SerializedName("errorCode")
    @Expose
    private Integer errorCode;
    @SerializedName("results")
    @Expose
    private ArrayList<SearchSuggestions> results = null;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public ArrayList<SearchSuggestions> getResults() {
        return results;
    }

    public void setResults(ArrayList<SearchSuggestions> results) {
        this.results = results;
    }


}
