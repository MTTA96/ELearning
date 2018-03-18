package com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan;

import android.app.Activity;
import android.widget.ImageView;

import com.eways.elearning.Data.DataModel.TaiKhoan.TaiKhoan;

/**
 * Created by ADMIN on 11/19/2017.
 */

public interface CapNhatTaiKhoanPresenterImp {
    public void NhanDataUpdate (TaiKhoan taiKhoan, Activity activity, ImageView ivTaiLieuXacMinh_MT,ImageView ivTaiLieuXacMinh_MS,ImageView ivAvarta);
    public void KetQuaCapNhat(String ketquacapnhat,TaiKhoan taiKhoan,Activity activity);
    public void NhanDataCapNhatTaiKhoanGiaSu(String idUser,Activity activity);
    public void KetQuaCapNhatTaiKhoanGiaSu(String ketQua, Activity activity);
}
