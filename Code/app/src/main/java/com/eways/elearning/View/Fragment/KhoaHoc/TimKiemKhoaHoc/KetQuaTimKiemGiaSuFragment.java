package com.eways.elearning.View.Fragment.KhoaHoc.TimKiemKhoaHoc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Data.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.Handler.Adapter.ViewPagerAdapter;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.R;


public class KetQuaTimKiemGiaSuFragment extends Fragment {
    private ViewPager viewPagerKetQuaTimKiem;
    private TabLayout tabLayoutKetQuaTimKiem;

    private final String titleTab1 = "Khóa học";
    private final String titleTab2 = "Gia sư";

    private static final String paramMon = "paramMon";
    private static final String paramRequestKhoaHoc = "RequestKhoaHoc";
    private static final String paramRequestGiaSu = "RequestGiaSu";
    private static final String paramRequestBangCap = "RequestBangCap";

    private KhoaHoc requestKhoaHoc;
    private boolean requestGiaSu;
    private String requestBangCap;
    private String requestMon;

    private FragmentHandler fragmentHandler;

//    private KetQuaKhoaHocViewImp ketQuaKhoaHocViewImp;

//    private KetQuaTimKiemKhoaHocFragmentPresenterImp ketQuaTimKiemKhoaHocFragmentPresenterImp;
    public KetQuaTimKiemGiaSuFragment() {
        // Required empty public constructor
    }

    public static KetQuaTimKiemGiaSuFragment newInstance(boolean timGiaSu, KhoaHoc requestKhoaHoc, String bangCap)
    {
        Bundle args = new Bundle();
        args.putSerializable(paramRequestKhoaHoc, requestKhoaHoc);
        args.putBoolean(paramRequestGiaSu, timGiaSu);
        args.putString(paramRequestBangCap, bangCap);
        KetQuaTimKiemGiaSuFragment fragment = new KetQuaTimKiemGiaSuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static KetQuaTimKiemGiaSuFragment newInstance(String tenMon) {

        Bundle args = new Bundle();
        args.putString(paramMon, tenMon);
        KetQuaTimKiemGiaSuFragment fragment = new KetQuaTimKiemGiaSuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            requestKhoaHoc = (KhoaHoc) getArguments().getSerializable(paramRequestKhoaHoc);
            requestGiaSu = getArguments().getBoolean(paramRequestGiaSu);
            requestBangCap = getArguments().getString(paramRequestBangCap, null);
            fragmentHandler = new FragmentHandler(getActivity(), getChildFragmentManager());
//            ketQuaTimKiemKhoaHocFragmentPresenterImp = new KetQuaTimKiemKhoaHocFragmentPresenter(this);

            //Request môn
            requestMon = getArguments().getString(paramMon, "");

//            ketQuaTimKiemKhoaHocFragmentPresenterImp = new KetQuaTimKiemKhoaHocFragmentPresenter(this);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_ket_qua_tim_kiem_gia_su, container, false);

        viewPagerKetQuaTimKiem = (ViewPager) root.findViewById(R.id.viewpagerKetQuaTimKiem);
        tabLayoutKetQuaTimKiem = (TabLayout)root.findViewById(R.id.tablayoutKetQuaTimKiem);

//        ketQuaTimKiemKhoaHocFragmentPresenterImp.guiYeuCauTimKhoaHocGiaSu(requestKhoaHoc,getActivity());
//        ketQuaTimKiemKhoaHocFragmentPresenterImp.guiYeuCauTimGiaSu(requestKhoaHoc,getActivity());
//        ketQuaKhoaHocViewImp.nhanThongTinTimKiem(requestKhoaHoc);
        setUpViewPager(viewPagerKetQuaTimKiem);
        return root;
    }

    private void setUpViewPager(ViewPager pager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(KetQuaKhoaHocFragment.newInstance(requestKhoaHoc), titleTab1);
        adapter.addFragment(KetQuaNguoiFragment.newInstance(requestKhoaHoc), titleTab2);
        pager.setAdapter(adapter);
    }

}
