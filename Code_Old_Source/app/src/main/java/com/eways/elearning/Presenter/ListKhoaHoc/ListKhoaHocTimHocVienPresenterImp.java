package com.eways.elearning.Presenter.ListKhoaHoc;

import com.eways.elearning.Model.DataModel.KhoaHoc.CustomModelKhoaHoc;

import java.util.ArrayList;

/**
 * Created by yowin on 09/11/2017.
 */

public interface ListKhoaHocTimHocVienPresenterImp {
    public void nhanDanhSachKhoaHoc(ArrayList<CustomModelKhoaHoc> listKhoaHoc);
    public void yeuCauDanhSachKhoaHoc(String linhVuc);
}
