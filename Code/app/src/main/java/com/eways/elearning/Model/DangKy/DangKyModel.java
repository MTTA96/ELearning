package com.eways.elearning.Model.DangKy;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.Presenter.DangKy.DangKyImpPresenter;
import com.eways.elearning.Presenter.DangKy.DangKyPresenter;
import com.eways.elearning.View.Activity.MainActivity;
import com.eways.elearning.View.Fragment.TaiKhoan.DangKy.DangKyFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

/**
 * Created by Quang Tri on 27/10/2017.
 */

public class DangKyModel implements DangKyImpModel{
    DangKyImpPresenter dangKyImpPresenter;
    private FirebaseAuth mAuth;


    public DangKyModel(DangKyImpPresenter dangKyImpPresenter) {
        this.dangKyImpPresenter = dangKyImpPresenter;
    }

    @Override
    public void NhanTaiKhoan(TaiKhoan taiKhoan, Activity activity) {
        mAuth=FirebaseAuth.getInstance(FirebaseApp.initializeApp(activity));
        mAuth.createUserWithEmailAndPassword(taiKhoan.getEmail(), taiKhoan.getPassword())
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dangKyImpPresenter.KetQuaDangKy(DangKyFragment.SIGN_UP_SUCCESS);
                        } else{
                            dangKyImpPresenter.KetQuaDangKy(DangKyFragment.SIGN_UP_FAILED);
                        }
                    }
                });
    }
}
