package com.eways.elearning.Presenter.DangKy.DangNhap;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.Model.DangNhap.DangNhapImpModel;
import com.eways.elearning.Model.DangNhap.DangNhapModel;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapImpView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ADMIN on 11/5/2017.
 */

public class DangNhapPresenter implements DangNhapImpPresenter {

    DangNhapImpView dangNhapImpView;
    SharedPreferencesHandler sharedPreferencesHandler;
    DangNhapImpModel dangNhapImpModel=new DangNhapModel(this);
    public DangNhapPresenter(DangNhapImpView dangNhapImpView) {
        this.dangNhapImpView = dangNhapImpView;
    }

    @Override
    public void NhanThongTinDN(String email, String Password, Activity activity) {
        if (email.compareTo("")==0 && Password.compareTo("")==0){
            dangNhapImpView.NhanKetQuaDN(DangNhapFragment.ERROR_MSG_THIEU_EMAIL_PW);
            return;
        }
        if (email.compareTo("")==0){
            dangNhapImpView.NhanKetQuaDN(DangNhapFragment.ERROR_MSG_THIEU_EMAIL);
            return;
        }
        if (Password.compareTo("")==0){
            dangNhapImpView.NhanKetQuaDN(DangNhapFragment.ERROR_MSG_THIEU_PW);
            return;
        }
        if (!KiemTraDinhDangEmail(email)){
            dangNhapImpView.NhanKetQuaDN(DangNhapFragment.ERROR_MSG_SAI_EMAIL);
            return;
        }
        if (DemKyTu(Password)<6)
        {
            dangNhapImpView.NhanKetQuaDN(DangNhapFragment.ERROR_MSG_SAI_PW);
            return;
        }

        dangNhapImpModel.NhanTaiKhoanDN(new TaiKhoan(email,Password),activity);
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
    public void KetQuaDangNhap(String ketqua,FirebaseUser user,Activity activity) {
        if (ketqua==DangNhapFragment.LOGIN_SUCCESS){
            sharedPreferencesHandler=new SharedPreferencesHandler(activity, SupportKeysList.SHARED_PREF_FILE_NAME);
            sharedPreferencesHandler.DangNhapThanhCong(user.getUid(),user.getEmail(),null,null,user.getDisplayName(),true,SupportKeysList.TAI_KHOAN_THUONG);
            dangNhapImpView.NhanKetQuaDN(ketqua);
        }else
            dangNhapImpView.NhanKetQuaDN(ketqua);

    }
}
