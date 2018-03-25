package com.eways.elearning.View.Fragment.TaiKhoan.KhoaHocCuaToi;

import com.eways.elearning.Model.DataModel.KhoaHoc.CustomModelKhoaHoc;

import java.util.ArrayList;

/**
 * Created by ADMIN on 1/10/2018.
 */

public interface KhoaHocCuaToiViewImp {
    void sendData(ArrayList<CustomModelKhoaHoc> danhSachKhoaHocThamGiaDangCho, ArrayList<CustomModelKhoaHoc> danhSachKhoaHocDaTao, ArrayList<CustomModelKhoaHoc> danhSachKhoaHocThamGiaDaDuyet);
}
