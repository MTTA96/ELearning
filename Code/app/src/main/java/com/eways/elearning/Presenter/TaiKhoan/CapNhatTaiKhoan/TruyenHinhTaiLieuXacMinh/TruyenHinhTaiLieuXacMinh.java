package com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan.TruyenHinhTaiLieuXacMinh;

import com.eways.elearning.Model.TaiKhoan.DangNhap.DangNhapImpModel;
import com.eways.elearning.Model.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan.CapNhatTaiKhoanModelImp;

/**
 * Created by ADMIN on 12/3/2017.
 */

public class TruyenHinhTaiLieuXacMinh implements TruyenHinhTaiLieuXacMinhImp {

    CapNhatTaiKhoanModelImp capNhatTaiKhoanModelImp;

    public TruyenHinhTaiLieuXacMinh(CapNhatTaiKhoanModelImp capNhatTaiKhoanModelImp) {
        this.capNhatTaiKhoanModelImp = capNhatTaiKhoanModelImp;
    }

    @Override
    public void TruyenHinhMT(String uri) {
        capNhatTaiKhoanModelImp.NhanHinhTaiLieuXacMinhMT(uri);
    }

    @Override
    public void TruyenHinhMS(String uri) {
        capNhatTaiKhoanModelImp.NhanHinhTaiLieuXacMinhMS(uri);
    }
}
