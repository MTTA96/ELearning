package com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan;

import android.accounts.Account;
import android.app.Activity;

import com.eways.elearning.DataModel.TaiKhoan.TaiLieu.TaiLieuChuyenMon.TaiLieuChuyenMon;

import java.util.ArrayList;

/**
 * Created by ADMIN on 1/8/2018.
 */

public interface CapNhatTaiLieuChuyenMonPresenterImp {
    public void YeuCauDataTaiLieuChuyenMon(String idUser, Activity activity);
    public void KetQuaDataTaiLieuChuyenMon(ArrayList<TaiLieuChuyenMon> danhSachTaiLieuChuyenMon);
}
