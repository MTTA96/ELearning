package com.eways.elearning.Presenter.ListKhoaHoc;

import com.eways.elearning.Model.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.Model.KhoaHoc.ListKhoaHoc.ListKhoaHocTimGiaSuImpModel;
import com.eways.elearning.Model.KhoaHoc.ListKhoaHoc.ListKhoaHocTimGiaSuModel;
import com.eways.elearning.View.Fragment.KhoaHoc.ListKhoaHoc.ListKhoaHocTimGiaSuImpView;

import java.util.ArrayList;

/**
 * Created by yowin on 09/11/2017.
 */

public class ListKhoaHocTimGiaSuPresenter implements ListKhoaHocTimGiaSuPresenterImp{

    ListKhoaHocTimGiaSuImpModel listKhoaHocTimGiaSuImpModel = new ListKhoaHocTimGiaSuModel(this); //
    ListKhoaHocTimGiaSuImpView listKhoaHocTimGiaSuImpView;

    public ListKhoaHocTimGiaSuPresenter(ListKhoaHocTimGiaSuImpView listKhoaHocTimGiaSuImpView) {
        this.listKhoaHocTimGiaSuImpView = listKhoaHocTimGiaSuImpView;
    }

    @Override
    public void nhanDanhSachKhoaHoc(ArrayList<CustomModelKhoaHoc> listKhoaHoc) {
        listKhoaHocTimGiaSuImpView.nhanDanhSach(listKhoaHoc);
    }

    @Override
    public void yeuCauDanhSachKhoaHoc(String linhVuc) {
        listKhoaHocTimGiaSuImpModel.getDanhSachKhoaHocTimGiaSu(linhVuc);
    }
}
