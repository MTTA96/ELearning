package com.eways.elearning.Presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Model.Search.SearchResults;
import com.eways.elearning.Utils.SupportKeys;
import com.eways.elearning.Views.Activity.HomeActivity;


/**
 * Created by zzzzz on 5/22/2018.
 */

public class SearchPresenter implements DataCallBack {
    private Context context;
    private DataCallBack dataCallBack;

    public SearchPresenter(Context context, DataCallBack dataCallBack) {
        this.context = context;
        this.dataCallBack = dataCallBack;
    }

    /** Get search results */
    public void search(String keyword) {

        // Check current type for searching
        switch (HomeActivity.currentSearchType) {
            case SupportKeys.SEARCH_SUBJECTS:
                SearchResults.search(keyword, this);
                break;
            case SupportKeys.SEARCH_STUDENTS:
                break;
        }
    }

    @Override
    public void dataCallBack(int resultCode, @Nullable Bundle bundle) {
        // Handle error
        if (resultCode == SupportKeys.FAILED_CODE) {
            Log.d("Search presenter", "Search failed!");
            dataCallBack.dataCallBack(resultCode, null);
            return;
        }

        // Get data success
        dataCallBack.dataCallBack(resultCode, bundle);
    }
}
