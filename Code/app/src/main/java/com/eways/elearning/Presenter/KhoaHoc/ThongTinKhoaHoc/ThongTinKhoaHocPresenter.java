package com.eways.elearning.Presenter.KhoaHoc.ThongTinKhoaHoc;

import android.app.Activity;

import com.eways.elearning.Model.KhoaHoc.ThongTinNguoiDang.ThongTinKhoaHoc.ThongTinKhoaHocModel;
import com.eways.elearning.Model.KhoaHoc.ThongTinNguoiDang.ThongTinKhoaHoc.ThongTinKhoaHocModelImp;

/**
 * Created by zzzzz on 12/8/2017.
 */

public class ThongTinKhoaHocPresenter implements ThongTinKhoaHocPresenterImp{
    ThongTinKhoaHocModelImp thongTinKhoaHocModelImp=new ThongTinKhoaHocModel(this);
    @Override
    public void YeuCauLayThongTinKhoaHoc(Activity activity,String loaiBaiDang) {
        thongTinKhoaHocModelImp.LayDataKhoaHoc(activity,loaiBaiDang);
    }
}
