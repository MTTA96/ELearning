package com.eways.elearning.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.eways.elearning.Views.Fragment.InfoUserFragment;
import com.eways.elearning.Views.Fragment.SpecialDocumentFragment;

/**
 * Created by ADMIN on 7/6/2018.
 */

public class UserInfoPager extends FragmentStatePagerAdapter {

    public UserInfoPager(FragmentManager fragmentManager) {
        super(fragmentManager);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment frag=null;
        switch (position){
            case 0:
                frag = new InfoUserFragment();
                break;
            case 1:
                frag = new SpecialDocumentFragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Thông tin";
                break;
            case 1:
                title = "Tài liệu chuyên môn";
                break;

        }
        return title;
    }
}
