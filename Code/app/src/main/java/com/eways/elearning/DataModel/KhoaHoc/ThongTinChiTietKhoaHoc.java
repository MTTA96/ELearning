package com.eways.elearning.DataModel.KhoaHoc;

import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.DataModel.TaiKhoan.TaiKhoan;

/**
 * Created by ADMIN on 12/12/2017.
 */

public class ThongTinChiTietKhoaHoc {
    TaiKhoan taiKhoan;
    KhoaHoc khoaHoc;

    public ThongTinChiTietKhoaHoc(TaiKhoan taiKhoan, KhoaHoc khoaHoc) {
        this.taiKhoan = taiKhoan;
        this.khoaHoc = khoaHoc;
    }

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public KhoaHoc getKhoaHoc() {
        return khoaHoc;
    }

    public void setKhoaHoc(KhoaHoc khoaHoc) {
        this.khoaHoc = khoaHoc;
    }
}
