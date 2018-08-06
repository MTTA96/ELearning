package com.eways.elearning.Presenter.Authentication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Model.Account.User;
import com.eways.elearning.R;
import com.eways.elearning.Utils.SharedPreferences.SharedPrefSupportKeys;
import com.eways.elearning.Utils.SharedPreferences.SharedPrefUtils;
import com.eways.elearning.Utils.SupportKeys;

/**
 * Created by zzzzz on 5/20/2018.
 */

public class SignInPresenter implements DataCallBack {
    private Context context;
    private DataCallBack dataCallBack;
    private SharedPrefUtils sharedPreferencesUtils;
    private String userName, password;

    public SignInPresenter(Context context, DataCallBack dataCallBack) {
        this.context = context;
        this.dataCallBack = dataCallBack;
        sharedPreferencesUtils = new SharedPrefUtils(context, SharedPrefSupportKeys.SHARED_PREF_FILE_NAME);
    }

    /**
     * Sign in
     */
    public void signIn(String userName, String password) {
        this.userName = userName;
        this.password = password;

        // Verify user with server
        User.signIn(userName, password, this);
    }

    /**
     * Handle data from server
     */
    @Override
    public void dataCallBack(int resultCode, @Nullable Bundle bundle) {
        // Handle errors
        if (resultCode == SupportKeys.FAILED_CODE) {
            dataCallBack.dataCallBack(resultCode, null);
            return;
        }

        // Get data success
        int status = Integer.parseInt(bundle.getString(null));
        String msg = "";
        switch (status) {
            // Sign in success
            case 0:
                msg = context.getString(R.string.msg_account_has_not_signed_up);
                break;

            // User existed in ETutor
            case 1:
                sharedPreferencesUtils.putString(SharedPrefSupportKeys.UID, bundle.getString("uID"), true);
                sharedPreferencesUtils.putString(SharedPrefSupportKeys.userName, userName, true);
                sharedPreferencesUtils.putString(SharedPrefSupportKeys.password, password, true);
                dataCallBack.dataCallBack(resultCode, bundle);
                break;

            // Wrong info
            case 2:
                msg = context.getString(R.string.msg_wrong_info);
                break;

            // Banned account
            case 3:
                msg = context.getString(R.string.msg_banned_account);
                break;
        }

        bundle.putString(SupportKeys.BUNDLE_MSG, msg);

        // Send result to view
        dataCallBack.dataCallBack(resultCode, bundle);
    }
}
