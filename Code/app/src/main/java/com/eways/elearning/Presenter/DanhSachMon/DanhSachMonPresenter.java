package com.eways.elearning.Presenter.DanhSachMon;

import com.eways.elearning.Model.DanhSachMon.DanhSachMonModel;
import com.eways.elearning.Model.DanhSachMon.DanhSachMonModelImp;
import com.eways.elearning.View.Activity.MainActivityImp;

import java.util.ArrayList;

/**
 * Created by yowin on 14/12/2017.
 */

public class DanhSachMonPresenter implements DanhSachMonPresenterImp {
    DanhSachMonModelImp danhSachMonModelImp = new DanhSachMonModel(this);
    MainActivityImp mainActivityImp;

    public DanhSachMonPresenter(MainActivityImp mainActivityImp) {
        this.mainActivityImp = mainActivityImp;
    }

    @Override
    public void guiYeuCau() {
        danhSachMonModelImp.GetDanhSachMon();
    }

    @Override
    public void NhanDanhSachKhoaHoc(ArrayList<String> dsMon) {
        mainActivityImp.NhanDanhSachMon(dsMon);
    }
}
