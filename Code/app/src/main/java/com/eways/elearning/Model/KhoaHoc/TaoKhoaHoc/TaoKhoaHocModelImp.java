package com.eways.elearning.Model.KhoaHoc.TaoKhoaHoc;

import android.app.Activity;

import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;

/**
 * Created by yowin on 19/11/2017.
 */

public interface TaoKhoaHocModelImp {
    public void postKhoaHoc(KhoaHoc khoaHoc, boolean loai, Activity activity);
    public void loaddataLinhvuc(Activity activity);
    public void loadDataKhuVuc(Activity activity);
}
