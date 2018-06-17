package com.eways.elearning.Network.Services;

import com.eways.elearning.Adapter.Course.CourseAdapter;
import com.eways.elearning.Model.Banner;
import com.eways.elearning.Network.Responses.CourseListResponse;
import com.eways.elearning.Network.Responses.SearchBaseResponse;
import com.eways.elearning.Network.Responses.SearchSuggestionsBaseResponse;
import com.eways.elearning.Network.Responses.User.TrendingSubjectResponse;
import com.eways.elearning.Network.Responses.User.UserFavoriteSubjectResponse;
import com.eways.elearning.Network.Responses.User.UserListResponse;
import com.eways.elearning.Network.ServerUrl;

import java.util.ArrayList;

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
    Call<ArrayList<Banner>> getBanners();

    /** Top tutors */
    @GET(ServerUrl.TOP_TUTORS)
    Call<UserListResponse> getTopTutors();

    /** Trending subjects */
    @GET(ServerUrl.TRENDING_SUBJECTS)
    Call<TrendingSubjectResponse> getTrendingSubjects();

    /** Get course list */
    @GET(ServerUrl.GET_COURSE_LIST_BY_SUBJECT_URL)
    Call<CourseListResponse> getCourseListBySubject(@Query("IdSubject") String subjectId,
                                                    @Query("CourseType") String courseType);


}
