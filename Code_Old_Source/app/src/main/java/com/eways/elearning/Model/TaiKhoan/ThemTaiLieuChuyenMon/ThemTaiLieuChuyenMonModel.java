package com.eways.elearning.Model.TaiKhoan.ThemTaiLieuChuyenMon;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.eways.elearning.Model.DataModel.LinhVuc.LinhVuc;
import com.eways.elearning.Model.DataModel.TaiKhoan.TaiLieu.TaiLieuChuyenMon.TaiLieuChuyenMon;
import com.eways.elearning.Presenter.TaiKhoan.ThemTaiLieuChuyenMon.ThemTaiLieuChuyenMonPresenterImp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
        mData.getReference().child("DataApp").child("DanhMucLinhVuc").addChildEventListener(new ChildEventListener() {
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

    @Override
    public void DataCapNhapTaiLieuChuyenMon(TaiLieuChuyenMon taiLieuChuyenMon, String idUser, Activity activity) {
        mData=FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));
        mData.getReference().child("TaiLieuChuyenMon").child(taiLieuChuyenMon.getTenLinhVucChuyenMon()).child(idUser).setValue(taiLieuChuyenMon).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                themTaiLieuChuyenMonPresenterImp.KetQuaCapNhatTaiLieuChuyenMon("CapNhatTaiLieuChuyenMonThanhCong");
            }
        });
    }
}
