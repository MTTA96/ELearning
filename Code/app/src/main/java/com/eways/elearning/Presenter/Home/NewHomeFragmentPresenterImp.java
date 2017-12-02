package com.eways.elearning.Presenter.Home;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;

import java.util.ArrayList;

/**
 * Created by yowin on 02/12/2017.
 */

public interface NewHomeFragmentPresenterImp {
    public void guiYeuCau(boolean loai,String linhVuc1,String linhVuc2,String linhVuc3);
    public void getListKhoaHoc(ArrayList<CustomModelKhoaHoc> khoaHocs1,ArrayList<CustomModelKhoaHoc> khoaHocs2,ArrayList<CustomModelKhoaHoc> khoaHocs3);
}
