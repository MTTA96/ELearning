package com.eways.elearning.Data.Model.KhoaHoc.TaoKhoaHoc;

import android.app.Activity;

import com.eways.elearning.Data.DataModel.KhoaHoc.KhoaHoc;

/**
 * Created by yowin on 19/11/2017.
 */

public interface TaoKhoaHocModelImp {
    public void postKhoaHoc(KhoaHoc khoaHoc, boolean loai, Activity activity);
    public void loaddataLinhvuc(Activity activity);
    public void loadDataKhuVuc(Activity activity);
}
