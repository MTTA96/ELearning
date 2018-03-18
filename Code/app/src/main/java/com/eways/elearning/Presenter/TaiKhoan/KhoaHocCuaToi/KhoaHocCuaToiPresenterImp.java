package com.eways.elearning.Presenter.TaiKhoan.KhoaHocCuaToi;

import android.app.Activity;

import com.eways.elearning.Data.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.View.Fragment.TaiKhoan.KhoaHocCuaToi.KhoaHocChoDuyetFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.KhoaHocCuaToi.KhoaHocDangThamGiaFragment;

import java.util.ArrayList;

/**
 * Created by ADMIN on 1/9/2018.
 */

public interface KhoaHocCuaToiPresenterImp {
    public void YeuCauDataKhoaHocDaDangKy(String idUser, Activity activity, KhoaHocChoDuyetFragment khoaHocChoDuyetFragment, KhoaHocDangThamGiaFragment khoaHocDangThamGiaFragment);
    public void NhanDataKhoaHocDaDangKy(ArrayList<KhoaHoc> khoaHoc,String idUser, KhoaHocChoDuyetFragment khoaHocChoDuyetFragment, KhoaHocDangThamGiaFragment khoaHocDangThamGiaFragment);
}
