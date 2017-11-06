package com.eways.elearning.Handler;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Tien Phat on 05/11/2017.
 * Adapter cho viewPager cửa Fragment_gia_su
 */

public class SelectionPagerAdapter_BaiDang extends FragmentPagerAdapter {

    public SelectionPagerAdapter_BaiDang(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return PlaceholderFragment_BaiDang.newInstance(position); //Lấy fragment
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override //Gắn "label" cho "button"
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "TÌM GIA SƯ";
            case 1:
                return "TÌM HỌC VIÊN";
        }
        return null;
    }
}
