package com.eways.elearning.Presenter.KhoaHoc.ThongTinNguoiDang;

import android.app.Activity;

import com.eways.elearning.Data.DataModel.TaiKhoan.TaiKhoan;

/**
 * Created by ADMIN on 12/7/2017.
 */

public interface ThongTinNguoiDangPresenterImp {
    public void GetThongTinNguoiDangPresenter(String idNguoiDang, Activity activity);
    public void NhanThongTinNguoiDangPresenter(TaiKhoan taiKhoan);
}
