package com.eways.elearning.Handler;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.eways.elearning.R;
import com.eways.elearning.View.Activity.MainActivity;
import com.eways.elearning.View.Dialog.LoadingDialog;
import com.eways.elearning.View.Fragment.Home.NewHomeFragment;

/**
 * Created by zzzzz on 10/8/2017.
 *
 * Description: Quản lý các fragment
 */

public class FragmentHandler {
    private Context context;
    private FragmentManager fragmentManager;
    private LoadingDialog loadingDialog;

    public FragmentHandler(Context context, FragmentManager fm){
        this.context = context;
        fragmentManager = fm;
    }

    public void  ChuyenFragment(Fragment toFragment, boolean toBackStack, @NonNull String tag){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ((MainActivity)context).tvScreenTitle.setText("");
        //Kiểm tra fragment chuyển tiếp
        if(toFragment instanceof NewHomeFragment)
            XoaTatCaFragment();
        else
            ((AppCompatActivity)context).getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

        //Kiểm tra lưu vào backstack
        if(toBackStack)
            transaction.replace(R.id.content_main, toFragment, tag).addToBackStack(tag).commit();
        else {
            if (tag != null)
                transaction.replace(R.id.content_main, toFragment, tag).commit();
            else
                transaction.replace(R.id.content_main, toFragment).commit();
        }
    }

    /**
     * Xóa fragment hiện tại
     * */
    public void XoaFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.content_main);
        if (currentFragment != null) {
            transaction.remove(currentFragment).commit();
            fragmentManager.popBackStack();
        }
    }

    public void XoaTatCaFragment(){
        int count = fragmentManager.getBackStackEntryCount();
        while(count > 1 ){
            XoaFragment();
            count--;
        }
    }

    /**
     * Lấy fragment trước đó
     * */
    public String getPreviousFragmentTag(){
        return fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount()-1).getName();
    }
}
