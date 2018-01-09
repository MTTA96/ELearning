package com.eways.elearning.Presenter.TimKiemKhoaHoc;

import android.app.Activity;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.DataModel.TaiKhoan.TaiKhoan;

import java.util.ArrayList;

/**
 * Created by yowin on 25/11/2017.
 */

public interface KetQuaTimKiemKhoaHocFragmentPresenterImp {

    //Tìm học viên(Khóa học tìm gia sư) hoặc tìm khóa học tìm gia sư theo môn
    public void guiYeuCauListKhoaHoc(KhoaHoc khoaHoc,boolean loai,String mon,Activity activity);
    public void nhanListKhoaHoc(ArrayList<CustomModelKhoaHoc> chinhxac, ArrayList<CustomModelKhoaHoc> ganChinhXac);

    //Tìm gia su (Khóa học tìm học viên và tìm người dùng là gia sư)
    public void guiYeuCauTimKhoaHocGiaSu(KhoaHoc khoaHoc,Activity activity);
    public void guiYeuCauTimGiaSu(KhoaHoc khoaHoc,Activity activity);
    public void nhanListKeyGiaSu(ArrayList<String> listKeyGiaSu);
    public void nhanListGiaSu(ArrayList<TaiKhoan> listGiaSu);
    public void nhanListKhoaHocGiaSu(ArrayList<CustomModelKhoaHoc> chinhxac, ArrayList<CustomModelKhoaHoc> ganChinhXac);


}
