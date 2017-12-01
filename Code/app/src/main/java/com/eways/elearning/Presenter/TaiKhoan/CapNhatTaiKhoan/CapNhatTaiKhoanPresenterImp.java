package com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan;

import android.app.Activity;

import com.eways.elearning.DataModel.TaiKhoan;

/**
 * Created by ADMIN on 11/19/2017.
 */

public interface CapNhatTaiKhoanPresenterImp {
    public void NhanDataUpdate (TaiKhoan taiKhoan, Activity activity);
    public void KetQuaCapNhat(String ketquacapnhat,TaiKhoan taiKhoan,Activity activity);
}
