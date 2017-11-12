package com.eways.elearning.Model.DangNhap;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Presenter.DangKy.DangNhap.DangNhapImpPresenter;
import com.eways.elearning.Presenter.DangKy.DangNhap.DangNhapPresenter;
import com.eways.elearning.Util.SupportKeysList;
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
    DangNhapImpPresenter dangNhapImpPresenter;
    SharedPreferencesHandler sharedPreferencesHandler;

    public DangNhapModel(DangNhapImpPresenter dangNhapImpPresenter) {
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
                    sharedPreferencesHandler=new SharedPreferencesHandler(activity, SupportKeysList.SHARED_PREF_FILE_NAME);
                    FirebaseUser firebaseUser=mAuth.getCurrentUser();
                    sharedPreferencesHandler.DangNhapThanhCong(firebaseUser.getUid(),firebaseUser.getEmail(),null,null,firebaseUser.getDisplayName(),true,SupportKeysList.TAI_KHOAN_THUONG);
                    dangNhapImpPresenter.KetQuaDangNhap(DangNhapFragment.LOGIN_SUCCESS);

                } else
                    dangNhapImpPresenter.KetQuaDangNhap(DangNhapFragment.LOGIN_FAILED);

            }
        });
    }
}
