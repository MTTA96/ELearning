package com.eways.elearning.Handler;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ADMIN on 5/7/2018.
 */

public class FragmentUtils {
    private Context context;
    private int resource;

    public FragmentUtils(Context context, int resource) {
        this.context = context;
        this.resource = resource;
    }

    public void changeFragment(Fragment toFragment, int animationIn, int animationOut){
        ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().setCustomAnimations(animationIn, animationOut).replace(resource, toFragment).commit();
    }
}
