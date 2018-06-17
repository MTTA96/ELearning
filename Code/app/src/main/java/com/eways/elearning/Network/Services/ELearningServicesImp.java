package com.eways.elearning.Network.Services;

import com.eways.elearning.Network.Responses.BannerResponse;
import com.eways.elearning.Network.Responses.SearchBaseResponse;
import com.eways.elearning.Network.Responses.SearchSuggestionsBaseResponse;
import com.eways.elearning.Network.Responses.User.TrendingSubjectResponse;
import com.eways.elearning.Network.Responses.User.UserFavoriteSubjectResponse;
import com.eways.elearning.Network.Responses.User.UserListResponse;
import com.eways.elearning.Network.ServerUrl;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zzzzz on 5/22/2018.
 */

public interface ELearningServicesImp {

    /** Search */
    @GET(ServerUrl.SEARCH_URL)
    Call<SearchBaseResponse> search(@Query("keyword") String keyWord);

    /** Search suggestions */
    @GET(ServerUrl.SEARCH_TUTOR_SUGGESTIONS_URL)
    Call<SearchSuggestionsBaseResponse> searchSuggestions(@Query("keyword") String keyWord);

    /** Banner */
    @GET(ServerUrl.BANNER_URL)
    Call<BannerResponse> getBanners();

    /** Top tutors */
    @GET(ServerUrl.TOP_TUTORS)
    Call<UserListResponse> getTopTutors();

    /** Trending subjects */
    @GET(ServerUrl.TRENDING_SUBJECTS)
    Call<TrendingSubjectResponse> getTrendingSubjects();

    /** User's favorite subject */
    @GET(ServerUrl.GET_USER_FAVORITE_SUBJECTS)
    Call<UserFavoriteSubjectResponse> getUserFavoriteSubjects(@Query("Uid") String uID);

}
