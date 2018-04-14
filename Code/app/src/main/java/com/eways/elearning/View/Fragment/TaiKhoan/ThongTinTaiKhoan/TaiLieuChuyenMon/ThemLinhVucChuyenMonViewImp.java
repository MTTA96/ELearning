package com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan.TaiLieuChuyenMon;

import com.eways.elearning.Model.DataModel.LinhVuc.LinhVuc;

import java.util.ArrayList;

/**
 * Created by ADMIN on 1/8/2018.
 */

public interface ThemLinhVucChuyenMonViewImp {
    public void DataLinhVuc(ArrayList<LinhVuc> listLinhVuc);
    public void KetQuaThemTaiLieuChuyenMon(String ketQuaCapNhat);
}
