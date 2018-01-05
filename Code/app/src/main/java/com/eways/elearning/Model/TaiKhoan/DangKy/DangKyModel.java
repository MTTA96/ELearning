package com.eways.elearning.Model.TaiKhoan.DangKy;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.eways.elearning.DataModel.TaiKhoan.TaiKhoan;
import com.eways.elearning.DataModel.TaiKhoan.TaiLieu.TaiLieuChuyenMon.TaiLieuChuyenMon;
import com.eways.elearning.Presenter.TaiKhoan.DangKy.DangKyPresenterImp;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.TaiKhoan.DangKy.DangKyFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

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

                            mData.getReference().child("TaiKhoan").child(user.getUid()).setValue(new TaiKhoan(user.getUid(),user.getEmail(),taiKhoan.getHo(),taiKhoan.getTen(),"null",false, SupportKeysList.TAI_KHOAN_GMAIL,taiKhoan.getPassword(),"null","null","null","null","null","null","null","null","null",false,"5" ));
                            mData.getReference().child("TaiLieuChuyenMon").child(user.getUid()).setValue(new TaiLieuChuyenMon(user.getUid(),null,null,null));
                        } else{
                            dangKyImpPresenter.KetQuaDangKy(DangKyFragment.SIGN_UP_FAILED);
                        }
                    }
                });
    }
}
