package com.eways.elearning.Presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Model.Course;
import com.eways.elearning.Model.SearchResults;
import com.eways.elearning.Utils.SupportKey;
import com.eways.elearning.Utils.params.GlobalParams;
import com.eways.elearning.Views.Activity.HomeActivity;
import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Created by ADMIN on 5/20/2018.
 */

public class HomePresenter implements DataCallBack {
    private DataCallBack dataCallBack;

    public HomePresenter(DataCallBack dataCallBack) {
        this.dataCallBack = dataCallBack;
    }

    /** Search suggestions */
    public void searchSuggestions(String keyWord) {
        // Check current type for searching
        switch (HomeActivity.currentSearchType) {
            case SupportKey.SEARCH_SUBJECTS:
                SearchResults.searchSubjectSuggestions(keyWord, this);
                break;
            case SupportKey.SEARCH_STUDENTS:
                SearchResults.searchSubjectSuggestions(keyWord, this);
                break;
        }
    }

    /** handle data from server */
    @Override
    public void dataCallBack(int result, @Nullable Bundle bundle) {
        // handle errors
        if (result == SupportKey.FAILED_CODE) {
            dataCallBack.dataCallBack(result, null);
            return;
        }

        // Get data success
        ArrayList tempList = (ArrayList) bundle.getSerializable(null);
        ArrayList results = new ArrayList();

        for (int i = 0; i < tempList.size(); i++) {

            JsonObject jsonObject = GlobalParams.getInstance().getGSon().toJsonTree(tempList.get(i)).getAsJsonObject();
            results.add(GlobalParams.getInstance().getGSon().fromJson(jsonObject.toString(), Course.class));

        }
        bundle.clear();
        bundle.putSerializable(null, result);
        dataCallBack.dataCallBack(result, bundle);
    }
}
