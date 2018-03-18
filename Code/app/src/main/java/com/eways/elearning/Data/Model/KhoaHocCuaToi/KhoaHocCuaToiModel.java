package com.eways.elearning.Data.Model.KhoaHocCuaToi;

import android.app.Activity;

import com.eways.elearning.Data.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.Presenter.TaiKhoan.KhoaHocCuaToi.KhoaHocCuaToiPresenterImp;
import com.eways.elearning.View.Fragment.TaiKhoan.KhoaHocCuaToi.KhoaHocChoDuyetFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.KhoaHocCuaToi.KhoaHocDangThamGiaFragment;
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
    public void NhanYeuCauLayDataDanhSachKhoaHocDangKy(final String idUser, Activity activity, final KhoaHocChoDuyetFragment khoaHocChoDuyetFragment, final KhoaHocDangThamGiaFragment khoaHocDangThamGiaFragment) {
        mData=FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));
        final ArrayList<KhoaHoc> danhSachKhoaHocDangKy=new ArrayList<>();
        mData.getReference().child("DanhSachDangKyKhoaHoc").child(idUser).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final String keyKhoaHoc=dataSnapshot.getKey().toString();
                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").orderByKey().equalTo(keyKhoaHoc).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        danhSachKhoaHocDangKy.add(dataSnapshot.getValue(KhoaHoc.class));
                        khoaHocCuaToiPresenterImp.NhanDataKhoaHocDaDangKy(danhSachKhoaHocDangKy,idUser,khoaHocChoDuyetFragment,khoaHocDangThamGiaFragment);
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
