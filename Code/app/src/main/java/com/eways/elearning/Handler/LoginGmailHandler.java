package com.eways.elearning.Handler;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapViewImp;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;


/**
 * Created by ADMIN on 11/10/2017.
 */

public class LoginGmailHandler  {
    private Activity activity;
    private int requestCode;
    private int resultCode;
    private DangNhapFragment dangNhapFragment;
    private Intent data;
    private SharedPreferencesHandler sharedPreferencesHandler;
    private DangNhapViewImp dangNhapImpView;
    private static final int RC_SIGN_IN=1;
    private GoogleApiClient mGoogleApiClient;
    private String TAG="MAIN_ACTIVITY";

    public LoginGmailHandler(Activity activity, DangNhapFragment dangNhapFragment, DangNhapViewImp dangNhapImpView) {
        this.activity = activity;
        this.dangNhapFragment = dangNhapFragment;
        this.dangNhapImpView = dangNhapImpView;
    }

    public void ConnectGmail(){
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken("0x7f0d006a")
                .requestEmail()
                .build();
        mGoogleApiClient=new GoogleApiClient.Builder(activity)
                .enableAutoManage((FragmentActivity) activity, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.d(TAG, "onConnectionFailed:Error");

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }
    public void signIn() {
        if (requestCode == 0){
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            dangNhapFragment.startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    public void onResult(int requestCode, int resultCode, Intent data) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                sharedPreferencesHandler=new SharedPreferencesHandler(activity, SupportKeysList.SHARED_PREF_FILE_NAME);
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                sharedPreferencesHandler.DangNhapThanhCong(account.getId(),account.getEmail(),account.getFamilyName(),account.getGivenName(),account.getDisplayName(),true,SupportKeysList.TAI_KHOAN_GMAIL);
                dangNhapImpView.NhanKetQuaDN(DangNhapFragment.LOGIN_SUCCESS);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
        this.requestCode=requestCode;
        this.data=data;
        this.resultCode=resultCode;
    }

}
