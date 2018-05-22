package com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan;

import android.app.Activity;

import com.eways.elearning.Model.DataModel.TaiKhoan.TaiLieu.TaiLieuChuyenMon.TaiLieuChuyenMon;
import com.eways.elearning.Model.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan.CapNhatTaiLieuChuyenMonModel;
import com.eways.elearning.Model.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan.CapNhatTaiLieuChuyenMonModelImp;
import com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan.TaiLieuChuyenMon.CapNhatTaiLieuChuyenMonViewImp;

import java.util.ArrayList;

/**
 * Created by ADMIN on 1/8/2018.
 */

public class CapNhatTaiLieuChuyenMonPresenter implements CapNhatTaiLieuChuyenMonPresenterImp {
    CapNhatTaiLieuChuyenMonViewImp capNhatTaiLieuChuyenMonViewImp;
    CapNhatTaiLieuChuyenMonModelImp capNhatTaiLieuChuyenMonModelImp=new CapNhatTaiLieuChuyenMonModel(this);

    public CapNhatTaiLieuChuyenMonPresenter(CapNhatTaiLieuChuyenMonViewImp capNhatTaiLieuChuyenMonViewImp) {
        this.capNhatTaiLieuChuyenMonViewImp = capNhatTaiLieuChuyenMonViewImp;
    }

    @Override
    public void YeuCauDataTaiLieuChuyenMon(String idUser, Activity activity) {
        capNhatTaiLieuChuyenMonModelImp.NhanDataLoadTaiLieuChuyenMon(idUser,activity);
    }

    @Override
    public void KetQuaDataTaiLieuChuyenMon(ArrayList<TaiLieuChuyenMon> danhSachTaiLieuChuyenMon) {
        capNhatTaiLieuChuyenMonViewImp.DataTaiLieuChuyenMon(danhSachTaiLieuChuyenMon);
    }
}
