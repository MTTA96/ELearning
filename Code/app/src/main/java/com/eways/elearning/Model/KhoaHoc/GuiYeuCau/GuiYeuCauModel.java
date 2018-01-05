package com.eways.elearning.Model.KhoaHoc.GuiYeuCau;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.eways.elearning.DataModel.KhoaHoc.DanhSachYeuCau;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.Presenter.KhoaHoc.GuiYeuCau.GuiYeuCauPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ADMIN on 12/31/2017.
 */

public class GuiYeuCauModel implements GuiYeuCauModelImp {
    GuiYeuCauPresenter guiYeuCauPresenter;

    public GuiYeuCauModel(GuiYeuCauPresenter guiYeuCauPresenter) {
        this.guiYeuCauPresenter = guiYeuCauPresenter;
    }

    @Override
    public void CapNhapYeuCau(final String keyKhoaHoc, String idNguoiGui, Activity activity) {
        final FirebaseDatabase mData=FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));
        mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").child(keyKhoaHoc).child("danhSachYeuCau").child("dangCho").push().setValue(idNguoiGui).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").orderByKey().equalTo(keyKhoaHoc).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        guiYeuCauPresenter.KetQuaGuiYeuCau("GuiYeuCauThanhCong",dataSnapshot.getValue(KhoaHoc.class));
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
        });
    }
}