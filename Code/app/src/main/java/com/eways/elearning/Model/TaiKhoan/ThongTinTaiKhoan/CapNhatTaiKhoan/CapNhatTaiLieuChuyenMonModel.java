package com.eways.elearning.Model.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan;

import android.app.Activity;

import com.eways.elearning.Model.DataModel.TaiKhoan.TaiLieu.TaiLieuChuyenMon.TaiLieuChuyenMon;
import com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan.CapNhatTaiLieuChuyenMonPresenterImp;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by ADMIN on 1/8/2018.
 */

public class CapNhatTaiLieuChuyenMonModel implements CapNhatTaiLieuChuyenMonModelImp {
    FirebaseDatabase mData;
    CapNhatTaiLieuChuyenMonPresenterImp capNhatTaiLieuChuyenMonPresenterImp;

    public CapNhatTaiLieuChuyenMonModel(CapNhatTaiLieuChuyenMonPresenterImp capNhatTaiLieuChuyenMonPresenterImp) {
        this.capNhatTaiLieuChuyenMonPresenterImp = capNhatTaiLieuChuyenMonPresenterImp;
    }

    @Override
    public void NhanDataLoadTaiLieuChuyenMon(final String idUser, Activity activity) {
        mData=FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));
        final ArrayList<TaiLieuChuyenMon> danhSachTaiLieuChuyenMon=new ArrayList<>();
        mData.getReference().child("TaiLieuChuyenMon").child("Ngoại ngữ").orderByKey().equalTo(idUser).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                danhSachTaiLieuChuyenMon.add(dataSnapshot.getValue(TaiLieuChuyenMon.class));
                mData.getReference().child("TaiLieuChuyenMon").child("Toán").orderByKey().equalTo(idUser).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        danhSachTaiLieuChuyenMon.add(dataSnapshot.getValue(TaiLieuChuyenMon.class));
                        capNhatTaiLieuChuyenMonPresenterImp.KetQuaDataTaiLieuChuyenMon(danhSachTaiLieuChuyenMon);
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
