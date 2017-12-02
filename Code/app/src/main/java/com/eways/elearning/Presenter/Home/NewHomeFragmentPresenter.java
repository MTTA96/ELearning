package com.eways.elearning.Presenter.Home;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.Model.Home.NewHomeFragmentModel;
import com.eways.elearning.Model.Home.NewHomeFragmentModelImp;
import com.eways.elearning.View.Fragment.Home.NewHomeFragmentImp;

import java.util.ArrayList;

/**
 * Created by yowin on 02/12/2017.
 */

public class NewHomeFragmentPresenter implements NewHomeFragmentPresenterImp {

    NewHomeFragmentModelImp newHomeFragmentModelImp = new NewHomeFragmentModel(this);
    NewHomeFragmentImp newHomeFragmentImp;

    public NewHomeFragmentPresenter(NewHomeFragmentImp newHomeFragmentImp) {
        this.newHomeFragmentImp = newHomeFragmentImp;
    }

    @Override
    public void guiYeuCau(boolean loai,String linhVuc1,String linhVuc2,String linhVuc3) {
        newHomeFragmentModelImp.getListkhoaHoc(loai,linhVuc1,linhVuc2,linhVuc3);
    }

    @Override
    public void getListKhoaHoc(ArrayList<CustomModelKhoaHoc> khoaHocs1,ArrayList<CustomModelKhoaHoc> khoaHocs2,ArrayList<CustomModelKhoaHoc> khoaHocs3) {
        newHomeFragmentImp.nhanListKhoaHoc(khoaHocs1,khoaHocs2,khoaHocs3);
    }
}
