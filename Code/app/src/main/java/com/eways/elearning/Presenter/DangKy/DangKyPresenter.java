package com.eways.elearning.Presenter.DangKy;

import android.app.Activity;

import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.Model.DangKy.DangKyImpModel;
import com.eways.elearning.Model.DangKy.DangKyModel;
import com.eways.elearning.View.Fragment.TaiKhoan.DangKy.DangKyImpView;

/**
 * Created by Quang Tri on 27/10/2017.
 */

public class DangKyPresenter implements DangKyImpPresenter {
    Activity activity;
    private DangKyModel dangKyModel=new DangKyModel();
    private DangKyImpView dangKyImpView;

    public DangKyPresenter(Activity activity) {
        this.activity = activity;
    }


    @Override
    public void NhanThongTinDangKy(String Email, String Password, String CPassword) {
        if (Password.compareTo(CPassword)==0)
            dangKyModel.NhanTaiKhoan(new TaiKhoan(Email,Password));
    }

    @Override
    public void KetQuaDangKy(String ketqua) {
        dangKyImpView.NhanKetQuaTuPresenter(ketqua);
    }
}
