package com.eways.elearning.Model.TaiKhoan.DangKy;

import android.app.Activity;

import com.eways.elearning.DataModel.TaiKhoan;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Quang Tri on 27/10/2017.
 */

public interface DangKyImpModel {
    public void NhanTaiKhoan (TaiKhoan taiKhoan, Activity activity);
}
