package com.eways.elearning.Presenter.TaiKhoan.DangKy;

import android.app.Activity;

/**
 * Created by zzzzz on 11/12/2017.
 */

public interface DangKyPresenterImp {
    public void NhanThongTinDangKy(String Email, String Password, String CPassword, Activity activity);
    public void KetQuaDangKy(String ketqua);
}
