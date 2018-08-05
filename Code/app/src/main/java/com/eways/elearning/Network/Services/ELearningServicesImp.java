package com.eways.elearning.Network.Services;

import com.eways.elearning.Model.Banner;
import com.eways.elearning.Model.Subject.Subject;
import com.eways.elearning.Network.Responses.BaseResponse;
import com.eways.elearning.Network.Responses.Course.CourseListResponse;
import com.eways.elearning.Network.Responses.Course.CourseRespsonse;
import com.eways.elearning.Network.Responses.SearchBaseResponse;
import com.eways.elearning.Network.Responses.SearchSuggestionsBaseResponse;
import com.eways.elearning.Network.Responses.User.TrendingSubjectResponse;
import com.eways.elearning.Network.Responses.User.UserListResponse;
import com.eways.elearning.Network.ServerUrl;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zzzzz on 5/22/2018.
 */

public interface ELearningServicesImp {

    /** Get subject list */
    @GET(ServerUrl.GET_SUBJECT_LIST_URL)
    Call<ArrayList<Subject>> getSubjectList();

    /** Search */
    @GET(ServerUrl.SEARCH_URL)
    Call<SearchBaseResponse> search(@Query("request") String keyWord);

    /** Search suggestions */
    @GET(ServerUrl.SEARCH_TUTOR_SUGGESTIONS_URL)
    Call<SearchSuggestionsBaseResponse> searchSuggestions(@Query("request") String keyWord);

    /** Banner */
    @GET(ServerUrl.BANNER_URL)
    Call<ArrayList<Banner>> getBanners();

    /** Top tutors */
    @GET(ServerUrl.TOP_TUTORS)
    Call<UserListResponse> getTopTutors();

    /** Trending subjects */
    @GET(ServerUrl.TRENDING_SUBJECTS)
    Call<TrendingSubjectResponse> getTrendingSubjects();

    /** ----- COURSE ----- */

    /** Get course by ID */
    @GET(ServerUrl.GET_COURSE_BY_ID_URL)
    Call<CourseRespsonse> getCourseById(@Query("IdCourse") String courseId);

    /** Get course list by subject */
    @GET(ServerUrl.GET_COURSE_LIST_BY_SUBJECT_URL)
    Call<CourseListResponse> getCourseListBySubject(@Query("IdSubject") String subjectId,
                                                    @Query("CourseType") String courseType);

    /** Create course */
    @POST(ServerUrl.CREATE_COURSE_URL)
    @FormUrlEncoded
    Call<BaseResponse> createCourse(@Field("mydata") String myData);

}
