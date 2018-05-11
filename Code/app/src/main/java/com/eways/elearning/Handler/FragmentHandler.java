package com.eways.elearning.Handler;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by zzzzz on 10/8/2017.
 *
 * Description: Quản lý các fragment
 */

public class FragmentHandler {
    private Context context;
    private int resource;

    public FragmentHandler(Context context, int resource) {
        this.context = context;
        this.resource = resource;
    }

    public void changeFragment(Fragment toFragment, int animationIn, int animationOut){
        ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().setCustomAnimations(animationIn, animationOut).replace(resource, toFragment).commit();
    }
}
