package com.eways.elearning.Model.DangKy;

import android.app.Activity;
import android.content.Context;

import com.eways.elearning.DataModel.TaiKhoan;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Quang Tri on 27/10/2017.
 */

public interface DangKyImpModel {
    public void NhanTaiKhoan (TaiKhoan taiKhoan, Activity activity);
}
