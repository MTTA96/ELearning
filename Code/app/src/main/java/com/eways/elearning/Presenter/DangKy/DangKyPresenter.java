package com.eways.elearning.Presenter.DangKy;

import android.app.Activity;
import android.content.Context;

import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.Model.DangKy.DangKyImpModel;
import com.eways.elearning.Model.DangKy.DangKyModel;
import com.eways.elearning.View.Fragment.TaiKhoan.DangKy.DangKyFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.DangKy.DangKyImpView;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Quang Tri on 27/10/2017.
 */

public class DangKyPresenter implements DangKyImpPresenter {
    private DangKyFragment dangKyFragment=new DangKyFragment();
    private DangKyModel dangKyModel=new DangKyModel();

    @Override
    public void NhanThongTinDangKy(String Email, String Password, String CPassword,Activity activity) {
        if (Password.compareTo(CPassword)==0)
            dangKyModel.NhanTaiKhoan(new TaiKhoan(Email,Password),activity);
    }

    @Override
    public void KetQuaDangKy(String ketqua) {
        dangKyFragment.NhanKetQuaTuPresenter(ketqua);
    }
}
