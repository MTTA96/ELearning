package com.eways.elearning.Presenter.TimKiemKhoaHoc;

import com.eways.elearning.Data.DataModel.KhoaHoc.CustomModelKhoaHoc;

import java.util.ArrayList;

/**
 * Created by yowin on 12/12/2017.
 */

public interface TimKiemTheoMonPresenterImp {
    public void guiYeuCauListKhoaHoc(String mon, boolean loai);
    public void nhanListKhoaHoc(ArrayList<CustomModelKhoaHoc> listKhoaHoc);
}
