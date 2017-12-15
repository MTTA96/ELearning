package com.eways.elearning.Presenter.TimKiemKhoaHoc;

import android.app.Activity;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.Model.KhoaHoc.TimKiemKhoaHoc.KetQuaTimKiemFragmentModel;
import com.eways.elearning.Model.KhoaHoc.TimKiemKhoaHoc.KetQuatimKiemFragmentModelImp;
import com.eways.elearning.View.Fragment.KhoaHoc.TimKiemKhoaHoc.KetQuaTimKiemFragmentViewImp;

import java.util.ArrayList;

/**
 * Created by yowin on 25/11/2017.
 */

public class KetQuaTimKiemKhoaHocFragmentPresenter implements KetQuaTimKiemKhoaHocFragmentPresenterImp {

    KetQuatimKiemFragmentModelImp ketQuatimKiemFragmentModelImp = new KetQuaTimKiemFragmentModel(this);
    KetQuaTimKiemFragmentViewImp ketQuaTimKiemFragmentViewImp;

    public KetQuaTimKiemKhoaHocFragmentPresenter(KetQuaTimKiemFragmentViewImp ketQuaTimKiemFragmentViewImp) {
        this.ketQuaTimKiemFragmentViewImp = ketQuaTimKiemFragmentViewImp;
    }



    @Override
    public void guiYeuCauListKhoaHoc(KhoaHoc khoaHoc, boolean loai, String mon,Activity activity) {
        ketQuatimKiemFragmentModelImp.getListKhoaHoc(khoaHoc,loai,mon,activity);
    }

    @Override
    public void nhanListKhoaHoc(ArrayList<CustomModelKhoaHoc> chinhxac, ArrayList<CustomModelKhoaHoc> ganChinhXac) {
        ketQuaTimKiemFragmentViewImp.nhanListKhoaHoc(chinhxac,ganChinhXac);
    }


}
