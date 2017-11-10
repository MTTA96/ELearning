package com.eways.elearning.Model.ListKhoaHoc;

import com.eways.elearning.Presenter.DangKy.ListKhoaHoc.ListKhoaHocTimGiaSuImpPresenter;

import org.json.JSONArray;

/**
 * Created by yowin on 09/11/2017.
 */

public class ListKhoaHocTimGiaSuModel implements ListKhoaHocTimGiaSuImpModel {

    ListKhoaHocTimGiaSuImpPresenter listKhoaHocTimGiaSuImpPresenter;

    public ListKhoaHocTimGiaSuModel(ListKhoaHocTimGiaSuImpPresenter listKhoaHocTimGiaSuImpPresenter) {
        this.listKhoaHocTimGiaSuImpPresenter = listKhoaHocTimGiaSuImpPresenter;
    }

    @Override
    public JSONArray LayDanhSachKhoaHocTimGiaSu() {

        return null;
    }
}
