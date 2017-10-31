package com.eways.elearning.Model;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.eways.elearning.R;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapFragment;
import com.google.firebase.database.Transaction;

/**
 * Created by zzzzz on 10/8/2017.
 *
 * Description: Quản lý các fragment
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
        if(toBackStack)
            transaction.replace(R.id.content_main, toFragment).addToBackStack(tag).commit();
        else
            transaction.replace(R.id.content_main, toFragment).commit();
    }

    //Xóa fragment hiện tại
    public void XoaFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.content_main);
        if (currentFragment != null) {
            transaction.remove(currentFragment).commit();
            fragmentManager.popBackStack();
        }
    }
}
