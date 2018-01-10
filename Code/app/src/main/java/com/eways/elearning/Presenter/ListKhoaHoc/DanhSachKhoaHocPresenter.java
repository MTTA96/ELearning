package com.eways.elearning.Presenter.ListKhoaHoc;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.Model.KhoaHoc.ListKhoaHoc.DanhSachKhoaHocModel;
import com.eways.elearning.Model.KhoaHoc.ListKhoaHoc.DanhSachKhoaHocModelImp;
import com.eways.elearning.View.Fragment.KhoaHoc.ListKhoaHoc.DanhSachKhoaHocViewImp;

import java.util.ArrayList;

/**
 * Created by yowin on 10/01/2018.
 */

public class DanhSachKhoaHocPresenter implements DanhSachKhoaHocPresenterImp{
    DanhSachKhoaHocModelImp danhSachKhoaHocModelImp = new DanhSachKhoaHocModel(this);
    DanhSachKhoaHocViewImp danhSachKhoaHocViewImp;

    public DanhSachKhoaHocPresenter(DanhSachKhoaHocViewImp danhSachKhoaHocViewImp) {
        this.danhSachKhoaHocViewImp = danhSachKhoaHocViewImp;
    }

    @Override
    public void guiYeuCauDanhSachKhoaHoc(String linhVuc, boolean loai) {
        danhSachKhoaHocModelImp.getDanhSachKhoaHoc(linhVuc, loai);
    }

    @Override
    public void nhanDanhSachKhoaHoc(ArrayList<CustomModelKhoaHoc> danhSachKhoaHoc) {
        danhSachKhoaHocViewImp.nhanDanhSachKhoaHoc(danhSachKhoaHoc);
    }
}
