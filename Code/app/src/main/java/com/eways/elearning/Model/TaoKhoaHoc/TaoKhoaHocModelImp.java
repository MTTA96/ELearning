package com.eways.elearning.Model.TaoKhoaHoc;

import android.app.Activity;

import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHocChuaHoanTat;

/**
 * Created by yowin on 19/11/2017.
 */

public interface TaoKhoaHocModelImp {
    public void postKhoaHoc(KhoaHoc khoaHoc, boolean loai, Activity activity);
}
