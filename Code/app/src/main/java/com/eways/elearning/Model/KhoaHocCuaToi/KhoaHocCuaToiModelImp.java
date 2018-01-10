package com.eways.elearning.Model.KhoaHocCuaToi;

import android.app.Activity;

import com.eways.elearning.View.Fragment.TaiKhoan.KhoaHocCuaToi.KhoaHocChoDuyetFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.KhoaHocCuaToi.KhoaHocDangThamGiaFragment;

/**
 * Created by ADMIN on 1/9/2018.
 */

public interface KhoaHocCuaToiModelImp {
    public void NhanYeuCauLayDataDanhSachKhoaHocDangKy(String idUser, Activity activity, KhoaHocChoDuyetFragment khoaHocChoDuyetFragment, KhoaHocDangThamGiaFragment khoaHocDangThamGiaFragment);
}
