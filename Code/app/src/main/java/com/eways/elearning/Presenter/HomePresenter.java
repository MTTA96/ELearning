package com.eways.elearning.Presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Interfaces.DataCallback.SearchSuggestion.SearchSuggestionCallBack;
import com.eways.elearning.Model.Banner;
import com.eways.elearning.Interfaces.DataCallback.BannerCallBack;
import com.eways.elearning.Interfaces.DataCallback.Subject.FavSubjectWithCoursesCallBack;
import com.eways.elearning.Interfaces.DataCallback.Subject.TrendingSubjectCallBack;
import com.eways.elearning.Interfaces.DataCallback.User.TopTutorsCallBack;
import com.eways.elearning.Model.Search.SearchResults;
import com.eways.elearning.Model.Search.SearchSuggestions;
import com.eways.elearning.Model.Subject.Subject;
import com.eways.elearning.Model.Account.User;
import com.eways.elearning.Utils.SharedPreferences.SharedPrefSupportKeys;
import com.eways.elearning.Utils.SharedPreferences.SharedPrefUtils;
import com.eways.elearning.Utils.SupportKeys;
import com.eways.elearning.Views.Activity.HomeActivity;

import java.util.ArrayList;

/**
 * Created by ADMIN on 5/20/2018.
 */

public class HomePresenter implements DataCallBack, BannerCallBack, TrendingSubjectCallBack, TopTutorsCallBack, FavSubjectWithCoursesCallBack {

    private DataCallBack dataCallBack;
    private SearchSuggestionCallBack searchSuggestionCallBack;
    private BannerCallBack bannerCallBack;
    private TopTutorsCallBack topTutorsCallBack;
    private TrendingSubjectCallBack trendingSubjectCallBack;
    private FavSubjectWithCoursesCallBack favSubjectWithCoursesCallBack;
    private SharedPrefUtils sharedPrefUtils;

    /** For home fragment */
    public HomePresenter(Context context) {
        sharedPrefUtils = new SharedPrefUtils(context, SharedPrefSupportKeys.SHARED_PREF_FILE_NAME);
    }

    /** For home activity */
    public HomePresenter(Context context, SearchSuggestionCallBack searchSuggestionCallBack) {
        this.searchSuggestionCallBack = searchSuggestionCallBack;
        sharedPrefUtils = new SharedPrefUtils(context, SharedPrefSupportKeys.SHARED_PREF_FILE_NAME);
    }

    /** Search suggestions */
    public void searchSuggestions(String keyWord) {

        // Check current type for searching
        switch (HomeActivity.currentSearchType) {
            case SupportKeys.SEARCH_SUBJECTS:
                SearchResults.searchSubjectSuggestions(keyWord, this);
                break;
            case SupportKeys.SEARCH_STUDENTS:
                SearchResults.searchSubjectSuggestions(keyWord, this);
                break;
        }

    }

    /** Get Banners */
    public void getBanners(BannerCallBack bannerCallBack) {
        this.bannerCallBack = bannerCallBack;
        Banner.getBanners(this);
    }

    /** Get top tutors */
    public void getTopTutors(TopTutorsCallBack topTutorsCallBack) {
        this.topTutorsCallBack = topTutorsCallBack;
        User.getTopTutors(this);
    }

    /** Get trending subjects */
    public void getTrendingSubjects(TrendingSubjectCallBack trendingSubjectCallBack) {
        this.trendingSubjectCallBack = trendingSubjectCallBack;
        Subject.getTrendingSubjects(this);
    }

    /** Get user favorite subjects list */
    public void getUserFavoriteSubjects(FavSubjectWithCoursesCallBack favSubjectWithCoursesCallBack) {
        this.favSubjectWithCoursesCallBack = favSubjectWithCoursesCallBack;
        User.getUserFavoriteSubjectsWithCourses( sharedPrefUtils.getString(SharedPrefSupportKeys.UID), this);
    }

    /**
     *  MARK: - Handle data from server
     *  */

    /** Search */
    @Override
    public void dataCallBack(int resultCode, @Nullable Bundle bundle) {

        // handle errors
        if (resultCode == SupportKeys.FAILED_CODE) {
            Log.d(getClass().getSimpleName(), "Search error!");
            dataCallBack.dataCallBack(resultCode, null);
            return;
        }

        // Get data success
        ArrayList<SearchSuggestions> results = (ArrayList<SearchSuggestions>) bundle.getSerializable(null);
        searchSuggestionCallBack.searchSuggestionCallBack(SupportKeys.SUCCESS_CODE, results);

    }

    /** Banner */
    @Override
    public void bannersCallBack(int resultCode, ArrayList<Banner> banners) {
        // Handle errors
        if (resultCode == SupportKeys.FAILED_CODE) {
            Log.d(getClass().getSimpleName(), "Error!");
            bannerCallBack.bannersCallBack(resultCode, null);
            return;
        }

        // Get data success
//        bannerCallBack.bannersCallBack(resultCode, bundle);
    }

    /** Top tutors */
    @Override
    public void topTutorCallBack(int errorCode, ArrayList result) {
        // Handle errors
        if (errorCode == SupportKeys.FAILED_CODE) {
            Log.d(getClass().getSimpleName(), "Error!");
            topTutorsCallBack.topTutorCallBack(errorCode, null);
            return;
        }

        // Get data success
        topTutorsCallBack.topTutorCallBack(errorCode, result);
    }

    /** Trending subjects */
    @Override
    public void trendingSubjectsCallBack(int errorCode, ArrayList result) {
        // Handle errors
        if (errorCode == SupportKeys.FAILED_CODE) {
            Log.d(getClass().getSimpleName(), "Error!");
            trendingSubjectCallBack.trendingSubjectsCallBack(errorCode, null);
            return;
        }

        // Get data success
        trendingSubjectCallBack.trendingSubjectsCallBack(errorCode, result);
    }

    /** Favorite subject with courses */
    @Override
    public void favSubjectsCourseCallBack(int errorCode, ArrayList result) {
        // Handle errors
        if (errorCode == SupportKeys.FAILED_CODE) {
            Log.d(getClass().getSimpleName(), "Error!");
            favSubjectWithCoursesCallBack.favSubjectsCourseCallBack(errorCode, null);
            return;
        }

        // Get data success
        favSubjectWithCoursesCallBack.favSubjectsCourseCallBack(errorCode, result);
    }
}
