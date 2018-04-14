package com.eways.elearning.Presenter.TaiKhoan.DangNhap;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.eways.elearning.Model.DataModel.TaiKhoan.TaiKhoan;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Handler.LoginGmailHandler;
import com.eways.elearning.Model.TaiKhoan.BaseUserResponse;
import com.eways.elearning.Model.TaiKhoan.User;
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
import retrofit2.http.HTTP;
import retrofit2.http.POST;

/**
 * Created by ADMIN on 11/5/2017.
 */

public class DangNhapPresenter implements DangNhapPresenterImp {
    private DangNhapViewImp dangNhapImpView;
    private LoginGmailHandler gmailHandler;
    private SharedPreferencesHandler sharedPreferencesHandler;
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
    public void userData(final GoogleSignInAccount account, final Activity activity) {
        if (account!=null) {

            //GET data to server after logged in gmail success
            userServicesImp.login(account.getId()).enqueue(new Callback<BaseUserResponse>() {
                @Override
                public void onResponse(Call<BaseUserResponse> call, Response<BaseUserResponse> response) {
                    if (response.body().getErrorCode() == 200) {
                        sharedPreferencesHandler = new SharedPreferencesHandler(activity, SupportKeysList.SHARED_PREF_FILE_NAME);

                        //Check if user is already existed in database
                        if (response.body().getUser() != null) {
                            User user = response.body().getUser();
                            sharedPreferencesHandler.DangNhapThanhCong(user.getUid(), user.getEmail(), user.getLastName(), user.getFirstName(), user.getAvatar(), true);
                            dangNhapImpView.NhanKetQuaDN(DangNhapFragment.LOGIN_SUCCESS);
                        } else {
                            sharedPreferencesHandler.DangNhapThanhCong(account.getId(), account.getEmail(), account.getFamilyName(), account.getGivenName(), account.getPhotoUrl().toString(), true);
                        }
                    } else {
                        Toast.makeText(activity, "Login failed!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BaseUserResponse> call, Throwable t) {
                    Log.d("azz: " ,t.toString());
                    Toast.makeText(activity, "Login failed!", Toast.LENGTH_SHORT).show();
                }
            });
        } else dangNhapImpView.NhanKetQuaDN(DangNhapFragment.LOGIN_FAILED);
    }

    @Override
    public void KetQuaDangNhap(final String ketqua, final FirebaseUser user, final GoogleSignInAccount Guser, final Activity activity, final TaiKhoan taiKhoan) {
        if (ketqua.compareTo(DangNhapFragment.LOGIN_SUCCESS)==0){

        } else Toast.makeText(activity, "Login failed!", Toast.LENGTH_SHORT).show();
    }
}
