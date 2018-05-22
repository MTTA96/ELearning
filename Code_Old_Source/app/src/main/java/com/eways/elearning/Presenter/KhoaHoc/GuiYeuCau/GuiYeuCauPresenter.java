package com.eways.elearning.Presenter.KhoaHoc.GuiYeuCau;

import android.app.Activity;

import com.eways.elearning.Model.DataModel.KhoaHoc.KhoaHoc;
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
    public void TruyenYeuCau(String keyKhoaHoc, String idNguoiGui, Activity activity,boolean kiemtra) {
        if (kiemtra==false)
            guiYeuCauModelImp.CapNhapYeuCau(keyKhoaHoc,idNguoiGui,activity);
        else{
            guiYeuCauModelImp.KiemTraTinhTrangYeuCau(keyKhoaHoc,activity);
        }

    }

    @Override
    public void KetQuaGuiYeuCau(String ketQuaGuiYC,KhoaHoc khoaHoc) {
        thongTinKhoaHocViewImp.KetQuaGuiYeuCau(ketQuaGuiYC,khoaHoc);
    }

    @Override
    public void HuyYeuCau(String keyKhoaHoc, String keyYeuCau,String nhanh,String idNguoiHuy, Activity activity) {
        guiYeuCauModelImp.NhanDataHuyYeuCau(keyKhoaHoc,keyYeuCau,nhanh,idNguoiHuy,activity);
    }

    @Override
    public void KetQuaHuyYeuCau(String ketQuaHuyYeuCau, KhoaHoc khoaHoc) {
        thongTinKhoaHocViewImp.KetQuaHuyYeuCau(ketQuaHuyYeuCau,khoaHoc);
    }


}
