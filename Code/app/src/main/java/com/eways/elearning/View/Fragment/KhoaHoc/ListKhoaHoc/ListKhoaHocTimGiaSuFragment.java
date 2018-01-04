package com.eways.elearning.View.Fragment.KhoaHoc.ListKhoaHoc;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.Handler.Adapter.KhoaHoc.KhoaHocRCAdapter;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.Presenter.ListKhoaHoc.ListKhoaHocTimGiaSuPresenter;
import com.eways.elearning.Presenter.ListKhoaHoc.ListKhoaHocTimGiaSuPresenterImp;
import com.eways.elearning.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListKhoaHocTimGiaSuFragment extends Fragment implements ListKhoaHocTimGiaSuImpView{
//implements ListKhoaHocTimGiaSuImpView
    private SwipeRefreshLayout srlKhoaHocTimGiaSu;
    private RecyclerView rcvKhoaHocTimGiaSu;

    private ListKhoaHocTimGiaSuPresenterImp listKhoaHocTimGiaSuPresenterImp;
    private ArrayList<CustomModelKhoaHoc> khoaHocArrayList;
    private KhoaHocRCAdapter khoaHocAdapter;
    private DatabaseReference mDatabase;
    private ImageHandler imageHandler;
    private FragmentHandler fragmentHandler;

    public ListKhoaHocTimGiaSuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listKhoaHocTimGiaSuPresenterImp = new ListKhoaHocTimGiaSuPresenter(this);
        fragmentHandler = new FragmentHandler(getActivity(), getActivity().getSupportFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root =  inflater.inflate(R.layout.fragment_list_khoa_hoc_tim_gia_su, container, false);

        srlKhoaHocTimGiaSu = (SwipeRefreshLayout)root.findViewById(R.id.srlKhoaHocTimGiaSu);
        rcvKhoaHocTimGiaSu = (RecyclerView)root.findViewById(R.id.rcvKhoaHocTimGiaSu);

        imageHandler = new ImageHandler(getActivity());

        mDatabase = FirebaseDatabase.getInstance().getReference();
        khoaHocArrayList = new ArrayList<CustomModelKhoaHoc>();
        listKhoaHocTimGiaSuPresenterImp.yeuCauDanhSachKhoaHoc();

        srlKhoaHocTimGiaSu.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                srlKhoaHocTimGiaSu.setRefreshing(true);
                khoaHocArrayList.clear();
                listKhoaHocTimGiaSuPresenterImp.yeuCauDanhSachKhoaHoc();
                srlKhoaHocTimGiaSu.setRefreshing(false);
            }
        });

        return root;
    }

    @Override
    public void nhanDanhSach(ArrayList<CustomModelKhoaHoc> khoaHocList) {

        khoaHocArrayList = khoaHocList;
        khoaHocAdapter = new KhoaHocRCAdapter(getActivity(), khoaHocArrayList, imageHandler, fragmentHandler);
        rcvKhoaHocTimGiaSu.setLayoutManager(new GridLayoutManager(getActivity(),1));
        rcvKhoaHocTimGiaSu.setAdapter(khoaHocAdapter);
    }

}
