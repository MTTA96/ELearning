package com.eways.elearning.View.Fragment.KhoaHoc.ListKhoaHoc;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eways.elearning.Adapter.ViewPagerAdapter;
import com.eways.elearning.R;
import com.eways.elearning.View.Activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 *
 * Note:
 * 1. Đổi màu tab khi user tương tác
 * 2. Khai báo biến title trên cùng để dễ  thay đổi
 * 3. Bỏ tablayout trong viewpager -> không cần set tablayout trong java
 */
public class ListKhoaHocFragment extends Fragment {
    private ViewPager viewPagerKhoaHoc;
    private TabLayout tabLayoutKhoaHoc;

    private final String titleTab1 = "Tìm gia sư";
    private final String titleTab2 = "Tìm học viên";
    private static final String param1 = "param1";
    private static final String param2 = "param2";

    private String linhVuc;
    private boolean loaiTimKiem;

    public ListKhoaHocFragment() {
        // Required empty public constructor
    }

    public static ListKhoaHocFragment newInstance(String linhVuc, boolean loaiTimKiem) {

        Bundle args = new Bundle();
        args.putString(param1, linhVuc);
        args.putBoolean(param2, loaiTimKiem);
        ListKhoaHocFragment fragment = new ListKhoaHocFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            linhVuc = getArguments().getString(param1);
            loaiTimKiem = getArguments().getBoolean(param2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_list_khoa_hoc, container, false);

        viewPagerKhoaHoc = (ViewPager) root.findViewById(R.id.viewpagerKhoaHoc);
        tabLayoutKhoaHoc = (TabLayout)root.findViewById(R.id.tablayoutKhoaHoc);

        Toast.makeText(getContext(), linhVuc+loaiTimKiem, Toast.LENGTH_SHORT).show();

        ((MainActivity)getActivity()).tvScreenTitle.setText(getResources().getString(R.string.title_danh_sach_khoa_hoc));
        setUpViewPager(viewPagerKhoaHoc);
        getActivity().supportInvalidateOptionsMenu();
        return root;
    }

    private void setUpViewPager(ViewPager pager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ListKhoaHocTimGiaSuFragment(), titleTab1);
        adapter.addFragment(new ListKhoaHocTimHocVienFragment(), titleTab2);
        pager.setAdapter(adapter);
    }
}
