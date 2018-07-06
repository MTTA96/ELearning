package com.eways.elearning.Views.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.eways.elearning.Adapter.UserInfoPager;
import com.eways.elearning.R;

public class InfoUserViewerActivity extends FragmentActivity {
    ViewPager mViewPager;
    TabLayout mTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user_viewer);

        declare_views();
        handle_views();
    }

    private void declare_views(){
        mViewPager = findViewById(R.id.viewpager);
        mTab = findViewById(R.id.tab_layout);
    }

    private void handle_views(){
        SetUpViewPager();
    }

    private void SetUpViewPager(){
        FragmentManager manager = getSupportFragmentManager();
        UserInfoPager adapter = new UserInfoPager(manager);
        mViewPager.setAdapter(adapter);
        mTab.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTab));
        mTab.setTabsFromPagerAdapter(adapter);
    }
}
