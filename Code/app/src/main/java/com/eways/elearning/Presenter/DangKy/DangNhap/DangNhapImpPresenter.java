package com.eways.elearning.Presenter.DangKy.DangNhap;

import android.app.Activity;

/**
 * Created by ADMIN on 11/5/2017.
 */

public interface DangNhapImpPresenter {
    public void NhanThongTinDN(String email,String Password ,Activity activity);
    public void KetQuaDangNhap(String ketqua);
}
