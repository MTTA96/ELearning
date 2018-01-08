package com.eways.elearning.Model.TaiKhoan.ThemTaiLieuChuyenMon;

import android.app.Activity;

import com.eways.elearning.DataModel.TaiKhoan.TaiLieu.TaiLieuChuyenMon.TaiLieuChuyenMon;

/**
 * Created by ADMIN on 1/8/2018.
 */

public interface ThemTaiLieuChuyenMonModelImp  {
    public void LoadLinhVucTaiLieuChuyenMon(Activity activity);
    public void DataCapNhapTaiLieuChuyenMon(TaiLieuChuyenMon taiLieuChuyenMon,String idUser,Activity activity);
}
