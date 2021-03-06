package com.eways.elearning.View.Fragment.TaiKhoan.KhoaHocCuaToi;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Model.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.Adapter.KhoaHoc.KhoaHocRCAdapter;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.R;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class KhoaHocDangThamGiaFragment extends Fragment implements KhoaHocDangThamGiaViewImp{
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rvKhoaHocDangThamGia;

    private ArrayList<CustomModelKhoaHoc> danhSachKhoaHoc = new ArrayList<>();
    private KhoaHocRCAdapter khoaHocAdapter;
    private DatabaseReference mDatabase;
    private ImageHandler imageHandler;
    private FragmentHandler fragmentHandler;

    private static final String paramDanhSachKhoaHocDangThamGia = "paramDanhSachKhoaHocDangThamGia";

    public KhoaHocDangThamGiaFragment() {
        // Required empty public constructor
    }

    public  static KhoaHocDangThamGiaFragment newInstance(ArrayList<CustomModelKhoaHoc> danhSachKhoaHocDaTao, ArrayList<CustomModelKhoaHoc> danhSachKhoaHocThamGiaDaDuyet){
        Bundle args = new Bundle();
        danhSachKhoaHocDaTao.addAll(danhSachKhoaHocDaTao);
        danhSachKhoaHocDaTao.addAll(danhSachKhoaHocThamGiaDaDuyet);
        args.putSerializable(paramDanhSachKhoaHocDangThamGia, danhSachKhoaHocDaTao);
        KhoaHocDangThamGiaFragment fragment = new KhoaHocDangThamGiaFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        fragmentHandler = new FragmentHandler(getActivity(), getActivity().getSupportFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_khoa_hoc_dang_tham_gia, container, false);
        refreshLayout = root.findViewById(R.id.refreshLayout_KhoaHocDangThamGia);
        rvKhoaHocDangThamGia = root.findViewById(R.id.recyclerView_DanhSachKhoaHocDangThamGia);

        imageHandler = new ImageHandler(getActivity());
        mDatabase = FirebaseDatabase.getInstance().getReference();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refreshLayout.setRefreshing(true);
                danhSachKhoaHoc.clear();
                refreshLayout.setRefreshing(false);
            }
        });
        if(getArguments()!=null){
            danhSachKhoaHoc= (ArrayList<CustomModelKhoaHoc>) getArguments().getSerializable(paramDanhSachKhoaHocDangThamGia);
            showData();
        }
        return root;
    }

    public void showData(){
        khoaHocAdapter = new KhoaHocRCAdapter(getActivity(), danhSachKhoaHoc, imageHandler, fragmentHandler);
        rvKhoaHocDangThamGia.setLayoutManager(new GridLayoutManager(getActivity(),1));
        rvKhoaHocDangThamGia.setAdapter(khoaHocAdapter);
    }

//    @Override
//    public void DataKhoaHocDangThamGia(ArrayList<CustomModelKhoaHoc> danhSachKhoaHocDaTao, ArrayList<CustomModelKhoaHoc> danhSachKhoaHocDaDuyet) {
//        if (danhSachKhoaHocDaDuyet!=null){
//            danhSachKhoaHoc=danhSachKhoaHocDaTao;
//            showData();
//        }
//    }
}
