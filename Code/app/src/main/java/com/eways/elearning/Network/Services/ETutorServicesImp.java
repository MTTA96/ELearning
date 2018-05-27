package com.eways.elearning.Network.Services;

import com.eways.elearning.Network.Responses.ListResponse;
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
    Call<ListResponse> search(@Query("CourseType") String type,
                              @Query("SubjectName") String subjectName);

    /** Search suggestions */
    @GET(ServerUrl.SEARCH_SUGGESTIONS_URL)
    Call<ListResponse> searchSuggestions(@Query("CourseType") String type,
                              @Query("SubjectName") String subjectName);
}
