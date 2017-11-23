package com.eways.elearning.Presenter.TaoKhoaHoc;

import android.app.Activity;

import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHocChuaHoanTat;
import com.eways.elearning.Model.TaoKhoaHoc.TaoKhoaHocModel;
import com.eways.elearning.Model.TaoKhoaHoc.TaoKhoaHocModelImp;
import com.eways.elearning.View.Fragment.KhoaHoc.TaoKhoaHoc.TaoKhoaHocFragment;
import com.eways.elearning.View.Fragment.KhoaHoc.TaoKhoaHoc.TaoKhoaHocViewImp;

/**
 * Created by yowin on 19/11/2017.
 */

public class TaoKhoaHocPresenter implements TaoKhoaHocPresenterImp {
    TaoKhoaHocModelImp taoKhoaHocModelImp=new TaoKhoaHocModel(this);
    TaoKhoaHocViewImp taoKhoaHocViewImp=new TaoKhoaHocFragment();

    public TaoKhoaHocPresenter(TaoKhoaHocViewImp taoKhoaHocViewImp) {
        this.taoKhoaHocViewImp = taoKhoaHocViewImp;
    }

    @Override
    public void nhanThongTinKhoaHoc(KhoaHoc khoaHoc, Activity activity) {
        taoKhoaHocModelImp.postKhoaHoc(khoaHoc,activity);
    }

    @Override
    public void ketQua(String ketQua) {

    }
}
