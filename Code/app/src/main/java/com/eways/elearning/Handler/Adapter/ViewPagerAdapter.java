package com.eways.elearning.Handler.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by yowin on 09/11/2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> listFragment = new ArrayList<>();
    private ArrayList<String> listTitles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listTitles.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    //Thêm fragment vào list để quản lý
    public void addFragment(Fragment fragment, String title){
        listFragment.add(fragment);
        listTitles.add(title);
    }
}
