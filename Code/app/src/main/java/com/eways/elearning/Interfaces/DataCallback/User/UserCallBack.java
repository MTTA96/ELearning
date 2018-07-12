package com.eways.elearning.Interfaces.DataCallback.User;

import android.os.Bundle;

import com.eways.elearning.Model.Account.User;

/**
 * Created by zzzzz on 6/2/2018.
 */

public interface UserCallBack {
    void userCallBack(int errorCode, User user);
}
