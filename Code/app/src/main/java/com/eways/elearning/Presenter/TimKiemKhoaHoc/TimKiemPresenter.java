package com.eways.elearning.Presenter.TimKiemKhoaHoc;

import android.app.Activity;

import com.eways.elearning.Data.DataModel.Other.KhuVuc;
import com.eways.elearning.Data.DataModel.LinhVuc.LinhVuc;
import com.eways.elearning.Data.Model.KhoaHoc.TimKiemKhoaHoc.TimKiemModel;
import com.eways.elearning.Data.Model.KhoaHoc.TimKiemKhoaHoc.TimKiemModelImp;
import com.eways.elearning.View.Fragment.KhoaHoc.TimKiemKhoaHoc.TimKiemFragment;
import com.eways.elearning.View.Fragment.KhoaHoc.TimKiemKhoaHoc.TimKiemViewImp;

import java.util.ArrayList;

/**
 * Created by yowin on 08/12/2017.
 */

public class TimKiemPresenter implements TimKiemPresenterImp {

    TimKiemModelImp timKiemModelImp=new TimKiemModel(this);
    TimKiemViewImp timKiemViewImp=new TimKiemFragment();

    public TimKiemPresenter(TimKiemViewImp timKiemViewImp) {
        this.timKiemViewImp = timKiemViewImp;
    }

    @Override
    public void loaddataLinhvuc(Activity activity) {
        timKiemModelImp.loaddataLinhvuc(activity);
    }

    @Override
    public void loaddataKhuVuc(Activity activity) {
       timKiemModelImp.loadDataKhuVuc(activity);
    }

    @Override
    public void nhanDanhSachLinhVuc(ArrayList<LinhVuc> danhSachLinhVuc) {
        timKiemViewImp.NhanDanhSachLinhVuc(danhSachLinhVuc);
    }

    @Override
    public void nhanDanhSachKhuVuc(ArrayList<KhuVuc> danhSachKhuVuc) {
        timKiemViewImp.NhanDanhSachKhuVuc(danhSachKhuVuc);
    }
}
