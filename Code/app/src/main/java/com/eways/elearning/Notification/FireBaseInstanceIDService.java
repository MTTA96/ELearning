package com.eways.elearning.Notification;

import android.util.Log;

import com.eways.elearning.Utils.params.GlobalParams;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by ADMIN on 7/13/2018.
 */

public class FireBaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {

//Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

//Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);
    }
}
