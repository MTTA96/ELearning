package com.eways.elearning.Presenter.TaiKhoan.KhoaHocCuaToi;

import android.app.Activity;

import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.Model.KhoaHocCuaToi.KhoaHocCuaToiModel;
import com.eways.elearning.Model.KhoaHocCuaToi.KhoaHocCuaToiModelImp;
import com.eways.elearning.View.Fragment.TaiKhoan.KhoaHocCuaToi.KhoaHocChoDuyetViewImp;

import java.util.ArrayList;

/**
 * Created by ADMIN on 1/9/2018.
 */

public class KhoaHocCuaToiPresenter implements KhoaHocCuaToiPresenterImp {
    KhoaHocCuaToiModelImp khoaHocCuaToiModelImp=new KhoaHocCuaToiModel(this);
    KhoaHocChoDuyetViewImp khoaHocChoDuyetViewImp;

    public KhoaHocCuaToiPresenter(KhoaHocChoDuyetViewImp khoaHocChoDuyetViewImp) {
        this.khoaHocChoDuyetViewImp = khoaHocChoDuyetViewImp;
    }

    @Override
    public void YeuCauDataKhoaHocDaDangKy(String idUser, Activity activity) {
        khoaHocCuaToiModelImp.NhanYeuCauLayDataDanhSachKhoaHocDangKy(idUser,activity);
    }

    @Override
    public void NhanDataKhoaHocDaDangKy(ArrayList<KhoaHoc> khoaHoc) {

    }
}
