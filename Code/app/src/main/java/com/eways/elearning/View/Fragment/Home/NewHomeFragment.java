package com.eways.elearning.View.Fragment.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.Handler.Adapter.KhoaHoc.DanhSachKhoaHocHomeAdapter;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.ListKhoaHoc.ListKhoaHocFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewHomeFragment extends Fragment implements View.OnClickListener,NewHomeFragmentImp {
    RecyclerView rvDanhSachKhoaHocAnhVan, rvDanhSachKhoaHocToan, rvDanhSachKhoaHocKhac;

    private FragmentHandler fragmentHandler;
    private SharedPreferencesHandler mySharedPref;
    private ImageHandler imageHandler;
    private ArrayList danhSachKhoaHocAnhVan, danhSachKhoaHocToan, danhSachKhoaHocKhac;

    public NewHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentHandler = new FragmentHandler(getActivity(), getActivity().getSupportFragmentManager());
        mySharedPref = new SharedPreferencesHandler(getActivity(), SupportKeysList.SHARED_PREF_FILE_NAME);
        imageHandler = new ImageHandler(getActivity());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_new_home, container, false);
        rvDanhSachKhoaHocAnhVan = (RecyclerView) root.findViewById(R.id.recyclerView_DanhSachKhoaHocAnhVan_Home);
        rvDanhSachKhoaHocToan = (RecyclerView) root.findViewById(R.id.recyclerView_DanhSachKhoaHocToan_Home);
        rvDanhSachKhoaHocKhac = (RecyclerView) root.findViewById(R.id.recyclerView_DanhSachKhoaHocKhac_Home);

        root.findViewById(R.id.textView_XemDanhSachKhoaHocAnhVan_Home).setOnClickListener(this);
        setUpDanhSach();
        return root;
    }

    private void setUpDanhSach() {
        rvDanhSachKhoaHocAnhVan.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvDanhSachKhoaHocToan.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvDanhSachKhoaHocKhac.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        rvDanhSachKhoaHocAnhVan.setAdapter(new DanhSachKhoaHocHomeAdapter(getActivity()));
        rvDanhSachKhoaHocToan.setAdapter(new DanhSachKhoaHocHomeAdapter(getActivity()));
        rvDanhSachKhoaHocKhac.setAdapter(new DanhSachKhoaHocHomeAdapter(getActivity()));
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().supportInvalidateOptionsMenu();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textView_XemDanhSachKhoaHocKhac_Home:
            case R.id.textView_XemDanhSachKhoaHocToan_Home:
            case R.id.textView_XemDanhSachKhoaHocAnhVan_Home:
                fragmentHandler.ChuyenFragment(new ListKhoaHocFragment(), true, SupportKeysList.TAG_DANH_SACH_KHOA_HOC);
                break;
        }
    }

    @Override
    public void nhanListKhoaHoc(ArrayList<CustomModelKhoaHoc> khoaHocs1, ArrayList<CustomModelKhoaHoc> khoaHocs2, ArrayList<CustomModelKhoaHoc> khoaHocs3) {
        
    }
}
