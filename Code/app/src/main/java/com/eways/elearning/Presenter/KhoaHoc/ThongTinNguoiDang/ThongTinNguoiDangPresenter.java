package com.eways.elearning.Presenter.KhoaHoc.ThongTinNguoiDang;

import android.app.Activity;

import com.eways.elearning.Model.DataModel.TaiKhoan.TaiKhoan;
import com.eways.elearning.Model.KhoaHoc.ThongTinNguoiDang.ThongTinNguoiDangModel;
import com.eways.elearning.Model.KhoaHoc.ThongTinNguoiDang.ThongTinNguoiDangModelImp;
import com.eways.elearning.View.Fragment.KhoaHoc.ThongTinNguoiDang.ThongTinNguoiDangFragmentImp;

/**
 * Created by ADMIN on 12/7/2017.
 */

public class ThongTinNguoiDangPresenter implements ThongTinNguoiDangPresenterImp {
    ThongTinNguoiDangFragmentImp thongTinNguoiDangFragmentImp;
    ThongTinNguoiDangModelImp thongTinNguoiDangModelImp;

    public ThongTinNguoiDangPresenter(ThongTinNguoiDangFragmentImp thongTinNguoiDangFragmentImp) {
        this.thongTinNguoiDangFragmentImp = thongTinNguoiDangFragmentImp;
        thongTinNguoiDangModelImp=new ThongTinNguoiDangModel(this);
    }

    @Override
    public void GetThongTinNguoiDangPresenter(String idNguoiDang, Activity activity) {
        thongTinNguoiDangModelImp.GetThongTinNguoiDang(idNguoiDang,activity);
    }

    @Override
    public void NhanThongTinNguoiDangPresenter(TaiKhoan taiKhoan) {
        thongTinNguoiDangFragmentImp.NhanThongTinNguoiDang(taiKhoan);
    }
}
