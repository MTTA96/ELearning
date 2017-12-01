package com.eways.elearning.Presenter.TimKiemKhoaHoc;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;

import java.util.ArrayList;

/**
 * Created by yowin on 25/11/2017.
 */

public interface KetQuaTimKiemKhoaHocFragmentPresenterImp {

    public void nhanListKhoaHocGanChinhXac(ArrayList<CustomModelKhoaHoc> khoaHocs);
    public void nhanListKhoaHocChinhXac(ArrayList<CustomModelKhoaHoc> khoaHocs);
    public void guiYeuCauListKhoaHoc(KhoaHoc khoaHoc,boolean loai);
    public void nhanListKhoaHoc(ArrayList<CustomModelKhoaHoc> chinhxac,ArrayList<CustomModelKhoaHoc> ganChinhXac);
}
