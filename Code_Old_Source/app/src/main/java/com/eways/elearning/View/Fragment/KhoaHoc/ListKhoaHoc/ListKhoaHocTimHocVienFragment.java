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
import com.eways.elearning.Presenter.ListKhoaHoc.ListKhoaHocTimHocVienPresenter;
import com.eways.elearning.Presenter.ListKhoaHoc.ListKhoaHocTimHocVienPresenterImp;
import com.eways.elearning.R;
import com.eways.elearning.View.Dialog.LoadingDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListKhoaHocTimHocVienFragment extends Fragment implements ListKhoaHocTimHocVienImpView{

    private SwipeRefreshLayout srlKhoaHocTimHocVien;
    private RecyclerView rvKhoaHocTimHocVien;

    private ListKhoaHocTimHocVienPresenterImp listKhoaHocTimHocVienPresenterImp;
    private ArrayList<CustomModelKhoaHoc> khoaHocArrayListhv;
    private KhoaHocRCAdapter khoaHocAdapterhv;
    private DatabaseReference mDatabase;
    private ImageHandler imageHandler;
    private FragmentHandler fragmentHandler;

    private static final String param1 = "param1";
    private static final String param2 = "param2";
    private String linhVuc;
    private boolean loaiTimKiem;

    public ListKhoaHocTimHocVienFragment() {
        // Required empty public constructor
    }

    public static ListKhoaHocTimHocVienFragment newInstance(String linhVuc, boolean loaiTimKiem) {

        Bundle args = new Bundle();
        args.putString(param1, linhVuc);
        args.putBoolean(param2, loaiTimKiem);
        ListKhoaHocTimHocVienFragment fragment = new ListKhoaHocTimHocVienFragment();
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
        listKhoaHocTimHocVienPresenterImp = new ListKhoaHocTimHocVienPresenter(this);
//        fragmentHandler = new FragmentHandler(getActivity(), getActivity().getSupportFragmentManager());
        LoadingDialog.showDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_list_khoa_hoc_tim_hoc_vien, container, false);

        srlKhoaHocTimHocVien = (SwipeRefreshLayout)root.findViewById(R.id.srlKhoaHocTimHocVien);
        rvKhoaHocTimHocVien = (RecyclerView)root.findViewById(R.id.lvKhoaHocTimHocVien);

        imageHandler = new ImageHandler(getActivity());

        mDatabase = FirebaseDatabase.getInstance().getReference();
        khoaHocArrayListhv = new ArrayList<CustomModelKhoaHoc>();
        listKhoaHocTimHocVienPresenterImp.yeuCauDanhSachKhoaHoc(linhVuc);


        srlKhoaHocTimHocVien.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                srlKhoaHocTimHocVien.setRefreshing(true);
                khoaHocArrayListhv.clear();
                listKhoaHocTimHocVienPresenterImp.yeuCauDanhSachKhoaHoc(linhVuc);
                srlKhoaHocTimHocVien.setRefreshing(false);
            }
        });

        return root;
    }

    @Override
    public void nhanDanhSach(ArrayList<CustomModelKhoaHoc> khoaHocList) {

        khoaHocArrayListhv = khoaHocList;
        khoaHocAdapterhv = new KhoaHocRCAdapter(getActivity(), khoaHocArrayListhv, imageHandler, fragmentHandler);
        rvKhoaHocTimHocVien.setLayoutManager(new GridLayoutManager(getActivity(),1));
        rvKhoaHocTimHocVien.setAdapter(khoaHocAdapterhv);
        LoadingDialog.dismissDialog();
    }
}
