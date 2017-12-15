package com.eways.elearning.Presenter.TimKiemKhoaHoc;

import android.app.Activity;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;

import java.util.ArrayList;

/**
 * Created by yowin on 25/11/2017.
 */

public interface KetQuaTimKiemKhoaHocFragmentPresenterImp {

    public void guiYeuCauListKhoaHoc(KhoaHoc khoaHoc,boolean loai,String mon,Activity activity);
    public void nhanListKhoaHoc(ArrayList<CustomModelKhoaHoc> chinhxac,ArrayList<CustomModelKhoaHoc> ganChinhXac);
}
