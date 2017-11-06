package com.eways.elearning.Presenter.DangKy.DangNhap;

import android.app.Activity;

import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.Model.DangNhap.DangNhapImpModel;
import com.eways.elearning.Model.DangNhap.DangNhapModel;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapImpView;

/**
 * Created by ADMIN on 11/5/2017.
 */

public class DangNhapPresenter implements DangNhapImpPresenter {
    DangNhapImpView dangNhapImpView;
    DangNhapImpModel dangNhapImpModel=new DangNhapModel(this);
    public DangNhapPresenter(DangNhapImpView dangNhapImpView) {
        this.dangNhapImpView = dangNhapImpView;
    }

    @Override
    public void NhanThongTinDN(String email, String Password, Activity activity) {
        if (email.isEmpty() && Password.isEmpty())
            dangNhapImpView.NhanKetQuaDN("thatbai");
        else {
            dangNhapImpModel.NhanTaiKhoanDN(new TaiKhoan(email,Password),activity);
        }
    }

    @Override
    public void KetQuaDangNhap(String ketqua) {
        dangNhapImpView.NhanKetQuaDN(ketqua);
    }
}
