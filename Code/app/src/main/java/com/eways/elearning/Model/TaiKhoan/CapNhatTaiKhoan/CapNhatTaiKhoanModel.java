package com.eways.elearning.Model.TaiKhoan.CapNhatTaiKhoan;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan.CapNhatTaiKhoanPresenterImp;
import com.eways.elearning.Util.SupportKeysList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
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
    public void CapNhatTaiKhoan(TaiKhoan taiKhoan, Activity activity) {
        mAuth=FirebaseAuth.getInstance(FirebaseApp.initializeApp(activity));
        mData=FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));
        mData.getReference().child("TaiKhoan").child(taiKhoan.getId()).setValue(taiKhoan).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    capNhatTaiKhoanPresenterImp.KetQuaCapNhat(SupportKeysList.TAG_CAPNHATTHANHCONG);
                }
            }
        });
    }
}
