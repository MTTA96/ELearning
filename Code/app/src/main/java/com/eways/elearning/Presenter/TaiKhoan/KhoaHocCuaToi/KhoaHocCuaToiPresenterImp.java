package com.eways.elearning.Presenter.TaiKhoan.KhoaHocCuaToi;

import android.app.Activity;

import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;

import java.util.ArrayList;

/**
 * Created by ADMIN on 1/9/2018.
 */

public interface KhoaHocCuaToiPresenterImp {
    public void YeuCauDataKhoaHocDaDangKy(String idUser, Activity activity);
    public void NhanDataKhoaHocDaDangKy(ArrayList<KhoaHoc> khoaHoc,String idUser);
}
