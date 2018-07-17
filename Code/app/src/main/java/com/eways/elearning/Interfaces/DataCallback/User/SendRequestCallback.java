package com.eways.elearning.Interfaces.DataCallback.User;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by zzzzz on 7/15/2018.
 */

public interface SendRequestCallback {
    void sendRequestCallback(int resultCode, @Nullable String msg);
}
