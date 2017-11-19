package com.eways.elearning.Model.TaiKhoan.DangNhap;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.Presenter.TaiKhoan.DangNhap.DangNhapPresenterImp;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapFragment;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ADMIN on 11/5/2017.
 */

public class DangNhapModel implements DangNhapImpModel{
    DangNhapPresenterImp dangNhapImpPresenter;
    FirebaseDatabase mData;


    public DangNhapModel(DangNhapPresenterImp dangNhapImpPresenter) {
        this.dangNhapImpPresenter = dangNhapImpPresenter;
    }

    @Override
    public void NhanTaiKhoanDN(TaiKhoan taiKhoan, final Activity activity) {
        final FirebaseAuth mAuth;
        mAuth=FirebaseAuth.getInstance(FirebaseApp.initializeApp(activity));
        mAuth.signInWithEmailAndPassword(taiKhoan.getEmail().toString(), taiKhoan.getPassword().toString()).addOnCompleteListener(activity,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user=mAuth.getCurrentUser();
                    dangNhapImpPresenter.KetQuaDangNhap(DangNhapFragment.LOGIN_SUCCESS,user,null,activity);

                } else
                    dangNhapImpPresenter.KetQuaDangNhap(DangNhapFragment.LOGIN_FAILED,null,null,activity);

            }
        });
    }

    @Override
    public void DangNhapGmail(final GoogleSignInAccount account, Activity activity) {
        mData=FirebaseDatabase.getInstance();
        mData.getReference().child("TaiKhoan").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TaiKhoan taiKhoan=new TaiKhoan();
                if (taiKhoan!=null){
                    taiKhoan=dataSnapshot.getValue(TaiKhoan.class);
                    if (account.getId().compareTo(taiKhoan.getId())!=0){
                        mData.getReference().child("TaiKhoan").child(account.getId().toString()).setValue(new TaiKhoan(account.getId(),account.getEmail(),account.getFamilyName(),account.getGivenName(),account.getDisplayName(),false, SupportKeysList.TAI_KHOAN_GMAIL,null,null,null,null));
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
