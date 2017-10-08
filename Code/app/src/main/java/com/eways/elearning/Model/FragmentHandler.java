package com.eways.elearning.Model;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.eways.elearning.R;
import com.google.firebase.database.Transaction;

/**
 * Created by zzzzz on 10/8/2017.
 */

public class FragmentHandler {
    private Context context;
    private FragmentManager fragmentManager;

    public FragmentHandler(Context context, FragmentManager fm){
        this.context = context;
        fragmentManager = fm;
    }

    public void ChuyenFragment(Fragment toFragment, boolean toBackStack, @Nullable String tag){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(toBackStack == true)
            transaction.replace(R.id.content_main, toFragment).addToBackStack(tag).commit();
        else
            transaction.replace(R.id.content_main, toFragment).commit();
    }
}
