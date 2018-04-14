package com.eways.elearning.View.Fragment.TaiKhoan.KhoaHocCuaToi;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Model.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.Handler.Adapter.ViewPagerAdapter;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Presenter.TaiKhoan.KhoaHocCuaToi.KhoaHocCuaToiPresenter;
import com.eways.elearning.Presenter.TaiKhoan.KhoaHocCuaToi.KhoaHocCuaToiPresenterImp;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Activity.MainActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class KhoaHocCuaToiFragment extends Fragment implements KhoaHocCuaToiViewImp {
    private ViewPager viewPagerKhoaHoc;
    private TabLayout tabLayoutKhoaHoc;
    private KhoaHocCuaToiPresenterImp khoaHocCuaToiPresenterImp;
    SharedPreferencesHandler sharedPreferencesHandler;

    KhoaHocChoDuyetFragment khoaHocChoDuyetFragment=new KhoaHocChoDuyetFragment();
    KhoaHocDangThamGiaFragment khoaHocDangThamGiaFragment=new KhoaHocDangThamGiaFragment();

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesHandler=new SharedPreferencesHandler(getActivity(), SupportKeysList.SHARED_PREF_FILE_NAME);

        khoaHocCuaToiPresenterImp=new KhoaHocCuaToiPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_khoa_hoc_cua_toi, container, false);

        viewPagerKhoaHoc = (ViewPager) root.findViewById(R.id.viewpager_KhoaHocCuaToi);
        tabLayoutKhoaHoc = (TabLayout)root.findViewById(R.id.tabLayout_KhoaHocCuaToi);
//        khoaHocChoDuyetFragment=new KhoaHocChoDuyetFragment();
//        khoaHocDangThamGiaFragment=new KhoaHocDangThamGiaFragment();
        ((MainActivity)getActivity()).tvScreenTitle.setText(getResources().getString(R.string.title_danh_sach_khoa_hoc));
        getActivity().supportInvalidateOptionsMenu();
        khoaHocCuaToiPresenterImp.YeuCauDataKhoaHocDaDangKy(sharedPreferencesHandler.getUID(),getActivity(),khoaHocChoDuyetFragment,khoaHocDangThamGiaFragment);
        return root;
    }

    private void setUpViewPager(ArrayList<CustomModelKhoaHoc> danhSachKhoaHocThamGiaDangCho, ArrayList<CustomModelKhoaHoc> danhSachKhoaHocDaTao, ArrayList<CustomModelKhoaHoc> danhSachKhoaHocThamGiaDaDuyet) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(KhoaHocDangThamGiaFragment.newInstance(danhSachKhoaHocDaTao, danhSachKhoaHocThamGiaDaDuyet), titleTab1);
        adapter.addFragment(KhoaHocChoDuyetFragment.newInstance(danhSachKhoaHocThamGiaDangCho), titleTab2);
        viewPagerKhoaHoc.setAdapter(adapter);
    }

    @Override
    public void sendData(ArrayList<CustomModelKhoaHoc> danhSachKhoaHocThamGiaDangCho, ArrayList<CustomModelKhoaHoc> danhSachKhoaHocDaTao, ArrayList<CustomModelKhoaHoc> danhSachKhoaHocThamGiaDaDuyet) {
        setUpViewPager(danhSachKhoaHocThamGiaDangCho, danhSachKhoaHocDaTao, danhSachKhoaHocThamGiaDaDuyet);
    }
}
