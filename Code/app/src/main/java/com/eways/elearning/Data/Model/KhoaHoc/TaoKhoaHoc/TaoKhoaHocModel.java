package com.eways.elearning.Data.Model.KhoaHoc.TaoKhoaHoc;

import android.app.Activity;

import com.eways.elearning.Data.DataModel.Other.KhuVuc;
import com.eways.elearning.Data.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.Data.DataModel.LinhVuc.LinhVuc;
import com.eways.elearning.Presenter.TaoKhoaHoc.TaoKhoaHocPresenterImp;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by yowin on 19/11/2017.
 */

public class TaoKhoaHocModel implements TaoKhoaHocModelImp {

    TaoKhoaHocPresenterImp taoKhoaHocPresenterImp;
    FirebaseDatabase mData ;
    FirebaseAuth mAuth;

    public TaoKhoaHocModel(TaoKhoaHocPresenterImp taoKhoaHocPresenterImp) {
        this.taoKhoaHocPresenterImp = taoKhoaHocPresenterImp;
    }

    @Override
    public void postKhoaHoc(KhoaHoc khoaHoc,boolean loai, Activity activity) {
        mData=FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));
        if(loai == false)
        {
            mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").push().setValue(khoaHoc);

        }
        else
        {
            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").push().setValue(khoaHoc);
        }
    }

    @Override
    public void loaddataLinhvuc(Activity activity) {
        mData=FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));
        final ArrayList<LinhVuc> danhSachLinhVuc=new ArrayList<>();
        mData.getReference().child("DataApp").child("DanhMucLinhVuc").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                danhSachLinhVuc.add(dataSnapshot.getValue(LinhVuc.class));
                taoKhoaHocPresenterImp.nhanDanhSachLinhVuc(danhSachLinhVuc);
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
                taoKhoaHocPresenterImp.nhanDanhSachKhuVuc(danhSachKhuVuc);
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
