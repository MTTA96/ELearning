package com.eways.elearning.Presenter.Authentication;

import android.content.Context;

import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Utils.SharedPreferences.SharedPrefSupportKeys;
import com.eways.elearning.Utils.SharedPreferences.SharedPrefUtils;
import com.eways.elearning.Utils.SupportKeys;


/**
 * Created by zzzzz on 7/1/2018.
 */

public class UserPresenter {

    private Context context;
    private SharedPrefUtils sharedPreferencesUtils;

    public UserPresenter(Context context) {
        this.context = context;
        sharedPreferencesUtils = new SharedPrefUtils(context, SharedPrefSupportKeys.SHARED_PREF_FILE_NAME);
    }

    public void signOut(DataCallBack dataCallBack) {

        sharedPreferencesUtils.clear();
        dataCallBack.dataCallBack(SupportKeys.SUCCESS_CODE, null);

    }
}
