package com.eways.elearning.Model.TaiKhoan.ThemTaiLieuChuyenMon;

import android.app.Activity;

import com.eways.elearning.DataModel.LinhVuc.LinhVuc;
import com.eways.elearning.DataModel.Other.KhuVuc;
import com.eways.elearning.Presenter.TaiKhoan.ThemTaiLieuChuyenMon.ThemTaiLieuChuyenMonPresenterImp;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by ADMIN on 1/8/2018.
 */

public class ThemTaiLieuChuyenMonModel implements ThemTaiLieuChuyenMonModelImp {
    FirebaseDatabase mData;
    ThemTaiLieuChuyenMonPresenterImp themTaiLieuChuyenMonPresenterImp;

    public ThemTaiLieuChuyenMonModel(ThemTaiLieuChuyenMonPresenterImp themTaiLieuChuyenMonPresenterImp) {
        this.themTaiLieuChuyenMonPresenterImp = themTaiLieuChuyenMonPresenterImp;
    }

    @Override
    public void LoadLinhVucTaiLieuChuyenMon(Activity activity) {
        mData=FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));
        final ArrayList<LinhVuc> listLinhVuc=new ArrayList<>();
        mData.getReference().child("DataApp").child("KhuVuc").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listLinhVuc.add(dataSnapshot.getValue(LinhVuc.class));
                themTaiLieuChuyenMonPresenterImp.KetQuaLinhVucTaiLieuChuyenMon(listLinhVuc);
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
