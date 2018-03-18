package com.eways.elearning.Presenter.ListKhoaHoc;

import com.eways.elearning.Data.DataModel.KhoaHoc.CustomModelKhoaHoc;

import java.util.ArrayList;

/**
 * Created by yowin on 10/01/2018.
 */

public interface DanhSachKhoaHocPresenterImp {
    public void guiYeuCauDanhSachKhoaHoc(String linhVuc, boolean loai);
    public void nhanDanhSachKhoaHoc(ArrayList<CustomModelKhoaHoc> danhSachKhoaHoc);
}
