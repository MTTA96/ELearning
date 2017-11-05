package com.eways.elearning.View.Fragment.GiaSu;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Handler.SelectionPagerAdapter_BaiDang;
import com.eways.elearning.R;

/**
 * Created by Tien Phat on 05/11/2017.
 *Hiển thị danh sách bài đăng theo thông tin tìm kiếm của người dùng
 */
public class GiaSuFragment extends Fragment {

    private SelectionPagerAdapter_BaiDang mSelectionsPagerAdapter;

    private ViewPager mViewPager;

    public GiaSuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gia_su, container, false);

        Toolbar toolbar = (Toolbar) root.findViewById(R.id.baidang_toolbar);
        mSelectionsPagerAdapter = new SelectionPagerAdapter_BaiDang(getActivity().getSupportFragmentManager());

        mViewPager = (ViewPager) root.findViewById(R.id.baidang_viewpager);
        mViewPager.setAdapter(mSelectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) root.findViewById(R.id.baidang_tablayout);
        tabLayout.setupWithViewPager(mViewPager);

        return root;
    }

}
