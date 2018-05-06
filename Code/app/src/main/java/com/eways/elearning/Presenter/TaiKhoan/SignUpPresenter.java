package com.eways.elearning.Presenter.TaiKhoan;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.eways.elearning.Handler.Other.DataCallBack;
import com.eways.elearning.Model.TaiKhoan.User;
import com.google.gson.Gson;

/**
 * Created by zzzzz on 4/14/2018.
 */

public class SignUpPresenter implements DataCallBack{
    private DataCallBack dataCallBack;

    public SignUpPresenter(DataCallBack dataCallBack) {
        this.dataCallBack = dataCallBack;
    }

    public void signUp(User user) {
        Gson gson = new Gson();

        // Parse obj to json
        String jsonData = gson.toJson(user);

        // Send to server
        User.signUp(jsonData, this);
    }

    @Override
    public void dataCallBack(String result, @Nullable Bundle bundle) {
        if (result.compareTo("Success") == 0) {
            dataCallBack.dataCallBack(result, null);
            return;
        }

        dataCallBack.dataCallBack(result, null);
    }
}
