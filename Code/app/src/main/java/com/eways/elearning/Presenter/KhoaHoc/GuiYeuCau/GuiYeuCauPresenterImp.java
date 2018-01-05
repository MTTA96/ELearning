package com.eways.elearning.Presenter.KhoaHoc.GuiYeuCau;

import android.app.Activity;

import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;

/**
 * Created by ADMIN on 12/31/2017.
 */

public interface GuiYeuCauPresenterImp {
    public void TruyenYeuCau(String keyKhoaHoc,String idNguoiGui, Activity activity);
    public void KetQuaGuiYeuCau(String ketQuaGuiYC,KhoaHoc khoaHoc);
}
