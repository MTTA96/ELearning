package com.eways.elearning.Presenter.DangKy.DangNhap;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by ADMIN on 11/5/2017.
 */

public interface DangNhapImpPresenter {
    public void NhanThongTinDN(String email,String Password ,Activity activity);
    public void KetQuaDangNhap(String ketqua, FirebaseUser user,Activity activity);
}
