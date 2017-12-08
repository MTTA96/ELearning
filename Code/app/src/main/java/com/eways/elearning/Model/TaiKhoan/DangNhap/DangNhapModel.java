package com.eways.elearning.Model.TaiKhoan.DangNhap;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.Presenter.TaiKhoan.DangNhap.DangNhapPresenterImp;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapFragment;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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

    public DangNhapModel(DangNhapPresenterImp dangNhapImpPresenter) {
        this.dangNhapImpPresenter = dangNhapImpPresenter;

    }

    @Override
    public void NhanTaiKhoanDN(final TaiKhoan taiKhoan, final Activity activity) {
        final FirebaseAuth mAuth;
        final FirebaseDatabase mDataNhanTaiKhoanDN;
        mDataNhanTaiKhoanDN=FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));
        mAuth=FirebaseAuth.getInstance(FirebaseApp.initializeApp(activity));
        mAuth.signInWithEmailAndPassword(taiKhoan.getEmail().toString(), taiKhoan.getPassword().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final FirebaseUser user=mAuth.getCurrentUser();
                    mDataNhanTaiKhoanDN.getReference().child("TaiKhoan").orderByKey().equalTo(user.getUid().toString()).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            dangNhapImpPresenter.KetQuaDangNhap(DangNhapFragment.LOGIN_SUCCESS,user,null,activity,dataSnapshot.getValue(TaiKhoan.class));
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

                } else
                    dangNhapImpPresenter.KetQuaDangNhap(DangNhapFragment.LOGIN_FAILED,null,null,activity,null);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("loidn", e.toString());
            }
        });
    }

    @Override
    public void DangNhapGmail(final GoogleSignInAccount account, final Activity activity) {
        final FirebaseDatabase mDataDangNhapGmail;
        mDataDangNhapGmail=FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));
        //kiếm và lấy child theo id
        mDataDangNhapGmail.getReference().child("TaiKhoan").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //nếu có lấy kqua truyen ra
                if (dataSnapshot.hasChild(account.getId())){
                    dangNhapImpPresenter.KetQuaDangNhap(DangNhapFragment.LOGIN_SUCCESS,null,account,activity,dataSnapshot.getValue(TaiKhoan.class));
                }
                //chua co thi set 1 nhanh theo id và lay ket qua tra ra
                else {
                    mDataDangNhapGmail.getReference().child("TaiKhoan").child(account.getId().trim().toString()).setValue(new TaiKhoan(account.getId(),account.getEmail(),account.getFamilyName(),account.getGivenName(),account.getDisplayName(),true, SupportKeysList.TAI_KHOAN_GMAIL,null,null,null,null,null,null,null,null,null));
                    mDataDangNhapGmail.getReference().child("TaiKhoan").orderByKey().equalTo(account.getId().toString()).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            dangNhapImpPresenter.KetQuaDangNhap(DangNhapFragment.LOGIN_SUCCESS,null,account,activity,dataSnapshot.getValue(TaiKhoan.class));
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
