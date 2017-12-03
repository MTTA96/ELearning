package com.eways.elearning.View.Fragment.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.support.design.widget.TabLayout;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.Handler.Adapter.KhoaHoc.DanhSachKhoaHocHomeAdapter;
import com.eways.elearning.Handler.Adapter.ViewPagerAdapter;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Presenter.Home.NewHomeFragmentPresenter;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.Home.HomeTimGiaSu.HomeTimGiaSuFragment;
import com.eways.elearning.View.Fragment.Home.HomeTimHocVien.HomeTimHocVienFragment;
import com.eways.elearning.View.Fragment.ListKhoaHoc.ListKhoaHocFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewHomeFragment extends Fragment {

    private final String titleTab1 = "Tìm gia sư";
    private final String titleTab2 = "Tìm học viên";

    public NewHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_new_home, container, false);
//        root.findViewById(R.id.textView_XemDanhSachKhoaHocAnhVan_Home).setOnClickListener(this);
        setUpViewPager((ViewPager) root.findViewById(R.id.viewPager_PhanMuc_Home));

//        newHomeFragmentPresenter.guiYeuCau("Ngoại ngữ", "Toán", "Khác");

        return root;
    }

    private void setUpViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new HomeTimGiaSuFragment(), titleTab1);
        viewPagerAdapter.addFragment(new HomeTimHocVienFragment(), titleTab2);

        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().supportInvalidateOptionsMenu();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);
    }
}
