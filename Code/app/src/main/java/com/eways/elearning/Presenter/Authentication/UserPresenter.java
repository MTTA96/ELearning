package com.eways.elearning.Presenter.Authentication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Interfaces.DataCallback.User.UserCallBack;
import com.eways.elearning.Model.Account.User;
import com.eways.elearning.Model.Request;
import com.eways.elearning.Utils.SharedPreferences.SharedPrefSupportKeys;
import com.eways.elearning.Utils.SharedPreferences.SharedPrefUtils;
import com.eways.elearning.Utils.SupportKeys;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by zzzzz on 7/1/2018.
 */

public class UserPresenter implements UserCallBack, DataCallBack {

    private Context context;
    private SharedPrefUtils sharedPreferencesUtils;
    private UserCallBack userCallBack;
    private DataCallBack dataCallBack;

    public UserPresenter(Context context) {
        this.context = context;
        sharedPreferencesUtils = new SharedPrefUtils(context, SharedPrefSupportKeys.SHARED_PREF_FILE_NAME);
    }

    public void signOut(DataCallBack dataCallBack) {

        sharedPreferencesUtils.clear();
        dataCallBack.dataCallBack(SupportKeys.SUCCESS_CODE, null);

    }

    public void getUserInfo(String uId, UserCallBack userCallBack) {

        this.userCallBack = userCallBack;

        User.getUserInfo(uId, this);

    }

    public void sendRequestToCourse(String courseId, DataCallBack dataCallBack) {

        this.dataCallBack = dataCallBack;

        // Prepare data

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String sentDate = df.format(c);

        Request request = new Request(courseId, sharedPreferencesUtils.getString(SharedPrefSupportKeys.UID), sentDate, "", "");

        Gson gson = new Gson();
        String jsonRequest = gson.toJson(request);

        // Call API

        User.sendRequest(jsonRequest, this);

    }

    @Override
    public void userCallBack(int errorCode, User user) {
        if (errorCode == SupportKeys.FAILED_CODE) {
            userCallBack.userCallBack(errorCode, null);
            return;
        }

        userCallBack.userCallBack(errorCode, user);

    }

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
