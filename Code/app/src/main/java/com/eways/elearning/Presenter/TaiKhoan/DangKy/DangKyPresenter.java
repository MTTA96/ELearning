package com.eways.elearning.Presenter.TaiKhoan.DangKy;

import android.app.Activity;

import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.Model.TaiKhoan.DangKy.DangKyImpModel;
import com.eways.elearning.Model.TaiKhoan.DangKy.DangKyModel;
import com.eways.elearning.View.Fragment.TaiKhoan.DangKy.DangKyFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.DangKy.DangKyViewImp;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapFragment;

/**
 * Created by Quang Tri on 27/10/2017.
 */

public class DangKyPresenter implements DangKyPresenterImp {
    DangKyImpModel dangKyImpModel=new DangKyModel(this);
    DangKyViewImp dangKyImpView;

    public DangKyPresenter(DangKyViewImp dangKyImpView) {
        this.dangKyImpView = dangKyImpView;
    }

    @Override
    public void NhanThongTinDangKy(String Email, String Password, String CPassword,String Hoten, Activity activity) {
        if (Email.isEmpty() && !Password.isEmpty() && !CPassword.isEmpty() && !Hoten.isEmpty()){
            dangKyImpView.NhanKetQuaTuPresenter(DangKyFragment.ERROR_MSG_THIEU_EMAIL);
            return;
        }
        if (Password.isEmpty() && !Email.isEmpty() && !CPassword.isEmpty() && !Hoten.isEmpty()){
            dangKyImpView.NhanKetQuaTuPresenter(DangKyFragment.ERROR_MSG_THIEU_PW);
            return;
        }
        if (CPassword.isEmpty() && !Email.isEmpty() && !Password.isEmpty() && !Hoten.isEmpty()) {
            dangKyImpView.NhanKetQuaTuPresenter(DangKyFragment.ERROR_MSG_THIEU_CPW);
            return;
        }
        if (Email.isEmpty() && Password.isEmpty() && !CPassword.isEmpty() && !Hoten.isEmpty()){
            dangKyImpView.NhanKetQuaTuPresenter(DangKyFragment.ERROR_MSG_THIEU_EMAIL_PW);
            return;
        }
        if (Email.isEmpty() && !Password.isEmpty() && CPassword.isEmpty() && !Hoten.isEmpty()){
            dangKyImpView.NhanKetQuaTuPresenter(DangKyFragment.ERROR_MSG_THIEU_EMAIL_CPW);
            return;
        }
        if (!Email.isEmpty() && Password.isEmpty() && CPassword.isEmpty() && !Hoten.isEmpty()){
            dangKyImpView.NhanKetQuaTuPresenter(DangKyFragment.ERROR_MSG_THIEU_PW_CPW);
            return;
        }
        if (Email.isEmpty() && Password.isEmpty() && CPassword.isEmpty() && Hoten.isEmpty() ){
            dangKyImpView.NhanKetQuaTuPresenter(DangKyFragment.ERROR_MSG_THIEU_EMAIL_HOTEN_PW_CPW);
            return;
        }
        if (Hoten.isEmpty() && !Email.isEmpty() && !Password.isEmpty() && !CPassword.isEmpty()){
            dangKyImpView.NhanKetQuaTuPresenter(DangKyFragment.ERROR_MSG_THIEU_HOTEN);
            return;
        }
        if(Hoten.isEmpty() && Email.isEmpty() && !Password.isEmpty() && !CPassword.isEmpty()){
            dangKyImpView.NhanKetQuaTuPresenter(DangKyFragment.ERROR_MSG_THIEU_EMAIL_HOTEN);
            return;
        }
        if (Hoten.isEmpty() && !Email.isEmpty() && Password.isEmpty() && !CPassword.isEmpty()){
            dangKyImpView.NhanKetQuaTuPresenter(DangKyFragment.ERROR_MSG_THIEU_HOTEN_PW);
            return;
        }
        if (Hoten.isEmpty() && !Email.isEmpty() && !Password.isEmpty() && CPassword.isEmpty()){
            dangKyImpView.NhanKetQuaTuPresenter(DangKyFragment.ERROR_MSG_THIEU_HOTEN_CPW);
            return;
        }
        if (!KiemTraDinhDangEmail(Email)) {
            dangKyImpView.NhanKetQuaTuPresenter(DangKyFragment.ERROR_MSG_SAI_DINH_DANG_EMAIL);
            return;
        }
        if (DemKyTu(Password)<6){
            dangKyImpView.NhanKetQuaTuPresenter(DangKyFragment.ERROR_MSG_PW_SAI_YEU_CAU);
            return;
        }
        if (Password.compareTo(CPassword)!=0){
            dangKyImpView.NhanKetQuaTuPresenter(DangKyFragment.ERROR_MSG_SAI_CPW);
            return;
        }
        String[] Ho_Ten=Hoten.split(" ");
        if (Ho_Ten.length>=2){
            dangKyImpModel.NhanTaiKhoan(new TaiKhoan(Email,Password,Ho_Ten[0],Ho_Ten[Ho_Ten.length-1]),activity);}
        else {
            dangKyImpModel.NhanTaiKhoan(new TaiKhoan(Email,Password,Ho_Ten[0],"null"),activity);
        }
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
