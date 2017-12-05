package com.eways.elearning.View.Fragment.KhoaHoc.TaoKhoaHoc;

import com.eways.elearning.DataModel.KhuVuc;
import com.eways.elearning.DataModel.LinhVuc.LinhVuc;

import java.util.ArrayList;

/**
 * Created by ADMIN on 11/23/2017.
 */

public interface TaoKhoaHocViewImp {
    public void KetQuaTaoKhoaHoc(String result);
    public void NhanDanhSachLinhVuc(ArrayList<LinhVuc> danhSachLinhVuc);
    public void NhanDanhSachKhuVuc(ArrayList<KhuVuc> danhSachKhuVuc);
}
