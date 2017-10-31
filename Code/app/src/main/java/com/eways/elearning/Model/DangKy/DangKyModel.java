package com.eways.elearning.Model.DangKy;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.support.annotation.NonNull;

import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.Presenter.DangKy.DangKyImpPresenter;
import com.eways.elearning.View.Activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Quang Tri on 27/10/2017.
 */

public class DangKyModel implements DangKyImpModel {

    private DangKyImpPresenter dangKyImpPresenter;

    public DangKyModel() {
    }

    private FirebaseAuth mAuth;
    @Override
    public void NhanTaiKhoan(TaiKhoan taiKhoan) {
//        FirebaseApp.initializeApp(new MainActivity());
        mAuth=FirebaseAuth.getInstance();
        if (taiKhoan.getEmail().isEmpty() || taiKhoan.getPassword().isEmpty()) {

        } else {

            mAuth.createUserWithEmailAndPassword(taiKhoan.getEmail(), taiKhoan.getPassword())
                    .addOnCompleteListener(new MainActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                dangKyImpPresenter.KetQuaDangKy("thanhcong");
                            } else
                                dangKyImpPresenter.KetQuaDangKy("khongthanhcong");

                        }
                    });

        }
    }
}
