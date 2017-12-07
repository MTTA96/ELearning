package com.eways.elearning.Presenter.TaoKhoaHoc;

import android.app.Activity;
import android.widget.ArrayAdapter;

import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHocChuaHoanTat;
import com.eways.elearning.DataModel.KhuVuc;
import com.eways.elearning.DataModel.LinhVuc.LinhVuc;

import java.util.ArrayList;

/**
 * Created by yowin on 19/11/2017.
 */

public interface TaoKhoaHocPresenterImp {
    public void nhanThongTinKhoaHoc(KhoaHoc khoaHoc, boolean checked, Activity activity);
    public void ketQua(String ketQua);
    public void loaddataLinhvuc(Activity activity);
    public void loaddataKhuVuc(Activity activity);
    public void nhanDanhSachLinhVuc(ArrayList<LinhVuc> danhSachLinhVuc);
    public void nhanDanhSachKhuVuc(ArrayList<KhuVuc> danhSachKhuVuc);
}
