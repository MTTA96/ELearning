package com.eways.elearning.Presenter.DangKy;

import android.app.Activity;
import android.content.Context;

import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.Model.DangKy.DangKyImpModel;
import com.eways.elearning.Model.DangKy.DangKyModel;
import com.eways.elearning.Model.FragmentHandler;
import com.eways.elearning.View.Fragment.TaiKhoan.DangKy.DangKyFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.DangKy.DangKyImpView;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapFragment;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Quang Tri on 27/10/2017.
 */

public class DangKyPresenter implements DangKyImpPresenter {
    DangKyImpModel dangKyImpModel=new DangKyModel(this);
    DangKyImpView dangKyImpView;

    public DangKyPresenter(DangKyImpView dangKyImpView) {
        this.dangKyImpView = dangKyImpView;
    }

    @Override
    public void NhanThongTinDangKy(String Email, String Password, String CPassword, Activity activity) {
        if (Email.isEmpty() && !Password.isEmpty() && !CPassword.isEmpty()){
            dangKyImpView.NhanKetQuaTuPresenter("emtyEmail");
            return;
        }
        if (Password.isEmpty() && !Email.isEmpty() && !CPassword.isEmpty()){
            dangKyImpView.NhanKetQuaTuPresenter("emtyPassword");
            return;
        }
        if (CPassword.isEmpty() && !Email.isEmpty() && !Password.isEmpty()) {
            dangKyImpView.NhanKetQuaTuPresenter("emtyCPassword");
            return;
        }
        if (Email.isEmpty() && Password.isEmpty() && !CPassword.isEmpty()){
            dangKyImpView.NhanKetQuaTuPresenter("emtyEmailPassword");
            return;
        }
        if (Email.isEmpty() && !Password.isEmpty() && CPassword.isEmpty()){
            dangKyImpView.NhanKetQuaTuPresenter("emtyEmailCPassword");
            return;
        }
        if (!Email.isEmpty() && Password.isEmpty() && CPassword.isEmpty()){
            dangKyImpView.NhanKetQuaTuPresenter("emtyPasswordCPassword");
            return;
        }
        if (Email.isEmpty() && Password.isEmpty() && CPassword.isEmpty()){
            dangKyImpView.NhanKetQuaTuPresenter("emtyEmailPasswordCPassword");
            return;
        }
        if (KiemTraDinhDangEmail(Email)==false)
            dangKyImpView.NhanKetQuaTuPresenter("khongdungdinhdangEmail");
        if (DemKyTu(Password)<6){
            dangKyImpView.NhanKetQuaTuPresenter("pass<6");
        }
        if (Password.compareTo(CPassword)!=0){
            dangKyImpView.NhanKetQuaTuPresenter("Pass#Cpass");
            return;
        }
        dangKyImpModel.NhanTaiKhoan(new TaiKhoan(Email,Password),activity);
    }
    //Đếm số chữ trong chuỗi
    public static int DemKyTu(String s){
        int count=0;
        for (int i=0;i<s.length();i++){
            count+=1;
        }
        return count;
    }
    //Kiểm tra Email
    public static boolean KiemTraDinhDangEmail(String email){
        String[] mangemail;
        mangemail=email.split("@");
        if (mangemail.length<=1)
            return false;
        return true;
    }
    @Override
    public void KetQuaDangKy(String ketqua) {
            dangKyImpView.NhanKetQuaTuPresenter(ketqua);

    }
}
