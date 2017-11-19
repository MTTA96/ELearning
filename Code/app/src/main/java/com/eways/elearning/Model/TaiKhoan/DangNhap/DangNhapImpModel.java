package com.eways.elearning.Model.TaiKhoan.DangNhap;

import android.app.Activity;

import com.eways.elearning.DataModel.TaiKhoan;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by ADMIN on 11/5/2017.
 */

public interface DangNhapImpModel {
    public void NhanTaiKhoanDN(TaiKhoan taiKhoan, Activity activity);
    public void DangNhapGmail(GoogleSignInAccount account,Activity activity);
}
