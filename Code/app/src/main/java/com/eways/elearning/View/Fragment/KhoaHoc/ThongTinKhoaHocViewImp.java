package com.eways.elearning.View.Fragment.KhoaHoc;


import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.ThongTinChiTietKhoaHoc;


/**
 * Created by ADMIN on 12/12/2017.
 */

public interface ThongTinKhoaHocViewImp {
    public void KetQuaThongTinKhoaHoc(ThongTinChiTietKhoaHoc thongTinChiTietKhoaHoc);
    public void KetQuaGuiYeuCau(String ketQuaYC, KhoaHoc khoaHoc);
}
