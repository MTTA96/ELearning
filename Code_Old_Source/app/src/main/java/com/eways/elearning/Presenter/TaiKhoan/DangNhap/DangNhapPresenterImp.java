package com.eways.elearning.Presenter.TaiKhoan.DangNhap;

import android.app.Activity;

import com.eways.elearning.Model.DataModel.TaiKhoan.TaiKhoan;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by ADMIN on 11/5/2017.
 */

public interface DangNhapPresenterImp {
    public void userData(GoogleSignInAccount account, Activity activity);
    public void KetQuaDangNhap(String ketqua, FirebaseUser user, GoogleSignInAccount Guser, Activity activity, TaiKhoan taiKhoan);
}
