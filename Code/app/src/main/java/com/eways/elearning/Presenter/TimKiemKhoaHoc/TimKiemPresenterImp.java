package com.eways.elearning.Presenter.TimKiemKhoaHoc;

import android.app.Activity;

import com.eways.elearning.DataModel.KhuVuc;
import com.eways.elearning.DataModel.LinhVuc.LinhVuc;

import java.util.ArrayList;

/**
 * Created by yowin on 08/12/2017.
 */

public interface TimKiemPresenterImp {
    public void loaddataLinhvuc(Activity activity);
    public void loaddataKhuVuc(Activity activity);
    public void nhanDanhSachLinhVuc(ArrayList<LinhVuc> danhSachLinhVuc);
    public void nhanDanhSachKhuVuc(ArrayList<KhuVuc> danhSachKhuVuc);
}