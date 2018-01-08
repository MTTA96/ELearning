package com.eways.elearning.Presenter.TaiKhoan.ThemTaiLieuChuyenMon;

import android.app.Activity;

import com.eways.elearning.DataModel.LinhVuc.LinhVuc;
import com.eways.elearning.DataModel.TaiKhoan.TaiLieu.TaiLieuChuyenMon.TaiLieuChuyenMon;

import java.util.ArrayList;

/**
 * Created by ADMIN on 1/8/2018.
 */

public interface ThemTaiLieuChuyenMonPresenterImp {
    public void LoadLinhVucTaiLieuChuyenMon(Activity activity);
    public void KetQuaLinhVucTaiLieuChuyenMon(ArrayList<LinhVuc> listLinhVuc);
    public void NhanDataCapNhatTaiLieuChuyenMon(TaiLieuChuyenMon taiLieuChuyenMon,String idUser,Activity activity);
    public void KetQuaCapNhatTaiLieuChuyenMon(String ketQuaCapNhat);
}
