package com.eways.elearning.Presenter.TaiKhoan.DangNhap;

import android.app.Activity;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by ADMIN on 11/5/2017.
 */

public interface DangNhapPresenterImp {
    public void NhanThongTinDN(String email, String Password, Activity activity);
    public void KetQuaDangNhap(String ketqua, FirebaseUser user, Activity activity);
}
