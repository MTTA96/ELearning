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
import com.eways.elearning.Handler.Adapter.KhoaHoc.KhoaHocRCAdapter;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.Presenter.ListKhoaHoc.ListKhoaHocTimGiaSuPresenter;
import com.eways.elearning.Presenter.ListKhoaHoc.ListKhoaHocTimGiaSuPresenterImp;
import com.eways.elearning.R;
import com.eways.elearning.View.Dialog.LoadingDialog;
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
    private ArrayList<CustomModelKhoaHoc> khoaHocArrayList = new ArrayList<>();
    private KhoaHocRCAdapter khoaHocAdapter;
    private DatabaseReference mDatabase;
    private ImageHandler imageHandler;
    private FragmentHandler fragmentHandler;

    private static final String param1 = "param1";
    private static final String param2 = "param2";
    private String linhVuc;
    private boolean loaiTimKiem;

    public ListKhoaHocTimGiaSuFragment() {
        // Required empty public constructor
    }

    public static ListKhoaHocTimGiaSuFragment newInstance(String linhVuc, boolean loaiTimKiem) {

        Bundle args = new Bundle();
        args.putString(param1, linhVuc);
        args.putBoolean(param2, loaiTimKiem);
        ListKhoaHocTimGiaSuFragment fragment = new ListKhoaHocTimGiaSuFragment();
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
        listKhoaHocTimGiaSuPresenterImp = new ListKhoaHocTimGiaSuPresenter(this);
        fragmentHandler = new FragmentHandler(getActivity(), getActivity().getSupportFragmentManager());
        LoadingDialog.showDialog();
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
        listKhoaHocTimGiaSuPresenterImp.yeuCauDanhSachKhoaHoc(linhVuc);

        srlKhoaHocTimGiaSu.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                srlKhoaHocTimGiaSu.setRefreshing(true);
                khoaHocArrayList.clear();
                listKhoaHocTimGiaSuPresenterImp.yeuCauDanhSachKhoaHoc(linhVuc);
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
        LoadingDialog.dismissDialog();
    }

}
