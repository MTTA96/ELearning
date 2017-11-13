package com.eways.elearning.Model.TaiKhoan.DangNhap;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.Presenter.TaiKhoan.DangNhap.DangNhapImpPresenter;
import com.eways.elearning.Presenter.TaiKhoan.DangNhap.DangNhapPresenterImp;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by ADMIN on 11/5/2017.
 */

public class DangNhapModel implements DangNhapImpModel{
    DangNhapPresenterImp dangNhapImpPresenter;


    public DangNhapModel(DangNhapPresenterImp dangNhapImpPresenter) {
        this.dangNhapImpPresenter = dangNhapImpPresenter;
    }

    @Override
    public void NhanTaiKhoanDN(TaiKhoan taiKhoan, final Activity activity) {
        final FirebaseAuth mAuth;
        mAuth=FirebaseAuth.getInstance(FirebaseApp.initializeApp(activity));
        mAuth.signInWithEmailAndPassword(taiKhoan.getEmail().toString(), taiKhoan.getPassword().toString()).addOnCompleteListener(activity,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user=mAuth.getCurrentUser();
                    dangNhapImpPresenter.KetQuaDangNhap(DangNhapFragment.LOGIN_SUCCESS,user,null,activity);

                } else
                    dangNhapImpPresenter.KetQuaDangNhap(DangNhapFragment.LOGIN_FAILED,null,null,activity);

            }
        });
    }
}
