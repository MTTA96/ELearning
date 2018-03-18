package com.eways.elearning.Data.Model.TaiKhoan.ThemTaiLieuChuyenMon;

import android.app.Activity;

import com.eways.elearning.Data.DataModel.TaiKhoan.TaiLieu.TaiLieuChuyenMon.TaiLieuChuyenMon;

/**
 * Created by ADMIN on 1/8/2018.
 */

public interface ThemTaiLieuChuyenMonModelImp  {
    public void LoadLinhVucTaiLieuChuyenMon(Activity activity);
    public void DataCapNhapTaiLieuChuyenMon(TaiLieuChuyenMon taiLieuChuyenMon,String idUser,Activity activity);
}
