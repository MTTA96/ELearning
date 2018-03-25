package com.eways.elearning.Presenter.KhoaHoc.GuiYeuCau;

import android.app.Activity;

import com.eways.elearning.Model.DataModel.KhoaHoc.KhoaHoc;

/**
 * Created by ADMIN on 12/31/2017.
 */

public interface GuiYeuCauPresenterImp {
    public void TruyenYeuCau(String keyKhoaHoc,String idNguoiGui, Activity activity,boolean kiemtra);
    public void KetQuaGuiYeuCau(String ketQuaGuiYC,KhoaHoc khoaHoc);
    public void HuyYeuCau(String keyKhoaHoc,String keyYeuCau,String nhanh,String idNguoiHuy,Activity activity);
    public void KetQuaHuyYeuCau(String ketQuaHuyYeuCau,KhoaHoc khoaHoc);

}
