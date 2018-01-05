package com.eways.elearning.Presenter.KhoaHoc.GuiYeuCau;

import android.app.Activity;

import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.Model.KhoaHoc.GuiYeuCau.GuiYeuCauModel;
import com.eways.elearning.Model.KhoaHoc.GuiYeuCau.GuiYeuCauModelImp;
import com.eways.elearning.View.Fragment.KhoaHoc.ThongTinKhoaHocViewImp;

/**
 * Created by ADMIN on 12/31/2017.
 */

public class GuiYeuCauPresenter implements GuiYeuCauPresenterImp {
    ThongTinKhoaHocViewImp thongTinKhoaHocViewImp;
    GuiYeuCauModelImp guiYeuCauModelImp=new GuiYeuCauModel(this);

    public GuiYeuCauPresenter(ThongTinKhoaHocViewImp thongTinKhoaHocViewImp) {
        this.thongTinKhoaHocViewImp = thongTinKhoaHocViewImp;
    }

    @Override
    public void TruyenYeuCau(String keyKhoaHoc, String idNguoiGui, Activity activity) {
        guiYeuCauModelImp.CapNhapYeuCau(keyKhoaHoc,idNguoiGui,activity);
    }

    @Override
    public void KetQuaGuiYeuCau(String ketQuaGuiYC,KhoaHoc khoaHoc) {
        thongTinKhoaHocViewImp.KetQuaGuiYeuCau(ketQuaGuiYC,khoaHoc);
    }
}
