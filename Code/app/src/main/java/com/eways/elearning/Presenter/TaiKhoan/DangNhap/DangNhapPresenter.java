package com.eways.elearning.Presenter.TaiKhoan.DangNhap;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.eways.elearning.Model.DataModel.TaiKhoan.TaiKhoan;
import com.eways.elearning.Model.TaiKhoan.DangNhap.DangNhapImpModel;
import com.eways.elearning.Model.TaiKhoan.DangNhap.DangNhapModel;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Handler.LoginGmailHandler;
import com.eways.elearning.Network.UserServicesImp;
import com.eways.elearning.Util.ApiUtils;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapViewImp;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

/**
 * Created by ADMIN on 11/5/2017.
 */

public class DangNhapPresenter implements DangNhapPresenterImp {
    private DangNhapViewImp dangNhapImpView;
    private LoginGmailHandler gmailHandler;
    private SharedPreferencesHandler sharedPreferencesHandler;
    private DangNhapImpModel dangNhapImpModel=new DangNhapModel(this);
    private UserServicesImp userServicesImp;

    public DangNhapPresenter(Activity activity,DangNhapFragment fragment, DangNhapViewImp dangNhapImpView) {
        this.dangNhapImpView = dangNhapImpView;
        gmailHandler = new LoginGmailHandler(activity, fragment, this);
        userServicesImp = ApiUtils.loginService();
    }

    public void login(){
        gmailHandler.ConnectGmail();
        gmailHandler.signIn();
    }

    public void onGmailResult(int requestCode, int resultCode, Intent data) {
        gmailHandler.onResult(requestCode,resultCode,data);
    }
    @Override
    public void userData(GoogleSignInAccount account, Activity activity) {
        dangNhapImpModel.DangNhapGmail(account,activity);
    }

    @Override
    public void KetQuaDangNhap(final String ketqua, final FirebaseUser user, final GoogleSignInAccount Guser, final Activity activity, final TaiKhoan taiKhoan) {
        if (ketqua.compareTo(DangNhapFragment.LOGIN_SUCCESS)==0){
            if (Guser!=null) {
                userServicesImp.login(Guser.getId()).enqueue(new Callback<POST>() {
                    @Override
                    public void onResponse(Call<POST> call, Response<POST> response) {
                        if (response.body() != null) {
                            sharedPreferencesHandler=new SharedPreferencesHandler(activity,SupportKeysList.SHARED_PREF_FILE_NAME);
                            sharedPreferencesHandler.DangNhapThanhCong(Guser.getId(),Guser.getEmail(),Guser.getFamilyName(),Guser.getGivenName(),taiKhoan.getAvatar(),Guser.getDisplayName(),true,SupportKeysList.TAI_KHOAN_GMAIL,taiKhoan.getNghenghiep(),taiKhoan.getNamsinh(),taiKhoan.getGioitinh(),taiKhoan.getTailieuxacminh_mt(),taiKhoan.getTailieuxacminh_ms(),taiKhoan.getTrinhdo(),taiKhoan.getDiadiem(),taiKhoan.getSodienthoai(),taiKhoan.getDacapnhat(),taiKhoan.getRating(),taiKhoan.getTaiKhoanGiaSu());
                            dangNhapImpView.NhanKetQuaDN(ketqua);
                        }
                    }

                    @Override
                    public void onFailure(Call<POST> call, Throwable t) {
                        Log.d("azz: " ,t.toString());
                        Toast.makeText(activity, "Post failed", Toast.LENGTH_SHORT).show();
                    }
                });
            } else dangNhapImpView.NhanKetQuaDN(ketqua);
        } else Toast.makeText(activity, "Login failed!", Toast.LENGTH_SHORT).show();
    }
}
