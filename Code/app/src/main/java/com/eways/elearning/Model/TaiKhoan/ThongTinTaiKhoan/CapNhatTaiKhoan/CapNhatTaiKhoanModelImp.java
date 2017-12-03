package com.eways.elearning.Model.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan;

import android.app.Activity;

import com.eways.elearning.DataModel.TaiKhoan;

/**
 * Created by ADMIN on 11/19/2017.
 */

public interface CapNhatTaiKhoanModelImp {
    public void CapNhatTaiKhoan(TaiKhoan taiKhoan, Activity activity,byte[] data_mt,byte[] data_ms);
    public void NhanHinhTaiLieuXacMinhMT(String uri);
    public void NhanHinhTaiLieuXacMinhMS(String uri);


}
