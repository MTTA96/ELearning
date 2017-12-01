package com.eways.elearning.Model.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan.CapNhatTaiKhoanPresenterImp;
import com.eways.elearning.Util.SupportKeysList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ADMIN on 11/19/2017.
 */

public class CapNhatTaiKhoanModel implements CapNhatTaiKhoanModelImp {
    FirebaseAuth mAuth;
    FirebaseDatabase mData;
    CapNhatTaiKhoanPresenterImp capNhatTaiKhoanPresenterImp;

    public CapNhatTaiKhoanModel(CapNhatTaiKhoanPresenterImp capNhatTaiKhoanPresenterImp) {
        this.capNhatTaiKhoanPresenterImp = capNhatTaiKhoanPresenterImp;
    }

    @Override
    public void CapNhatTaiKhoan(TaiKhoan taiKhoan, final Activity activity) {
        mAuth=FirebaseAuth.getInstance(FirebaseApp.initializeApp(activity));
        mData=FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));
        mData.getReference().child("TaiKhoan").child(taiKhoan.getId()).setValue(taiKhoan);
        mData.getReference().child("TaiKhoan").orderByKey().equalTo(taiKhoan.getId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                capNhatTaiKhoanPresenterImp.KetQuaCapNhat(SupportKeysList.TAG_CAPNHATTHANHCONG,dataSnapshot.getValue(TaiKhoan.class),activity);
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
