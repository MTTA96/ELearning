package com.eways.elearning.View.Fragment.KhoaHoc.TimKiemKhoaHoc;

import com.eways.elearning.Model.DataModel.KhoaHoc.CustomModelKhoaHoc;

import java.util.ArrayList;

/**
 * Created by yowin on 25/11/2017.
 */

public interface KetQuaTimKiemFragmentViewImp {
    public void nhanListKhoaHocGanChinhXac(ArrayList<CustomModelKhoaHoc> khoaHocsGanChinhXac);
    public void nhanListKhoaHocChinhXac(ArrayList<CustomModelKhoaHoc> khoaHocsChinhXac);
    public void nhanListKhoaHoc(ArrayList<CustomModelKhoaHoc> chinhxac,ArrayList<CustomModelKhoaHoc> ganchinhxac);
}
