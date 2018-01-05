package com.eways.elearning.View.Fragment.KhoaHoc.TimKiemKhoaHoc;

import com.eways.elearning.DataModel.Other.KhuVuc;
import com.eways.elearning.DataModel.LinhVuc.LinhVuc;

import java.util.ArrayList;

/**
 * Created by yowin on 08/12/2017.
 */

public interface TimKiemViewImp {
    public void NhanDanhSachLinhVuc(ArrayList<LinhVuc> danhSachLinhVuc);
    public void NhanDanhSachKhuVuc(ArrayList<KhuVuc> danhSachKhuVuc);
}
