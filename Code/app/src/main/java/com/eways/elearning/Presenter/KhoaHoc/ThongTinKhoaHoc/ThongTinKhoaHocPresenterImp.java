package com.eways.elearning.Presenter.KhoaHoc.ThongTinKhoaHoc;

import android.app.Activity;

import com.eways.elearning.DataModel.ThongTinChiTietKhoaHoc;

/**
 * Created by ADMIN on 12/12/2017.
 */

public interface ThongTinKhoaHocPresenterImp {
    public void YeuCauLayThongTinKhoaHoc(Activity activity,String loaiKhoaHoc,String idNguoiDang,String idKhoaHoc);
    public void NhanKetQuaThongTinKhoaHoc(ThongTinChiTietKhoaHoc thongTinChiTietKhoaHoc);
}
