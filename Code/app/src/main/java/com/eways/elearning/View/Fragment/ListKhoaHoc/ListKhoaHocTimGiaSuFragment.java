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
import com.eways.elearning.Presenter.ListKhoaHoc.ListKhoaHocTimGiaSuPresenter;
import com.eways.elearning.Presenter.ListKhoaHoc.ListKhoaHocTimGiaSuPresenterImp;
import com.eways.elearning.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListKhoaHocTimGiaSuFragment extends Fragment implements ListKhoaHocTimGiaSuImpView{

    SwipeRefreshLayout srlKhoaHocTimGiaSu;
    ListView lvKhoaHocTimGiaSu;

    ListKhoaHocTimGiaSuPresenterImp listKhoaHocTimGiaSuPresenterImp;

    public ListKhoaHocTimGiaSuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listKhoaHocTimGiaSuPresenterImp = new ListKhoaHocTimGiaSuPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_list_khoa_hoc_tim_gia_su, container, false);

        srlKhoaHocTimGiaSu = (SwipeRefreshLayout)root.findViewById(R.id.srlKhoaHocTimGiaSu);
        lvKhoaHocTimGiaSu = (ListView)root.findViewById(R.id.lvKhoaHocTimGiaSu);

        listKhoaHocTimGiaSuPresenterImp.yeuCauDanhSachKhoaHoc();
//        srlKhoaHocTimGiaSu.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            /**
//             * Called when a swipe gesture triggers a refresh.
//             */
//            @Override
//            public void onRefresh() {
//
//            }
//        });

        return root;
    }

    @Override
    public void nhanDanhSach(ArrayList<CustomModelKhoaHoc> khoaHocList) {
        CustomModelKhoaHocAdapter customModelKhoaHocAdapter = new CustomModelKhoaHocAdapter(
                getActivity(),
                R.layout.custom_item_khoahoc,
                khoaHocList
        );
        lvKhoaHocTimGiaSu.setAdapter(customModelKhoaHocAdapter);
    }
}
