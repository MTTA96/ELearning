package com.eways.elearning.Presenter.ListKhoaHoc;

import com.eways.elearning.Data.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.Data.Model.KhoaHoc.ListKhoaHoc.ListKhoaHocTimHocVienImpModel;
import com.eways.elearning.Data.Model.KhoaHoc.ListKhoaHoc.ListKhoaHocTimHocVienModel;
import com.eways.elearning.View.Fragment.KhoaHoc.ListKhoaHoc.ListKhoaHocTimHocVienImpView;

import java.util.ArrayList;

/**
 * Created by yowin on 09/11/2017.
 */

public class ListKhoaHocTimHocVienPresenter implements ListKhoaHocTimHocVienPresenterImp{

    ListKhoaHocTimHocVienImpModel listKhoaHocTimHocVienImpModel = new ListKhoaHocTimHocVienModel(this); //
    ListKhoaHocTimHocVienImpView listKhoaHocTimHocVienImpView;

    public ListKhoaHocTimHocVienPresenter(ListKhoaHocTimHocVienImpView listKhoaHocTimHocVienImpView) {
        this.listKhoaHocTimHocVienImpView = listKhoaHocTimHocVienImpView;
    }

    @Override
    public void nhanDanhSachKhoaHoc(ArrayList<CustomModelKhoaHoc> listKhoaHoc) {
        listKhoaHocTimHocVienImpView.nhanDanhSach(listKhoaHoc);
    }

    @Override
    public void yeuCauDanhSachKhoaHoc(String linhVuc) {
        listKhoaHocTimHocVienImpModel.getDanhSachKhoaHocTimHocVien(linhVuc);
    }
}
