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
public class KhoaHocChoDuyetFragment extends Fragment implements KhoaHocChoDuyetViewImp {
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rvKhoaHocChoDuyet;

    private ArrayList<CustomModelKhoaHoc> danhSachKhoaHoc = new ArrayList<>();
    private KhoaHocRCAdapter khoaHocAdapter;
    private DatabaseReference mDatabase;
    private ImageHandler imageHandler;
    private FragmentHandler fragmentHandler;
    private static final String paramDanhSachKhoaHocChoDuyet = "paramDanhSachKhoaHocChoDuyet";

    public KhoaHocChoDuyetFragment() {
        // Required empty public constructor
    }

    public static KhoaHocChoDuyetFragment newInstance(ArrayList<CustomModelKhoaHoc> customModelKhoaHocs){
        Bundle args = new Bundle();
        args.putSerializable(paramDanhSachKhoaHocChoDuyet, customModelKhoaHocs);
        KhoaHocChoDuyetFragment fragment = new KhoaHocChoDuyetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_khoa_hoc_cho_duyet, container, false);
        refreshLayout = root.findViewById(R.id.refreshLayout_KhoaHocChoDuyet);
        rvKhoaHocChoDuyet = root.findViewById(R.id.recyclerView_DanhSachKhoaHocChoDuyet);

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
        if (getArguments()!=null){
            danhSachKhoaHoc= (ArrayList<CustomModelKhoaHoc>) getArguments().getSerializable(paramDanhSachKhoaHocChoDuyet);
            showData();
        }
        return root;
    }

    public void showData(){
        khoaHocAdapter = new KhoaHocRCAdapter(getActivity(), danhSachKhoaHoc, imageHandler, fragmentHandler);
        rvKhoaHocChoDuyet.setLayoutManager(new GridLayoutManager(getActivity(),1));
        rvKhoaHocChoDuyet.setAdapter(khoaHocAdapter);
    }

//    @Override
//    public void DataKhoaHocDangChoDuyet(ArrayList<CustomModelKhoaHoc> danhSachKhoaHocChoDuyet) {
//        if (danhSachKhoaHocChoDuyet!=null){
//            danhSachKhoaHoc=danhSachKhoaHocChoDuyet;
//            showData();
//        }
//    }
}
