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

import com.eways.elearning.Model.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.Adapter.KhoaHoc.KhoaHocRCAdapter;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.Presenter.ListKhoaHoc.DanhSachKhoaHocPresenter;
import com.eways.elearning.Presenter.ListKhoaHoc.DanhSachKhoaHocPresenterImp;
import com.eways.elearning.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DanhSachKhoaHocFragment extends Fragment implements DanhSachKhoaHocViewImp {

    private RecyclerView rcvDanhSachKhoaHoc;
    private SwipeRefreshLayout srlDanhSachKhoaHoc;

    private DanhSachKhoaHocPresenterImp danhSachKhoaHocPresenterImp;
    private KhoaHocRCAdapter adapterDanhSachKhoaHoc;
    private ImageHandler imageHandler;
    private FragmentHandler fragmentHandler;
    private ArrayList<CustomModelKhoaHoc> danhSachKhoaHoc = new ArrayList<>();

    private static final String param1 = "param1";
    private static final String param2 = "param2";
    private String linhVuc;
    private boolean loaiTimKiem;

    public DanhSachKhoaHocFragment() {
        // Required empty public constructor
    }

    public static DanhSachKhoaHocFragment newInstance(String linhVuc, boolean loaiTimKiem) {

        Bundle args = new Bundle();
        args.putString(param1, linhVuc);
        args.putBoolean(param2, loaiTimKiem);
        DanhSachKhoaHocFragment fragment = new DanhSachKhoaHocFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            linhVuc = getArguments().getString(param1);
            loaiTimKiem = getArguments().getBoolean(param2);
        }
        danhSachKhoaHocPresenterImp = new DanhSachKhoaHocPresenter(this);
//        fragmentHandler = new FragmentHandler(getActivity(), getActivity().getSupportFragmentManager());
//        LoadingDialog.showDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_danh_sach_khoa_hoc, container, false);

        rcvDanhSachKhoaHoc = (RecyclerView) root.findViewById(R.id.rcvDanhSachKhoaHoc);
        srlDanhSachKhoaHoc = (SwipeRefreshLayout) root.findViewById(R.id.srlDanhSachKhoaHoc);

        imageHandler = new ImageHandler(getActivity());
        danhSachKhoaHoc = new ArrayList<>();

        danhSachKhoaHocPresenterImp.guiYeuCauDanhSachKhoaHoc(linhVuc, loaiTimKiem);

        srlDanhSachKhoaHoc.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                srlDanhSachKhoaHoc.setRefreshing(true);
                danhSachKhoaHoc.clear();
                danhSachKhoaHocPresenterImp.guiYeuCauDanhSachKhoaHoc(linhVuc, loaiTimKiem);
                srlDanhSachKhoaHoc.setRefreshing(false);
            }
        });


        return root;
    }

    @Override
    public void nhanDanhSachKhoaHoc(ArrayList<CustomModelKhoaHoc> danhSachKhoaHoc) {
        this.danhSachKhoaHoc = danhSachKhoaHoc;
        adapterDanhSachKhoaHoc = new KhoaHocRCAdapter(getActivity(), this.danhSachKhoaHoc, imageHandler, fragmentHandler);
        rcvDanhSachKhoaHoc.setLayoutManager(new GridLayoutManager(getActivity(),1));
        rcvDanhSachKhoaHoc.setAdapter(adapterDanhSachKhoaHoc);
//        LoadingDialog.dismissDialog();
    }
}
