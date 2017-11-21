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
import com.eways.elearning.DataModel.KhoaHoc.KhoaHocChuaHoanTat;
import com.eways.elearning.Handler.Adapter.KhoaHocRCAdapter;
import com.eways.elearning.Handler.ImageHandler;
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
public class ListKhoaHocTimGiaSuFragment extends Fragment {
//implements ListKhoaHocTimGiaSuImpView
    private SwipeRefreshLayout srlKhoaHocTimGiaSu;
//    private ListView lvKhoaHocTimGiaSu;
    private RecyclerView rvKhoaHocTimGiaSu;

    private ArrayList<CustomModelKhoaHoc> khoaHocArrayList;
//    private CustomModelKhoaHocAdapter khoaHocAdapter;
    private KhoaHocRCAdapter khoaHocAdapter;

    private DatabaseReference mDatabase;
    private ImageHandler imageHandler;
//    ListKhoaHocTimGiaSuPresenterImp listKhoaHocTimGiaSuPresenterImp;

    public ListKhoaHocTimGiaSuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        listKhoaHocTimGiaSuPresenterImp = new ListKhoaHocTimGiaSuPresenter(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_list_khoa_hoc_tim_gia_su, container, false);

        srlKhoaHocTimGiaSu = (SwipeRefreshLayout)root.findViewById(R.id.srlKhoaHocTimGiaSu);
//        lvKhoaHocTimGiaSu = (ListView)root.findViewById(R.id.lvKhoaHocTimGiaSu);
        rvKhoaHocTimGiaSu = (RecyclerView)root.findViewById(R.id.lvKhoaHocTimGiaSu);

        imageHandler = new ImageHandler(getActivity());

        mDatabase = FirebaseDatabase.getInstance().getReference();
        khoaHocArrayList = new ArrayList<CustomModelKhoaHoc>();
//        listKhoaHocTimGiaSuPresenterImp.yeuCauDanhSachKhoaHoc();
//        srlKhoaHocTimGiaSu.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            /**
//             * Called when a swipe gesture triggers a refresh.
//             */
//            @Override
//            public void onRefresh() {
//
//            }
//        });

//        khoaHocAdapter = new CustomModelKhoaHocAdapter(
//                getActivity().getApplicationContext(),
//                R.layout.custom_item_khoahoc,
//                khoaHocArrayList
//        );
//        lvKhoaHocTimGiaSu.setAdapter(khoaHocAdapter);
        khoaHocAdapter = new KhoaHocRCAdapter(
                khoaHocArrayList,
                imageHandler
        );
        rvKhoaHocTimGiaSu.setLayoutManager(new GridLayoutManager(getActivity(),1));
        rvKhoaHocTimGiaSu.setAdapter(khoaHocAdapter);

        srlKhoaHocTimGiaSu.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                srlKhoaHocTimGiaSu.setRefreshing(true);
                khoaHocAdapter = new KhoaHocRCAdapter(
                        khoaHocArrayList,
                        imageHandler
                );
                rvKhoaHocTimGiaSu.setLayoutManager(new GridLayoutManager(getActivity(),1));
                rvKhoaHocTimGiaSu.setAdapter(khoaHocAdapter);
                srlKhoaHocTimGiaSu.setRefreshing(false);
            }
        });
        mDatabase.child(SupportKeysList.CHILD_KHOAHOC).child(SupportKeysList.CHILD_KHOAHOC_TIMGIASU).child(SupportKeysList.CHILD_KHOAHOC_CHUAHOANTAT).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                KhoaHocChuaHoanTat kh = new KhoaHocChuaHoanTat();
                kh = dataSnapshot.getValue(KhoaHocChuaHoanTat.class);
                CustomModelKhoaHoc ckh = new CustomModelKhoaHoc(kh.HoTen,kh.NguoiDang,kh.Avatar,kh.LichHoc.ThoiGian,kh.Rating,kh.HocPhi,kh.Mon,kh.Lop);
                khoaHocArrayList.add(ckh);
                khoaHocAdapter.notifyDataSetChanged();
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
//        lvKhoaHocTimGiaSu.setAdapter(customModelKhoaHocAdapter);
//    }
}
