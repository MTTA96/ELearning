package com.eways.elearning.Presenter.DangKy.DangNhap;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.Model.DangNhap.DangNhapImpModel;
import com.eways.elearning.Model.DangNhap.DangNhapModel;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapImpView;
import com.google.firebase.auth.FirebaseAuth;

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
        if (email.compareTo("")==0 && Password.compareTo("")==0)
            dangNhapImpView.NhanKetQuaDN("thatbai");
        else {
            dangNhapImpModel.NhanTaiKhoanDN(new TaiKhoan(email,Password),activity);
        }
    }

    @Override
    public void KetQuaDangNhap(String ketqua, FirebaseAuth mAuth) {
        String username=mAuth.getCurrentUser().getEmail();
        Fragment fragment=new Fragment();
        Bundle bundle=new Bundle();
        bundle.putString("UserLogin","username");
        fragment.setArguments(bundle);
        dangNhapImpView.NhanKetQuaDN(ketqua);
    }
}
