package com.eways.elearning.Views.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toolbar;

import com.eways.elearning.Adapter.UserInfoPager;
import com.eways.elearning.Interfaces.DataCallback.User.UserCallBack;
import com.eways.elearning.Model.Account.User;
import com.eways.elearning.Presenter.Authentication.UserPresenter;
import com.eways.elearning.R;
import com.eways.elearning.Utils.SupportKeys;

public class InfoUserViewerActivity extends FragmentActivity implements UserCallBack {
    ViewPager mViewPager;
    TabLayout mTab;
    View btnBack, ivAvarta;
    android.support.v7.widget.Toolbar mToolbar;

    private UserPresenter userPresenter;
    private User user;

    public static String paramUId = "ParamUId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user_viewer);

        declare_views();
        handle_views();

        userPresenter = new UserPresenter(this);

        String uId = getIntent().getStringExtra(paramUId);
        if (uId != null) {
            userPresenter.getUserInfo(uId, this);
        }

    }

    /** ----- CONFIG ----- */

    private void declare_views(){
        mViewPager = findViewById(R.id.viewpager);
        mTab = findViewById(R.id.tab_layout);
        mToolbar = findViewById(R.id.toolbar);
        btnBack = findViewById(R.id.btn_back);
        ivAvarta = findViewById(R.id.avarta);
    }

    private void handle_views(){
        SetUpViewPager();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InfoUserViewerActivity.this.finish();
            }
        });
        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorBlue));
    }

    private void SetUpViewPager(){
        FragmentManager manager = getSupportFragmentManager();
        UserInfoPager adapter = new UserInfoPager(manager);
        mViewPager.setAdapter(adapter);
        mTab.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTab));
        mTab.setTabsFromPagerAdapter(adapter);
    }

    private void loadData() {

    }

    /** ----- ACTION ----- */

    @Override
    public void userCallBack(int errorCode, User user) {

        if (errorCode == SupportKeys.FAILED_CODE) {
            return;
        }

        this.user = user;

        Log.d("User info view:", user != null ? "Success" : "Failed!");

        loadData();

    }

}
