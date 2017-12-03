package com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Handler.Adapter.ViewPagerAdapter;
import com.eways.elearning.R;
import com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan.CapNhatThongTinTaiKhoanFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan.TaiLieuChuyenMon.TaiLieuChuyenMonFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThongTinCaNhanFragment extends Fragment {
    private final String titleTab1 = "Thông tin cá nhân";
    private final String titleTab2 = "Tài liệu chuyên môn";

    public ThongTinCaNhanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_thong_tin_tai_khoan, container, false);
        ViewPager viewPager = (ViewPager) root.findViewById(R.id.viewPager_ThongTinTaiKhoan);

        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        getActivity().supportInvalidateOptionsMenu();
        seUpViewpager(viewPager);
        return root;
    }

    private void seUpViewpager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new CapNhatThongTinTaiKhoanFragment(), titleTab1);
        viewPagerAdapter.addFragment(new TaiLieuChuyenMonFragment(), titleTab2);
        viewPager.setAdapter(viewPagerAdapter);
    }

}
