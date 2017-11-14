package com.eways.elearning.Model.ListKhoaHoc;

import com.eways.elearning.DataModel.KhoaHoc.KhoaHocChuaHoanTat;
import com.eways.elearning.Presenter.ListKhoaHoc.ListKhoaHocTimGiaSuPresenterImp;
import com.eways.elearning.Util.SupportKeysList;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by yowin on 09/11/2017.
 */

public class ListKhoaHocTimGiaSuModel implements ListKhoaHocTimGiaSuImpModel {

    ListKhoaHocTimGiaSuPresenterImp listKhoaHocTimGiaSuPresenterImp;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    ArrayList<KhoaHocChuaHoanTat> listKhoaHoc =new ArrayList<>();

    public ListKhoaHocTimGiaSuModel(ListKhoaHocTimGiaSuPresenterImp listKhoaHocTimGiaSuPresenterImp) {
        this.listKhoaHocTimGiaSuPresenterImp = listKhoaHocTimGiaSuPresenterImp;
    }

    @Override
    public void getDanhSachKhoaHocTimGiaSu() {
        mData.child(SupportKeysList.CHILD_KHOAHOC).child(SupportKeysList.CHILD_KHOAHOC_TIMGIASU).child(SupportKeysList.CHILD_KHOAHOC_CHUAHOANTAT).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                KhoaHocChuaHoanTat kh = dataSnapshot.getValue(KhoaHocChuaHoanTat.class);
                listKhoaHoc.add(kh);
                listKhoaHocTimGiaSuPresenterImp.nhanDanhSachKhoaHoc(listKhoaHoc);
//                saveData.saveData(kh);
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
    }
}
