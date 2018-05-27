package com.eways.elearning.Network.Services;

import com.eways.elearning.Network.Responses.SearchBaseResponse;
import com.eways.elearning.Network.Responses.SearchSuggestionsBaseResponse;
import com.eways.elearning.Network.ServerUrl;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zzzzz on 5/22/2018.
 */

public interface ETutorServicesImp {

    /** Search */
    @GET(ServerUrl.SEARCH_URL)
    Call<SearchBaseResponse> search(@Query("keyword") String keyWord);

    /** Search suggestions */
    @GET(ServerUrl.SEARCH_TUTOR_SUGGESTIONS_URL)
    Call<SearchSuggestionsBaseResponse> searchSuggestions(@Query("keyword") String keyWord);
}
