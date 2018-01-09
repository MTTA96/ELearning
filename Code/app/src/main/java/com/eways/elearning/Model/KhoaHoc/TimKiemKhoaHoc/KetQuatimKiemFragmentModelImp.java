package com.eways.elearning.Model.KhoaHoc.TimKiemKhoaHoc;

import android.app.Activity;

import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;

import java.util.ArrayList;

/**
 * Created by yowin on 25/11/2017.
 */

public interface KetQuatimKiemFragmentModelImp {
    public void getListKhoaHoc(KhoaHoc khoaHoc,boolean loai,String mon,Activity activity);
    public void getListGiaSu(KhoaHoc khoaHoc,Activity activity);
    public void getListKhoaHocGiaSu(KhoaHoc khoaHoc,Activity activity);
    public void nhanListKeyGiaSu(ArrayList<String> listKeyGiaSu);
}
