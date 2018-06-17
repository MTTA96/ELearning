package com.eways.elearning.Presenter.Authentication;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Model.Account.User;
import com.eways.elearning.Utils.SupportKeys;
import com.google.gson.Gson;

/**
 * Created by zzzzz on 5/13/2018.
 */

public class SignUpInfoPresenter implements DataCallBack {
    private DataCallBack dataCallBack;

    public SignUpInfoPresenter(DataCallBack dataCallBack) {
        this.dataCallBack = dataCallBack;
    }

    /** Sign up */
    public void signUp(User user) {
        Gson gson = new Gson();

        // Parse obj to json
        String jsonData = gson.toJson(user);

        // Send to server
        User.signUp(jsonData, this);
    }

    /** handle results from database */
    @Override
    public void dataCallBack(int resultCode, @Nullable Bundle bundle) {
        // handle error
        if (resultCode == SupportKeys.FAILED_CODE) {
            dataCallBack.dataCallBack(resultCode, null);
            return;
        }

        // handle data
        dataCallBack.dataCallBack(resultCode, bundle);
    }

}
