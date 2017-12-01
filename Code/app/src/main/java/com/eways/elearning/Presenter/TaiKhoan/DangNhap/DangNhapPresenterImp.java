package com.eways.elearning.Presenter.TaiKhoan.DangNhap;

import android.app.Activity;

import com.eways.elearning.DataModel.TaiKhoan;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ADMIN on 11/5/2017.
 */

public interface DangNhapPresenterImp {
    public void NhanThongTinDN(String email, String Password, Activity activity);
    public void ChuyenTaiKhoanGmai(GoogleSignInAccount account,Activity activity);
    public void KetQuaDangNhap(String ketqua, FirebaseUser user, GoogleSignInAccount Guser, Activity activity, TaiKhoan taiKhoan);
}
