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
import com.eways.elearning.DataModel.KhoaHoc.KhoaHocChuaHoanTat;
import com.eways.elearning.Handler.Adapter.CustomModelKhoaHocAdapter;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListKhoaHocTimHocVienFragment extends Fragment {
//implements ListKhoaHocTimHocVienImpView
    private SwipeRefreshLayout srlKhoaHocTimHocVien;
    private ListView lvKhoaHocTimHocVien;

    private ArrayList<CustomModelKhoaHoc> khoaHocArrayListhv;
    private CustomModelKhoaHocAdapter khoaHocAdapterhv;

    private DatabaseReference mDatabase;
//    ListKhoaHocTimHocVienPresenterImp listKhoaHocTimHocVienPresenterImp;

    public ListKhoaHocTimHocVienFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        listKhoaHocTimHocVienPresenterImp = new ListKhoaHocTimHocVienPresenter(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_list_khoa_hoc_tim_hoc_vien, container, false);

        srlKhoaHocTimHocVien = (SwipeRefreshLayout)root.findViewById(R.id.srlKhoaHocTimHocVien);
        lvKhoaHocTimHocVien = (ListView)root.findViewById(R.id.lvKhoaHocTimHocVien);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        khoaHocArrayListhv = new ArrayList<CustomModelKhoaHoc>();
//        listKhoaHocTimHocVienPresenterImp.yeuCauDanhSachKhoaHoc();

//        srlKhoaHocTimHocVien.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                srlKhoaHocTimHocVien.setRefreshing(true);
//            }
//        });


        khoaHocAdapterhv = new CustomModelKhoaHocAdapter(
                getActivity().getApplicationContext(),
                R.layout.custom_item_khoahoc,
                khoaHocArrayListhv
        );
        lvKhoaHocTimHocVien.setAdapter(khoaHocAdapterhv);
        mDatabase.child(SupportKeysList.CHILD_KHOAHOC).child(SupportKeysList.CHILD_KHOAHOC_TIMHOCVIEN).child(SupportKeysList.CHILD_KHOAHOC_CHUAHOANTAT).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                KhoaHocChuaHoanTat kh = new KhoaHocChuaHoanTat();
                kh = dataSnapshot.getValue(KhoaHocChuaHoanTat.class);
                CustomModelKhoaHoc ckh = new CustomModelKhoaHoc(kh.HoTen,kh.NguoiDang,kh.Avatar,kh.LichHoc.ThoiGian,kh.Rating,kh.HocPhi,kh.Mon,kh.Lop);
                khoaHocArrayListhv.add(ckh);
                khoaHocAdapterhv.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return root;
    }

//    @Override
//    public void nhanDanhSach(ArrayList<CustomModelKhoaHoc> khoaHocList) {
//        CustomModelKhoaHocAdapter customModelKhoaHocAdapter = new CustomModelKhoaHocAdapter(
//                getActivity(),
//                R.layout.custom_item_khoahoc,
//                khoaHocList
//        );
//        lvKhoaHocTimHocVien.setAdapter(customModelKhoaHocAdapter);
//    }
}
