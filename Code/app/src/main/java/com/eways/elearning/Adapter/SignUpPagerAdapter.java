package com.eways.elearning.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by ADMIN on 4/14/2018.
 */

public class SignUpPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> listFragment ;
    public SignUpPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public SignUpPagerAdapter(FragmentManager fm, ArrayList<Fragment> listFragment) {
        super(fm);

        this.listFragment = listFragment;
    }



    @Override
    public Fragment getItem(int position) {
        Fragment frag=null;

        for (int i = 0; i < listFragment.size(); i++){
            if (position == i){
                frag = listFragment.get(i);
            }
        }

        return frag;
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }
}
