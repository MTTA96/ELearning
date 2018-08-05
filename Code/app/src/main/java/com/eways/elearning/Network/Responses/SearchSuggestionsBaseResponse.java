package com.eways.elearning.Network.Responses;

/**
 * Created by ADMIN on 5/20/2018.
 */


import java.util.ArrayList;

import com.eways.elearning.Model.Search.SearchSuggestions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchSuggestionsBaseResponse {

    @SerializedName("response")
    @Expose
    private ArrayList<SearchSuggestions> suggestionList = null;

    public ArrayList<SearchSuggestions> getSuggestionList() {
        return suggestionList;
    }

    public void setSuggestionList(ArrayList<SearchSuggestions> suggestionList) {
        this.suggestionList = suggestionList;
    }

}
