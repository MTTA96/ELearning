package com.eways.elearning.Model.KhoaHoc.GuiYeuCau;

import android.app.Activity;

import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;

/**
 * Created by ADMIN on 12/31/2017.
 */

public interface GuiYeuCauModelImp {
    public void CapNhapYeuCau(String keyKhoaHoc, String idNguoiGui, Activity activity);
    public void KiemTraTinhTrangYeuCau(String keyKhoaHoc,Activity activity);
    public void NhanDataHuyYeuCau(String keyKhoaHoc,String keyYeuCau,String nhanh,String idNguoiHuy,Activity activity);
}
