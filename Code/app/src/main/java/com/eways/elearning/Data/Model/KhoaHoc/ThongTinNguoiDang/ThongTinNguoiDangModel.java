package com.eways.elearning.Data.Model.KhoaHoc.ThongTinNguoiDang;

import android.app.Activity;

import com.eways.elearning.Data.DataModel.TaiKhoan.TaiKhoan;
import com.eways.elearning.Presenter.KhoaHoc.ThongTinNguoiDang.ThongTinNguoiDangPresenterImp;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ADMIN on 12/7/2017.
 */

public class ThongTinNguoiDangModel implements ThongTinNguoiDangModelImp {
    ThongTinNguoiDangPresenterImp thongTinNguoiDangPresenterImp;
    FirebaseDatabase mData;

    public ThongTinNguoiDangModel(ThongTinNguoiDangPresenterImp thongTinNguoiDangPresenterImp) {
        this.thongTinNguoiDangPresenterImp = thongTinNguoiDangPresenterImp;
    }

    @Override
    public void GetThongTinNguoiDang(final String idNguoiDang, Activity activity) {
        mData=FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));
        mData.getReference().child("TaiKhoan").orderByKey().equalTo(idNguoiDang).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    thongTinNguoiDangPresenterImp.NhanThongTinNguoiDangPresenter(dataSnapshot.getValue(TaiKhoan.class));
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
