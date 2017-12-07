package com.eways.elearning.Model.TaiKhoan.DangKy;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.Presenter.TaiKhoan.DangKy.DangKyPresenterImp;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.TaiKhoan.DangKy.DangKyFragment;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Quang Tri on 27/10/2017.
 */

public class DangKyModel implements DangKyImpModel{
    DangKyPresenterImp dangKyImpPresenter;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mData;

    public DangKyModel(DangKyPresenterImp dangKyImpPresenter) {
        this.dangKyImpPresenter = dangKyImpPresenter;
    }

    @Override
    public void NhanTaiKhoan(final TaiKhoan taiKhoan, final Activity activity) {
        mAuth=FirebaseAuth.getInstance(FirebaseApp.initializeApp(activity));
        mData=FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));
        mAuth.createUserWithEmailAndPassword(taiKhoan.getEmail(), taiKhoan.getPassword())
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dangKyImpPresenter.KetQuaDangKy(DangKyFragment.SIGN_UP_SUCCESS);
                            final FirebaseUser user=mAuth.getCurrentUser();
                            mData.getReference().child("TaiKhoan").child(user.getUid()).setValue(new TaiKhoan(user.getUid(),user.getEmail(),taiKhoan.getHo(),taiKhoan.getTen(),user.getDisplayName(),false, SupportKeysList.TAI_KHOAN_GMAIL,taiKhoan.getPassword(),null,null,null,null,null,null,null,null));
                        } else{
                            dangKyImpPresenter.KetQuaDangKy(DangKyFragment.SIGN_UP_FAILED);
                        }
                    }
                });
    }
}
