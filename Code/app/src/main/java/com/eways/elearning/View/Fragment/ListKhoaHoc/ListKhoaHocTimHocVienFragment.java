package com.eways.elearning.View.Fragment.ListKhoaHoc;


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
import com.eways.elearning.Handler.Adapter.KhoaHocRCAdapter;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.Presenter.ListKhoaHoc.ListKhoaHocTimHocVienPresenter;
import com.eways.elearning.Presenter.ListKhoaHoc.ListKhoaHocTimHocVienPresenterImp;
import com.eways.elearning.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListKhoaHocTimHocVienFragment extends Fragment implements ListKhoaHocTimHocVienImpView{

    private SwipeRefreshLayout srlKhoaHocTimHocVien;
    private RecyclerView rcvKhoaHocTimHocVien;
    private ListKhoaHocTimHocVienPresenterImp listKhoaHocTimHocVienPresenterImp;

    private ArrayList<CustomModelKhoaHoc> khoaHocArrayListhv;
    private KhoaHocRCAdapter khoaHocAdapterhv;

    private DatabaseReference mDatabase;
    private ImageHandler imageHandler;

    private int sizeOld = 0;
    private int sizeNew = 0;
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
        rcvKhoaHocTimHocVien = (RecyclerView)root.findViewById(R.id.rcvKhoaHocTimHocVien);

        imageHandler = new ImageHandler(getActivity());

        mDatabase = FirebaseDatabase.getInstance().getReference();
        khoaHocArrayListhv = new ArrayList<CustomModelKhoaHoc>();
        listKhoaHocTimHocVienPresenterImp.yeuCauDanhSachKhoaHoc();


        rcvKhoaHocTimHocVien.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });

        srlKhoaHocTimHocVien.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                srlKhoaHocTimHocVien.setRefreshing(true);
                khoaHocArrayListhv.clear();
                listKhoaHocTimHocVienPresenterImp.yeuCauDanhSachKhoaHoc();
                srlKhoaHocTimHocVien.setRefreshing(false);
            }
        });

        /**Get khóa học Non-MVP*/
//        mDatabase.child(SupportKeysList.CHILD_KHOAHOC).child(SupportKeysList.CHILD_KHOAHOC_TIMHOCVIEN).child(SupportKeysList.CHILD_KHOAHOC_CHUAHOANTAT).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                KhoaHocChuaHoanTat kh = new KhoaHocChuaHoanTat();
//                kh = dataSnapshot.getValue(KhoaHocChuaHoanTat.class);
//                CustomModelKhoaHoc ckh = new CustomModelKhoaHoc(kh.HoTen,kh.NguoiDang,kh.Avatar,kh.LichHoc.ThoiGian,kh.Rating,kh.HocPhi,kh.Mon,kh.Lop);
//                khoaHocArrayListhv.add(ckh);
//                khoaHocAdapterhv.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });



        return root;
    }

    @Override
    public void nhanDanhSach(ArrayList<CustomModelKhoaHoc> khoaHocList) {

        khoaHocArrayListhv = khoaHocList;
        khoaHocAdapterhv = new KhoaHocRCAdapter(
                khoaHocArrayListhv,
                imageHandler
        );
        rcvKhoaHocTimHocVien.setLayoutManager(new GridLayoutManager(getActivity(),1));
        rcvKhoaHocTimHocVien.setAdapter(khoaHocAdapterhv);
    }

}
