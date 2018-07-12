package com.eways.elearning.Presenter.Authentication;

import android.content.Context;

import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Interfaces.DataCallback.User.UserCallBack;
import com.eways.elearning.Model.Account.User;
import com.eways.elearning.Utils.SharedPreferences.SharedPrefSupportKeys;
import com.eways.elearning.Utils.SharedPreferences.SharedPrefUtils;
import com.eways.elearning.Utils.SupportKeys;


/**
 * Created by zzzzz on 7/1/2018.
 */

public class UserPresenter implements UserCallBack {

    private Context context;
    private SharedPrefUtils sharedPreferencesUtils;
    private UserCallBack userCallBack;

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

    @Override
    public void userCallBack(int errorCode, User user) {
        if (errorCode == SupportKeys.FAILED_CODE) {
            userCallBack.userCallBack(errorCode, null);
            return;
        }

        userCallBack.userCallBack(errorCode, user);

    }
}
