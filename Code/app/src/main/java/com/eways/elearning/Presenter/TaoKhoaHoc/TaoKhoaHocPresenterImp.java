package com.eways.elearning.Presenter.TaoKhoaHoc;

import android.app.Activity;

import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHocChuaHoanTat;

/**
 * Created by yowin on 19/11/2017.
 */

public interface TaoKhoaHocPresenterImp {
    public void nhanThongTinKhoaHoc(KhoaHoc khoaHoc, boolean checked, Activity activity);
    public void ketQua(String ketQua);
}
