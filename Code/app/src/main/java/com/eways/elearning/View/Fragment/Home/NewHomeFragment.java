package com.eways.elearning.View.Fragment.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Handler.Adapter.ViewPagerAdapter;
import com.eways.elearning.R;
import com.eways.elearning.View.Fragment.Home.HomeTimGiaSu.HomeTimGiaSuFragment;
import com.eways.elearning.View.Fragment.Home.HomeTimHocVien.HomeTimHocVienFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewHomeFragment extends Fragment {

    private final String titleTab1 = "Tìm gia sư";
    private final String titleTab2 = "Tìm học viên";
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
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