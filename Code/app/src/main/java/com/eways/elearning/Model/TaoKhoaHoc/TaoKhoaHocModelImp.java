package com.eways.elearning.Model.TaoKhoaHoc;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;

import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHocChuaHoanTat;
import com.eways.elearning.DataModel.LinhVuc.LinhVuc;

import java.util.ArrayList;

/**
 * Created by yowin on 19/11/2017.
 */

public interface TaoKhoaHocModelImp {
    public void postKhoaHoc(KhoaHoc khoaHoc, boolean loai, Activity activity);
    public void loaddataLinhvuc(Activity activity);
    public void loadDataKhuVuc(Activity activity);
}
