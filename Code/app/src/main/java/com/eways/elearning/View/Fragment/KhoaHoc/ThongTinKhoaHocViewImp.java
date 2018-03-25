package com.eways.elearning.View.Fragment.KhoaHoc;


import com.eways.elearning.Model.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.Model.DataModel.KhoaHoc.ThongTinChiTietKhoaHoc;


/**
 * Created by ADMIN on 12/12/2017.
 */

public interface ThongTinKhoaHocViewImp {
    public void KetQuaThongTinKhoaHoc(ThongTinChiTietKhoaHoc thongTinChiTietKhoaHoc);
    public void KetQuaGuiYeuCau(String ketQuaYC, KhoaHoc khoaHoc);
    public void KetQuaHuyYeuCau(String ketQuaHYC,KhoaHoc khoaHoc);
}
