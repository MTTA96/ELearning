package com.eways.elearning.Handler;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.eways.elearning.Model.DangNhap.DangNhapImpModel;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Presenter.DangKy.DangNhap.DangNhapImpPresenter;
import com.eways.elearning.Presenter.DangKy.DangNhap.DangNhapPresenter;
import com.eways.elearning.R;
import com.eways.elearning.View.Fragment.Home.HomeFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.DangKy.DangKyFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapImpView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


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
<<<<<<< HEAD
    private DangNhapImpView dangNhapImpView;
=======
    private static final int RC_SIGN_IN=1;
    private GoogleApiClient mGoogleApiClient;
    private String TAG="MAIN_ACTIVITY";
>>>>>>> f0d23f4a7324eae0385e2399ada4039f80d96409

    public LoginGmailHandler(Activity activity, DangNhapFragment dangNhapFragment, DangNhapImpView dangNhapImpView) {
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
                sharedPreferencesHandler=new SharedPreferencesHandler(activity,"TrangThaiDangNhap");
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                sharedPreferencesHandler.DangNhapThanhCong(account.getId(),account.getEmail(),account.getFamilyName(),account.getGivenName(),account.getDisplayName(),true,"Gmail");
                dangNhapImpView.NhanKetQuaDN("thanhcong");
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
