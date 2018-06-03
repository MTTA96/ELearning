package com.eways.elearning.Network.Responses;

/**
 * Created by ADMIN on 5/20/2018.
 */


import java.util.ArrayList;

import com.eways.elearning.Model.SearchSuggestions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchSuggestionsBaseResponse {

    @SerializedName("errorCode")
    @Expose
    private Integer errorCode;
    @SerializedName("suggestionLsit")
    @Expose
    private ArrayList<SearchSuggestions> suggestionLsit = null;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public ArrayList<SearchSuggestions> getSuggestionLsit() {
        return suggestionLsit;
    }

    public void setSuggestionLsit(ArrayList<SearchSuggestions> suggestionLsit) {
        this.suggestionLsit = suggestionLsit;
    }


}
