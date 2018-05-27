package com.eways.elearning.Presenter.Authentication;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Model.User;
import com.eways.elearning.Utils.SupportKey;

/**
 * Created by zzzzz on 5/20/2018.
 */

public class SignInPresenter implements DataCallBack {
    private DataCallBack dataCallBack;

    public SignInPresenter(DataCallBack dataCallBack) {
        this.dataCallBack = dataCallBack;
    }

    /** Sign in */
    public void signIn(String userName, String password) {
        // Verify user with server
        User.signIn(userName, password, this);
    }

    /** Handle data from server */
    @Override
    public void dataCallBack(int resultCode, @Nullable Bundle bundle) {
        // Handle errors
        if (resultCode == SupportKey.FAILED_CODE) {
            dataCallBack.dataCallBack(resultCode, null);
            return;
        }

        // Get data success
        dataCallBack.dataCallBack(resultCode, bundle);
    }
}
