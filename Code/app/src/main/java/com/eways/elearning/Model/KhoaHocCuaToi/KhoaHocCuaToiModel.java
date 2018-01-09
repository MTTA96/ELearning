package com.eways.elearning.Model.KhoaHocCuaToi;

import android.app.Activity;

import com.eways.elearning.Presenter.TaiKhoan.KhoaHocCuaToi.KhoaHocCuaToiPresenter;
import com.eways.elearning.Presenter.TaiKhoan.KhoaHocCuaToi.KhoaHocCuaToiPresenterImp;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by ADMIN on 1/9/2018.
 */

public class KhoaHocCuaToiModel implements KhoaHocCuaToiModelImp {
    KhoaHocCuaToiPresenterImp khoaHocCuaToiPresenterImp;
    FirebaseDatabase mData;

    public KhoaHocCuaToiModel(KhoaHocCuaToiPresenterImp khoaHocCuaToiPresenterImp) {
        this.khoaHocCuaToiPresenterImp = khoaHocCuaToiPresenterImp;
    }

    @Override
    public void NhanYeuCauLayDataDanhSachKhoaHocDangKy(String idUser, Activity activity) {
        mData=FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));
        final ArrayList<String> danhSachKeyKhoaHocDaDangKy=new ArrayList<>();
        mData.getReference().child("DanhSachDangKyKhoaHoc").child(idUser).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                danhSachKeyKhoaHocDaDangKy.add(dataSnapshot.getKey());

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
