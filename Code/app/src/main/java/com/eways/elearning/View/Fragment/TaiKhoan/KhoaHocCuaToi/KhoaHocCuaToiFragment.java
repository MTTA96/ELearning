package com.eways.elearning.View.Fragment.TaiKhoan.KhoaHocCuaToi;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Handler.Adapter.ViewPagerAdapter;
import com.eways.elearning.R;
import com.eways.elearning.View.Activity.MainActivity;
import com.eways.elearning.View.Fragment.KhoaHoc.ListKhoaHoc.ListKhoaHocTimGiaSuFragment;
import com.eways.elearning.View.Fragment.KhoaHoc.ListKhoaHoc.ListKhoaHocTimHocVienFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class KhoaHocCuaToiFragment extends Fragment {
    private ViewPager viewPagerKhoaHoc;
    private TabLayout tabLayoutKhoaHoc;

    private final String titleTab1 = "Khóa học đang tham gia";
    private final String titleTab2 = "Khóa học chờ duyệt";

    public KhoaHocCuaToiFragment() {
        // Required empty public constructor
    }

    public static KhoaHocCuaToiFragment newInstance() {
        
        Bundle args = new Bundle();
        
        KhoaHocCuaToiFragment fragment = new KhoaHocCuaToiFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_khoa_hoc_cua_toi, container, false);

        viewPagerKhoaHoc = (ViewPager) root.findViewById(R.id.viewpager_KhoaHocCuaToi);
        tabLayoutKhoaHoc = (TabLayout)root.findViewById(R.id.tabLayout_KhoaHocCuaToi);

        ((MainActivity)getActivity()).tvScreenTitle.setText(getResources().getString(R.string.title_danh_sach_khoa_hoc));
        setUpViewPager(viewPagerKhoaHoc);
        getActivity().supportInvalidateOptionsMenu();
        return root;
    }

    private void setUpViewPager(ViewPager pager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new KhoaHocDangThamGiaFragment(), titleTab1);
        adapter.addFragment(new KhoaHocDangThamGiaFragment(), titleTab2);
        pager.setAdapter(adapter);
    }
}
