package com.eways.elearning.View.Fragment.ListKhoaHoc;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.Handler.Adapter.CustomModelKhoaHocAdapter;
import com.eways.elearning.Handler.Adapter.KhoaHocChuaHoanTatAdapter;
import com.eways.elearning.Presenter.ListKhoaHoc.ListKhoaHocTimHocVienPresenter;
import com.eways.elearning.Presenter.ListKhoaHoc.ListKhoaHocTimHocVienPresenterImp;
import com.eways.elearning.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListKhoaHocTimHocVienFragment extends Fragment implements ListKhoaHocTimHocVienImpView{

    SwipeRefreshLayout srlKhoaHocTimHocVien;
    ListView lvKhoaHocTimHocVien;

    KhoaHocChuaHoanTatAdapter khoaHocChuaHoanTatAdapter;
    ListKhoaHocTimHocVienPresenterImp listKhoaHocTimHocVienPresenterImp;

    public ListKhoaHocTimHocVienFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listKhoaHocTimHocVienPresenterImp = new ListKhoaHocTimHocVienPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_list_khoa_hoc_tim_hoc_vien, container, false);

        srlKhoaHocTimHocVien = (SwipeRefreshLayout)root.findViewById(R.id.srlKhoaHocTimHocVien);
        lvKhoaHocTimHocVien = (ListView)root.findViewById(R.id.lvKhoaHocTimHocVien);

        listKhoaHocTimHocVienPresenterImp.yeuCauDanhSachKhoaHoc();

        srlKhoaHocTimHocVien.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                
            }
        });

        return root;
    }

    @Override
    public void nhanDanhSach(ArrayList<CustomModelKhoaHoc> khoaHocList) {
        CustomModelKhoaHocAdapter customModelKhoaHocAdapter = new CustomModelKhoaHocAdapter(
                getActivity(),
                R.layout.custom_item_khoahoc,
                khoaHocList
        );
        lvKhoaHocTimHocVien.setAdapter(customModelKhoaHocAdapter);
    }
}
