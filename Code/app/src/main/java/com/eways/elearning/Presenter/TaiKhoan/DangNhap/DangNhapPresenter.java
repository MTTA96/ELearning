package com.eways.elearning.Presenter.TaiKhoan.DangNhap;

import android.accounts.AccountManager;
import android.app.Activity;

import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.Model.TaiKhoan.DangNhap.DangNhapImpModel;
import com.eways.elearning.Model.TaiKhoan.DangNhap.DangNhapModel;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapViewImp;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ADMIN on 11/5/2017.
 */

public class DangNhapPresenter implements DangNhapPresenterImp {

    private DangNhapViewImp dangNhapImpView;
    private SharedPreferencesHandler sharedPreferencesHandler;
    private DangNhapImpModel dangNhapImpModel=new DangNhapModel(this);
    public DangNhapPresenter(DangNhapViewImp dangNhapImpView) {
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

    @Override
    public void ChuyenTaiKhoanGmai(GoogleSignInAccount account, Activity activity) {
        dangNhapImpModel.DangNhapGmail(account,activity);
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
    public void KetQuaDangNhap(String ketqua, FirebaseUser user,GoogleSignInAccount Guser, Activity activity,TaiKhoan taiKhoan) {
        if (ketqua.compareTo(DangNhapFragment.LOGIN_SUCCESS)==0){
            if (user!=null){
                sharedPreferencesHandler=new SharedPreferencesHandler(activity, SupportKeysList.SHARED_PREF_FILE_NAME);
                sharedPreferencesHandler.DangNhapThanhCong(user.getUid(), user.getEmail(),taiKhoan.getHo(),taiKhoan.getTen(),taiKhoan.getAvarta(), user.getDisplayName(),true,SupportKeysList.TAI_KHOAN_THUONG,taiKhoan.getNghenghiep(),taiKhoan.getNamsinh(),taiKhoan.getGioitinh(),taiKhoan.getTailieuxacminh_mt(),taiKhoan.getTailieuxacminh_ms(),taiKhoan.getTrinhdo(),taiKhoan.getDiadiem(),taiKhoan.getSodienthoai(),taiKhoan.getDacapnhat());
                dangNhapImpView.NhanKetQuaDN(ketqua);
                return;
            }
            if (Guser!=null)
            {
                sharedPreferencesHandler=new SharedPreferencesHandler(activity,SupportKeysList.SHARED_PREF_FILE_NAME);
                sharedPreferencesHandler.DangNhapThanhCong(Guser.getId(),Guser.getEmail(),Guser.getFamilyName(),Guser.getGivenName(),taiKhoan.getAvarta(),Guser.getDisplayName(),true,SupportKeysList.TAI_KHOAN_GMAIL,taiKhoan.getNghenghiep(),taiKhoan.getNamsinh(),taiKhoan.getGioitinh(),taiKhoan.getTailieuxacminh_mt(),taiKhoan.getTailieuxacminh_ms(),taiKhoan.getTrinhdo(),taiKhoan.getDiadiem(),taiKhoan.getSodienthoai(),taiKhoan.getDacapnhat());
                dangNhapImpView.NhanKetQuaDN(ketqua);
                return;
            }
        }else
            dangNhapImpView.NhanKetQuaDN(ketqua);

    }
}
