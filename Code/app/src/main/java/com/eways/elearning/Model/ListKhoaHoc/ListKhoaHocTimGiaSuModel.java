package com.eways.elearning.Model.ListKhoaHoc;

import com.eways.elearning.Presenter.ListKhoaHoc.ListKhoaHocTimGiaSuPresenter;

import org.json.JSONArray;

/**
 * Created by yowin on 09/11/2017.
 */

public class ListKhoaHocTimGiaSuModel implements ListKhoaHocTimGiaSuImpModel {

    ListKhoaHocTimGiaSuPresenter listKhoaHocTimGiaSuImpPresenter;

    public ListKhoaHocTimGiaSuModel(ListKhoaHocTimGiaSuPresenter listKhoaHocTimGiaSuImpPresenter) {
        this.listKhoaHocTimGiaSuImpPresenter = listKhoaHocTimGiaSuImpPresenter;
    }

    @Override
    public JSONArray LayDanhSachKhoaHocTimGiaSu() {

        return null;
    }
}
