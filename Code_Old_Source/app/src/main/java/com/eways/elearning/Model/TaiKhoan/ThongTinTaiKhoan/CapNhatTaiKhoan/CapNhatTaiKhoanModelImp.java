package com.eways.elearning.Model.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan;

import android.app.Activity;

import com.eways.elearning.Model.DataModel.TaiKhoan.TaiKhoan;

/**
 * Created by ADMIN on 11/19/2017.
 */

public interface CapNhatTaiKhoanModelImp {
    public void CapNhatTaiKhoan(TaiKhoan taiKhoan, Activity activity,byte[] data_mt,byte[] data_ms,byte[] data_avarta);
    public void CapNhatTaiKhoanGiaSu(String idUser,Activity activity);

}
