package com.eways.elearning.Data.Model.KhoaHoc.GuiYeuCau;

import android.app.Activity;

/**
 * Created by ADMIN on 12/31/2017.
 */

public interface GuiYeuCauModelImp {
    public void CapNhapYeuCau(String keyKhoaHoc, String idNguoiGui, Activity activity);
    public void KiemTraTinhTrangYeuCau(String keyKhoaHoc,Activity activity);
    public void NhanDataHuyYeuCau(String keyKhoaHoc,String keyYeuCau,String nhanh,String idNguoiHuy,Activity activity);
}
