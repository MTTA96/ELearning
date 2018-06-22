package com.eways.elearning.Utils;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by ADMIN on 6/19/2018.
 */

public class ActivityUtils {

    public static void ChangeActivity(Activity fromActivity, Class<?> toActivity ){
        Intent i = new Intent(fromActivity, toActivity);
        fromActivity.startActivity(i);
    }
}
