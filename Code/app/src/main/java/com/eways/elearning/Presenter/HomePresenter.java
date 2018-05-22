package com.eways.elearning.Presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Model.Course;
import com.eways.elearning.Utils.SupportKey;

/**
 * Created by ADMIN on 5/20/2018.
 */

public class HomePresenter implements DataCallBack {
    private DataCallBack dataCallBack;

    public HomePresenter(DataCallBack dataCallBack) {
        this.dataCallBack = dataCallBack;
    }

    /** Search courses */
    public void searchCorese(String keyWord, String filter) {
        Course.searchCourses(keyWord, filter, this);
    }

    /** Handle data from server */
    @Override
    public void dataCallBack(int result, @Nullable Bundle bundle) {
        // Handle errors
        if (result == SupportKey.FAILED_CODE) {
            dataCallBack.dataCallBack(result, null);
            return;
        }

        // Get data success
        dataCallBack.dataCallBack(result, bundle);
    }
}
