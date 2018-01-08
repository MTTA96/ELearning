package com.eways.elearning.Presenter.TaiKhoan.ThemTaiLieuChuyenMon;

import android.app.Activity;

import com.eways.elearning.DataModel.LinhVuc.LinhVuc;
import com.eways.elearning.Model.LinhVuc.LinhVucModelImp;
import com.eways.elearning.Model.TaiKhoan.ThemTaiLieuChuyenMon.ThemTaiLieuChuyenMonModel;
import com.eways.elearning.Model.TaiKhoan.ThemTaiLieuChuyenMon.ThemTaiLieuChuyenMonModelImp;
import com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan.ThemLinhVucChuyenMonFragment;

import java.util.ArrayList;

/**
 * Created by ADMIN on 1/8/2018.
 */

public class ThemTaiLieuChuyenMonPresenter implements ThemTaiLieuChuyenMonPresenterImp {
    ThemTaiLieuChuyenMonModelImp themTaiLieuChuyenMonModelImp=new ThemTaiLieuChuyenMonModel(this);
    ThemLinhVucChuyenMonFragment themLinhVucChuyenMonFragment;

    public ThemTaiLieuChuyenMonPresenter(ThemLinhVucChuyenMonFragment themLinhVucChuyenMonFragment) {
        this.themLinhVucChuyenMonFragment = themLinhVucChuyenMonFragment;
    }

    @Override
    public void LoadLinhVucTaiLieuChuyenMon(Activity activity) {
        themTaiLieuChuyenMonModelImp.LoadLinhVucTaiLieuChuyenMon(activity);
    }

    @Override
    public void KetQuaLinhVucTaiLieuChuyenMon(ArrayList<LinhVuc> listLinhVuc) {
        themLinhVucChuyenMonFragment.DataLinhVuc(listLinhVuc);
    }
}
