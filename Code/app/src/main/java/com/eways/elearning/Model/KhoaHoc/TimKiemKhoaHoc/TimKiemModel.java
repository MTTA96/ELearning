package com.eways.elearning.Model.KhoaHoc.TimKiemKhoaHoc;

import android.app.Activity;

import com.eways.elearning.Model.DataModel.Other.KhuVuc;
import com.eways.elearning.Model.DataModel.LinhVuc.LinhVuc;
import com.eways.elearning.Presenter.TimKiemKhoaHoc.TimKiemPresenterImp;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by yowin on 08/12/2017.
 */

public class TimKiemModel implements TimKiemModelImp {
    TimKiemPresenterImp timKiemPresenterImp;
    FirebaseDatabase mData ;
    FirebaseAuth mAuth;

    public TimKiemModel(TimKiemPresenterImp timKiemPresenterImp) {
        this.timKiemPresenterImp = timKiemPresenterImp;
    }

    @Override
    public void loaddataLinhvuc(Activity activity) {
        mData=FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));
        mData=FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));
        final ArrayList<LinhVuc> danhSachLinhVuc=new ArrayList<>();
        mData.getReference().child("DataApp").child("DanhMucLinhVuc").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                danhSachLinhVuc.add(dataSnapshot.getValue(LinhVuc.class));
                timKiemPresenterImp.nhanDanhSachLinhVuc(danhSachLinhVuc);
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

    @Override
    public void loadDataKhuVuc(Activity activity) {
        mData=FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));
        final ArrayList<KhuVuc> danhSachKhuVuc=new ArrayList<>();
        mData.getReference().child("DataApp").child("KhuVuc").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                danhSachKhuVuc.add(dataSnapshot.getValue(KhuVuc.class));
                timKiemPresenterImp.nhanDanhSachKhuVuc(danhSachKhuVuc);
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
