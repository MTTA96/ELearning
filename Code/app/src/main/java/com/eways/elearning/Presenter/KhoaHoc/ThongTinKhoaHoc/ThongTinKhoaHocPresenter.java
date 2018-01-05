package com.eways.elearning.Presenter.KhoaHoc.ThongTinKhoaHoc;

import android.app.Activity;

import com.eways.elearning.DataModel.KhoaHoc.ThongTinChiTietKhoaHoc;
import com.eways.elearning.Model.KhoaHoc.ThongTinNguoiDang.ThongTinKhoaHoc.ThongTinKhoaHocModel;
import com.eways.elearning.Model.KhoaHoc.ThongTinNguoiDang.ThongTinKhoaHoc.ThongTinKhoaHocModelImp;
import com.eways.elearning.View.Fragment.KhoaHoc.ThongTinKhoaHocViewImp;

/**
 * Created by zzzzz on 12/8/2017.
 */

public class ThongTinKhoaHocPresenter implements ThongTinKhoaHocPresenterImp{
    ThongTinKhoaHocModelImp thongTinKhoaHocModelImp=new ThongTinKhoaHocModel(this);
    ThongTinKhoaHocViewImp thongTinKhoaHocViewImp;

    public ThongTinKhoaHocPresenter(ThongTinKhoaHocViewImp thongTinKhoaHocViewImp) {
        this.thongTinKhoaHocViewImp = thongTinKhoaHocViewImp;
    }

    @Override
    public void YeuCauLayThongTinKhoaHoc(Activity activity,String loaiKhoaHoc,String idNguoiDang,String idKhoaHoc) {
        thongTinKhoaHocModelImp.LayDataKhoaHoc(activity,loaiKhoaHoc,idNguoiDang,idKhoaHoc );
    }

    @Override
    public void NhanKetQuaThongTinKhoaHoc(ThongTinChiTietKhoaHoc thongTinChiTietKhoaHoc) {
        thongTinKhoaHocViewImp.KetQuaThongTinKhoaHoc(thongTinChiTietKhoaHoc);
    }
}
