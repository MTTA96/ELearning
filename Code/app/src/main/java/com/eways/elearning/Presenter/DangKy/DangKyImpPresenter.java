package com.eways.elearning.Presenter.DangKy;

import android.app.Activity;
import android.content.Context;

import com.eways.elearning.DataModel.TaiKhoan;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Quang Tri on 27/10/2017.
 */

public interface DangKyImpPresenter {
    public void NhanThongTinDangKy(String Email, String Password, String CPassword, Activity activity);
    public void KetQuaDangKy(String ketqua);

}
