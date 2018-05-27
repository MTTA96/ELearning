package com.eways.elearning.Presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Model.SearchResults;
import com.eways.elearning.Utils.SupportKey;
import com.eways.elearning.Views.Activity.HomeActivity;

import java.util.ArrayList;


/**
 * Created by zzzzz on 5/22/2018.
 */

public class SearchPresenter implements DataCallBack {

    private DataCallBack dataCallBack;

    public SearchPresenter(DataCallBack dataCallBack) {
        this.dataCallBack = dataCallBack;
    }

    /** Get search results */
    public void search(String keyword, String filter) {

        // Check current type for searching
        switch (HomeActivity.currentSearchType) {
            case SupportKey.SEARCH_SUBJECTS:
                SearchResults.search(keyword, this);
                break;
            case SupportKey.SEARCH_STUDENTS:
                break;
        }
    }

    @Override
    public void dataCallBack(int resultCode, @Nullable Bundle bundle) {
        ArrayList resultsList = (ArrayList) bundle.getSerializable(null);

        if (resultsList.size() > 0) {

        }
    }
}
