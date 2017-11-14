package com.eways.elearning.Presenter.ListKhoaHoc;

import com.eways.elearning.DataModel.KhoaHoc.KhoaHocChuaHoanTat;
import com.eways.elearning.Model.ListKhoaHoc.ListKhoaHocTimHocVienImpModel;
import com.eways.elearning.Model.ListKhoaHoc.ListKhoaHocTimHocVienModel;
import com.eways.elearning.View.Fragment.ListKhoaHoc.ListKhoaHocTimHocVienImpView;

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
    public void nhanDanhSachKhoaHoc(ArrayList<KhoaHocChuaHoanTat> listKhoaHocChuaHoanTat) {
        listKhoaHocTimHocVienImpView.nhanDanhSach(listKhoaHocChuaHoanTat);
    }

    @Override
    public void yeuCauDanhSachKhoaHoc() {
        listKhoaHocTimHocVienImpModel.getDanhSachKhoaHocTimHocVien();
    }
}
